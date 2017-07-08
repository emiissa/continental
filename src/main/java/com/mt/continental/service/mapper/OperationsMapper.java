package com.mt.continental.service.mapper;

import com.mt.continental.domain.*;
import com.mt.continental.service.dto.OperationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operations and its DTO OperationsDTO.
 */
@Mapper(componentModel = "spring", uses = {RemboursementMapper.class, })
public interface OperationsMapper extends EntityMapper <OperationsDTO, Operations> {

    @Mapping(source = "remboursement.id", target = "remboursementId")
    OperationsDTO toDto(Operations operations); 

    @Mapping(source = "remboursementId", target = "remboursement")
    @Mapping(target = "prestataires", ignore = true)
    Operations toEntity(OperationsDTO operationsDTO); 
    default Operations fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operations operations = new Operations();
        operations.setId(id);
        return operations;
    }
}
