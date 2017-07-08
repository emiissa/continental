package com.mt.continental.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mt.continental.service.EntrepriseService;
import com.mt.continental.web.rest.util.HeaderUtil;
import com.mt.continental.service.dto.EntrepriseDTO;
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
 * REST controller for managing Entreprise.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    private final EntrepriseService entrepriseService;

    public EntrepriseResource(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    /**
     * POST  /entreprises : Create a new entreprise.
     *
     * @param entrepriseDTO the entrepriseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrepriseDTO, or with status 400 (Bad Request) if the entreprise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entreprises")
    @Timed
    public ResponseEntity<EntrepriseDTO> createEntreprise(@Valid @RequestBody EntrepriseDTO entrepriseDTO) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entrepriseDTO);
        if (entrepriseDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entreprise cannot already have an ID")).body(null);
        }
        EntrepriseDTO result = entrepriseService.save(entrepriseDTO);
        return ResponseEntity.created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entreprises : Updates an existing entreprise.
     *
     * @param entrepriseDTO the entrepriseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrepriseDTO,
     * or with status 400 (Bad Request) if the entrepriseDTO is not valid,
     * or with status 500 (Internal Server Error) if the entrepriseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entreprises")
    @Timed
    public ResponseEntity<EntrepriseDTO> updateEntreprise(@Valid @RequestBody EntrepriseDTO entrepriseDTO) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}", entrepriseDTO);
        if (entrepriseDTO.getId() == null) {
            return createEntreprise(entrepriseDTO);
        }
        EntrepriseDTO result = entrepriseService.save(entrepriseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entrepriseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entreprises : get all the entreprises.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entreprises in body
     */
    @GetMapping("/entreprises")
    @Timed
    public List<EntrepriseDTO> getAllEntreprises() {
        log.debug("REST request to get all Entreprises");
        return entrepriseService.findAll();
    }

    /**
     * GET  /entreprises/:id : get the "id" entreprise.
     *
     * @param id the id of the entrepriseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrepriseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entreprises/{id}")
    @Timed
    public ResponseEntity<EntrepriseDTO> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get Entreprise : {}", id);
        EntrepriseDTO entrepriseDTO = entrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entrepriseDTO));
    }

    /**
     * DELETE  /entreprises/:id : delete the "id" entreprise.
     *
     * @param id the id of the entrepriseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entreprises/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
