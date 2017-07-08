package com.mt.continental.service.mapper;

import com.mt.continental.domain.*;
import com.mt.continental.service.dto.PrestatairesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Prestataires and its DTO PrestatairesDTO.
 */
@Mapper(componentModel = "spring", uses = {OperationsMapper.class, })
public interface PrestatairesMapper extends EntityMapper <PrestatairesDTO, Prestataires> {

    @Mapping(source = "operations.id", target = "operationsId")
    PrestatairesDTO toDto(Prestataires prestataires); 

    @Mapping(source = "operationsId", target = "operations")
    Prestataires toEntity(PrestatairesDTO prestatairesDTO); 
    default Prestataires fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prestataires prestataires = new Prestataires();
        prestataires.setId(id);
        return prestataires;
    }
}
