/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import metier.modele.*;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Intervenant.Statut;

/**
 *
 * @author bvilleroy
 */

public class IntervenantDao{
    
    public static List<Intervenant> obtenirIntervenantsParStatut(Statut statut){
        String jpql = "select i from Intervenant i where i.statut = :unStatut order by i.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        query.setParameter("unStatut",statut);
        
        return query.getResultList();
    }
    
    public static List<Intervenant> obtenirIntervenants(){
        String jpql = "select i from Intervenant i order by i.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        
        return query.getResultList();
    }
    
}
