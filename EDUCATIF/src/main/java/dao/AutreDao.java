/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Autre;
import metier.modele.Intervenant;
import metier.modele.Personne;

/**
 *
 * @author bvilleroy
 */
public class AutreDao{
    
    public static void ajouterAutre(Autre a){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(a);
    }
    public static void supprimerAutre(Autre a){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(a);
    }
    public static Autre modifierPersonne(Autre a){
        //Modification du client dans la base
        a=JpaUtil.obtenirContextePersistance().merge(a);
        return a;
    }
    public static Personne obtenirAutreParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Personne.class, id);
    }
    
    public static Autre obtenirAutreParEmail(String email){
        String jpql = "select a from Autre a where a.email = :unEmail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Autre.class);
        query.setParameter("unEmail", email);
        Personne a = (Personne) query.getSingleResult();
        
        return JpaUtil.obtenirContextePersistance().find(Autre.class, a.getId());
    }
    
    public static List<Autre> obtenirAutresParStatut(Intervenant.Statut statut){
        String jpql = "select a from Autre a where a.statut = :unStatut order by a.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Autre.class);
        query.setParameter("unStatut",statut);
        
        return query.getResultList();
    }
    
    public static List<Autre> obtenirAutres(){
        String jpql = "select a from Autres a order by a.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Autre.class);
        
        return query.getResultList();
    }
}
