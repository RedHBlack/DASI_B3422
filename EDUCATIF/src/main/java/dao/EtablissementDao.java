/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Etablissement;

/**
 *
 * @author bvilleroy
 */
public class EtablissementDao {
    
    public static void ajouterEtablissement(Etablissement e){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    public static void supprimerDemande(Etablissement e){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(e);
    }
    public static Etablissement modifierDemande(Etablissement e){
        //Modification du client dans la base
        e=JpaUtil.obtenirContextePersistance().merge(e);
        return e;
    }
    public static Etablissement obtenirEtablissementParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Etablissement.class, id);
    }
    
    public static Etablissement obtenirEtablissementParCode(String code){
        String jpql = "select e from Etablissement e where e.code = :unCode";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("unCode", code);
        Etablissement e = null;
        try{
             e = (Etablissement)(query.getSingleResult());
             e = JpaUtil.obtenirContextePersistance().find(Etablissement.class, e.getId());
        }catch(Exception ex){
            e = null;
        }finally{
            return e;
        }
        
        
    }
    
    public static Etablissement obtenirEtablissementParNom(String nom){
        String jpql = "select e from Etablissement e where e.nom = :unNom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("unNom", nom);
        Etablissement e= (Etablissement)(query.getSingleResult());
        return JpaUtil.obtenirContextePersistance().find(Etablissement.class, e.getId());
    }
    
    public static List<Etablissement> obtenirEtablissementsParDepartement(int departement){
        String jpql = "select e from Etablissement e where e.codeDepartement = :unDepartement order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("unDepartement", departement);
        
        return query.getResultList();
    }
    
    public static List<Etablissement> obtenirEtablissementsParAcademie(String academie){
        String jpql = "select e from Etablissement e where e.academie = :uneAcademie order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("unAcademie", academie);
        
        return query.getResultList();
    }
    
    public static List<Etablissement> obtenirEtablissementsParSecteur(String secteur){
        String jpql = "select e from Etablissement e where e.secteur = :unSecteur order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("unSecteur", secteur);
        
        return query.getResultList();
    }
    
    public static List<Etablissement> obtenirEtablissementsParCommune(int codePostal){
        String jpql = "select e from Etablissement e where e.codeCPostal = :uneCommune order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        query.setParameter("uneCommune", codePostal);
        
        return query.getResultList();
    }
    
    public static List<Etablissement> obtenirTousEtablissements(){
        String jpql = "select e from Etablissement e order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etablissement.class);
        return query.getResultList();
    }
}
