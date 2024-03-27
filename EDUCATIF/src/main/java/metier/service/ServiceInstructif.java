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
public class ServiceInstructif {
    
    /**
     * Le service d'inscription d'un élève dans l'application. 
     * 
     * @param eleve L'élève à inscrire dans la base de données du site
     * @param codeEtablissement Le code de l'établissement de l'élève
     * @return True si l'inscription a eu lieu, False sinon
     *
     * @see EducNetApi#getInformationCollege(String)
     * @see EducNetApi#getInformationLycee(String)
     * @see EleveDao#ajouterEleve(Eleve)
     * @see EtablissementDao#ajouterEtablissement(Etablissement)
     */
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
    

    /**
     * Le service d'authentification d'un élève dans l'application. 
     * 
     * @param motDePasse    le mot de passe à tester
     * @param mail          le mail à tester
     * 
     * @return Si l'authentification a eu lieu l'élève correspondant, null sinon

     */
    public Eleve authentifierEleve(String motDePasse, String mail){
         
         Eleve insc = null;
         
         JpaUtil.creerContextePersistance();
         
         insc = EleveDao.obtenirEleveParEmail(mail);
         
         JpaUtil.fermerContextePersistance();
         
         if (insc != null && insc.getMotDePasse().equals(motDePasse))
         {
            //On initialise la liste des demandes de l'élève avec ses demandes en mémoire 
            insc.setDemandes(DemandeDao.obtenirDemandesParEleve(insc));
             
            //trace
             System.out.println("connexion reussie");
         }
         
         return insc;      
     }
    
    /**
     * Le service de récupération des demandes d'un élève 
     * 
     * @param e         L'élève dont il faut récupérer les demandes
     * 
     * @return          La liste des demandes (si elle n'est pas vide), null sinon

     */
    public List<Demande> consulterListeDemandesEleve(Eleve e){
    
        return e.getDemandes();

    }
    
    /**
     * Le service de récupération des détails d'une demande
     * 
     * @param idDemande         L'id de la demande à récupérer
     * 
     * @return                  La demande

     */
    public Demande consulterDetailDemande (Long idDemande) {
        
        Demande d=null;
        
        try {
            JpaUtil.creerContextePersistance();
         
            d = DemandeDao.obtenirDemandeParId(idDemande);
            
            JpaUtil.fermerContextePersistance();
        } catch (Exception e) {
            d=null;
        }
        
        return d;
    }
    
    //Acceuil - Elève
    
    /**
     * Crée une demande associée à un élève dans la base de données
     * 
     * @param d     La demande à inscrire dans la base de données
     * @return      True si la création a eu lieu, False sinon. 
     */
    public boolean creerDemande(Demande d){
     
        boolean test = false;

        try {

            //Transaction
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            //Ajout de la demande à la liste de demandes de l'élève
            d.getEleve().ajouterDemande(d);
            DemandeDao.ajouterDemande(d);

            JpaUtil.validerTransaction(); 

            //Succès lors de la création
            test = true;
            
           System.out.println("Trace : succès creation demande " + d);
        }
        catch (Exception ex) {
                
                //Annulation de la transaction
                 JpaUtil.annulerTransaction();
                
                System.out.println("Trace : échec demande " + d);
        }
        finally { // dans tous les cas, on ferme l'entity manager
                JpaUtil.fermerContextePersistance();
        }
        
        return test;
    }
}
