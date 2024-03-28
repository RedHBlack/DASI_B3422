/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.util.Date;
import java.util.List;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.service.ServiceInstructif;
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
        ServiceInstructif service = new ServiceInstructif();
        JpaUtil.desactiverLog();
        JpaUtil.creerFabriquePersistance();
        
        //Etablissement etab = new Etablissement("0691664J","COLLEGE JEAN JAURES","public",69266,"VILLEURBANNE",69,"RHONE","LYON",84.5,new ArrayList<Eleve>());
        Eleve eleve = new Eleve(new Date(2001,11,22), 5, "VILLEROY", "Billy", "motDePasse", "billy.villeroy@insa-lyon.fr");
        
        Eleve e2 = new Eleve(new Date(2001,11,22), 5, "Pas VILLEROY", "Pas Billy", "mdp", "pasbilly.villeroy@insa-lyon.fr");
        
        
        //System.out.println(eleve.getDemandes().get(0).toString());
        
        //etab.addEleve(eleve);
        try{
            service.inscrireEleve(eleve, "0691664J");
            service.inscrireEleve(e2, "0691664N");
            {
                eleve = service.authentifierEleve("billy.villeroy@insa-lyon.fr","motDePasse");
                Demande d1 = new Demande("ceci est une description 1 de demande");
                Demande d2 = new Demande("ceci est une description 2 de demande");

                service.creerDemande(d1,eleve);

                /*for (int i=0; i<100000 ; i++){
                    System.out.println(i);
                }*/

                service.creerDemande(d2,eleve);
                //System.out.println(eleve.getId());

                List<Demande> l = service.consulterListeDemandesEleve(eleve);

                System.out.println("Liste des Demandes");
                for (int i=0; i<l.size() ; i++){
                   System.out.println(l.get(i)); 
                }
                eleve.getDemandes().clear();
            }
            eleve = service.authentifierEleve("billy.villeroy@insa-lyon.fr","motDePasse");
            System.out.println(eleve.getEtablissement().toString());
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