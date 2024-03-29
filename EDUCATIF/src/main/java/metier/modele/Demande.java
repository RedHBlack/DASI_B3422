/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bvilleroy
 */
@Entity
public class Demande {
  
   public enum Etat {ACCEPTEE, ANNULEE;}
   
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   protected Long id;
   
   @Column(name="date_demande")
   @Temporal(TemporalType.DATE)
   protected Date date;
   
   @Temporal(TemporalType.TIMESTAMP)
   protected Date duree;
   
   @Column(nullable=false)
   protected String description;
   
   protected String autoevaluation;
   protected String bilan;
   
   @ManyToOne
   protected Intervenant intervenant;
   
   @ManyToOne
   protected Eleve eleve;
   
   @Column(nullable=false)
   protected Etat statut;

    public Demande() {
    }

    public Demande(Date date, Date duree, String description, String autoevaluation, String bilan, Etat statut) {
        this.date = date;
        this.duree = duree;
        this.description = description;
        this.autoevaluation = autoevaluation;
        this.bilan = bilan;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Date getDuree() {
        return duree;
    }

    public String getDescription() {
        return description;
    }

    public String getAutoevaluation() {
        return autoevaluation;
    }

    public String getBilan() {
        return bilan;
    }

    public Etat getStatut() {
        return statut;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public Eleve getEleve() {
        return eleve;
    }

    
    public void setDate(Date date) {
        this.date = date;
    }

    public void setDuree(Date duree) {
        this.duree = duree;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAutoevaluation(String autoevaluation) {
        this.autoevaluation = autoevaluation;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public void setStatut(Etat statut) {
        this.statut = statut;
    }    

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
    
    
}
