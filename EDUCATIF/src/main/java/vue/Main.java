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
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import metier.modele.Personne;
import metier.service.ServiceEducatif;
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
        ServiceEducatif service = new ServiceEducatif();
        JpaUtil.creerFabriquePersistance();
        Etablissement etab = new Etablissement("0691664J","COLLEGE JEAN JAURES","public",69266,"VILLEURBANNE",69,"RHONE","LYON",84.5,new ArrayList<Eleve>());
        Eleve eleve = new Eleve(new Date(2001,11,22), 0, "VILLEROY", "Billy", "motDePasse", "billy.villeroy@insa-lyon.fr");
        etab.addEleve(eleve);
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            EtablissementDao.ajouterEtablissement(etab);
            System.out.println("Ajout réussi");
            JpaUtil.validerTransaction();
        }catch(Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }
        JpaUtil.fermerContextePersistance();
        JpaUtil.fermerFabriquePersistance();
    }
}