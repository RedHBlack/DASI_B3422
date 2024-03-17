/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author bvilleroy
 */

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Intervenant extends Personne{
   
    public enum Statut{DISPONIBLE,OCCUPE;}
    
    @Column(nullable=false, unique=true)
    protected String numeroDeTelephone;
    
    @Column(nullable=false)
    protected Statut statut;
    
    @Column(nullable=false)
    protected List<Integer> niveauxCouverts;
    
    @OneToMany(mappedBy="intervenant", cascade = CascadeType.ALL)
    protected List<Demande> interventions;

    public Intervenant() {
    }

    public Intervenant(String numeroDeTelephone, List<Integer> niveauxCouverts, String nom, String prenom, String motDePasse, String email) {
        super(nom, prenom, motDePasse, email);
        this.numeroDeTelephone = numeroDeTelephone;
        this.niveauxCouverts = niveauxCouverts;
        this.interventions = new ArrayList<Demande>();
        this.statut=Statut.DISPONIBLE;
    }

    public String getNumeroDeTelephone() {
        return numeroDeTelephone;
    }

    public Statut getStatut() {
        return statut;
    }

    public List<Integer> getNiveauxCouvert() {
        return niveauxCouverts;
    }

    public List<Demande> getInterventions() {
        return interventions;
    }

    public void setNumeroDeTelephone(String numeroDeTelephone) {
        this.numeroDeTelephone = numeroDeTelephone;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public void setNiveauxCouvert(List<Integer> niveauxCouverts) {
        this.niveauxCouverts = niveauxCouverts;
    }

    public void setInterventions(List<Demande> interventions) {
        this.interventions = interventions;
    }
    
    public void ajouterIntervention(Demande d){
        this.interventions.add(d);
        if(d.getIntervenant()!=this)
            d.setIntervenant(this);
    }
    
}
