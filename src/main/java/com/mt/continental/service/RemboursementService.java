package com.mt.continental.service;

import com.mt.continental.service.dto.RemboursementDTO;
import java.util.List;

/**
 * Service Interface for managing Remboursement.
 */
public interface RemboursementService {

    /**
     * Save a remboursement.
     *
     * @param remboursementDTO the entity to save
     * @return the persisted entity
     */
    RemboursementDTO save(RemboursementDTO remboursementDTO);

    /**
     *  Get all the remboursements.
     *
     *  @return the list of entities
     */
    List<RemboursementDTO> findAll();

    /**
     *  Get the "id" remboursement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RemboursementDTO findOne(Long id);

    /**
     *  Delete the "id" remboursement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
