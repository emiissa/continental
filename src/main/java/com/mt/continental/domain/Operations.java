package com.mt.continental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Entity Operations
 */
@ApiModel(description = "Entity Operations")
@Entity
@Table(name = "operations")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_operation")
    private LocalDate dateOperation;

    @Column(name = "description")
    private String description;

    @Column(name = "montant")
    private Float montant;

    @Column(name = "tiers_payant")
    private Float tiersPayant;

    @Column(name = "pourcentage")
    private Float pourcentage;

    @ManyToOne
    private Remboursement remboursement;

    @OneToMany(mappedBy = "operations")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Prestataires> prestataires = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public Operations dateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
        return this;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getDescription() {
        return description;
    }

    public Operations description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMontant() {
        return montant;
    }

    public Operations montant(Float montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Float getTiersPayant() {
        return tiersPayant;
    }

    public Operations tiersPayant(Float tiersPayant) {
        this.tiersPayant = tiersPayant;
        return this;
    }

    public void setTiersPayant(Float tiersPayant) {
        this.tiersPayant = tiersPayant;
    }

    public Float getPourcentage() {
        return pourcentage;
    }

    public Operations pourcentage(Float pourcentage) {
        this.pourcentage = pourcentage;
        return this;
    }

    public void setPourcentage(Float pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Remboursement getRemboursement() {
        return remboursement;
    }

    public Operations remboursement(Remboursement remboursement) {
        this.remboursement = remboursement;
        return this;
    }

    public void setRemboursement(Remboursement remboursement) {
        this.remboursement = remboursement;
    }

    public Set<Prestataires> getPrestataires() {
        return prestataires;
    }

    public Operations prestataires(Set<Prestataires> prestataires) {
        this.prestataires = prestataires;
        return this;
    }

    public Operations addPrestataire(Prestataires prestataires) {
        this.prestataires.add(prestataires);
        prestataires.setOperations(this);
        return this;
    }

    public Operations removePrestataire(Prestataires prestataires) {
        this.prestataires.remove(prestataires);
        prestataires.setOperations(null);
        return this;
    }

    public void setPrestataires(Set<Prestataires> prestataires) {
        this.prestataires = prestataires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Operations operations = (Operations) o;
        if (operations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operations{" +
            "id=" + getId() +
            ", dateOperation='" + getDateOperation() + "'" +
            ", description='" + getDescription() + "'" +
            ", montant='" + getMontant() + "'" +
            ", tiersPayant='" + getTiersPayant() + "'" +
            ", pourcentage='" + getPourcentage() + "'" +
            "}";
    }
}
