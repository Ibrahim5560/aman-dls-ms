package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ApplicationPhase;
import com.isoft.dls.domain.Application;
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

import com.isoft.dls.domain.enumeration.PhaseType;
/**
 * Integration tests for the {@link ApplicationPhaseResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ApplicationPhaseResourceIT {

    private static final PhaseType DEFAULT_TYPE = PhaseType.CUSTOMER_ELIGIBILITY;
    private static final PhaseType UPDATED_TYPE = PhaseType.DRIVING_LEARNING_FILE_PROCESSING;

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;
    private static final Integer SMALLER_SEQUENCE = 1 - 1;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA = "BBBBBBBBBB";

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
            .type(DEFAULT_TYPE)
            .sequence(DEFAULT_SEQUENCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .persona(DEFAULT_PERSONA);
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
            .type(UPDATED_TYPE)
            .sequence(UPDATED_SEQUENCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .persona(UPDATED_PERSONA);
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
        assertThat(testApplicationPhase.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testApplicationPhase.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testApplicationPhase.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testApplicationPhase.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testApplicationPhase.getPersona()).isEqualTo(DEFAULT_PERSONA);
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
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setType(null);

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
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setSequence(null);

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
    public void checkPersonaIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationPhaseRepository.findAll().size();
        // set the field null
        applicationPhase.setPersona(null);

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
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)));
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
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.persona").value(DEFAULT_PERSONA));
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
    public void getAllApplicationPhasesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where type equals to DEFAULT_TYPE
        defaultApplicationPhaseShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the applicationPhaseList where type equals to UPDATED_TYPE
        defaultApplicationPhaseShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where type not equals to DEFAULT_TYPE
        defaultApplicationPhaseShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the applicationPhaseList where type not equals to UPDATED_TYPE
        defaultApplicationPhaseShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultApplicationPhaseShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the applicationPhaseList where type equals to UPDATED_TYPE
        defaultApplicationPhaseShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where type is not null
        defaultApplicationPhaseShouldBeFound("type.specified=true");

        // Get all the applicationPhaseList where type is null
        defaultApplicationPhaseShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence equals to DEFAULT_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the applicationPhaseList where sequence equals to UPDATED_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence not equals to DEFAULT_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.notEquals=" + DEFAULT_SEQUENCE);

        // Get all the applicationPhaseList where sequence not equals to UPDATED_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.notEquals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the applicationPhaseList where sequence equals to UPDATED_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence is not null
        defaultApplicationPhaseShouldBeFound("sequence.specified=true");

        // Get all the applicationPhaseList where sequence is null
        defaultApplicationPhaseShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence is greater than or equal to DEFAULT_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.greaterThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the applicationPhaseList where sequence is greater than or equal to UPDATED_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.greaterThanOrEqual=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence is less than or equal to DEFAULT_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.lessThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the applicationPhaseList where sequence is less than or equal to SMALLER_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.lessThanOrEqual=" + SMALLER_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence is less than DEFAULT_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the applicationPhaseList where sequence is less than UPDATED_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllApplicationPhasesBySequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);

        // Get all the applicationPhaseList where sequence is greater than DEFAULT_SEQUENCE
        defaultApplicationPhaseShouldNotBeFound("sequence.greaterThan=" + DEFAULT_SEQUENCE);

        // Get all the applicationPhaseList where sequence is greater than SMALLER_SEQUENCE
        defaultApplicationPhaseShouldBeFound("sequence.greaterThan=" + SMALLER_SEQUENCE);
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
    public void getAllApplicationPhasesByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationPhaseRepository.saveAndFlush(applicationPhase);
        Application application = ApplicationResourceIT.createEntity(em);
        em.persist(application);
        em.flush();
        applicationPhase.setApplication(application);
        applicationPhaseRepository.saveAndFlush(applicationPhase);
        Long applicationId = application.getId();

        // Get all the applicationPhaseList where application equals to applicationId
        defaultApplicationPhaseShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the applicationPhaseList where application equals to applicationId + 1
        defaultApplicationPhaseShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationPhaseShouldBeFound(String filter) throws Exception {
        restApplicationPhaseMockMvc.perform(get("/api/application-phases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)));

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
            .type(UPDATED_TYPE)
            .sequence(UPDATED_SEQUENCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .persona(UPDATED_PERSONA);
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseMapper.toDto(updatedApplicationPhase);

        restApplicationPhaseMockMvc.perform(put("/api/application-phases").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationPhaseDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationPhase in the database
        List<ApplicationPhase> applicationPhaseList = applicationPhaseRepository.findAll();
        assertThat(applicationPhaseList).hasSize(databaseSizeBeforeUpdate);
        ApplicationPhase testApplicationPhase = applicationPhaseList.get(applicationPhaseList.size() - 1);
        assertThat(testApplicationPhase.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApplicationPhase.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testApplicationPhase.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testApplicationPhase.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testApplicationPhase.getPersona()).isEqualTo(UPDATED_PERSONA);
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
