package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ApplicationPhase;
import com.isoft.dls.repository.ApplicationPhaseRepository;
import com.isoft.dls.service.ApplicationPhaseService;
import com.isoft.dls.service.dto.ApplicationPhaseDTO;
import com.isoft.dls.service.mapper.ApplicationPhaseMapper;
import com.isoft.dls.service.dto.ApplicationPhaseCriteria;
import com.isoft.dls.service.ApplicationPhaseQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ApplicationPhaseResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ApplicationPhaseResourceIT {

    private static final String DEFAULT_PHASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PHASE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PHASE_SEQUENCE = 1L;
    private static final Long UPDATED_PHASE_SEQUENCE = 2L;
    private static final Long SMALLER_PHASE_SEQUENCE = 1L - 1L;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA = "BBBBBBBBBB";

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;
    private static final Long SMALLER_APPLICATION_ID = 1L - 1L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicationPhaseRepository applicationPhaseRepository;

    @Autowired
    private ApplicationPhaseMapper applicationPhaseMapper;

    @Autowired
    private ApplicationPhaseService applicationPhaseService;

    @Autowired
    private ApplicationPhaseQueryService applicationPhaseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationPhaseMockMvc;

    private ApplicationPhase applicationPhase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationPhase createEntity(EntityManager em) {
        ApplicationPhase applicationPhase = new ApplicationPhase()
            .phaseType(DEFAULT_PHASE_TYPE)
            .phaseSequence(DEFAULT_PHASE_SEQUENCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .persona(DEFAULT_PERSONA)
            .applicationId(DEFAULT_APPLICATION_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return applicationPhase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationPhase createUpdatedEntity(EntityManager em) {
        ApplicationPhase applicationPhase = new ApplicationPhase()
            .phaseType(UPDATED_PHASE_TYPE)
            .phaseSequence(UPDATED_PHASE_SEQUENCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .persona(UPDATED_PERSONA)
            .applicationId(UPDATED_APPLICATION_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return applicationPhase;
    }

    @BeforeEach
    public void initTest() {
        applicationPhase = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationPhase() throws Exception {
        int databaseSizeBeforeCreate = applicationPhaseRepository.findAll().size();

        // Create the ApplicationPhase
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);
        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationPhase testApplicationPhase = applicationPhaseList.get(applicationPhaseList.size() - 1);
        assertThat(testApplicationPhase.getPhaseType()).isEqualTo(DEFAULT_PHASE_TYPE);
        assertThat(testApplicationPhase.getPhaseSequence()).isEqualTo(DEFAULT_PHASE_SEQUENCE);
        assertThat(testApplicationPhase.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testApplicationPhase.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testApplicationPhase.getPersona()).isEqualTo(DEFAULT_PERSONA);
        assertThat(testApplicationPhase.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testApplicationPhase.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationPhase.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApplicationPhase.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApplicationPhase.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApplicationPhaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationPhaseRepository.findAll().size();

        // Create the ApplicationPhase with an existing ID
        applicationPhase.setId(1L);
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPhaseTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setPhaseType(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhaseSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setPhaseSequence(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setStartDate(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setApplicationId(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setCreatedBy(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setCreatedDate(null);

        // Create the ApplicationPhase, which fails.
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        restApplicationPhaseMockMvc.perform(post("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationPhases() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList
        restApplicationPhaseMockMvc.perform(get("/api/application-phases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].phaseType").value(hasItem(DEFAULT_PHASE_TYPE)))
            .andExpect(jsonPath("$.[*].phaseSequence").value(hasItem(DEFAULT_PHASE_SEQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationPhase() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get the applicationPhase
        restApplicationPhaseMockMvc.perform(get("/api/application-phases/{id}", applicationPhase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationPhase.getId().intValue()))
            .andExpect(jsonPath("$.phaseType").value(DEFAULT_PHASE_TYPE))
            .andExpect(jsonPath("$.phaseSequence").value(DEFAULT_PHASE_SEQUENCE.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.persona").value(DEFAULT_PERSONA))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getApplicationPhasesByIdFiltering() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        Long id = applicationPhase.getId();

        defaultApplicationPhaseShouldBeFound("id.equals=" + id);
        defaultApplicationPhaseShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationPhaseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationPhaseShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationPhaseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationPhaseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseType equals to DEFAULT_PHASE_TYPE
        defaultApplicationPhaseShouldBeFound("phaseType.equals=" + DEFAULT_PHASE_TYPE);

        // Get all the applicationPhaseList where phaseType equals to UPDATED_PHASE_TYPE
        defaultApplicationPhaseShouldNotBeFound("phaseType.equals=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseType not equals to DEFAULT_PHASE_TYPE
        defaultApplicationPhaseShouldNotBeFound("phaseType.notEquals=" + DEFAULT_PHASE_TYPE);

        // Get all the applicationPhaseList where phaseType not equals to UPDATED_PHASE_TYPE
        defaultApplicationPhaseShouldBeFound("phaseType.notEquals=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseType in DEFAULT_PHASE_TYPE or UPDATED_PHASE_TYPE
        defaultApplicationPhaseShouldBeFound("phaseType.in=" + DEFAULT_PHASE_TYPE + "," + UPDATED_PHASE_TYPE);

        // Get all the applicationPhaseList where phaseType equals to UPDATED_PHASE_TYPE
        defaultApplicationPhaseShouldNotBeFound("phaseType.in=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseType is not null
        defaultApplicationPhaseShouldBeFound("phaseType.specified=true");

        // Get all the applicationPhaseList where phaseType is null
        defaultApplicationPhaseShouldNotBeFound("phaseType.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseTypeContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseType contains DEFAULT_PHASE_TYPE
        defaultApplicationPhaseShouldBeFound("phaseType.contains=" + DEFAULT_PHASE_TYPE);

        // Get all the applicationPhaseList where phaseType contains UPDATED_PHASE_TYPE
        defaultApplicationPhaseShouldNotBeFound("phaseType.contains=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseTypeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseType does not contain DEFAULT_PHASE_TYPE
        defaultApplicationPhaseShouldNotBeFound("phaseType.doesNotContain=" + DEFAULT_PHASE_TYPE);

        // Get all the applicationPhaseList where phaseType does not contain UPDATED_PHASE_TYPE
        defaultApplicationPhaseShouldBeFound("phaseType.doesNotContain=" + UPDATED_PHASE_TYPE);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence equals to DEFAULT_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.equals=" + DEFAULT_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence equals to UPDATED_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.equals=" + UPDATED_PHASE_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence not equals to DEFAULT_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.notEquals=" + DEFAULT_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence not equals to UPDATED_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.notEquals=" + UPDATED_PHASE_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence in DEFAULT_PHASE_SEQUENCE or UPDATED_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.in=" + DEFAULT_PHASE_SEQUENCE + "," + UPDATED_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence equals to UPDATED_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.in=" + UPDATED_PHASE_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence is not null
        defaultApplicationPhaseShouldBeFound("phaseSequence.specified=true");

        // Get all the applicationPhaseList where phaseSequence is null
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence is greater than or equal to DEFAULT_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.greaterThanOrEqual=" + DEFAULT_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence is greater than or equal to UPDATED_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.greaterThanOrEqual=" + UPDATED_PHASE_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence is less than or equal to DEFAULT_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.lessThanOrEqual=" + DEFAULT_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence is less than or equal to SMALLER_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.lessThanOrEqual=" + SMALLER_PHASE_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence is less than DEFAULT_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.lessThan=" + DEFAULT_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence is less than UPDATED_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.lessThan=" + UPDATED_PHASE_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPhaseSequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where phaseSequence is greater than DEFAULT_PHASE_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("phaseSequence.greaterThan=" + DEFAULT_PHASE_SEQUENCE);

        // Get all the applicationPhaseList where phaseSequence is greater than SMALLER_PHASE_SEQUENCE
        defaultApplicationPhaseShouldBeFound("phaseSequence.greaterThan=" + SMALLER_PHASE_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where startDate equals to DEFAULT_START_DATE
        defaultApplicationPhaseShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the applicationPhaseList where startDate equals to UPDATED_START_DATE
        defaultApplicationPhaseShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where startDate not equals to DEFAULT_START_DATE
        defaultApplicationPhaseShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the applicationPhaseList where startDate not equals to UPDATED_START_DATE
        defaultApplicationPhaseShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultApplicationPhaseShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the applicationPhaseList where startDate equals to UPDATED_START_DATE
        defaultApplicationPhaseShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where startDate is not null
        defaultApplicationPhaseShouldBeFound("startDate.specified=true");

        // Get all the applicationPhaseList where startDate is null
        defaultApplicationPhaseShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where endDate equals to DEFAULT_END_DATE
        defaultApplicationPhaseShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the applicationPhaseList where endDate equals to UPDATED_END_DATE
        defaultApplicationPhaseShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where endDate not equals to DEFAULT_END_DATE
        defaultApplicationPhaseShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the applicationPhaseList where endDate not equals to UPDATED_END_DATE
        defaultApplicationPhaseShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultApplicationPhaseShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the applicationPhaseList where endDate equals to UPDATED_END_DATE
        defaultApplicationPhaseShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where endDate is not null
        defaultApplicationPhaseShouldBeFound("endDate.specified=true");

        // Get all the applicationPhaseList where endDate is null
        defaultApplicationPhaseShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPersonaIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where persona equals to DEFAULT_PERSONA
        defaultApplicationPhaseShouldBeFound("persona.equals=" + DEFAULT_PERSONA);

        // Get all the applicationPhaseList where persona equals to UPDATED_PERSONA
        defaultApplicationPhaseShouldNotBeFound("persona.equals=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPersonaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where persona not equals to DEFAULT_PERSONA
        defaultApplicationPhaseShouldNotBeFound("persona.notEquals=" + DEFAULT_PERSONA);

        // Get all the applicationPhaseList where persona not equals to UPDATED_PERSONA
        defaultApplicationPhaseShouldBeFound("persona.notEquals=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPersonaIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where persona in DEFAULT_PERSONA or UPDATED_PERSONA
        defaultApplicationPhaseShouldBeFound("persona.in=" + DEFAULT_PERSONA + "," + UPDATED_PERSONA);

        // Get all the applicationPhaseList where persona equals to UPDATED_PERSONA
        defaultApplicationPhaseShouldNotBeFound("persona.in=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPersonaIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where persona is not null
        defaultApplicationPhaseShouldBeFound("persona.specified=true");

        // Get all the applicationPhaseList where persona is null
        defaultApplicationPhaseShouldNotBeFound("persona.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationPhasesByPersonaContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where persona contains DEFAULT_PERSONA
        defaultApplicationPhaseShouldBeFound("persona.contains=" + DEFAULT_PERSONA);

        // Get all the applicationPhaseList where persona contains UPDATED_PERSONA
        defaultApplicationPhaseShouldNotBeFound("persona.contains=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByPersonaNotContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where persona does not contain DEFAULT_PERSONA
        defaultApplicationPhaseShouldNotBeFound("persona.doesNotContain=" + DEFAULT_PERSONA);

        // Get all the applicationPhaseList where persona does not contain UPDATED_PERSONA
        defaultApplicationPhaseShouldBeFound("persona.doesNotContain=" + UPDATED_PERSONA);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId equals to DEFAULT_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.equals=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId equals to UPDATED_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.equals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId not equals to DEFAULT_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.notEquals=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId not equals to UPDATED_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.notEquals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId in DEFAULT_APPLICATION_ID or UPDATED_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.in=" + DEFAULT_APPLICATION_ID + "," + UPDATED_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId equals to UPDATED_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.in=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId is not null
        defaultApplicationPhaseShouldBeFound("applicationId.specified=true");

        // Get all the applicationPhaseList where applicationId is null
        defaultApplicationPhaseShouldNotBeFound("applicationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId is greater than or equal to DEFAULT_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.greaterThanOrEqual=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId is greater than or equal to UPDATED_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.greaterThanOrEqual=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId is less than or equal to DEFAULT_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.lessThanOrEqual=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId is less than or equal to SMALLER_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.lessThanOrEqual=" + SMALLER_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId is less than DEFAULT_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.lessThan=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId is less than UPDATED_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.lessThan=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByApplicationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where applicationId is greater than DEFAULT_APPLICATION_ID
        defaultApplicationPhaseShouldNotBeFound("applicationId.greaterThan=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationPhaseList where applicationId is greater than SMALLER_APPLICATION_ID
        defaultApplicationPhaseShouldBeFound("applicationId.greaterThan=" + SMALLER_APPLICATION_ID);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationPhaseShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationPhaseList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationPhaseShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicationPhaseShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicationPhaseList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicationPhaseShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationPhaseShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationPhaseList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationPhaseShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdBy is not null
        defaultApplicationPhaseShouldBeFound("createdBy.specified=true");

        // Get all the applicationPhaseList where createdBy is null
        defaultApplicationPhaseShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdBy contains DEFAULT_CREATED_BY
        defaultApplicationPhaseShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the applicationPhaseList where createdBy contains UPDATED_CREATED_BY
        defaultApplicationPhaseShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdBy does not contain DEFAULT_CREATED_BY
        defaultApplicationPhaseShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the applicationPhaseList where createdBy does not contain UPDATED_CREATED_BY
        defaultApplicationPhaseShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdDate equals to DEFAULT_CREATED_DATE
        defaultApplicationPhaseShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationPhaseList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationPhaseShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultApplicationPhaseShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationPhaseList where createdDate not equals to UPDATED_CREATED_DATE
        defaultApplicationPhaseShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultApplicationPhaseShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the applicationPhaseList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationPhaseShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where createdDate is not null
        defaultApplicationPhaseShouldBeFound("createdDate.specified=true");

        // Get all the applicationPhaseList where createdDate is null
        defaultApplicationPhaseShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationPhaseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationPhaseList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the applicationPhaseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedBy is not null
        defaultApplicationPhaseShouldBeFound("lastModifiedBy.specified=true");

        // Get all the applicationPhaseList where lastModifiedBy is null
        defaultApplicationPhaseShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationPhaseList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationPhaseList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApplicationPhaseShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationPhaseShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationPhaseList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationPhaseShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationPhaseShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationPhaseList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationPhaseShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultApplicationPhaseShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the applicationPhaseList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationPhaseShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where lastModifiedDate is not null
        defaultApplicationPhaseShouldBeFound("lastModifiedDate.specified=true");

        // Get all the applicationPhaseList where lastModifiedDate is null
        defaultApplicationPhaseShouldNotBeFound("lastModifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationPhaseShouldBeFound(String filter) throws Exception {
        restApplicationPhaseMockMvc.perform(get("/api/application-phases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].phaseType").value(hasItem(DEFAULT_PHASE_TYPE)))
            .andExpect(jsonPath("$.[*].phaseSequence").value(hasItem(DEFAULT_PHASE_SEQUENCE.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restApplicationPhaseMockMvc.perform(get("/api/application-phases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationPhaseShouldNotBeFound(String filter) throws Exception {
        restApplicationPhaseMockMvc.perform(get("/api/application-phases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationPhaseMockMvc.perform(get("/api/application-phases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationPhase() throws Exception {
        // Get the applicationPhase
        restApplicationPhaseMockMvc.perform(get("/api/application-phases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationPhase() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        int databaseSizeBeforeUpdate = applicationPhaseRepository.findAll().size();

        // Update the applicationPhase
        ApplicationPhase updatedApplicationPhase = applicationPhaseRepository.findById(applicationPhase.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationPhase are not directly saved in db
        em.detach(updatedApplicationPhase);
        updatedApplicationPhase
            .phaseType(UPDATED_PHASE_TYPE)
            .phaseSequence(UPDATED_PHASE_SEQUENCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .persona(UPDATED_PERSONA)
            .applicationId(UPDATED_APPLICATION_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(updatedApplicationPhase);

        restApplicationPhaseMockMvc.perform(put("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeUpdate);
        ApplicationPhase testApplicationPhase = applicationPhaseList.get(applicationPhaseList.size() - 1);
        assertThat(testApplicationPhase.getPhaseType()).isEqualTo(UPDATED_PHASE_TYPE);
        assertThat(testApplicationPhase.getPhaseSequence()).isEqualTo(UPDATED_PHASE_SEQUENCE);
        assertThat(testApplicationPhase.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testApplicationPhase.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testApplicationPhase.getPersona()).isEqualTo(UPDATED_PERSONA);
        assertThat(testApplicationPhase.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testApplicationPhase.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationPhase.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApplicationPhase.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApplicationPhase.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationPhase() throws Exception {
        int databaseSizeBeforeUpdate = applicationPhaseRepository.findAll().size();

        // Create the ApplicationPhase
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(applicationPhase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationPhaseMockMvc.perform(put("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationPhase() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        int databaseSizeBeforeDelete = applicationPhaseRepository.findAll().size();

        // Delete the applicationPhase
        restApplicationPhaseMockMvc.perform(delete("/api/application-phases/{id}", applicationPhase.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
