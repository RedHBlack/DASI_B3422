/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bvilleroy
 */
@Entity
public class Eleve extends Personne{
    
    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;
    
    @Column(nullable=false)
    protected int classe;
    
    @ManyToOne
    protected Etablissement etablissement;
    
    @OneToMany(mappedBy="eleve")
    protected List<Demande> demandes;

    public Eleve() {
        this.demandes = new ArrayList<Demande>();
    }

    public Eleve(Date dateNaissance, int classe, String nom, String prenom, String motDePasse, String email) {
        super(nom, prenom, motDePasse, email);
        this.dateNaissance = dateNaissance;
        this.classe = classe;
        this.demandes = new ArrayList<Demande>();
        
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public int getClasse() {
        return classe;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
    
    public void ajouterDemande(Demande d){
        this.demandes.add(d);
        if(d.getEleve()!=this)
            d.setEleve(this);
    }

    @Override
    public String toString() {
        String s="Eleve{" +"id="+id+", dateNaissance=" + dateNaissance + ", classe=" + classe + ", etablissement=" + etablissement.getId() + ", demandes={";
        for(Demande demande : demandes){
            s +=' '+demande.toString();
        }
        s+="}}";
        return s;
        
    }
    
    
}