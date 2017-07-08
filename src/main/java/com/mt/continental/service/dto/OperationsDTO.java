package com.mt.continental.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Operations entity.
 */
public class OperationsDTO implements Serializable {

    private Long id;

    private LocalDate dateOperation;

    private String description;

    private Float montant;

    private Float tiersPayant;

    private Float pourcentage;

    private Long remboursementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Float getTiersPayant() {
        return tiersPayant;
    }

    public void setTiersPayant(Float tiersPayant) {
        this.tiersPayant = tiersPayant;
    }

    public Float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Float pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Long getRemboursementId() {
        return remboursementId;
    }

    public void setRemboursementId(Long remboursementId) {
        this.remboursementId = remboursementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationsDTO operationsDTO = (OperationsDTO) o;
        if(operationsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationsDTO{" +
            "id=" + getId() +
            ", dateOperation='" + getDateOperation() + "'" +
            ", description='" + getDescription() + "'" +
            ", montant='" + getMontant() + "'" +
            ", tiersPayant='" + getTiersPayant() + "'" +
            ", pourcentage='" + getPourcentage() + "'" +
            "}";
    }
}
