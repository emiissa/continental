package com.mt.continental.web.rest;

import com.mt.continental.ContinentalApp;

import com.mt.continental.domain.Remboursement;
import com.mt.continental.domain.Client;
import com.mt.continental.repository.RemboursementRepository;
import com.mt.continental.service.RemboursementService;
import com.mt.continental.service.dto.RemboursementDTO;
import com.mt.continental.service.mapper.RemboursementMapper;
import com.mt.continental.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mt.continental.domain.enumeration.LienMalade;
/**
 * Test class for the RemboursementResource REST controller.
 *
 * @see RemboursementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContinentalApp.class)
public class RemboursementResourceIntTest {

    private static final Long DEFAULT_NUMERO_DOSSIER = 1L;
    private static final Long UPDATED_NUMERO_DOSSIER = 2L;

    private static final LocalDate DEFAULT_DATE_R = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_R = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOM_MALADE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MALADE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MALADE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MALADE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_NAISSANCE = "BBBBBBBBBB";

    private static final LienMalade DEFAULT_LIEN = LienMalade.CLIENT;
    private static final LienMalade UPDATED_LIEN = LienMalade.CONJOINT;

    private static final byte[] DEFAULT_PIECE_JOINTE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PIECE_JOINTE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PIECE_JOINTE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PIECE_JOINTE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PIECE_JOINTE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PIECE_JOINTE_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PIECE_JOINTE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PIECE_JOINTE_2_CONTENT_TYPE = "image/png";

    @Autowired
    private RemboursementRepository remboursementRepository;

    @Autowired
    private RemboursementMapper remboursementMapper;

    @Autowired
    private RemboursementService remboursementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRemboursementMockMvc;

    private Remboursement remboursement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RemboursementResource remboursementResource = new RemboursementResource(remboursementService);
        this.restRemboursementMockMvc = MockMvcBuilders.standaloneSetup(remboursementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remboursement createEntity(EntityManager em) {
        Remboursement remboursement = new Remboursement()
            .numeroDossier(DEFAULT_NUMERO_DOSSIER)
            .dateR(DEFAULT_DATE_R)
            .nomMalade(DEFAULT_NOM_MALADE)
            .prenomMalade(DEFAULT_PRENOM_MALADE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lien(DEFAULT_LIEN)
            .pieceJointe(DEFAULT_PIECE_JOINTE)
            .pieceJointeContentType(DEFAULT_PIECE_JOINTE_CONTENT_TYPE)
            .pieceJointe2(DEFAULT_PIECE_JOINTE_2)
            .pieceJointe2ContentType(DEFAULT_PIECE_JOINTE_2_CONTENT_TYPE);
        // Add required entity
        Client client = ClientResourceIntTest.createEntity(em);
        em.persist(client);
        em.flush();
        remboursement.setClient(client);
        return remboursement;
    }

    @Before
    public void initTest() {
        remboursement = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemboursement() throws Exception {
        int databaseSizeBeforeCreate = remboursementRepository.findAll().size();

        // Create the Remboursement
        RemboursementDTO remboursementDTO = remboursementMapper.toDto(remboursement);
        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursementDTO)))
            .andExpect(status().isCreated());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeCreate + 1);
        Remboursement testRemboursement = remboursementList.get(remboursementList.size() - 1);
        assertThat(testRemboursement.getNumeroDossier()).isEqualTo(DEFAULT_NUMERO_DOSSIER);
        assertThat(testRemboursement.getDateR()).isEqualTo(DEFAULT_DATE_R);
        assertThat(testRemboursement.getNomMalade()).isEqualTo(DEFAULT_NOM_MALADE);
        assertThat(testRemboursement.getPrenomMalade()).isEqualTo(DEFAULT_PRENOM_MALADE);
        assertThat(testRemboursement.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testRemboursement.getLien()).isEqualTo(DEFAULT_LIEN);
        assertThat(testRemboursement.getPieceJointe()).isEqualTo(DEFAULT_PIECE_JOINTE);
        assertThat(testRemboursement.getPieceJointeContentType()).isEqualTo(DEFAULT_PIECE_JOINTE_CONTENT_TYPE);
        assertThat(testRemboursement.getPieceJointe2()).isEqualTo(DEFAULT_PIECE_JOINTE_2);
        assertThat(testRemboursement.getPieceJointe2ContentType()).isEqualTo(DEFAULT_PIECE_JOINTE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createRemboursementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remboursementRepository.findAll().size();

        // Create the Remboursement with an existing ID
        remboursement.setId(1L);
        RemboursementDTO remboursementDTO = remboursementMapper.toDto(remboursement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemboursementMockMvc.perform(post("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRemboursements() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);

        // Get all the remboursementList
        restRemboursementMockMvc.perform(get("/api/remboursements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remboursement.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDossier").value(hasItem(DEFAULT_NUMERO_DOSSIER.intValue())))
            .andExpect(jsonPath("$.[*].dateR").value(hasItem(DEFAULT_DATE_R.toString())))
            .andExpect(jsonPath("$.[*].nomMalade").value(hasItem(DEFAULT_NOM_MALADE.toString())))
            .andExpect(jsonPath("$.[*].prenomMalade").value(hasItem(DEFAULT_PRENOM_MALADE.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())))
            .andExpect(jsonPath("$.[*].pieceJointeContentType").value(hasItem(DEFAULT_PIECE_JOINTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pieceJointe").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE))))
            .andExpect(jsonPath("$.[*].pieceJointe2ContentType").value(hasItem(DEFAULT_PIECE_JOINTE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pieceJointe2").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE_2))));
    }

    @Test
    @Transactional
    public void getRemboursement() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);

        // Get the remboursement
        restRemboursementMockMvc.perform(get("/api/remboursements/{id}", remboursement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(remboursement.getId().intValue()))
            .andExpect(jsonPath("$.numeroDossier").value(DEFAULT_NUMERO_DOSSIER.intValue()))
            .andExpect(jsonPath("$.dateR").value(DEFAULT_DATE_R.toString()))
            .andExpect(jsonPath("$.nomMalade").value(DEFAULT_NOM_MALADE.toString()))
            .andExpect(jsonPath("$.prenomMalade").value(DEFAULT_PRENOM_MALADE.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()))
            .andExpect(jsonPath("$.pieceJointeContentType").value(DEFAULT_PIECE_JOINTE_CONTENT_TYPE))
            .andExpect(jsonPath("$.pieceJointe").value(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE)))
            .andExpect(jsonPath("$.pieceJointe2ContentType").value(DEFAULT_PIECE_JOINTE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.pieceJointe2").value(Base64Utils.encodeToString(DEFAULT_PIECE_JOINTE_2)));
    }

    @Test
    @Transactional
    public void getNonExistingRemboursement() throws Exception {
        // Get the remboursement
        restRemboursementMockMvc.perform(get("/api/remboursements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemboursement() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);
        int databaseSizeBeforeUpdate = remboursementRepository.findAll().size();

        // Update the remboursement
        Remboursement updatedRemboursement = remboursementRepository.findOne(remboursement.getId());
        updatedRemboursement
            .numeroDossier(UPDATED_NUMERO_DOSSIER)
            .dateR(UPDATED_DATE_R)
            .nomMalade(UPDATED_NOM_MALADE)
            .prenomMalade(UPDATED_PRENOM_MALADE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lien(UPDATED_LIEN)
            .pieceJointe(UPDATED_PIECE_JOINTE)
            .pieceJointeContentType(UPDATED_PIECE_JOINTE_CONTENT_TYPE)
            .pieceJointe2(UPDATED_PIECE_JOINTE_2)
            .pieceJointe2ContentType(UPDATED_PIECE_JOINTE_2_CONTENT_TYPE);
        RemboursementDTO remboursementDTO = remboursementMapper.toDto(updatedRemboursement);

        restRemboursementMockMvc.perform(put("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursementDTO)))
            .andExpect(status().isOk());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeUpdate);
        Remboursement testRemboursement = remboursementList.get(remboursementList.size() - 1);
        assertThat(testRemboursement.getNumeroDossier()).isEqualTo(UPDATED_NUMERO_DOSSIER);
        assertThat(testRemboursement.getDateR()).isEqualTo(UPDATED_DATE_R);
        assertThat(testRemboursement.getNomMalade()).isEqualTo(UPDATED_NOM_MALADE);
        assertThat(testRemboursement.getPrenomMalade()).isEqualTo(UPDATED_PRENOM_MALADE);
        assertThat(testRemboursement.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testRemboursement.getLien()).isEqualTo(UPDATED_LIEN);
        assertThat(testRemboursement.getPieceJointe()).isEqualTo(UPDATED_PIECE_JOINTE);
        assertThat(testRemboursement.getPieceJointeContentType()).isEqualTo(UPDATED_PIECE_JOINTE_CONTENT_TYPE);
        assertThat(testRemboursement.getPieceJointe2()).isEqualTo(UPDATED_PIECE_JOINTE_2);
        assertThat(testRemboursement.getPieceJointe2ContentType()).isEqualTo(UPDATED_PIECE_JOINTE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRemboursement() throws Exception {
        int databaseSizeBeforeUpdate = remboursementRepository.findAll().size();

        // Create the Remboursement
        RemboursementDTO remboursementDTO = remboursementMapper.toDto(remboursement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRemboursementMockMvc.perform(put("/api/remboursements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remboursementDTO)))
            .andExpect(status().isCreated());

        // Validate the Remboursement in the database
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRemboursement() throws Exception {
        // Initialize the database
        remboursementRepository.saveAndFlush(remboursement);
        int databaseSizeBeforeDelete = remboursementRepository.findAll().size();

        // Get the remboursement
        restRemboursementMockMvc.perform(delete("/api/remboursements/{id}", remboursement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Remboursement> remboursementList = remboursementRepository.findAll();
        assertThat(remboursementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remboursement.class);
        Remboursement remboursement1 = new Remboursement();
        remboursement1.setId(1L);
        Remboursement remboursement2 = new Remboursement();
        remboursement2.setId(remboursement1.getId());
        assertThat(remboursement1).isEqualTo(remboursement2);
        remboursement2.setId(2L);
        assertThat(remboursement1).isNotEqualTo(remboursement2);
        remboursement1.setId(null);
        assertThat(remboursement1).isNotEqualTo(remboursement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemboursementDTO.class);
        RemboursementDTO remboursementDTO1 = new RemboursementDTO();
        remboursementDTO1.setId(1L);
        RemboursementDTO remboursementDTO2 = new RemboursementDTO();
        assertThat(remboursementDTO1).isNotEqualTo(remboursementDTO2);
        remboursementDTO2.setId(remboursementDTO1.getId());
        assertThat(remboursementDTO1).isEqualTo(remboursementDTO2);
        remboursementDTO2.setId(2L);
        assertThat(remboursementDTO1).isNotEqualTo(remboursementDTO2);
        remboursementDTO1.setId(null);
        assertThat(remboursementDTO1).isNotEqualTo(remboursementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(remboursementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(remboursementMapper.fromId(null)).isNull();
    }
}
