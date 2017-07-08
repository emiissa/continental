package com.mt.continental.service.mapper;

import com.mt.continental.domain.*;
import com.mt.continental.service.dto.RemboursementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Remboursement and its DTO RemboursementDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, })
public interface RemboursementMapper extends EntityMapper <RemboursementDTO, Remboursement> {

    @Mapping(source = "client.id", target = "clientId")
    RemboursementDTO toDto(Remboursement remboursement); 
    @Mapping(target = "operations", ignore = true)

    @Mapping(source = "clientId", target = "client")
    Remboursement toEntity(RemboursementDTO remboursementDTO); 
    default Remboursement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Remboursement remboursement = new Remboursement();
        remboursement.setId(id);
        return remboursement;
    }
}
