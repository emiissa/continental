package com.mt.continental.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mt.continental.service.OperationsService;
import com.mt.continental.web.rest.util.HeaderUtil;
import com.mt.continental.web.rest.util.PaginationUtil;
import com.mt.continental.service.dto.OperationsDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Operations.
 */
@RestController
@RequestMapping("/api")
public class OperationsResource {

    private final Logger log = LoggerFactory.getLogger(OperationsResource.class);

    private static final String ENTITY_NAME = "operations";

    private final OperationsService operationsService;

    public OperationsResource(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    /**
     * POST  /operations : Create a new operations.
     *
     * @param operationsDTO the operationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationsDTO, or with status 400 (Bad Request) if the operations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operations")
    @Timed
    public ResponseEntity<OperationsDTO> createOperations(@RequestBody OperationsDTO operationsDTO) throws URISyntaxException {
        log.debug("REST request to save Operations : {}", operationsDTO);
        if (operationsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operations cannot already have an ID")).body(null);
        }
        OperationsDTO result = operationsService.save(operationsDTO);
        return ResponseEntity.created(new URI("/api/operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operations : Updates an existing operations.
     *
     * @param operationsDTO the operationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationsDTO,
     * or with status 400 (Bad Request) if the operationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the operationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operations")
    @Timed
    public ResponseEntity<OperationsDTO> updateOperations(@RequestBody OperationsDTO operationsDTO) throws URISyntaxException {
        log.debug("REST request to update Operations : {}", operationsDTO);
        if (operationsDTO.getId() == null) {
            return createOperations(operationsDTO);
        }
        OperationsDTO result = operationsService.save(operationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operations : get all the operations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operations in body
     */
    @GetMapping("/operations")
    @Timed
    public ResponseEntity<List<OperationsDTO>> getAllOperations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Operations");
        Page<OperationsDTO> page = operationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operations/:id : get the "id" operations.
     *
     * @param id the id of the operationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operations/{id}")
    @Timed
    public ResponseEntity<OperationsDTO> getOperations(@PathVariable Long id) {
        log.debug("REST request to get Operations : {}", id);
        OperationsDTO operationsDTO = operationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationsDTO));
    }

    /**
     * DELETE  /operations/:id : delete the "id" operations.
     *
     * @param id the id of the operationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperations(@PathVariable Long id) {
        log.debug("REST request to delete Operations : {}", id);
        operationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
