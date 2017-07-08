package com.mt.continental.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mt.continental.domain.Prestataires;

import com.mt.continental.repository.PrestatairesRepository;
import com.mt.continental.web.rest.util.HeaderUtil;
import com.mt.continental.service.dto.PrestatairesDTO;
import com.mt.continental.service.mapper.PrestatairesMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prestataires.
 */
@RestController
@RequestMapping("/api")
public class PrestatairesResource {

    private final Logger log = LoggerFactory.getLogger(PrestatairesResource.class);

    private static final String ENTITY_NAME = "prestataires";

    private final PrestatairesRepository prestatairesRepository;

    private final PrestatairesMapper prestatairesMapper;

    public PrestatairesResource(PrestatairesRepository prestatairesRepository, PrestatairesMapper prestatairesMapper) {
        this.prestatairesRepository = prestatairesRepository;
        this.prestatairesMapper = prestatairesMapper;
    }

    /**
     * POST  /prestataires : Create a new prestataires.
     *
     * @param prestatairesDTO the prestatairesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestatairesDTO, or with status 400 (Bad Request) if the prestataires has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestataires")
    @Timed
    public ResponseEntity<PrestatairesDTO> createPrestataires(@Valid @RequestBody PrestatairesDTO prestatairesDTO) throws URISyntaxException {
        log.debug("REST request to save Prestataires : {}", prestatairesDTO);
        if (prestatairesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new prestataires cannot already have an ID")).body(null);
        }
        Prestataires prestataires = prestatairesMapper.toEntity(prestatairesDTO);
        prestataires = prestatairesRepository.save(prestataires);
        PrestatairesDTO result = prestatairesMapper.toDto(prestataires);
        return ResponseEntity.created(new URI("/api/prestataires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestataires : Updates an existing prestataires.
     *
     * @param prestatairesDTO the prestatairesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestatairesDTO,
     * or with status 400 (Bad Request) if the prestatairesDTO is not valid,
     * or with status 500 (Internal Server Error) if the prestatairesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestataires")
    @Timed
    public ResponseEntity<PrestatairesDTO> updatePrestataires(@Valid @RequestBody PrestatairesDTO prestatairesDTO) throws URISyntaxException {
        log.debug("REST request to update Prestataires : {}", prestatairesDTO);
        if (prestatairesDTO.getId() == null) {
            return createPrestataires(prestatairesDTO);
        }
        Prestataires prestataires = prestatairesMapper.toEntity(prestatairesDTO);
        prestataires = prestatairesRepository.save(prestataires);
        PrestatairesDTO result = prestatairesMapper.toDto(prestataires);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestatairesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestataires : get all the prestataires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prestataires in body
     */
    @GetMapping("/prestataires")
    @Timed
    public List<PrestatairesDTO> getAllPrestataires() {
        log.debug("REST request to get all Prestataires");
        List<Prestataires> prestataires = prestatairesRepository.findAll();
        return prestatairesMapper.toDto(prestataires);
    }

    /**
     * GET  /prestataires/:id : get the "id" prestataires.
     *
     * @param id the id of the prestatairesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestatairesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prestataires/{id}")
    @Timed
    public ResponseEntity<PrestatairesDTO> getPrestataires(@PathVariable Long id) {
        log.debug("REST request to get Prestataires : {}", id);
        Prestataires prestataires = prestatairesRepository.findOne(id);
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(prestataires);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestatairesDTO));
    }

    /**
     * DELETE  /prestataires/:id : delete the "id" prestataires.
     *
     * @param id the id of the prestatairesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestataires/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestataires(@PathVariable Long id) {
        log.debug("REST request to delete Prestataires : {}", id);
        prestatairesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
