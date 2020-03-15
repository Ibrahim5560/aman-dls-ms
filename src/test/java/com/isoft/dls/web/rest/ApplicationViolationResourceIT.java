package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ApplicationViolation;
import com.isoft.dls.domain.Application;
import com.isoft.dls.repository.ApplicationViolationRepository;
import com.isoft.dls.service.ApplicationViolationService;
import com.isoft.dls.service.dto.ApplicationViolationDTO;
import com.isoft.dls.service.mapper.ApplicationViolationMapper;
import com.isoft.dls.service.dto.ApplicationViolationCriteria;
import com.isoft.dls.service.ApplicationViolationQueryService;

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
 * Integration tests for the {@link ApplicationViolationResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ApplicationViolationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION = 1L;
    private static final Long UPDATED_IS_ELIGIBLE_FOR_EXEMPTION = 2L;
    private static final Long SMALLER_IS_ELIGIBLE_FOR_EXEMPTION = 1L - 1L;

    private static final Long DEFAULT_IS_EXEMPTED = 1L;
    private static final Long UPDATED_IS_EXEMPTED = 2L;
    private static final Long SMALLER_IS_EXEMPTED = 1L - 1L;

    private static final String DEFAULT_VIOLATION_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_VIOLATION_LEVEL = "BBBBBBBBBB";

    private static final Long DEFAULT_EXEMPTION_PROCESS_ID = 1L;
    private static final Long UPDATED_EXEMPTION_PROCESS_ID = 2L;
    private static final Long SMALLER_EXEMPTION_PROCESS_ID = 1L - 1L;

    private static final String DEFAULT_EXEMPTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXEMPTED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXEMPTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXEMPTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
    private ApplicationViolationRepository applicationViolationRepository;

    @Autowired
    private ApplicationViolationMapper applicationViolationMapper;

    @Autowired
    private ApplicationViolationService applicationViolationService;

    @Autowired
    private ApplicationViolationQueryService applicationViolationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationViolationMockMvc;

    private ApplicationViolation applicationViolation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationViolation createEntity(EntityManager em) {
        ApplicationViolation applicationViolation = new ApplicationViolation()
            .code(DEFAULT_CODE)
            .isEligibleForExemption(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION)
            .isExempted(DEFAULT_IS_EXEMPTED)
            .violationLevel(DEFAULT_VIOLATION_LEVEL)
            .exemptionProcessId(DEFAULT_EXEMPTION_PROCESS_ID)
            .exemptedBy(DEFAULT_EXEMPTED_BY)
            .exemptionDate(DEFAULT_EXEMPTION_DATE)
            .applicationId(DEFAULT_APPLICATION_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return applicationViolation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationViolation createUpdatedEntity(EntityManager em) {
        ApplicationViolation applicationViolation = new ApplicationViolation()
            .code(UPDATED_CODE)
            .isEligibleForExemption(UPDATED_IS_ELIGIBLE_FOR_EXEMPTION)
            .isExempted(UPDATED_IS_EXEMPTED)
            .violationLevel(UPDATED_VIOLATION_LEVEL)
            .exemptionProcessId(UPDATED_EXEMPTION_PROCESS_ID)
            .exemptedBy(UPDATED_EXEMPTED_BY)
            .exemptionDate(UPDATED_EXEMPTION_DATE)
            .applicationId(UPDATED_APPLICATION_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return applicationViolation;
    }

    @BeforeEach
    public void initTest() {
        applicationViolation = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationViolation() throws Exception {
        int databaseSizeBeforeCreate = applicationViolationRepository.findAll().size();

        // Create the ApplicationViolation
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);
        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationViolation testApplicationViolation = applicationViolationList.get(applicationViolationList.size() - 1);
        assertThat(testApplicationViolation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApplicationViolation.getIsEligibleForExemption()).isEqualTo(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);
        assertThat(testApplicationViolation.getIsExempted()).isEqualTo(DEFAULT_IS_EXEMPTED);
        assertThat(testApplicationViolation.getViolationLevel()).isEqualTo(DEFAULT_VIOLATION_LEVEL);
        assertThat(testApplicationViolation.getExemptionProcessId()).isEqualTo(DEFAULT_EXEMPTION_PROCESS_ID);
        assertThat(testApplicationViolation.getExemptedBy()).isEqualTo(DEFAULT_EXEMPTED_BY);
        assertThat(testApplicationViolation.getExemptionDate()).isEqualTo(DEFAULT_EXEMPTION_DATE);
        assertThat(testApplicationViolation.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testApplicationViolation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationViolation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApplicationViolation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApplicationViolation.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApplicationViolationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationViolationRepository.findAll().size();

        // Create the ApplicationViolation with an existing ID
        applicationViolation.setId(1L);
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setCode(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEligibleForExemptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setIsEligibleForExemption(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExemptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setIsExempted(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkViolationLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setViolationLevel(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setApplicationId(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setCreatedBy(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationViolationRepository.findAll().size();
        // set the field null
        applicationViolation.setCreatedDate(null);

        // Create the ApplicationViolation, which fails.
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        restApplicationViolationMockMvc.perform(post("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationViolations() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList
        restApplicationViolationMockMvc.perform(get("/api/application-violations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationViolation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].isEligibleForExemption").value(hasItem(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION.intValue())))
            .andExpect(jsonPath("$.[*].isExempted").value(hasItem(DEFAULT_IS_EXEMPTED.intValue())))
            .andExpect(jsonPath("$.[*].violationLevel").value(hasItem(DEFAULT_VIOLATION_LEVEL)))
            .andExpect(jsonPath("$.[*].exemptionProcessId").value(hasItem(DEFAULT_EXEMPTION_PROCESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].exemptedBy").value(hasItem(DEFAULT_EXEMPTED_BY)))
            .andExpect(jsonPath("$.[*].exemptionDate").value(hasItem(DEFAULT_EXEMPTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationViolation() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get the applicationViolation
        restApplicationViolationMockMvc.perform(get("/api/application-violations/{id}", applicationViolation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationViolation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.isEligibleForExemption").value(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION.intValue()))
            .andExpect(jsonPath("$.isExempted").value(DEFAULT_IS_EXEMPTED.intValue()))
            .andExpect(jsonPath("$.violationLevel").value(DEFAULT_VIOLATION_LEVEL))
            .andExpect(jsonPath("$.exemptionProcessId").value(DEFAULT_EXEMPTION_PROCESS_ID.intValue()))
            .andExpect(jsonPath("$.exemptedBy").value(DEFAULT_EXEMPTED_BY))
            .andExpect(jsonPath("$.exemptionDate").value(DEFAULT_EXEMPTION_DATE.toString()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getApplicationViolationsByIdFiltering() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        Long id = applicationViolation.getId();

        defaultApplicationViolationShouldBeFound("id.equals=" + id);
        defaultApplicationViolationShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationViolationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationViolationShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationViolationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationViolationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where code equals to DEFAULT_CODE
        defaultApplicationViolationShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the applicationViolationList where code equals to UPDATED_CODE
        defaultApplicationViolationShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where code not equals to DEFAULT_CODE
        defaultApplicationViolationShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the applicationViolationList where code not equals to UPDATED_CODE
        defaultApplicationViolationShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where code in DEFAULT_CODE or UPDATED_CODE
        defaultApplicationViolationShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the applicationViolationList where code equals to UPDATED_CODE
        defaultApplicationViolationShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where code is not null
        defaultApplicationViolationShouldBeFound("code.specified=true");

        // Get all the applicationViolationList where code is null
        defaultApplicationViolationShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationViolationsByCodeContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where code contains DEFAULT_CODE
        defaultApplicationViolationShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the applicationViolationList where code contains UPDATED_CODE
        defaultApplicationViolationShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where code does not contain DEFAULT_CODE
        defaultApplicationViolationShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the applicationViolationList where code does not contain UPDATED_CODE
        defaultApplicationViolationShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption equals to DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.equals=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption equals to UPDATED_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.equals=" + UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption not equals to DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.notEquals=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption not equals to UPDATED_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.notEquals=" + UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption in DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION or UPDATED_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.in=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION + "," + UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption equals to UPDATED_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.in=" + UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption is not null
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.specified=true");

        // Get all the applicationViolationList where isEligibleForExemption is null
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption is greater than or equal to DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.greaterThanOrEqual=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption is greater than or equal to UPDATED_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.greaterThanOrEqual=" + UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption is less than or equal to DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.lessThanOrEqual=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption is less than or equal to SMALLER_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.lessThanOrEqual=" + SMALLER_IS_ELIGIBLE_FOR_EXEMPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption is less than DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.lessThan=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption is less than UPDATED_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.lessThan=" + UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsEligibleForExemptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isEligibleForExemption is greater than DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldNotBeFound("isEligibleForExemption.greaterThan=" + DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION);

        // Get all the applicationViolationList where isEligibleForExemption is greater than SMALLER_IS_ELIGIBLE_FOR_EXEMPTION
        defaultApplicationViolationShouldBeFound("isEligibleForExemption.greaterThan=" + SMALLER_IS_ELIGIBLE_FOR_EXEMPTION);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted equals to DEFAULT_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.equals=" + DEFAULT_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted equals to UPDATED_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.equals=" + UPDATED_IS_EXEMPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted not equals to DEFAULT_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.notEquals=" + DEFAULT_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted not equals to UPDATED_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.notEquals=" + UPDATED_IS_EXEMPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted in DEFAULT_IS_EXEMPTED or UPDATED_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.in=" + DEFAULT_IS_EXEMPTED + "," + UPDATED_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted equals to UPDATED_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.in=" + UPDATED_IS_EXEMPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted is not null
        defaultApplicationViolationShouldBeFound("isExempted.specified=true");

        // Get all the applicationViolationList where isExempted is null
        defaultApplicationViolationShouldNotBeFound("isExempted.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted is greater than or equal to DEFAULT_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.greaterThanOrEqual=" + DEFAULT_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted is greater than or equal to UPDATED_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.greaterThanOrEqual=" + UPDATED_IS_EXEMPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted is less than or equal to DEFAULT_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.lessThanOrEqual=" + DEFAULT_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted is less than or equal to SMALLER_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.lessThanOrEqual=" + SMALLER_IS_EXEMPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted is less than DEFAULT_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.lessThan=" + DEFAULT_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted is less than UPDATED_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.lessThan=" + UPDATED_IS_EXEMPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByIsExemptedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where isExempted is greater than DEFAULT_IS_EXEMPTED
        defaultApplicationViolationShouldNotBeFound("isExempted.greaterThan=" + DEFAULT_IS_EXEMPTED);

        // Get all the applicationViolationList where isExempted is greater than SMALLER_IS_EXEMPTED
        defaultApplicationViolationShouldBeFound("isExempted.greaterThan=" + SMALLER_IS_EXEMPTED);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByViolationLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where violationLevel equals to DEFAULT_VIOLATION_LEVEL
        defaultApplicationViolationShouldBeFound("violationLevel.equals=" + DEFAULT_VIOLATION_LEVEL);

        // Get all the applicationViolationList where violationLevel equals to UPDATED_VIOLATION_LEVEL
        defaultApplicationViolationShouldNotBeFound("violationLevel.equals=" + UPDATED_VIOLATION_LEVEL);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByViolationLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where violationLevel not equals to DEFAULT_VIOLATION_LEVEL
        defaultApplicationViolationShouldNotBeFound("violationLevel.notEquals=" + DEFAULT_VIOLATION_LEVEL);

        // Get all the applicationViolationList where violationLevel not equals to UPDATED_VIOLATION_LEVEL
        defaultApplicationViolationShouldBeFound("violationLevel.notEquals=" + UPDATED_VIOLATION_LEVEL);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByViolationLevelIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where violationLevel in DEFAULT_VIOLATION_LEVEL or UPDATED_VIOLATION_LEVEL
        defaultApplicationViolationShouldBeFound("violationLevel.in=" + DEFAULT_VIOLATION_LEVEL + "," + UPDATED_VIOLATION_LEVEL);

        // Get all the applicationViolationList where violationLevel equals to UPDATED_VIOLATION_LEVEL
        defaultApplicationViolationShouldNotBeFound("violationLevel.in=" + UPDATED_VIOLATION_LEVEL);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByViolationLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where violationLevel is not null
        defaultApplicationViolationShouldBeFound("violationLevel.specified=true");

        // Get all the applicationViolationList where violationLevel is null
        defaultApplicationViolationShouldNotBeFound("violationLevel.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationViolationsByViolationLevelContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where violationLevel contains DEFAULT_VIOLATION_LEVEL
        defaultApplicationViolationShouldBeFound("violationLevel.contains=" + DEFAULT_VIOLATION_LEVEL);

        // Get all the applicationViolationList where violationLevel contains UPDATED_VIOLATION_LEVEL
        defaultApplicationViolationShouldNotBeFound("violationLevel.contains=" + UPDATED_VIOLATION_LEVEL);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByViolationLevelNotContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where violationLevel does not contain DEFAULT_VIOLATION_LEVEL
        defaultApplicationViolationShouldNotBeFound("violationLevel.doesNotContain=" + DEFAULT_VIOLATION_LEVEL);

        // Get all the applicationViolationList where violationLevel does not contain UPDATED_VIOLATION_LEVEL
        defaultApplicationViolationShouldBeFound("violationLevel.doesNotContain=" + UPDATED_VIOLATION_LEVEL);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId equals to DEFAULT_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.equals=" + DEFAULT_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId equals to UPDATED_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.equals=" + UPDATED_EXEMPTION_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId not equals to DEFAULT_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.notEquals=" + DEFAULT_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId not equals to UPDATED_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.notEquals=" + UPDATED_EXEMPTION_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId in DEFAULT_EXEMPTION_PROCESS_ID or UPDATED_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.in=" + DEFAULT_EXEMPTION_PROCESS_ID + "," + UPDATED_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId equals to UPDATED_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.in=" + UPDATED_EXEMPTION_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId is not null
        defaultApplicationViolationShouldBeFound("exemptionProcessId.specified=true");

        // Get all the applicationViolationList where exemptionProcessId is null
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId is greater than or equal to DEFAULT_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.greaterThanOrEqual=" + DEFAULT_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId is greater than or equal to UPDATED_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.greaterThanOrEqual=" + UPDATED_EXEMPTION_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId is less than or equal to DEFAULT_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.lessThanOrEqual=" + DEFAULT_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId is less than or equal to SMALLER_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.lessThanOrEqual=" + SMALLER_EXEMPTION_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId is less than DEFAULT_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.lessThan=" + DEFAULT_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId is less than UPDATED_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.lessThan=" + UPDATED_EXEMPTION_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionProcessIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionProcessId is greater than DEFAULT_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldNotBeFound("exemptionProcessId.greaterThan=" + DEFAULT_EXEMPTION_PROCESS_ID);

        // Get all the applicationViolationList where exemptionProcessId is greater than SMALLER_EXEMPTION_PROCESS_ID
        defaultApplicationViolationShouldBeFound("exemptionProcessId.greaterThan=" + SMALLER_EXEMPTION_PROCESS_ID);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptedBy equals to DEFAULT_EXEMPTED_BY
        defaultApplicationViolationShouldBeFound("exemptedBy.equals=" + DEFAULT_EXEMPTED_BY);

        // Get all the applicationViolationList where exemptedBy equals to UPDATED_EXEMPTED_BY
        defaultApplicationViolationShouldNotBeFound("exemptedBy.equals=" + UPDATED_EXEMPTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptedBy not equals to DEFAULT_EXEMPTED_BY
        defaultApplicationViolationShouldNotBeFound("exemptedBy.notEquals=" + DEFAULT_EXEMPTED_BY);

        // Get all the applicationViolationList where exemptedBy not equals to UPDATED_EXEMPTED_BY
        defaultApplicationViolationShouldBeFound("exemptedBy.notEquals=" + UPDATED_EXEMPTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptedBy in DEFAULT_EXEMPTED_BY or UPDATED_EXEMPTED_BY
        defaultApplicationViolationShouldBeFound("exemptedBy.in=" + DEFAULT_EXEMPTED_BY + "," + UPDATED_EXEMPTED_BY);

        // Get all the applicationViolationList where exemptedBy equals to UPDATED_EXEMPTED_BY
        defaultApplicationViolationShouldNotBeFound("exemptedBy.in=" + UPDATED_EXEMPTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptedBy is not null
        defaultApplicationViolationShouldBeFound("exemptedBy.specified=true");

        // Get all the applicationViolationList where exemptedBy is null
        defaultApplicationViolationShouldNotBeFound("exemptedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationViolationsByExemptedByContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptedBy contains DEFAULT_EXEMPTED_BY
        defaultApplicationViolationShouldBeFound("exemptedBy.contains=" + DEFAULT_EXEMPTED_BY);

        // Get all the applicationViolationList where exemptedBy contains UPDATED_EXEMPTED_BY
        defaultApplicationViolationShouldNotBeFound("exemptedBy.contains=" + UPDATED_EXEMPTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptedBy does not contain DEFAULT_EXEMPTED_BY
        defaultApplicationViolationShouldNotBeFound("exemptedBy.doesNotContain=" + DEFAULT_EXEMPTED_BY);

        // Get all the applicationViolationList where exemptedBy does not contain UPDATED_EXEMPTED_BY
        defaultApplicationViolationShouldBeFound("exemptedBy.doesNotContain=" + UPDATED_EXEMPTED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionDate equals to DEFAULT_EXEMPTION_DATE
        defaultApplicationViolationShouldBeFound("exemptionDate.equals=" + DEFAULT_EXEMPTION_DATE);

        // Get all the applicationViolationList where exemptionDate equals to UPDATED_EXEMPTION_DATE
        defaultApplicationViolationShouldNotBeFound("exemptionDate.equals=" + UPDATED_EXEMPTION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionDate not equals to DEFAULT_EXEMPTION_DATE
        defaultApplicationViolationShouldNotBeFound("exemptionDate.notEquals=" + DEFAULT_EXEMPTION_DATE);

        // Get all the applicationViolationList where exemptionDate not equals to UPDATED_EXEMPTION_DATE
        defaultApplicationViolationShouldBeFound("exemptionDate.notEquals=" + UPDATED_EXEMPTION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionDate in DEFAULT_EXEMPTION_DATE or UPDATED_EXEMPTION_DATE
        defaultApplicationViolationShouldBeFound("exemptionDate.in=" + DEFAULT_EXEMPTION_DATE + "," + UPDATED_EXEMPTION_DATE);

        // Get all the applicationViolationList where exemptionDate equals to UPDATED_EXEMPTION_DATE
        defaultApplicationViolationShouldNotBeFound("exemptionDate.in=" + UPDATED_EXEMPTION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByExemptionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where exemptionDate is not null
        defaultApplicationViolationShouldBeFound("exemptionDate.specified=true");

        // Get all the applicationViolationList where exemptionDate is null
        defaultApplicationViolationShouldNotBeFound("exemptionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId equals to DEFAULT_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.equals=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId equals to UPDATED_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.equals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId not equals to DEFAULT_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.notEquals=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId not equals to UPDATED_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.notEquals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId in DEFAULT_APPLICATION_ID or UPDATED_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.in=" + DEFAULT_APPLICATION_ID + "," + UPDATED_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId equals to UPDATED_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.in=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId is not null
        defaultApplicationViolationShouldBeFound("applicationId.specified=true");

        // Get all the applicationViolationList where applicationId is null
        defaultApplicationViolationShouldNotBeFound("applicationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId is greater than or equal to DEFAULT_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.greaterThanOrEqual=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId is greater than or equal to UPDATED_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.greaterThanOrEqual=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId is less than or equal to DEFAULT_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.lessThanOrEqual=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId is less than or equal to SMALLER_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.lessThanOrEqual=" + SMALLER_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId is less than DEFAULT_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.lessThan=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId is less than UPDATED_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.lessThan=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where applicationId is greater than DEFAULT_APPLICATION_ID
        defaultApplicationViolationShouldNotBeFound("applicationId.greaterThan=" + DEFAULT_APPLICATION_ID);

        // Get all the applicationViolationList where applicationId is greater than SMALLER_APPLICATION_ID
        defaultApplicationViolationShouldBeFound("applicationId.greaterThan=" + SMALLER_APPLICATION_ID);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationViolationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationViolationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationViolationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicationViolationShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicationViolationList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicationViolationShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationViolationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationViolationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationViolationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdBy is not null
        defaultApplicationViolationShouldBeFound("createdBy.specified=true");

        // Get all the applicationViolationList where createdBy is null
        defaultApplicationViolationShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdBy contains DEFAULT_CREATED_BY
        defaultApplicationViolationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the applicationViolationList where createdBy contains UPDATED_CREATED_BY
        defaultApplicationViolationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultApplicationViolationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the applicationViolationList where createdBy does not contain UPDATED_CREATED_BY
        defaultApplicationViolationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultApplicationViolationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationViolationList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationViolationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultApplicationViolationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationViolationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultApplicationViolationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultApplicationViolationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the applicationViolationList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationViolationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where createdDate is not null
        defaultApplicationViolationShouldBeFound("createdDate.specified=true");

        // Get all the applicationViolationList where createdDate is null
        defaultApplicationViolationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationViolationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationViolationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationViolationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationViolationShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationViolationList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationViolationShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApplicationViolationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the applicationViolationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationViolationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedBy is not null
        defaultApplicationViolationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the applicationViolationList where lastModifiedBy is null
        defaultApplicationViolationShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApplicationViolationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationViolationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApplicationViolationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApplicationViolationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationViolationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApplicationViolationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationViolationShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationViolationList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationViolationShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationViolationShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationViolationList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationViolationShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultApplicationViolationShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the applicationViolationList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationViolationShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        // Get all the applicationViolationList where lastModifiedDate is not null
        defaultApplicationViolationShouldBeFound("lastModifiedDate.specified=true");

        // Get all the applicationViolationList where lastModifiedDate is null
        defaultApplicationViolationShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationViolationsByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);
        Application application = ApplicationResourceIT.createEntity(em);
        em.persist(application);
        em.flush();
        applicationViolation.setApplication(application);
        applicationViolationRepository.saveAndFlush(applicationViolation);
        Long applicationId = application.getId();

        // Get all the applicationViolationList where application equals to applicationId
        defaultApplicationViolationShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the applicationViolationList where application equals to applicationId + 1
        defaultApplicationViolationShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationViolationShouldBeFound(String filter) throws Exception {
        restApplicationViolationMockMvc.perform(get("/api/application-violations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationViolation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].isEligibleForExemption").value(hasItem(DEFAULT_IS_ELIGIBLE_FOR_EXEMPTION.intValue())))
            .andExpect(jsonPath("$.[*].isExempted").value(hasItem(DEFAULT_IS_EXEMPTED.intValue())))
            .andExpect(jsonPath("$.[*].violationLevel").value(hasItem(DEFAULT_VIOLATION_LEVEL)))
            .andExpect(jsonPath("$.[*].exemptionProcessId").value(hasItem(DEFAULT_EXEMPTION_PROCESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].exemptedBy").value(hasItem(DEFAULT_EXEMPTED_BY)))
            .andExpect(jsonPath("$.[*].exemptionDate").value(hasItem(DEFAULT_EXEMPTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restApplicationViolationMockMvc.perform(get("/api/application-violations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationViolationShouldNotBeFound(String filter) throws Exception {
        restApplicationViolationMockMvc.perform(get("/api/application-violations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationViolationMockMvc.perform(get("/api/application-violations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationViolation() throws Exception {
        // Get the applicationViolation
        restApplicationViolationMockMvc.perform(get("/api/application-violations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationViolation() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        int databaseSizeBeforeUpdate = applicationViolationRepository.findAll().size();

        // Update the applicationViolation
        ApplicationViolation updatedApplicationViolation = applicationViolationRepository.findById(applicationViolation.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationViolation are not directly saved in db
        em.detach(updatedApplicationViolation);
        updatedApplicationViolation
            .code(UPDATED_CODE)
            .isEligibleForExemption(UPDATED_IS_ELIGIBLE_FOR_EXEMPTION)
            .isExempted(UPDATED_IS_EXEMPTED)
            .violationLevel(UPDATED_VIOLATION_LEVEL)
            .exemptionProcessId(UPDATED_EXEMPTION_PROCESS_ID)
            .exemptedBy(UPDATED_EXEMPTED_BY)
            .exemptionDate(UPDATED_EXEMPTION_DATE)
            .applicationId(UPDATED_APPLICATION_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(updatedApplicationViolation);

        restApplicationViolationMockMvc.perform(put("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeUpdate);
        ApplicationViolation testApplicationViolation = applicationViolationList.get(applicationViolationList.size() - 1);
        assertThat(testApplicationViolation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApplicationViolation.getIsEligibleForExemption()).isEqualTo(UPDATED_IS_ELIGIBLE_FOR_EXEMPTION);
        assertThat(testApplicationViolation.getIsExempted()).isEqualTo(UPDATED_IS_EXEMPTED);
        assertThat(testApplicationViolation.getViolationLevel()).isEqualTo(UPDATED_VIOLATION_LEVEL);
        assertThat(testApplicationViolation.getExemptionProcessId()).isEqualTo(UPDATED_EXEMPTION_PROCESS_ID);
        assertThat(testApplicationViolation.getExemptedBy()).isEqualTo(UPDATED_EXEMPTED_BY);
        assertThat(testApplicationViolation.getExemptionDate()).isEqualTo(UPDATED_EXEMPTION_DATE);
        assertThat(testApplicationViolation.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testApplicationViolation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationViolation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApplicationViolation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApplicationViolation.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationViolation() throws Exception {
        int databaseSizeBeforeUpdate = applicationViolationRepository.findAll().size();

        // Create the ApplicationViolation
        ApplicationViolationDTO applicationViolationDTO = applicationViolationMapper.toDto(applicationViolation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationViolationMockMvc.perform(put("/api/application-violations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationViolationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationViolation in the database
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationViolation() throws Exception {
        // Initialize the database
        applicationViolationRepository.saveAndFlush(applicationViolation);

        int databaseSizeBeforeDelete = applicationViolationRepository.findAll().size();

        // Delete the applicationViolation
        restApplicationViolationMockMvc.perform(delete("/api/application-violations/{id}", applicationViolation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationViolation> applicationViolationList = applicationViolationRepository.findAll();
        assertThat(applicationViolationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
