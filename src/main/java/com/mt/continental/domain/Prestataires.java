package com.mt.continental.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.mt.continental.domain.enumeration.TypePrestataire;

/**
 * A Prestataires.
 */
@Entity
@Table(name = "prestataires")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prestataires implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private Long tel;

    @Column(name = "numero_compte")
    private Long numeroCompte;

    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypePrestataire type;

    @ManyToOne
    private Operations operations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Prestataires nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Prestataires adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getTel() {
        return tel;
    }

    public Prestataires tel(Long tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public Long getNumeroCompte() {
        return numeroCompte;
    }

    public Prestataires numeroCompte(Long numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(Long numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getEmail() {
        return email;
    }

    public Prestataires email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypePrestataire getType() {
        return type;
    }

    public Prestataires type(TypePrestataire type) {
        this.type = type;
        return this;
    }

    public void setType(TypePrestataire type) {
        this.type = type;
    }

    public Operations getOperations() {
        return operations;
    }

    public Prestataires operations(Operations operations) {
        this.operations = operations;
        return this;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prestataires prestataires = (Prestataires) o;
        if (prestataires.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestataires.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prestataires{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel='" + getTel() + "'" +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
