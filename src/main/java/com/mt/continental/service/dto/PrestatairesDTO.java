package com.mt.continental.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.mt.continental.domain.enumeration.TypePrestataire;

/**
 * A DTO for the Prestataires entity.
 */
public class PrestatairesDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String adresse;

    private Long tel;

    private Long numeroCompte;

    private String email;

    @NotNull
    private TypePrestataire type;

    private Long operationsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public Long getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(Long numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypePrestataire getType() {
        return type;
    }

    public void setType(TypePrestataire type) {
        this.type = type;
    }

    public Long getOperationsId() {
        return operationsId;
    }

    public void setOperationsId(Long operationsId) {
        this.operationsId = operationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrestatairesDTO prestatairesDTO = (PrestatairesDTO) o;
        if(prestatairesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestatairesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestatairesDTO{" +
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
