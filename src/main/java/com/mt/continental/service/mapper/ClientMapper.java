package com.mt.continental.service.mapper;

import com.mt.continental.domain.*;
import com.mt.continental.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {EntrepriseMapper.class, })
public interface ClientMapper extends EntityMapper <ClientDTO, Client> {

    @Mapping(source = "entreprise.id", target = "entrepriseId")
    @Mapping(source = "entreprise.nom", target = "entrepriseNom")
    ClientDTO toDto(Client client); 
    @Mapping(target = "remboursements", ignore = true)

    @Mapping(source = "entrepriseId", target = "entreprise")
    Client toEntity(ClientDTO clientDTO); 
    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
