package com.mt.continental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mt.continental.domain.enumeration.LienMalade;

/**
 * Entity Remboursement
 */
@ApiModel(description = "Entity Remboursement")
@Entity
@Table(name = "remboursement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Remboursement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_dossier")
    private Long numeroDossier;

    @Column(name = "date_r")
    private LocalDate dateR;

    @Column(name = "nom_malade")
    private String nomMalade;

    @Column(name = "prenom_malade")
    private String prenomMalade;

    @Column(name = "date_naissance")
    private String dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "lien")
    private LienMalade lien;

    @Lob
    @Column(name = "piece_jointe")
    private byte[] pieceJointe;

    @Column(name = "piece_jointe_content_type")
    private String pieceJointeContentType;

    @Lob
    @Column(name = "piece_jointe_2")
    private byte[] pieceJointe2;

    @Column(name = "piece_jointe_2_content_type")
    private String pieceJointe2ContentType;

    @OneToMany(mappedBy = "remboursement")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operations> operations = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDossier() {
        return numeroDossier;
    }

    public Remboursement numeroDossier(Long numeroDossier) {
        this.numeroDossier = numeroDossier;
        return this;
    }

    public void setNumeroDossier(Long numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public LocalDate getDateR() {
        return dateR;
    }

    public Remboursement dateR(LocalDate dateR) {
        this.dateR = dateR;
        return this;
    }

    public void setDateR(LocalDate dateR) {
        this.dateR = dateR;
    }

    public String getNomMalade() {
        return nomMalade;
    }

    public Remboursement nomMalade(String nomMalade) {
        this.nomMalade = nomMalade;
        return this;
    }

    public void setNomMalade(String nomMalade) {
        this.nomMalade = nomMalade;
    }

    public String getPrenomMalade() {
        return prenomMalade;
    }

    public Remboursement prenomMalade(String prenomMalade) {
        this.prenomMalade = prenomMalade;
        return this;
    }

    public void setPrenomMalade(String prenomMalade) {
        this.prenomMalade = prenomMalade;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public Remboursement dateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LienMalade getLien() {
        return lien;
    }

    public Remboursement lien(LienMalade lien) {
        this.lien = lien;
        return this;
    }

    public void setLien(LienMalade lien) {
        this.lien = lien;
    }

    public byte[] getPieceJointe() {
        return pieceJointe;
    }

    public Remboursement pieceJointe(byte[] pieceJointe) {
        this.pieceJointe = pieceJointe;
        return this;
    }

    public void setPieceJointe(byte[] pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public String getPieceJointeContentType() {
        return pieceJointeContentType;
    }

    public Remboursement pieceJointeContentType(String pieceJointeContentType) {
        this.pieceJointeContentType = pieceJointeContentType;
        return this;
    }

    public void setPieceJointeContentType(String pieceJointeContentType) {
        this.pieceJointeContentType = pieceJointeContentType;
    }

    public byte[] getPieceJointe2() {
        return pieceJointe2;
    }

    public Remboursement pieceJointe2(byte[] pieceJointe2) {
        this.pieceJointe2 = pieceJointe2;
        return this;
    }

    public void setPieceJointe2(byte[] pieceJointe2) {
        this.pieceJointe2 = pieceJointe2;
    }

    public String getPieceJointe2ContentType() {
        return pieceJointe2ContentType;
    }

    public Remboursement pieceJointe2ContentType(String pieceJointe2ContentType) {
        this.pieceJointe2ContentType = pieceJointe2ContentType;
        return this;
    }

    public void setPieceJointe2ContentType(String pieceJointe2ContentType) {
        this.pieceJointe2ContentType = pieceJointe2ContentType;
    }

    public Set<Operations> getOperations() {
        return operations;
    }

    public Remboursement operations(Set<Operations> operations) {
        this.operations = operations;
        return this;
    }

    public Remboursement addOperations(Operations operations) {
        this.operations.add(operations);
        operations.setRemboursement(this);
        return this;
    }

    public Remboursement removeOperations(Operations operations) {
        this.operations.remove(operations);
        operations.setRemboursement(null);
        return this;
    }

    public void setOperations(Set<Operations> operations) {
        this.operations = operations;
    }

    public Client getClient() {
        return client;
    }

    public Remboursement client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Remboursement remboursement = (Remboursement) o;
        if (remboursement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remboursement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Remboursement{" +
            "id=" + getId() +
            ", numeroDossier='" + getNumeroDossier() + "'" +
            ", dateR='" + getDateR() + "'" +
            ", nomMalade='" + getNomMalade() + "'" +
            ", prenomMalade='" + getPrenomMalade() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lien='" + getLien() + "'" +
            ", pieceJointe='" + getPieceJointe() + "'" +
            ", pieceJointeContentType='" + pieceJointeContentType + "'" +
            ", pieceJointe2='" + getPieceJointe2() + "'" +
            ", pieceJointe2ContentType='" + pieceJointe2ContentType + "'" +
            "}";
    }
}
