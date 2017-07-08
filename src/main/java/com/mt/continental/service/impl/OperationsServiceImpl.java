package com.mt.continental.service.impl;

import com.mt.continental.service.OperationsService;
import com.mt.continental.domain.Operations;
import com.mt.continental.repository.OperationsRepository;
import com.mt.continental.service.dto.OperationsDTO;
import com.mt.continental.service.mapper.OperationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Operations.
 */
@Service
@Transactional
public class OperationsServiceImpl implements OperationsService{

    private final Logger log = LoggerFactory.getLogger(OperationsServiceImpl.class);

    private final OperationsRepository operationsRepository;

    private final OperationsMapper operationsMapper;

    public OperationsServiceImpl(OperationsRepository operationsRepository, OperationsMapper operationsMapper) {
        this.operationsRepository = operationsRepository;
        this.operationsMapper = operationsMapper;
    }

    /**
     * Save a operations.
     *
     * @param operationsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OperationsDTO save(OperationsDTO operationsDTO) {
        log.debug("Request to save Operations : {}", operationsDTO);
        Operations operations = operationsMapper.toEntity(operationsDTO);
        operations = operationsRepository.save(operations);
        return operationsMapper.toDto(operations);
    }

    /**
     *  Get all the operations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        return operationsRepository.findAll(pageable)
            .map(operationsMapper::toDto);
    }

    /**
     *  Get one operations by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OperationsDTO findOne(Long id) {
        log.debug("Request to get Operations : {}", id);
        Operations operations = operationsRepository.findOne(id);
        return operationsMapper.toDto(operations);
    }

    /**
     *  Delete the  operations by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operations : {}", id);
        operationsRepository.delete(id);
    }
}
