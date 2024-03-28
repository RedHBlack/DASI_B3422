/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Demande;
import metier.modele.Eleve;
import metier.modele.Intervenant;

/**
 *
 * @author bvilleroy
 */
public class DemandeDao {
    
    public static void ajouterDemande(Demande d){
        //Inscription de la demande dans la table
        JpaUtil.obtenirContextePersistance().persist(d);
    }
    public static void supprimerDemande(Demande d){
        //Suppression de la demande de la base
        JpaUtil.obtenirContextePersistance().remove(d);
    }
    public static Demande modifierDemande(Demande d){
        //Modification du client dans la base
        d=JpaUtil.obtenirContextePersistance().merge(d);
        return d;
    }
    public static Demande obtenirDemandeParId(Long id){
        return JpaUtil.obtenirContextePersistance().find(Demande.class, id);
    }
    

    public static Demande obtenirDernièreDemandeParEleve(Eleve e){
        String jpql = "select d from Demande d where d.eleve = :unEleve order by d.date asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("unEleve", e);
        
        return (Demande)query.getResultList().get(0);
    }

    public static List<Demande> obtenirDemandesParEleve(Eleve e){
        String jpql = "select d from Demande d where d.eleve = :unEleve";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("unEleve", e);
        
        return query.getResultList();
    }
    
    public static List<Demande> obtenirDemandesParIntervenant(Intervenant i){
        String jpql = "select d from Demande d where d.intervenant = :unIntervenant";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        query.setParameter("unIntervenant", i);
        
        return query.getResultList();
    }
    
    public static List<Demande> obtenirToutesDemandes(){
        String jpql = "select d from Demande d order by d.date";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Demande.class);
        return query.getResultList();
    }
}
