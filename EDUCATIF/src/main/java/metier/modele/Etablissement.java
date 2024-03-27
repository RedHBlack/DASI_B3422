/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.GeoNetApi;

/**
 *
 * @author bvilleroy
 */
@Entity
public class Etablissement {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    @Column(nullable=false, unique=true)
    protected String code;
    
    @Column(nullable=false)
    protected String nom;
    
    @Column(nullable=false)
    protected String secteur;
    
    @Column(nullable=false)
    protected int codePostal;
    
    @Column(nullable=false)
    protected String nomCommune;
    
    @Column(nullable=false)
    protected int codeDepartement;
    
    @Column(nullable=false)
    protected String departement;
    
    @Column(nullable=false)
    protected String academie;
    
    @Column(nullable=false)
    protected double ips;
    
    @Column(nullable=false)
    protected double longitude;
    
    @Column(nullable=false)
    protected double latitude;
    
    @OneToMany(mappedBy="etablissement", cascade = CascadeType.ALL)
    protected List<Eleve> eleves;

    public Etablissement() {
    }

    public Etablissement(String code, String nom, String secteur, int codePostal, String nomCommune, int codeDepartement, String departement, String academie, double ips) {
        this.code = code;
        this.nom = nom;
        this.secteur = secteur;
        this.codePostal = codePostal;
        this.nomCommune = nomCommune;
        this.codeDepartement = codeDepartement;
        this.departement = departement;
        this.academie = academie;
        this.ips = ips;
        this.longitude = GeoNetApi.getLatLng(nomCommune).lng;
        this.latitude = GeoNetApi.getLatLng(nomCommune).lat;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getSecteur() {
        return secteur;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public int getCodeDepartement() {
        return codeDepartement;
    }

    public String getDepartement() {
        return departement;
    }

    public String getAcademie() {
        return academie;
    }

    public double getIps() {
        return ips;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public void setCodeDepartement(int codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public void setAcademie(String academie) {
        this.academie = academie;
    }

    public void setIps(double ips) {
        this.ips = ips;
    }

    public void setEleves(List<Eleve> eleves) {
        this.eleves = eleves;
    }
    
    public void addEleve(Eleve e){
        this.eleves.add(e);
        if(e.getEtablissement()!=this)
            e.setEtablissement(this);
    }
    
    public void removeEleve(Eleve e){
        eleves.remove(e);
        e.setEtablissement(null);
    }
    
}
