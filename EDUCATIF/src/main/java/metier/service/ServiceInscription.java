package metier.service;

import com.google.maps.model.LatLng;
import dao.*;
import java.util.ArrayList;
import java.util.List;
import metier.modele.*;
import util.*;
import static util.Message.envoyerMail;

/**
 *
 * @author ncatherine
 */
public class ServiceInscription {
     
     public boolean inscrireEleve(Eleve eleve){
     
     boolean test = false;
            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();

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
            return test;}
     
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
     
     
    
     
     /*
     public Eleve ajoutInteractif(){
         Eleve e = new Eleve();
         List<Integer> valeursPossibles =new ArrayList<Integer>();
         for(int i = 0;i<=6;i++){
             valeursPossibles.add(i);
         }
         
         String nom = Saisie.lireChaine("Nom ?");
         String prenom = Saisie.lireChaine("Prenom ?");
         String motdepasse = Saisie.lireChaine("Mdp ?");
         String mail = Saisie.lireChaine("EMail ?");
         int classe = Saisie.lireInteger("Niveau ?", valeursPossibles);
         String date = Saisie.lireChaine("Niveau ?");
         c.setNom(nom);
         c.setPrenom(prenom);
         c.setAdressepostale(adressepostale);
         c.setMail(mail);
         c.setMotdepasse(motdepasse);
        

         return c;
     }*/
     
     
     /*public Long rechercheInteractif() {
         Integer idd = Saisie.lireInteger("ID ?");
         Long id = (long) idd;
          return id;
         
     }
     
     
     public Client authentifierInteractif() {
         Saisie s = new Saisie();
         
         Client c = new Client();
         String mail = Saisie.lireChaine("Mail ?");
         String mdp = Saisie.lireChaine("Mdp ?");
         
         c.setMail(mail);
         c.setMotdepasse(mdp);
         
         return c;
         
     }*/
     
    public ServiceInscription() {
    }
}
