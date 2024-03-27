/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import metier.modele.*;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author bvilleroy
 */
public class EleveDao{
    
    
    public static void ajouterEleve(Eleve e){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    public static void supprimerEleve(Eleve e){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(e);
    }
    public static Eleve modifierEleve(Eleve e){
        //Modification du client dans la base
        e=JpaUtil.obtenirContextePersistance().merge(e);
        return e;
    }
    public static Eleve obtenirEleveParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, id);
    }
    
    public static Eleve obtenirEleveParEmail(String email){
        String jpql = "select e from Eleve e where e.email = :unEmail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Eleve.class);
        query.setParameter("unEmail", email);
        Eleve e = (Eleve) query.getSingleResult();
        
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, e.getId());
    }
    
    public static List<Eleve> obtenirElevesParEtablissement(Etablissement e){
        String jpql = "select e from Eleve e where e.etablissement = :unEtablissement order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Eleve.class);
        query.setParameter("unEtablissement", e);
        
        return query.getResultList();
    }
    
    public static List<Eleve> obtenirEleves(){
        String jpql = "select e from Eleves e order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Eleve.class);
        
        return query.getResultList();
    }
    
    
    
}