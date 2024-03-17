/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author bvilleroy
 */
@Entity
public class Etudiant extends Intervenant{
    
    @Column(nullable=false)
    protected String universite;
    
    @Column(nullable=false)
    protected String specialite;

    public Etudiant() {
    }

    public Etudiant(String universite, String specialite, String numeroDeTelephone, List<Integer> niveauxCouvert, String nom, String prenom, String motDePasse, String email) {
        super(numeroDeTelephone, niveauxCouvert, nom, prenom, motDePasse, email);
        this.universite = universite;
        this.specialite = specialite;
    }

    public String getUniversite() {
        return universite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
}
