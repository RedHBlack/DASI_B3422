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
public class Enseignant extends Intervenant{
    
    @Column(nullable=false)
    protected String typeEtabissementExercice; 

    public Enseignant() {
    }

    public Enseignant(String typeEtabissementExercice, String numeroDeTelephone, List<Integer> niveauxCouverts, String nom, String prenom, String motDePasse, String email) {
        super(numeroDeTelephone, niveauxCouverts, nom, prenom, motDePasse, email);
        this.typeEtabissementExercice = typeEtabissementExercice;
    }

    public String getTypeEtabissementExercice() {
        return typeEtabissementExercice;
    }

    public void setTypeEtabissementExercice(String typeEtabissementExercice) {
        this.typeEtabissementExercice = typeEtabissementExercice;
    }
    
    
}
