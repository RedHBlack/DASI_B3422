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
public class EtudiantDao{
    
    public static void ajouterEtudiant(Etudiant e){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    public static void supprimerEtudiant(Etudiant e){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(e);
    }
    public static Personne modifierEtudiant(Etudiant e){
        //Modification du client dans la base
        e=JpaUtil.obtenirContextePersistance().merge(e);
        return e;
    }
    public static Personne obtenirEtudiantParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Etudiant.class, id);
    }
    
    public static Personne obtenirEtudiantParEmail(String email){
        String jpql = "select e from Etudiant e where e.email = :unEmail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etudiant.class);
        query.setParameter("unEmail", email);
        Etudiant e = (Etudiant) query.getSingleResult();
        
        return JpaUtil.obtenirContextePersistance().find(Personne.class, e.getId());
    }
    
    public static List<Etudiant> obtenirEtudiantstParUniversite(String universite){
        String jpql = "select e from Etudiant e where e.typeEtablissementExercice = :uneUniversite order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etudiant.class);
        query.setParameter("uneUniversite", universite);
        
        return query.getResultList();
    }
    
    public static List<Etudiant> obtenirEtudiantsParStatut(Intervenant.Statut statut){
        String jpql = "select e from Etudiant e where e.statut = :unStatut order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etudiant.class);
        query.setParameter("unStatut",statut);
        
        return query.getResultList();
    }
    
    public static List<Etudiant> obtenirEtudiants(){
        String jpql = "select e from Eudiant e order by e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Etudiant.class);
        
        return query.getResultList();
    }
    
}
