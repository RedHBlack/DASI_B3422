/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.DemandeDao;
import dao.EleveDao;
import dao.EtablissementDao;
import dao.JpaUtil;
import java.util.List;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import util.EducNetApi;
import static util.Message.envoyerMail;

/**
 *
 * @author bvilleroy
 */
public class ServiceAccueilEleve {
    
    public boolean inscrireEleve(Eleve eleve, String codeEtablissement){
        
        EducNetApi ed= new EducNetApi();
        int niveau=eleve.getClasse();
        Etablissement etab;
        List<String> infoEtab;
     
        boolean test = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            System.out.println("Trace : debut " );
            if(niveau > 2) {
                infoEtab = ed.getInformationCollege(codeEtablissement);
                System.out.println("Trace : if niveau >2 " );
            }
            else {
                infoEtab = ed.getInformationLycee(codeEtablissement);
                System.out.println("Trace : else niveau >2 " );
            }
            
            etab = EtablissementDao.obtenirEtablissementParCode(codeEtablissement);
            System.out.println("Trace : avant if etab " + etab );
            
            // si l'élève n'existe pas, on l'ajoute
            if(etab == null){
                System.out.println("Trace : dans if etab " );
                etab = new Etablissement(infoEtab.get(0),infoEtab.get(1),infoEtab.get(2),Integer.parseInt(infoEtab.get(3)),infoEtab.get(4),Integer.parseInt(infoEtab.get(5)),infoEtab.get(6),infoEtab.get(7),Double.parseDouble(infoEtab.get(8)));

                EtablissementDao.ajouterEtablissement(etab); //retourne un bool
                etab = EtablissementDao.obtenirEtablissementParCode(codeEtablissement); //retourne un etab
                System.out.println("Trace : fin if etab " + etab);
            }
            
            System.out.println("Trace : après if etab " );
            
            eleve.setEtablissement(etab);
            EleveDao.ajouterEleve(eleve);
            JpaUtil.validerTransaction(); 

            test = true;
            envoyerMail("moi", eleve.getEmail(), "inscription", "réussie");
           // System.out.println("Trace : succès recruter " + client);
        }
        catch (Exception ex) { // ça n'a pas marché
                 JpaUtil.annulerTransaction(); // ne pas oublier d'annuler la transaction !
                // todo : envoyer le mail d'infirmation
                // on pourrait aussi lancer une exception
                System.out.println("Trace : salut " + eleve);
                envoyerMail("moi", eleve.getEmail(), "inscription", "problème");
                test = false;
        }
        finally { // dans tous les cas, on ferme l'entity manager
                JpaUtil.fermerContextePersistance();
        }
        
        return test;
    }
    
    public Eleve authentifierEleve(String motDePasse, String mail){
         
         Eleve insc = null;
         
         JpaUtil.creerContextePersistance();
         
         Eleve c0 = EleveDao.obtenirEleveParEmail(mail);
         
         JpaUtil.fermerContextePersistance();
         
         if (c0 != null && c0.getMotDePasse().equals(motDePasse))
         {
             insc = c0;
             //trace
             System.out.println("connexion reussie");
         }
         
         return insc;      
     }
    
    //Initialisation
    public List<Demande> consulterListeDemandesEleve(Eleve e){
        
        JpaUtil.creerContextePersistance();
         
         List<Demande> liste_d = DemandeDao.obtenirDemandesParEleve(e);
         
         JpaUtil.fermerContextePersistance();
         
        return liste_d;
        
    }
    
    public Demande consulterDetailDemande (Long idDemande) {
        
        return DemandeDao.obtenirDemandeParId(idDemande);
    }
    
    //Acceuil - Elève
    
    public boolean creerDemande(Demande d){
     
        boolean test = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            d.getEleve().ajouterDemande(d);
            DemandeDao.ajouterDemande(d);
            
            JpaUtil.validerTransaction(); 

            test = true;
            
           System.out.println("Trace : succès creation demande " + d);
        }
        catch (Exception ex) { // ça n'a pas marché
                 JpaUtil.annulerTransaction(); // ne pas oublier d'annuler la transaction !
                
                System.out.println("Trace : échec demande " + d);
        }
        finally { // dans tous les cas, on ferme l'entity manager
                JpaUtil.fermerContextePersistance();
        }
        
        return test;
    }
}
