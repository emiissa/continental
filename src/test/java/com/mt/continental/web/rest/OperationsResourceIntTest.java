package com.mt.continental.web.rest;

import com.mt.continental.ContinentalApp;

import com.mt.continental.domain.Operations;
import com.mt.continental.repository.OperationsRepository;
import com.mt.continental.service.OperationsService;
import com.mt.continental.service.dto.OperationsDTO;
import com.mt.continental.service.mapper.OperationsMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OperationsResource REST controller.
 *
 * @see OperationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContinentalApp.class)
public class OperationsResourceIntTest {

    private static final LocalDate DEFAULT_DATE_OPERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OPERATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_MONTANT = 1F;
    private static final Float UPDATED_MONTANT = 2F;

    private static final Float DEFAULT_TIERS_PAYANT = 1F;
    private static final Float UPDATED_TIERS_PAYANT = 2F;

    private static final Float DEFAULT_POURCENTAGE = 1F;
    private static final Float UPDATED_POURCENTAGE = 2F;

    @Autowired
    private OperationsRepository operationsRepository;

    @Autowired
    private OperationsMapper operationsMapper;

    @Autowired
    private OperationsService operationsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperationsMockMvc;

    private Operations operations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OperationsResource operationsResource = new OperationsResource(operationsService);
        this.restOperationsMockMvc = MockMvcBuilders.standaloneSetup(operationsResource)
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
    public static Operations createEntity(EntityManager em) {
        Operations operations = new Operations()
            .dateOperation(DEFAULT_DATE_OPERATION)
            .description(DEFAULT_DESCRIPTION)
            .montant(DEFAULT_MONTANT)
            .tiersPayant(DEFAULT_TIERS_PAYANT)
            .pourcentage(DEFAULT_POURCENTAGE);
        return operations;
    }

    @Before
    public void initTest() {
        operations = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperations() throws Exception {
        int databaseSizeBeforeCreate = operationsRepository.findAll().size();

        // Create the Operations
        OperationsDTO operationsDTO = operationsMapper.toDto(operations);
        restOperationsMockMvc.perform(post("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Operations in the database
        List<Operations> operationsList = operationsRepository.findAll();
        assertThat(operationsList).hasSize(databaseSizeBeforeCreate + 1);
        Operations testOperations = operationsList.get(operationsList.size() - 1);
        assertThat(testOperations.getDateOperation()).isEqualTo(DEFAULT_DATE_OPERATION);
        assertThat(testOperations.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOperations.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testOperations.getTiersPayant()).isEqualTo(DEFAULT_TIERS_PAYANT);
        assertThat(testOperations.getPourcentage()).isEqualTo(DEFAULT_POURCENTAGE);
    }

    @Test
    @Transactional
    public void createOperationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationsRepository.findAll().size();

        // Create the Operations with an existing ID
        operations.setId(1L);
        OperationsDTO operationsDTO = operationsMapper.toDto(operations);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationsMockMvc.perform(post("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Operations> operationsList = operationsRepository.findAll();
        assertThat(operationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOperations() throws Exception {
        // Initialize the database
        operationsRepository.saveAndFlush(operations);

        // Get all the operationsList
        restOperationsMockMvc.perform(get("/api/operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operations.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOperation").value(hasItem(DEFAULT_DATE_OPERATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].tiersPayant").value(hasItem(DEFAULT_TIERS_PAYANT.doubleValue())))
            .andExpect(jsonPath("$.[*].pourcentage").value(hasItem(DEFAULT_POURCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getOperations() throws Exception {
        // Initialize the database
        operationsRepository.saveAndFlush(operations);

        // Get the operations
        restOperationsMockMvc.perform(get("/api/operations/{id}", operations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operations.getId().intValue()))
            .andExpect(jsonPath("$.dateOperation").value(DEFAULT_DATE_OPERATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.tiersPayant").value(DEFAULT_TIERS_PAYANT.doubleValue()))
            .andExpect(jsonPath("$.pourcentage").value(DEFAULT_POURCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOperations() throws Exception {
        // Get the operations
        restOperationsMockMvc.perform(get("/api/operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperations() throws Exception {
        // Initialize the database
        operationsRepository.saveAndFlush(operations);
        int databaseSizeBeforeUpdate = operationsRepository.findAll().size();

        // Update the operations
        Operations updatedOperations = operationsRepository.findOne(operations.getId());
        updatedOperations
            .dateOperation(UPDATED_DATE_OPERATION)
            .description(UPDATED_DESCRIPTION)
            .montant(UPDATED_MONTANT)
            .tiersPayant(UPDATED_TIERS_PAYANT)
            .pourcentage(UPDATED_POURCENTAGE);
        OperationsDTO operationsDTO = operationsMapper.toDto(updatedOperations);

        restOperationsMockMvc.perform(put("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsDTO)))
            .andExpect(status().isOk());

        // Validate the Operations in the database
        List<Operations> operationsList = operationsRepository.findAll();
        assertThat(operationsList).hasSize(databaseSizeBeforeUpdate);
        Operations testOperations = operationsList.get(operationsList.size() - 1);
        assertThat(testOperations.getDateOperation()).isEqualTo(UPDATED_DATE_OPERATION);
        assertThat(testOperations.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOperations.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testOperations.getTiersPayant()).isEqualTo(UPDATED_TIERS_PAYANT);
        assertThat(testOperations.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingOperations() throws Exception {
        int databaseSizeBeforeUpdate = operationsRepository.findAll().size();

        // Create the Operations
        OperationsDTO operationsDTO = operationsMapper.toDto(operations);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperationsMockMvc.perform(put("/api/operations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsDTO)))
            .andExpect(status().isCreated());

        // Validate the Operations in the database
        List<Operations> operationsList = operationsRepository.findAll();
        assertThat(operationsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperations() throws Exception {
        // Initialize the database
        operationsRepository.saveAndFlush(operations);
        int databaseSizeBeforeDelete = operationsRepository.findAll().size();

        // Get the operations
        restOperationsMockMvc.perform(delete("/api/operations/{id}", operations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operations> operationsList = operationsRepository.findAll();
        assertThat(operationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operations.class);
        Operations operations1 = new Operations();
        operations1.setId(1L);
        Operations operations2 = new Operations();
        operations2.setId(operations1.getId());
        assertThat(operations1).isEqualTo(operations2);
        operations2.setId(2L);
        assertThat(operations1).isNotEqualTo(operations2);
        operations1.setId(null);
        assertThat(operations1).isNotEqualTo(operations2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationsDTO.class);
        OperationsDTO operationsDTO1 = new OperationsDTO();
        operationsDTO1.setId(1L);
        OperationsDTO operationsDTO2 = new OperationsDTO();
        assertThat(operationsDTO1).isNotEqualTo(operationsDTO2);
        operationsDTO2.setId(operationsDTO1.getId());
        assertThat(operationsDTO1).isEqualTo(operationsDTO2);
        operationsDTO2.setId(2L);
        assertThat(operationsDTO1).isNotEqualTo(operationsDTO2);
        operationsDTO1.setId(null);
        assertThat(operationsDTO1).isNotEqualTo(operationsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operationsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operationsMapper.fromId(null)).isNull();
    }
}
