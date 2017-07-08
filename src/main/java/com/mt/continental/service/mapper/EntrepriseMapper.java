package com.mt.continental.service.mapper;

import com.mt.continental.domain.*;
import com.mt.continental.service.dto.EntrepriseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Entreprise and its DTO EntrepriseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntrepriseMapper extends EntityMapper <EntrepriseDTO, Entreprise> {
    
    @Mapping(target = "clients", ignore = true)
    Entreprise toEntity(EntrepriseDTO entrepriseDTO); 
    default Entreprise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(id);
        return entreprise;
    }
}
