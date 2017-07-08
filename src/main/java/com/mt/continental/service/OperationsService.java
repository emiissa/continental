package com.mt.continental.service;

import com.mt.continental.service.dto.OperationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Operations.
 */
public interface OperationsService {

    /**
     * Save a operations.
     *
     * @param operationsDTO the entity to save
     * @return the persisted entity
     */
    OperationsDTO save(OperationsDTO operationsDTO);

    /**
     *  Get all the operations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OperationsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" operations.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OperationsDTO findOne(Long id);

    /**
     *  Delete the "id" operations.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
