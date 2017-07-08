package com.mt.continental.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mt.continental.service.RemboursementService;
import com.mt.continental.web.rest.util.HeaderUtil;
import com.mt.continental.service.dto.RemboursementDTO;
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
 * REST controller for managing Remboursement.
 */
@RestController
@RequestMapping("/api")
public class RemboursementResource {

    private final Logger log = LoggerFactory.getLogger(RemboursementResource.class);

    private static final String ENTITY_NAME = "remboursement";

    private final RemboursementService remboursementService;

    public RemboursementResource(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }

    /**
     * POST  /remboursements : Create a new remboursement.
     *
     * @param remboursementDTO the remboursementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new remboursementDTO, or with status 400 (Bad Request) if the remboursement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/remboursements")
    @Timed
    public ResponseEntity<RemboursementDTO> createRemboursement(@Valid @RequestBody RemboursementDTO remboursementDTO) throws URISyntaxException {
        log.debug("REST request to save Remboursement : {}", remboursementDTO);
        if (remboursementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new remboursement cannot already have an ID")).body(null);
        }
        RemboursementDTO result = remboursementService.save(remboursementDTO);
        return ResponseEntity.created(new URI("/api/remboursements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /remboursements : Updates an existing remboursement.
     *
     * @param remboursementDTO the remboursementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated remboursementDTO,
     * or with status 400 (Bad Request) if the remboursementDTO is not valid,
     * or with status 500 (Internal Server Error) if the remboursementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/remboursements")
    @Timed
    public ResponseEntity<RemboursementDTO> updateRemboursement(@Valid @RequestBody RemboursementDTO remboursementDTO) throws URISyntaxException {
        log.debug("REST request to update Remboursement : {}", remboursementDTO);
        if (remboursementDTO.getId() == null) {
            return createRemboursement(remboursementDTO);
        }
        RemboursementDTO result = remboursementService.save(remboursementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, remboursementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /remboursements : get all the remboursements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of remboursements in body
     */
    @GetMapping("/remboursements")
    @Timed
    public List<RemboursementDTO> getAllRemboursements() {
        log.debug("REST request to get all Remboursements");
        return remboursementService.findAll();
    }

    /**
     * GET  /remboursements/:id : get the "id" remboursement.
     *
     * @param id the id of the remboursementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the remboursementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/remboursements/{id}")
    @Timed
    public ResponseEntity<RemboursementDTO> getRemboursement(@PathVariable Long id) {
        log.debug("REST request to get Remboursement : {}", id);
        RemboursementDTO remboursementDTO = remboursementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(remboursementDTO));
    }

    /**
     * DELETE  /remboursements/:id : delete the "id" remboursement.
     *
     * @param id the id of the remboursementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/remboursements/{id}")
    @Timed
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        log.debug("REST request to delete Remboursement : {}", id);
        remboursementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
