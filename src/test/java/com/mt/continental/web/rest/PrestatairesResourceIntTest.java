package com.mt.continental.web.rest;

import com.mt.continental.ContinentalApp;

import com.mt.continental.domain.Prestataires;
import com.mt.continental.repository.PrestatairesRepository;
import com.mt.continental.service.dto.PrestatairesDTO;
import com.mt.continental.service.mapper.PrestatairesMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mt.continental.domain.enumeration.TypePrestataire;
/**
 * Test class for the PrestatairesResource REST controller.
 *
 * @see PrestatairesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContinentalApp.class)
public class PrestatairesResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Long DEFAULT_TEL = 1L;
    private static final Long UPDATED_TEL = 2L;

    private static final Long DEFAULT_NUMERO_COMPTE = 1L;
    private static final Long UPDATED_NUMERO_COMPTE = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final TypePrestataire DEFAULT_TYPE = TypePrestataire.CLINIQUE;
    private static final TypePrestataire UPDATED_TYPE = TypePrestataire.PHARMACIE;

    @Autowired
    private PrestatairesRepository prestatairesRepository;

    @Autowired
    private PrestatairesMapper prestatairesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrestatairesMockMvc;

    private Prestataires prestataires;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrestatairesResource prestatairesResource = new PrestatairesResource(prestatairesRepository, prestatairesMapper);
        this.restPrestatairesMockMvc = MockMvcBuilders.standaloneSetup(prestatairesResource)
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
    public static Prestataires createEntity(EntityManager em) {
        Prestataires prestataires = new Prestataires()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .tel(DEFAULT_TEL)
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .email(DEFAULT_EMAIL)
            .type(DEFAULT_TYPE);
        return prestataires;
    }

    @Before
    public void initTest() {
        prestataires = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestataires() throws Exception {
        int databaseSizeBeforeCreate = prestatairesRepository.findAll().size();

        // Create the Prestataires
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(prestataires);
        restPrestatairesMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestatairesDTO)))
            .andExpect(status().isCreated());

        // Validate the Prestataires in the database
        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeCreate + 1);
        Prestataires testPrestataires = prestatairesList.get(prestatairesList.size() - 1);
        assertThat(testPrestataires.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPrestataires.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPrestataires.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testPrestataires.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
        assertThat(testPrestataires.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPrestataires.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPrestatairesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestatairesRepository.findAll().size();

        // Create the Prestataires with an existing ID
        prestataires.setId(1L);
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(prestataires);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestatairesMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestatairesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestatairesRepository.findAll().size();
        // set the field null
        prestataires.setNom(null);

        // Create the Prestataires, which fails.
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(prestataires);

        restPrestatairesMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestatairesDTO)))
            .andExpect(status().isBadRequest());

        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestatairesRepository.findAll().size();
        // set the field null
        prestataires.setType(null);

        // Create the Prestataires, which fails.
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(prestataires);

        restPrestatairesMockMvc.perform(post("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestatairesDTO)))
            .andExpect(status().isBadRequest());

        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrestataires() throws Exception {
        // Initialize the database
        prestatairesRepository.saveAndFlush(prestataires);

        // Get all the prestatairesList
        restPrestatairesMockMvc.perform(get("/api/prestataires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestataires.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.intValue())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPrestataires() throws Exception {
        // Initialize the database
        prestatairesRepository.saveAndFlush(prestataires);

        // Get the prestataires
        restPrestatairesMockMvc.perform(get("/api/prestataires/{id}", prestataires.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestataires.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.intValue()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrestataires() throws Exception {
        // Get the prestataires
        restPrestatairesMockMvc.perform(get("/api/prestataires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestataires() throws Exception {
        // Initialize the database
        prestatairesRepository.saveAndFlush(prestataires);
        int databaseSizeBeforeUpdate = prestatairesRepository.findAll().size();

        // Update the prestataires
        Prestataires updatedPrestataires = prestatairesRepository.findOne(prestataires.getId());
        updatedPrestataires
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .tel(UPDATED_TEL)
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .email(UPDATED_EMAIL)
            .type(UPDATED_TYPE);
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(updatedPrestataires);

        restPrestatairesMockMvc.perform(put("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestatairesDTO)))
            .andExpect(status().isOk());

        // Validate the Prestataires in the database
        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeUpdate);
        Prestataires testPrestataires = prestatairesList.get(prestatairesList.size() - 1);
        assertThat(testPrestataires.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPrestataires.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPrestataires.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testPrestataires.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
        assertThat(testPrestataires.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPrestataires.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestataires() throws Exception {
        int databaseSizeBeforeUpdate = prestatairesRepository.findAll().size();

        // Create the Prestataires
        PrestatairesDTO prestatairesDTO = prestatairesMapper.toDto(prestataires);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrestatairesMockMvc.perform(put("/api/prestataires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestatairesDTO)))
            .andExpect(status().isCreated());

        // Validate the Prestataires in the database
        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrestataires() throws Exception {
        // Initialize the database
        prestatairesRepository.saveAndFlush(prestataires);
        int databaseSizeBeforeDelete = prestatairesRepository.findAll().size();

        // Get the prestataires
        restPrestatairesMockMvc.perform(delete("/api/prestataires/{id}", prestataires.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prestataires> prestatairesList = prestatairesRepository.findAll();
        assertThat(prestatairesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prestataires.class);
        Prestataires prestataires1 = new Prestataires();
        prestataires1.setId(1L);
        Prestataires prestataires2 = new Prestataires();
        prestataires2.setId(prestataires1.getId());
        assertThat(prestataires1).isEqualTo(prestataires2);
        prestataires2.setId(2L);
        assertThat(prestataires1).isNotEqualTo(prestataires2);
        prestataires1.setId(null);
        assertThat(prestataires1).isNotEqualTo(prestataires2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrestatairesDTO.class);
        PrestatairesDTO prestatairesDTO1 = new PrestatairesDTO();
        prestatairesDTO1.setId(1L);
        PrestatairesDTO prestatairesDTO2 = new PrestatairesDTO();
        assertThat(prestatairesDTO1).isNotEqualTo(prestatairesDTO2);
        prestatairesDTO2.setId(prestatairesDTO1.getId());
        assertThat(prestatairesDTO1).isEqualTo(prestatairesDTO2);
        prestatairesDTO2.setId(2L);
        assertThat(prestatairesDTO1).isNotEqualTo(prestatairesDTO2);
        prestatairesDTO1.setId(null);
        assertThat(prestatairesDTO1).isNotEqualTo(prestatairesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prestatairesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prestatairesMapper.fromId(null)).isNull();
    }
}
