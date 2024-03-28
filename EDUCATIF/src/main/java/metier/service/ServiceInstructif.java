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

import java.util.Date;
import java.util.List;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import util.EducNetApi;

import static metier.modele.Demande.Etat.ACCEPTEE;
import metier.modele.Intervenant;
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
            
            if(niveau > 2) {
                infoEtab = ed.getInformationCollege(codeEtablissement);
            }
            else {
                infoEtab = ed.getInformationLycee(codeEtablissement);
            }
            
            etab = EtablissementDao.obtenirEtablissementParCode(codeEtablissement);
            
            // si l'établissement n'existe pas, on l'ajoute
            if(etab == null){
                etab = new Etablissement(infoEtab.get(0),infoEtab.get(1),infoEtab.get(2),Integer.parseInt(infoEtab.get(3)),infoEtab.get(4),Integer.parseInt(infoEtab.get(5)),infoEtab.get(6),infoEtab.get(7),Double.parseDouble(infoEtab.get(8)));

                EtablissementDao.ajouterEtablissement(etab); //retourne un bool
                etab = EtablissementDao.obtenirEtablissementParCode(codeEtablissement); //retourne un etab
            }
                        
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
    public Eleve authentifierEleve(String mail,String motDePasse){
         
         Eleve insc = null;
         
         try{
            JpaUtil.creerContextePersistance();

            insc = EleveDao.obtenirEleveParEmail(mail);
            
            if (insc.getMotDePasse().equals(motDePasse))
            {
               //On initialise la liste des demandes de l'élève avec ses demandes en mémoire 
               insc.setDemandes(DemandeDao.obtenirDemandesParEleve(insc));
               insc.getEtablissement().setEleves(EleveDao.obtenirElevesParEtablissement(insc.getEtablissement()));

               //trace
                System.out.println("connexion reussie");
            }
         }catch(Exception e){
             insc=null;
         }finally{
             JpaUtil.fermerContextePersistance();
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
            
        } catch (Exception e) {
            d=null;
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        
        return d;
    }
    
    //Acceuil - Elève
    
    /**
     * Crée une demande associée à un élève dans la base de données
     * 
     * @param d     La demande à inscrire dans la base de données
     * @param e     L'élève à qui la demande doit être attribuée
     * @return      True si la création a eu lieu, False sinon. 
     */
    public boolean creerDemande(Demande d, Eleve e){
     
        boolean test = false;

        try {

            //Transaction
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            //Initialisation de l'élève de la demande
            d.setEleve(e);

            //Statut de la demande
            d.setStatut(ACCEPTEE);

            //Date actuelle
            d.setDate(new Date());

            //Ajout de la demande dans la BDD
            DemandeDao.ajouterDemande(d);
            //Récupération de la demande avec l'id initialisé
            d = DemandeDao.obtenirDerniereDemandeParEleve(e);
            //Ajout de la demande dans la liste de l'élève
            e.ajouterDemande(d);
            //Validation de la transaction
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
    
    public Eleve ouvrirProfil(Long idEleve){
        
        Eleve eleve=null;
        
        try{
            JpaUtil.creerContextePersistance();
            eleve = EleveDao.obtenirEleveParId(idEleve);
        }catch(Exception ex){
            eleve=null;
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        
        return eleve;
    }
    
    public List<Etablissement> consulterListeEtablissements(){
        List<Etablissement> etablissements=null;
        
        try{
            JpaUtil.creerContextePersistance();
            etablissements = EtablissementDao.obtenirTousEtablissements();
        }catch(Exception ex){
            etablissements=null;
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        
        return etablissements;
    }
    
    public Etablissement consulterDetailEtablissement(Long idEtablissement){
        
        Etablissement etablissement=null;
        
        try{
            JpaUtil.creerContextePersistance();
            etablissement = EtablissementDao.obtenirEtablissementParId(idEtablissement);
        }catch(Exception ex){
            etablissement=null;
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        
        return etablissement;
    }
    
     public List<Demande> consulterListeInterventionsIntervenant(Intervenant i){
    
        return i.getInterventions();

    }
     
     public Demande verifierDemandeActuelle(Intervenant i){
         Demande d;
         try{
            JpaUtil.creerContextePersistance();
            d = DemandeDao.obtenirDerniereInterventionParIntervenant(i);
            if(d.getBilan()!=null)
                d=null;            
         }catch(Exception e){
             d=null;
         }finally{
             JpaUtil.fermerContextePersistance();
         }
         
         return d;
     }

}
