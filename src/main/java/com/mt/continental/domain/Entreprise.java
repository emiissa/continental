package com.mt.continental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "sigle")
    private String sigle;

    @Column(name = "numero_contrat")
    private String numeroContrat;

    @Column(name = "contact")
    private Long contact;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "entreprise")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Entreprise nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSigle() {
        return sigle;
    }

    public Entreprise sigle(String sigle) {
        this.sigle = sigle;
        return this;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public Entreprise numeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
        return this;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public Long getContact() {
        return contact;
    }

    public Entreprise contact(Long contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public Entreprise email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Entreprise clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Entreprise addClients(Client client) {
        this.clients.add(client);
        client.setEntreprise(this);
        return this;
    }

    public Entreprise removeClients(Client client) {
        this.clients.remove(client);
        client.setEntreprise(null);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entreprise entreprise = (Entreprise) o;
        if (entreprise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreprise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", sigle='" + getSigle() + "'" +
            ", numeroContrat='" + getNumeroContrat() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
