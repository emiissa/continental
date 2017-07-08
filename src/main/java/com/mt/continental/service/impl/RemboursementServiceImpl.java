package com.mt.continental.service.impl;

import com.mt.continental.service.RemboursementService;
import com.mt.continental.domain.Remboursement;
import com.mt.continental.repository.RemboursementRepository;
import com.mt.continental.service.dto.RemboursementDTO;
import com.mt.continental.service.mapper.RemboursementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Remboursement.
 */
@Service
@Transactional
public class RemboursementServiceImpl implements RemboursementService{

    private final Logger log = LoggerFactory.getLogger(RemboursementServiceImpl.class);

    private final RemboursementRepository remboursementRepository;

    private final RemboursementMapper remboursementMapper;

    public RemboursementServiceImpl(RemboursementRepository remboursementRepository, RemboursementMapper remboursementMapper) {
        this.remboursementRepository = remboursementRepository;
        this.remboursementMapper = remboursementMapper;
    }

    /**
     * Save a remboursement.
     *
     * @param remboursementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RemboursementDTO save(RemboursementDTO remboursementDTO) {
        log.debug("Request to save Remboursement : {}", remboursementDTO);
        Remboursement remboursement = remboursementMapper.toEntity(remboursementDTO);
        remboursement = remboursementRepository.save(remboursement);
        return remboursementMapper.toDto(remboursement);
    }

    /**
     *  Get all the remboursements.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RemboursementDTO> findAll() {
        log.debug("Request to get all Remboursements");
        return remboursementRepository.findAll().stream()
            .map(remboursementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one remboursement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RemboursementDTO findOne(Long id) {
        log.debug("Request to get Remboursement : {}", id);
        Remboursement remboursement = remboursementRepository.findOne(id);
        return remboursementMapper.toDto(remboursement);
    }

    /**
     *  Delete the  remboursement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Remboursement : {}", id);
        remboursementRepository.delete(id);
    }
}
