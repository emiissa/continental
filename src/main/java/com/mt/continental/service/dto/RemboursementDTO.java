package com.mt.continental.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.mt.continental.domain.enumeration.LienMalade;

/**
 * A DTO for the Remboursement entity.
 */
public class RemboursementDTO implements Serializable {

    private Long id;

    private Long numeroDossier;

    private LocalDate dateR;

    private String nomMalade;

    private String prenomMalade;

    private String dateNaissance;

    private LienMalade lien;

    @Lob
    private byte[] pieceJointe;
    private String pieceJointeContentType;

    @Lob
    private byte[] pieceJointe2;
    private String pieceJointe2ContentType;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(Long numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public LocalDate getDateR() {
        return dateR;
    }

    public void setDateR(LocalDate dateR) {
        this.dateR = dateR;
    }

    public String getNomMalade() {
        return nomMalade;
    }

    public void setNomMalade(String nomMalade) {
        this.nomMalade = nomMalade;
    }

    public String getPrenomMalade() {
        return prenomMalade;
    }

    public void setPrenomMalade(String prenomMalade) {
        this.prenomMalade = prenomMalade;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LienMalade getLien() {
        return lien;
    }

    public void setLien(LienMalade lien) {
        this.lien = lien;
    }

    public byte[] getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(byte[] pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public String getPieceJointeContentType() {
        return pieceJointeContentType;
    }

    public void setPieceJointeContentType(String pieceJointeContentType) {
        this.pieceJointeContentType = pieceJointeContentType;
    }

    public byte[] getPieceJointe2() {
        return pieceJointe2;
    }

    public void setPieceJointe2(byte[] pieceJointe2) {
        this.pieceJointe2 = pieceJointe2;
    }

    public String getPieceJointe2ContentType() {
        return pieceJointe2ContentType;
    }

    public void setPieceJointe2ContentType(String pieceJointe2ContentType) {
        this.pieceJointe2ContentType = pieceJointe2ContentType;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RemboursementDTO remboursementDTO = (RemboursementDTO) o;
        if(remboursementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remboursementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RemboursementDTO{" +
            "id=" + getId() +
            ", numeroDossier='" + getNumeroDossier() + "'" +
            ", dateR='" + getDateR() + "'" +
            ", nomMalade='" + getNomMalade() + "'" +
            ", prenomMalade='" + getPrenomMalade() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lien='" + getLien() + "'" +
            ", pieceJointe='" + getPieceJointe() + "'" +
            ", pieceJointe2='" + getPieceJointe2() + "'" +
            "}";
    }
}
