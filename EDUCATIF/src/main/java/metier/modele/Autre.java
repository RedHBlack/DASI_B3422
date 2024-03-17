/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author bvilleroy
 */
@Entity
public class Autre extends Intervenant{
    
    protected String activite;

    public Autre() {
    }

    public Autre(String activite, String numeroDeTelephone, List<Integer> niveauxCouverts, String nom, String prenom, String motDePasse, String email) {
        super(numeroDeTelephone, niveauxCouverts, nom, prenom, motDePasse, email);
        this.activite = activite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
    
    
}
