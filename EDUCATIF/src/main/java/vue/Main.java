/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.EleveDao;
import dao.EtablissementDao;
import dao.JpaUtil;
import dao.PersonneDao;
import static java.lang.System.console;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.modele.Demande;
import static metier.modele.Demande.Etat.*;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import metier.modele.Personne;
import metier.service.ServiceAccueilEleve;
import metier.service.ServiceInscription;
/**
 *
 * @author bvilleroy
 */
public class Main{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        ServiceAccueilEleve service = new ServiceAccueilEleve();
        JpaUtil.creerFabriquePersistance();
        
        //Etablissement etab = new Etablissement("0691664J","COLLEGE JEAN JAURES","public",69266,"VILLEURBANNE",69,"RHONE","LYON",84.5,new ArrayList<Eleve>());
        Eleve eleve = new Eleve(new Date(2001,11,22), 5, "VILLEROY", "Billy", "motDePasse", "billy.villeroy@insa-lyon.fr");
        
         Eleve e2 = new Eleve(new Date(2001,11,22), 5, "Pas VILLEROY", "Pas Billy", "mdp", "pasbilly.villeroy@insa-lyon.fr");
        
        
        //System.out.println(eleve.getDemandes().get(0).toString());
        
        //etab.addEleve(eleve);
        try{
            service.inscrireEleve(eleve, "0691664J");
            service.inscrireEleve(e2, "0691664N");
            eleve = service.authentifierEleve("motDePasse", "billy.villeroy@insa-lyon.fr");
            Demande d1 = new Demande(eleve, new Date (2024, 02, 22), 33, "ceci est une description 1 de demande", "ceci est une autoevaluation 1", "ceci est un bilan 1", ACCEPTEE );
            Demande d2 = new Demande(eleve, new Date (2028, 02, 22), 33, "ceci est une description 2 de demande", "ceci est une autoevaluation 2", "ceci est un bilan 1", ANNULEE );
        
            service.creerDemande(d2);
            service.creerDemande(d1);
            
            //System.out.println(eleve.getId());
            
            System.out.println("Liste des Demandes");
            List<Demande> l = service.consulterListeDemandesEleve(eleve);
            for (int i=0; i<2 ; i++){
               System.out.println(l.get(i)); 
            }
            
             
            /*System.out.println("Liste des Demandes");
            service.consulterListeDemandesEleve(eleve);
            System.out.println("Detail Demande 1");
            service.consulterDetailDemande(d1.getId());*/
                   

        }catch(Exception e){
            
            e.printStackTrace();
        }
        
        JpaUtil.fermerFabriquePersistance();
    }
}