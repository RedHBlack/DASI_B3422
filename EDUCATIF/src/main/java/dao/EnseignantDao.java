/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import metier.modele.*;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author bvilleroy
 */
public class EnseignantDao{
    
    
    public static void ajouterEnseignant(Enseignant e){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    public static void supprimerEnseignant(Enseignant e){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(e);
    }
    public static Enseignant modifierEnseignant(Enseignant e){
        //Modification du client dans la base
        e=JpaUtil.obtenirContextePersistance().merge(e);
        return e;
    }
    public static Enseignant obtenirEnseignantParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Enseignant.class, id);
    }
    
    public static Personne obtenirEnseigantParEmail(String email){
        String jpql = "select e from Enseignant e where e.email = :unEmail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Enseignant.class);
        query.setParameter("unEmail", email);
        Enseignant e = (Enseignant) query.getSingleResult();
        
        return JpaUtil.obtenirContextePersistance().find(Personne.class, e.getId());
    }
    
    public static List<Enseignant> obtenirEnseignantParTypeEtablissementExercice(String type){
        String jpql = "select e from Enseignant e where e.typeEtablissementExercice = :unType order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Enseignant.class);
        query.setParameter("unType", type);
        
        return query.getResultList();
    }
    
    public static List<Enseignant> obtenirEnseignantsParStatut(Intervenant.Statut statut){
        String jpql = "select e from Enseignant e where e.statut = :unStatut order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Enseignant.class);
        query.setParameter("unStatut",statut);
        
        return query.getResultList();
    }
    
    public static List<Enseignant> obtenirEnseignants(){
        String jpql = "select e from Enseignants e order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Enseignant.class);
        
        return query.getResultList();
    }
    
}
