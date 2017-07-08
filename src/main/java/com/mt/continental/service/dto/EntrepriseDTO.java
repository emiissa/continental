package com.mt.continental.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Entreprise entity.
 */
public class EntrepriseDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String sigle;

    private String numeroContrat;

    private Long contact;

    private String email;

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

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrepriseDTO entrepriseDTO = (EntrepriseDTO) o;
        if(entrepriseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrepriseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntrepriseDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", sigle='" + getSigle() + "'" +
            ", numeroContrat='" + getNumeroContrat() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
