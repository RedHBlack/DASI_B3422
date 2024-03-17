/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.TypedQuery;
import metier.modele.Personne;

/**
 *
 * @author bvilleroy
 */
public class PersonneDao {
    
    public static void ajouterPersonne(Personne p){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(p);
    }
    public static void supprimerPersonne(Personne p){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(p);
    }
    public static Personne modifierPersonne(Personne p){
        //Modification du client dans la base
        p=JpaUtil.obtenirContextePersistance().merge(p);
        return p;
    }
    public static Personne obtenirPersonneParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Personne.class, id);
    }
    
    public static Personne obtenirPersonneParEmail(String email){
        String jpql = "select p from Personne p where p.email = :unEmail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Personne.class);
        query.setParameter("unEmail", email);
        Personne p = (Personne) query.getSingleResult();
        
        return JpaUtil.obtenirContextePersistance().find(Personne.class, p.getId());
    }
}
