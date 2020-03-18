package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.Application;
import com.isoft.dls.domain.ApplicationPhase;
import com.isoft.dls.domain.ServiceRequest;
import com.isoft.dls.domain.ApplicationViolation;
import com.isoft.dls.domain.ApplicationType;
import com.isoft.dls.repository.ApplicationRepository;
import com.isoft.dls.service.ApplicationService;
import com.isoft.dls.service.dto.ApplicationDTO;
import com.isoft.dls.service.mapper.ApplicationMapper;
import com.isoft.dls.service.dto.ApplicationCriteria;
import com.isoft.dls.service.ApplicationQueryService;

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

import com.isoft.dls.domain.enumeration.ApplicationStatus;
import com.isoft.dls.domain.enumeration.PhaseType;
/**
 * Integration tests for the {@link ApplicationResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ApplicationResourceIT {

    private static final ApplicationStatus DEFAULT_STATUS = ApplicationStatus.DRAFT;
    private static final ApplicationStatus UPDATED_STATUS = ApplicationStatus.UNDER_PROCESSING;

    private static final String DEFAULT_STATUS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PhaseType DEFAULT_ACTIVE_PHASE = PhaseType.CUSTOMER_ELIGIBILITY;
    private static final PhaseType UPDATED_ACTIVE_PHASE = PhaseType.DRIVING_LEARNING_FILE_PROCESSING;

    private static final String DEFAULT_CONFIRMED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIRMED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CONFIRMATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRMATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REJECTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_REJECTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REJECTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PROCESS_INSTANCE_ID = 1L;
    private static final Long UPDATED_PROCESS_INSTANCE_ID = 2L;
    private static final Long SMALLER_PROCESS_INSTANCE_ID = 1L - 1L;

    private static final String DEFAULT_CHANNEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ARABIC_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARABIC_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRADE_LICENSE_NO = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_LICENSE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_CORPORATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_CORPORATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ARABIC_CORPORATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARABIC_CORPORATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_TYPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_USER_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONA_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationQueryService applicationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationMockMvc;

    private Application application;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .status(DEFAULT_STATUS)
            .statusDescription(DEFAULT_STATUS_DESCRIPTION)
            .statusDate(DEFAULT_STATUS_DATE)
            .activePhase(DEFAULT_ACTIVE_PHASE)
            .confirmedBy(DEFAULT_CONFIRMED_BY)
            .confirmationDate(DEFAULT_CONFIRMATION_DATE)
            .rejectedBy(DEFAULT_REJECTED_BY)
            .rejectionReason(DEFAULT_REJECTION_REASON)
            .rejectionDate(DEFAULT_REJECTION_DATE)
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID)
            .channelCode(DEFAULT_CHANNEL_CODE)
            .channelDescription(DEFAULT_CHANNEL_DESCRIPTION)
            .customerId(DEFAULT_CUSTOMER_ID)
            .englishCustomerName(DEFAULT_ENGLISH_CUSTOMER_NAME)
            .arabicCustomerName(DEFAULT_ARABIC_CUSTOMER_NAME)
            .tradeLicenseNo(DEFAULT_TRADE_LICENSE_NO)
            .englishCorporateName(DEFAULT_ENGLISH_CORPORATE_NAME)
            .arabicCorporateName(DEFAULT_ARABIC_CORPORATE_NAME)
            .userType(DEFAULT_USER_TYPE)
            .userTypeDescription(DEFAULT_USER_TYPE_DESCRIPTION)
            .userRole(DEFAULT_USER_ROLE)
            .applicationCriteria(DEFAULT_APPLICATION_CRITERIA)
            .persona(DEFAULT_PERSONA)
            .personaVersion(DEFAULT_PERSONA_VERSION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return application;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity(EntityManager em) {
        Application application = new Application()
            .status(UPDATED_STATUS)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusDate(UPDATED_STATUS_DATE)
            .activePhase(UPDATED_ACTIVE_PHASE)
            .confirmedBy(UPDATED_CONFIRMED_BY)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .rejectedBy(UPDATED_REJECTED_BY)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .rejectionDate(UPDATED_REJECTION_DATE)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelDescription(UPDATED_CHANNEL_DESCRIPTION)
            .customerId(UPDATED_CUSTOMER_ID)
            .englishCustomerName(UPDATED_ENGLISH_CUSTOMER_NAME)
            .arabicCustomerName(UPDATED_ARABIC_CUSTOMER_NAME)
            .tradeLicenseNo(UPDATED_TRADE_LICENSE_NO)
            .englishCorporateName(UPDATED_ENGLISH_CORPORATE_NAME)
            .arabicCorporateName(UPDATED_ARABIC_CORPORATE_NAME)
            .userType(UPDATED_USER_TYPE)
            .userTypeDescription(UPDATED_USER_TYPE_DESCRIPTION)
            .userRole(UPDATED_USER_ROLE)
            .applicationCriteria(UPDATED_APPLICATION_CRITERIA)
            .persona(UPDATED_PERSONA)
            .personaVersion(UPDATED_PERSONA_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return application;
    }

    @BeforeEach
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);
        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplication.getStatusDescription()).isEqualTo(DEFAULT_STATUS_DESCRIPTION);
        assertThat(testApplication.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testApplication.getActivePhase()).isEqualTo(DEFAULT_ACTIVE_PHASE);
        assertThat(testApplication.getConfirmedBy()).isEqualTo(DEFAULT_CONFIRMED_BY);
        assertThat(testApplication.getConfirmationDate()).isEqualTo(DEFAULT_CONFIRMATION_DATE);
        assertThat(testApplication.getRejectedBy()).isEqualTo(DEFAULT_REJECTED_BY);
        assertThat(testApplication.getRejectionReason()).isEqualTo(DEFAULT_REJECTION_REASON);
        assertThat(testApplication.getRejectionDate()).isEqualTo(DEFAULT_REJECTION_DATE);
        assertThat(testApplication.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
        assertThat(testApplication.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testApplication.getChannelDescription()).isEqualTo(DEFAULT_CHANNEL_DESCRIPTION);
        assertThat(testApplication.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testApplication.getEnglishCustomerName()).isEqualTo(DEFAULT_ENGLISH_CUSTOMER_NAME);
        assertThat(testApplication.getArabicCustomerName()).isEqualTo(DEFAULT_ARABIC_CUSTOMER_NAME);
        assertThat(testApplication.getTradeLicenseNo()).isEqualTo(DEFAULT_TRADE_LICENSE_NO);
        assertThat(testApplication.getEnglishCorporateName()).isEqualTo(DEFAULT_ENGLISH_CORPORATE_NAME);
        assertThat(testApplication.getArabicCorporateName()).isEqualTo(DEFAULT_ARABIC_CORPORATE_NAME);
        assertThat(testApplication.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testApplication.getUserTypeDescription()).isEqualTo(DEFAULT_USER_TYPE_DESCRIPTION);
        assertThat(testApplication.getUserRole()).isEqualTo(DEFAULT_USER_ROLE);
        assertThat(testApplication.getApplicationCriteria()).isEqualTo(DEFAULT_APPLICATION_CRITERIA);
        assertThat(testApplication.getPersona()).isEqualTo(DEFAULT_PERSONA);
        assertThat(testApplication.getPersonaVersion()).isEqualTo(DEFAULT_PERSONA_VERSION);
        assertThat(testApplication.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplication.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApplication.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApplication.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(1L);
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setStatus(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setStatusDescription(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setStatusDate(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivePhaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setActivePhase(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setChannelCode(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setChannelDescription(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setUserType(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setUserTypeDescription(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setUserRole(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setCreatedBy(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setCreatedDate(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc.perform(post("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].activePhase").value(hasItem(DEFAULT_ACTIVE_PHASE.toString())))
            .andExpect(jsonPath("$.[*].confirmedBy").value(hasItem(DEFAULT_CONFIRMED_BY)))
            .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].rejectedBy").value(hasItem(DEFAULT_REJECTED_BY)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)))
            .andExpect(jsonPath("$.[*].rejectionDate").value(hasItem(DEFAULT_REJECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].channelDescription").value(hasItem(DEFAULT_CHANNEL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].englishCustomerName").value(hasItem(DEFAULT_ENGLISH_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].arabicCustomerName").value(hasItem(DEFAULT_ARABIC_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].tradeLicenseNo").value(hasItem(DEFAULT_TRADE_LICENSE_NO)))
            .andExpect(jsonPath("$.[*].englishCorporateName").value(hasItem(DEFAULT_ENGLISH_CORPORATE_NAME)))
            .andExpect(jsonPath("$.[*].arabicCorporateName").value(hasItem(DEFAULT_ARABIC_CORPORATE_NAME)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].userTypeDescription").value(hasItem(DEFAULT_USER_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE)))
            .andExpect(jsonPath("$.[*].applicationCriteria").value(hasItem(DEFAULT_APPLICATION_CRITERIA)))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)))
            .andExpect(jsonPath("$.[*].personaVersion").value(hasItem(DEFAULT_PERSONA_VERSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusDescription").value(DEFAULT_STATUS_DESCRIPTION))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.activePhase").value(DEFAULT_ACTIVE_PHASE.toString()))
            .andExpect(jsonPath("$.confirmedBy").value(DEFAULT_CONFIRMED_BY))
            .andExpect(jsonPath("$.confirmationDate").value(DEFAULT_CONFIRMATION_DATE.toString()))
            .andExpect(jsonPath("$.rejectedBy").value(DEFAULT_REJECTED_BY))
            .andExpect(jsonPath("$.rejectionReason").value(DEFAULT_REJECTION_REASON))
            .andExpect(jsonPath("$.rejectionDate").value(DEFAULT_REJECTION_DATE.toString()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID.intValue()))
            .andExpect(jsonPath("$.channelCode").value(DEFAULT_CHANNEL_CODE))
            .andExpect(jsonPath("$.channelDescription").value(DEFAULT_CHANNEL_DESCRIPTION))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.englishCustomerName").value(DEFAULT_ENGLISH_CUSTOMER_NAME))
            .andExpect(jsonPath("$.arabicCustomerName").value(DEFAULT_ARABIC_CUSTOMER_NAME))
            .andExpect(jsonPath("$.tradeLicenseNo").value(DEFAULT_TRADE_LICENSE_NO))
            .andExpect(jsonPath("$.englishCorporateName").value(DEFAULT_ENGLISH_CORPORATE_NAME))
            .andExpect(jsonPath("$.arabicCorporateName").value(DEFAULT_ARABIC_CORPORATE_NAME))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE))
            .andExpect(jsonPath("$.userTypeDescription").value(DEFAULT_USER_TYPE_DESCRIPTION))
            .andExpect(jsonPath("$.userRole").value(DEFAULT_USER_ROLE))
            .andExpect(jsonPath("$.applicationCriteria").value(DEFAULT_APPLICATION_CRITERIA))
            .andExpect(jsonPath("$.persona").value(DEFAULT_PERSONA))
            .andExpect(jsonPath("$.personaVersion").value(DEFAULT_PERSONA_VERSION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        Long id = application.getId();

        defaultApplicationShouldBeFound("id.equals=" + id);
        defaultApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where status equals to DEFAULT_STATUS
        defaultApplicationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the applicationList where status equals to UPDATED_STATUS
        defaultApplicationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where status not equals to DEFAULT_STATUS
        defaultApplicationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the applicationList where status not equals to UPDATED_STATUS
        defaultApplicationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApplicationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the applicationList where status equals to UPDATED_STATUS
        defaultApplicationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where status is not null
        defaultApplicationShouldBeFound("status.specified=true");

        // Get all the applicationList where status is null
        defaultApplicationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDescription equals to DEFAULT_STATUS_DESCRIPTION
        defaultApplicationShouldBeFound("statusDescription.equals=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the applicationList where statusDescription equals to UPDATED_STATUS_DESCRIPTION
        defaultApplicationShouldNotBeFound("statusDescription.equals=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDescription not equals to DEFAULT_STATUS_DESCRIPTION
        defaultApplicationShouldNotBeFound("statusDescription.notEquals=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the applicationList where statusDescription not equals to UPDATED_STATUS_DESCRIPTION
        defaultApplicationShouldBeFound("statusDescription.notEquals=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDescription in DEFAULT_STATUS_DESCRIPTION or UPDATED_STATUS_DESCRIPTION
        defaultApplicationShouldBeFound("statusDescription.in=" + DEFAULT_STATUS_DESCRIPTION + "," + UPDATED_STATUS_DESCRIPTION);

        // Get all the applicationList where statusDescription equals to UPDATED_STATUS_DESCRIPTION
        defaultApplicationShouldNotBeFound("statusDescription.in=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDescription is not null
        defaultApplicationShouldBeFound("statusDescription.specified=true");

        // Get all the applicationList where statusDescription is null
        defaultApplicationShouldNotBeFound("statusDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByStatusDescriptionContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDescription contains DEFAULT_STATUS_DESCRIPTION
        defaultApplicationShouldBeFound("statusDescription.contains=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the applicationList where statusDescription contains UPDATED_STATUS_DESCRIPTION
        defaultApplicationShouldNotBeFound("statusDescription.contains=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDescription does not contain DEFAULT_STATUS_DESCRIPTION
        defaultApplicationShouldNotBeFound("statusDescription.doesNotContain=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the applicationList where statusDescription does not contain UPDATED_STATUS_DESCRIPTION
        defaultApplicationShouldBeFound("statusDescription.doesNotContain=" + UPDATED_STATUS_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllApplicationsByStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDate equals to DEFAULT_STATUS_DATE
        defaultApplicationShouldBeFound("statusDate.equals=" + DEFAULT_STATUS_DATE);

        // Get all the applicationList where statusDate equals to UPDATED_STATUS_DATE
        defaultApplicationShouldNotBeFound("statusDate.equals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDate not equals to DEFAULT_STATUS_DATE
        defaultApplicationShouldNotBeFound("statusDate.notEquals=" + DEFAULT_STATUS_DATE);

        // Get all the applicationList where statusDate not equals to UPDATED_STATUS_DATE
        defaultApplicationShouldBeFound("statusDate.notEquals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDate in DEFAULT_STATUS_DATE or UPDATED_STATUS_DATE
        defaultApplicationShouldBeFound("statusDate.in=" + DEFAULT_STATUS_DATE + "," + UPDATED_STATUS_DATE);

        // Get all the applicationList where statusDate equals to UPDATED_STATUS_DATE
        defaultApplicationShouldNotBeFound("statusDate.in=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where statusDate is not null
        defaultApplicationShouldBeFound("statusDate.specified=true");

        // Get all the applicationList where statusDate is null
        defaultApplicationShouldNotBeFound("statusDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByActivePhaseIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where activePhase equals to DEFAULT_ACTIVE_PHASE
        defaultApplicationShouldBeFound("activePhase.equals=" + DEFAULT_ACTIVE_PHASE);

        // Get all the applicationList where activePhase equals to UPDATED_ACTIVE_PHASE
        defaultApplicationShouldNotBeFound("activePhase.equals=" + UPDATED_ACTIVE_PHASE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByActivePhaseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where activePhase not equals to DEFAULT_ACTIVE_PHASE
        defaultApplicationShouldNotBeFound("activePhase.notEquals=" + DEFAULT_ACTIVE_PHASE);

        // Get all the applicationList where activePhase not equals to UPDATED_ACTIVE_PHASE
        defaultApplicationShouldBeFound("activePhase.notEquals=" + UPDATED_ACTIVE_PHASE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByActivePhaseIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where activePhase in DEFAULT_ACTIVE_PHASE or UPDATED_ACTIVE_PHASE
        defaultApplicationShouldBeFound("activePhase.in=" + DEFAULT_ACTIVE_PHASE + "," + UPDATED_ACTIVE_PHASE);

        // Get all the applicationList where activePhase equals to UPDATED_ACTIVE_PHASE
        defaultApplicationShouldNotBeFound("activePhase.in=" + UPDATED_ACTIVE_PHASE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByActivePhaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where activePhase is not null
        defaultApplicationShouldBeFound("activePhase.specified=true");

        // Get all the applicationList where activePhase is null
        defaultApplicationShouldNotBeFound("activePhase.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmedBy equals to DEFAULT_CONFIRMED_BY
        defaultApplicationShouldBeFound("confirmedBy.equals=" + DEFAULT_CONFIRMED_BY);

        // Get all the applicationList where confirmedBy equals to UPDATED_CONFIRMED_BY
        defaultApplicationShouldNotBeFound("confirmedBy.equals=" + UPDATED_CONFIRMED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmedBy not equals to DEFAULT_CONFIRMED_BY
        defaultApplicationShouldNotBeFound("confirmedBy.notEquals=" + DEFAULT_CONFIRMED_BY);

        // Get all the applicationList where confirmedBy not equals to UPDATED_CONFIRMED_BY
        defaultApplicationShouldBeFound("confirmedBy.notEquals=" + UPDATED_CONFIRMED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmedBy in DEFAULT_CONFIRMED_BY or UPDATED_CONFIRMED_BY
        defaultApplicationShouldBeFound("confirmedBy.in=" + DEFAULT_CONFIRMED_BY + "," + UPDATED_CONFIRMED_BY);

        // Get all the applicationList where confirmedBy equals to UPDATED_CONFIRMED_BY
        defaultApplicationShouldNotBeFound("confirmedBy.in=" + UPDATED_CONFIRMED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmedBy is not null
        defaultApplicationShouldBeFound("confirmedBy.specified=true");

        // Get all the applicationList where confirmedBy is null
        defaultApplicationShouldNotBeFound("confirmedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByConfirmedByContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmedBy contains DEFAULT_CONFIRMED_BY
        defaultApplicationShouldBeFound("confirmedBy.contains=" + DEFAULT_CONFIRMED_BY);

        // Get all the applicationList where confirmedBy contains UPDATED_CONFIRMED_BY
        defaultApplicationShouldNotBeFound("confirmedBy.contains=" + UPDATED_CONFIRMED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmedBy does not contain DEFAULT_CONFIRMED_BY
        defaultApplicationShouldNotBeFound("confirmedBy.doesNotContain=" + DEFAULT_CONFIRMED_BY);

        // Get all the applicationList where confirmedBy does not contain UPDATED_CONFIRMED_BY
        defaultApplicationShouldBeFound("confirmedBy.doesNotContain=" + UPDATED_CONFIRMED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationsByConfirmationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmationDate equals to DEFAULT_CONFIRMATION_DATE
        defaultApplicationShouldBeFound("confirmationDate.equals=" + DEFAULT_CONFIRMATION_DATE);

        // Get all the applicationList where confirmationDate equals to UPDATED_CONFIRMATION_DATE
        defaultApplicationShouldNotBeFound("confirmationDate.equals=" + UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmationDate not equals to DEFAULT_CONFIRMATION_DATE
        defaultApplicationShouldNotBeFound("confirmationDate.notEquals=" + DEFAULT_CONFIRMATION_DATE);

        // Get all the applicationList where confirmationDate not equals to UPDATED_CONFIRMATION_DATE
        defaultApplicationShouldBeFound("confirmationDate.notEquals=" + UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmationDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmationDate in DEFAULT_CONFIRMATION_DATE or UPDATED_CONFIRMATION_DATE
        defaultApplicationShouldBeFound("confirmationDate.in=" + DEFAULT_CONFIRMATION_DATE + "," + UPDATED_CONFIRMATION_DATE);

        // Get all the applicationList where confirmationDate equals to UPDATED_CONFIRMATION_DATE
        defaultApplicationShouldNotBeFound("confirmationDate.in=" + UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByConfirmationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where confirmationDate is not null
        defaultApplicationShouldBeFound("confirmationDate.specified=true");

        // Get all the applicationList where confirmationDate is null
        defaultApplicationShouldNotBeFound("confirmationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectedBy equals to DEFAULT_REJECTED_BY
        defaultApplicationShouldBeFound("rejectedBy.equals=" + DEFAULT_REJECTED_BY);

        // Get all the applicationList where rejectedBy equals to UPDATED_REJECTED_BY
        defaultApplicationShouldNotBeFound("rejectedBy.equals=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectedBy not equals to DEFAULT_REJECTED_BY
        defaultApplicationShouldNotBeFound("rejectedBy.notEquals=" + DEFAULT_REJECTED_BY);

        // Get all the applicationList where rejectedBy not equals to UPDATED_REJECTED_BY
        defaultApplicationShouldBeFound("rejectedBy.notEquals=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectedBy in DEFAULT_REJECTED_BY or UPDATED_REJECTED_BY
        defaultApplicationShouldBeFound("rejectedBy.in=" + DEFAULT_REJECTED_BY + "," + UPDATED_REJECTED_BY);

        // Get all the applicationList where rejectedBy equals to UPDATED_REJECTED_BY
        defaultApplicationShouldNotBeFound("rejectedBy.in=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectedBy is not null
        defaultApplicationShouldBeFound("rejectedBy.specified=true");

        // Get all the applicationList where rejectedBy is null
        defaultApplicationShouldNotBeFound("rejectedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByRejectedByContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectedBy contains DEFAULT_REJECTED_BY
        defaultApplicationShouldBeFound("rejectedBy.contains=" + DEFAULT_REJECTED_BY);

        // Get all the applicationList where rejectedBy contains UPDATED_REJECTED_BY
        defaultApplicationShouldNotBeFound("rejectedBy.contains=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectedBy does not contain DEFAULT_REJECTED_BY
        defaultApplicationShouldNotBeFound("rejectedBy.doesNotContain=" + DEFAULT_REJECTED_BY);

        // Get all the applicationList where rejectedBy does not contain UPDATED_REJECTED_BY
        defaultApplicationShouldBeFound("rejectedBy.doesNotContain=" + UPDATED_REJECTED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationsByRejectionReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionReason equals to DEFAULT_REJECTION_REASON
        defaultApplicationShouldBeFound("rejectionReason.equals=" + DEFAULT_REJECTION_REASON);

        // Get all the applicationList where rejectionReason equals to UPDATED_REJECTION_REASON
        defaultApplicationShouldNotBeFound("rejectionReason.equals=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionReason not equals to DEFAULT_REJECTION_REASON
        defaultApplicationShouldNotBeFound("rejectionReason.notEquals=" + DEFAULT_REJECTION_REASON);

        // Get all the applicationList where rejectionReason not equals to UPDATED_REJECTION_REASON
        defaultApplicationShouldBeFound("rejectionReason.notEquals=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionReasonIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionReason in DEFAULT_REJECTION_REASON or UPDATED_REJECTION_REASON
        defaultApplicationShouldBeFound("rejectionReason.in=" + DEFAULT_REJECTION_REASON + "," + UPDATED_REJECTION_REASON);

        // Get all the applicationList where rejectionReason equals to UPDATED_REJECTION_REASON
        defaultApplicationShouldNotBeFound("rejectionReason.in=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionReason is not null
        defaultApplicationShouldBeFound("rejectionReason.specified=true");

        // Get all the applicationList where rejectionReason is null
        defaultApplicationShouldNotBeFound("rejectionReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByRejectionReasonContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionReason contains DEFAULT_REJECTION_REASON
        defaultApplicationShouldBeFound("rejectionReason.contains=" + DEFAULT_REJECTION_REASON);

        // Get all the applicationList where rejectionReason contains UPDATED_REJECTION_REASON
        defaultApplicationShouldNotBeFound("rejectionReason.contains=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionReasonNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionReason does not contain DEFAULT_REJECTION_REASON
        defaultApplicationShouldNotBeFound("rejectionReason.doesNotContain=" + DEFAULT_REJECTION_REASON);

        // Get all the applicationList where rejectionReason does not contain UPDATED_REJECTION_REASON
        defaultApplicationShouldBeFound("rejectionReason.doesNotContain=" + UPDATED_REJECTION_REASON);
    }


    @Test
    @Transactional
    public void getAllApplicationsByRejectionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionDate equals to DEFAULT_REJECTION_DATE
        defaultApplicationShouldBeFound("rejectionDate.equals=" + DEFAULT_REJECTION_DATE);

        // Get all the applicationList where rejectionDate equals to UPDATED_REJECTION_DATE
        defaultApplicationShouldNotBeFound("rejectionDate.equals=" + UPDATED_REJECTION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionDate not equals to DEFAULT_REJECTION_DATE
        defaultApplicationShouldNotBeFound("rejectionDate.notEquals=" + DEFAULT_REJECTION_DATE);

        // Get all the applicationList where rejectionDate not equals to UPDATED_REJECTION_DATE
        defaultApplicationShouldBeFound("rejectionDate.notEquals=" + UPDATED_REJECTION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionDate in DEFAULT_REJECTION_DATE or UPDATED_REJECTION_DATE
        defaultApplicationShouldBeFound("rejectionDate.in=" + DEFAULT_REJECTION_DATE + "," + UPDATED_REJECTION_DATE);

        // Get all the applicationList where rejectionDate equals to UPDATED_REJECTION_DATE
        defaultApplicationShouldNotBeFound("rejectionDate.in=" + UPDATED_REJECTION_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByRejectionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where rejectionDate is not null
        defaultApplicationShouldBeFound("rejectionDate.specified=true");

        // Get all the applicationList where rejectionDate is null
        defaultApplicationShouldNotBeFound("rejectionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId equals to DEFAULT_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.equals=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId equals to UPDATED_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.equals=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId not equals to DEFAULT_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.notEquals=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId not equals to UPDATED_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.notEquals=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId in DEFAULT_PROCESS_INSTANCE_ID or UPDATED_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.in=" + DEFAULT_PROCESS_INSTANCE_ID + "," + UPDATED_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId equals to UPDATED_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.in=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId is not null
        defaultApplicationShouldBeFound("processInstanceId.specified=true");

        // Get all the applicationList where processInstanceId is null
        defaultApplicationShouldNotBeFound("processInstanceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId is greater than or equal to DEFAULT_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.greaterThanOrEqual=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId is greater than or equal to UPDATED_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.greaterThanOrEqual=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId is less than or equal to DEFAULT_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.lessThanOrEqual=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId is less than or equal to SMALLER_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.lessThanOrEqual=" + SMALLER_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId is less than DEFAULT_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.lessThan=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId is less than UPDATED_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.lessThan=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByProcessInstanceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where processInstanceId is greater than DEFAULT_PROCESS_INSTANCE_ID
        defaultApplicationShouldNotBeFound("processInstanceId.greaterThan=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the applicationList where processInstanceId is greater than SMALLER_PROCESS_INSTANCE_ID
        defaultApplicationShouldBeFound("processInstanceId.greaterThan=" + SMALLER_PROCESS_INSTANCE_ID);
    }


    @Test
    @Transactional
    public void getAllApplicationsByChannelCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelCode equals to DEFAULT_CHANNEL_CODE
        defaultApplicationShouldBeFound("channelCode.equals=" + DEFAULT_CHANNEL_CODE);

        // Get all the applicationList where channelCode equals to UPDATED_CHANNEL_CODE
        defaultApplicationShouldNotBeFound("channelCode.equals=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelCode not equals to DEFAULT_CHANNEL_CODE
        defaultApplicationShouldNotBeFound("channelCode.notEquals=" + DEFAULT_CHANNEL_CODE);

        // Get all the applicationList where channelCode not equals to UPDATED_CHANNEL_CODE
        defaultApplicationShouldBeFound("channelCode.notEquals=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelCode in DEFAULT_CHANNEL_CODE or UPDATED_CHANNEL_CODE
        defaultApplicationShouldBeFound("channelCode.in=" + DEFAULT_CHANNEL_CODE + "," + UPDATED_CHANNEL_CODE);

        // Get all the applicationList where channelCode equals to UPDATED_CHANNEL_CODE
        defaultApplicationShouldNotBeFound("channelCode.in=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelCode is not null
        defaultApplicationShouldBeFound("channelCode.specified=true");

        // Get all the applicationList where channelCode is null
        defaultApplicationShouldNotBeFound("channelCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByChannelCodeContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelCode contains DEFAULT_CHANNEL_CODE
        defaultApplicationShouldBeFound("channelCode.contains=" + DEFAULT_CHANNEL_CODE);

        // Get all the applicationList where channelCode contains UPDATED_CHANNEL_CODE
        defaultApplicationShouldNotBeFound("channelCode.contains=" + UPDATED_CHANNEL_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelCode does not contain DEFAULT_CHANNEL_CODE
        defaultApplicationShouldNotBeFound("channelCode.doesNotContain=" + DEFAULT_CHANNEL_CODE);

        // Get all the applicationList where channelCode does not contain UPDATED_CHANNEL_CODE
        defaultApplicationShouldBeFound("channelCode.doesNotContain=" + UPDATED_CHANNEL_CODE);
    }


    @Test
    @Transactional
    public void getAllApplicationsByChannelDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelDescription equals to DEFAULT_CHANNEL_DESCRIPTION
        defaultApplicationShouldBeFound("channelDescription.equals=" + DEFAULT_CHANNEL_DESCRIPTION);

        // Get all the applicationList where channelDescription equals to UPDATED_CHANNEL_DESCRIPTION
        defaultApplicationShouldNotBeFound("channelDescription.equals=" + UPDATED_CHANNEL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelDescription not equals to DEFAULT_CHANNEL_DESCRIPTION
        defaultApplicationShouldNotBeFound("channelDescription.notEquals=" + DEFAULT_CHANNEL_DESCRIPTION);

        // Get all the applicationList where channelDescription not equals to UPDATED_CHANNEL_DESCRIPTION
        defaultApplicationShouldBeFound("channelDescription.notEquals=" + UPDATED_CHANNEL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelDescription in DEFAULT_CHANNEL_DESCRIPTION or UPDATED_CHANNEL_DESCRIPTION
        defaultApplicationShouldBeFound("channelDescription.in=" + DEFAULT_CHANNEL_DESCRIPTION + "," + UPDATED_CHANNEL_DESCRIPTION);

        // Get all the applicationList where channelDescription equals to UPDATED_CHANNEL_DESCRIPTION
        defaultApplicationShouldNotBeFound("channelDescription.in=" + UPDATED_CHANNEL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelDescription is not null
        defaultApplicationShouldBeFound("channelDescription.specified=true");

        // Get all the applicationList where channelDescription is null
        defaultApplicationShouldNotBeFound("channelDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByChannelDescriptionContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelDescription contains DEFAULT_CHANNEL_DESCRIPTION
        defaultApplicationShouldBeFound("channelDescription.contains=" + DEFAULT_CHANNEL_DESCRIPTION);

        // Get all the applicationList where channelDescription contains UPDATED_CHANNEL_DESCRIPTION
        defaultApplicationShouldNotBeFound("channelDescription.contains=" + UPDATED_CHANNEL_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByChannelDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where channelDescription does not contain DEFAULT_CHANNEL_DESCRIPTION
        defaultApplicationShouldNotBeFound("channelDescription.doesNotContain=" + DEFAULT_CHANNEL_DESCRIPTION);

        // Get all the applicationList where channelDescription does not contain UPDATED_CHANNEL_DESCRIPTION
        defaultApplicationShouldBeFound("channelDescription.doesNotContain=" + UPDATED_CHANNEL_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllApplicationsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultApplicationShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the applicationList where customerId equals to UPDATED_CUSTOMER_ID
        defaultApplicationShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultApplicationShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the applicationList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultApplicationShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultApplicationShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the applicationList where customerId equals to UPDATED_CUSTOMER_ID
        defaultApplicationShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where customerId is not null
        defaultApplicationShouldBeFound("customerId.specified=true");

        // Get all the applicationList where customerId is null
        defaultApplicationShouldNotBeFound("customerId.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByCustomerIdContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where customerId contains DEFAULT_CUSTOMER_ID
        defaultApplicationShouldBeFound("customerId.contains=" + DEFAULT_CUSTOMER_ID);

        // Get all the applicationList where customerId contains UPDATED_CUSTOMER_ID
        defaultApplicationShouldNotBeFound("customerId.contains=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCustomerIdNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where customerId does not contain DEFAULT_CUSTOMER_ID
        defaultApplicationShouldNotBeFound("customerId.doesNotContain=" + DEFAULT_CUSTOMER_ID);

        // Get all the applicationList where customerId does not contain UPDATED_CUSTOMER_ID
        defaultApplicationShouldBeFound("customerId.doesNotContain=" + UPDATED_CUSTOMER_ID);
    }


    @Test
    @Transactional
    public void getAllApplicationsByEnglishCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCustomerName equals to DEFAULT_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldBeFound("englishCustomerName.equals=" + DEFAULT_ENGLISH_CUSTOMER_NAME);

        // Get all the applicationList where englishCustomerName equals to UPDATED_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("englishCustomerName.equals=" + UPDATED_ENGLISH_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCustomerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCustomerName not equals to DEFAULT_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("englishCustomerName.notEquals=" + DEFAULT_ENGLISH_CUSTOMER_NAME);

        // Get all the applicationList where englishCustomerName not equals to UPDATED_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldBeFound("englishCustomerName.notEquals=" + UPDATED_ENGLISH_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCustomerName in DEFAULT_ENGLISH_CUSTOMER_NAME or UPDATED_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldBeFound("englishCustomerName.in=" + DEFAULT_ENGLISH_CUSTOMER_NAME + "," + UPDATED_ENGLISH_CUSTOMER_NAME);

        // Get all the applicationList where englishCustomerName equals to UPDATED_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("englishCustomerName.in=" + UPDATED_ENGLISH_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCustomerName is not null
        defaultApplicationShouldBeFound("englishCustomerName.specified=true");

        // Get all the applicationList where englishCustomerName is null
        defaultApplicationShouldNotBeFound("englishCustomerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByEnglishCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCustomerName contains DEFAULT_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldBeFound("englishCustomerName.contains=" + DEFAULT_ENGLISH_CUSTOMER_NAME);

        // Get all the applicationList where englishCustomerName contains UPDATED_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("englishCustomerName.contains=" + UPDATED_ENGLISH_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCustomerName does not contain DEFAULT_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("englishCustomerName.doesNotContain=" + DEFAULT_ENGLISH_CUSTOMER_NAME);

        // Get all the applicationList where englishCustomerName does not contain UPDATED_ENGLISH_CUSTOMER_NAME
        defaultApplicationShouldBeFound("englishCustomerName.doesNotContain=" + UPDATED_ENGLISH_CUSTOMER_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicationsByArabicCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCustomerName equals to DEFAULT_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldBeFound("arabicCustomerName.equals=" + DEFAULT_ARABIC_CUSTOMER_NAME);

        // Get all the applicationList where arabicCustomerName equals to UPDATED_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("arabicCustomerName.equals=" + UPDATED_ARABIC_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCustomerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCustomerName not equals to DEFAULT_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("arabicCustomerName.notEquals=" + DEFAULT_ARABIC_CUSTOMER_NAME);

        // Get all the applicationList where arabicCustomerName not equals to UPDATED_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldBeFound("arabicCustomerName.notEquals=" + UPDATED_ARABIC_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCustomerName in DEFAULT_ARABIC_CUSTOMER_NAME or UPDATED_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldBeFound("arabicCustomerName.in=" + DEFAULT_ARABIC_CUSTOMER_NAME + "," + UPDATED_ARABIC_CUSTOMER_NAME);

        // Get all the applicationList where arabicCustomerName equals to UPDATED_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("arabicCustomerName.in=" + UPDATED_ARABIC_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCustomerName is not null
        defaultApplicationShouldBeFound("arabicCustomerName.specified=true");

        // Get all the applicationList where arabicCustomerName is null
        defaultApplicationShouldNotBeFound("arabicCustomerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByArabicCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCustomerName contains DEFAULT_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldBeFound("arabicCustomerName.contains=" + DEFAULT_ARABIC_CUSTOMER_NAME);

        // Get all the applicationList where arabicCustomerName contains UPDATED_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("arabicCustomerName.contains=" + UPDATED_ARABIC_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCustomerName does not contain DEFAULT_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldNotBeFound("arabicCustomerName.doesNotContain=" + DEFAULT_ARABIC_CUSTOMER_NAME);

        // Get all the applicationList where arabicCustomerName does not contain UPDATED_ARABIC_CUSTOMER_NAME
        defaultApplicationShouldBeFound("arabicCustomerName.doesNotContain=" + UPDATED_ARABIC_CUSTOMER_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicationsByTradeLicenseNoIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where tradeLicenseNo equals to DEFAULT_TRADE_LICENSE_NO
        defaultApplicationShouldBeFound("tradeLicenseNo.equals=" + DEFAULT_TRADE_LICENSE_NO);

        // Get all the applicationList where tradeLicenseNo equals to UPDATED_TRADE_LICENSE_NO
        defaultApplicationShouldNotBeFound("tradeLicenseNo.equals=" + UPDATED_TRADE_LICENSE_NO);
    }

    @Test
    @Transactional
    public void getAllApplicationsByTradeLicenseNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where tradeLicenseNo not equals to DEFAULT_TRADE_LICENSE_NO
        defaultApplicationShouldNotBeFound("tradeLicenseNo.notEquals=" + DEFAULT_TRADE_LICENSE_NO);

        // Get all the applicationList where tradeLicenseNo not equals to UPDATED_TRADE_LICENSE_NO
        defaultApplicationShouldBeFound("tradeLicenseNo.notEquals=" + UPDATED_TRADE_LICENSE_NO);
    }

    @Test
    @Transactional
    public void getAllApplicationsByTradeLicenseNoIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where tradeLicenseNo in DEFAULT_TRADE_LICENSE_NO or UPDATED_TRADE_LICENSE_NO
        defaultApplicationShouldBeFound("tradeLicenseNo.in=" + DEFAULT_TRADE_LICENSE_NO + "," + UPDATED_TRADE_LICENSE_NO);

        // Get all the applicationList where tradeLicenseNo equals to UPDATED_TRADE_LICENSE_NO
        defaultApplicationShouldNotBeFound("tradeLicenseNo.in=" + UPDATED_TRADE_LICENSE_NO);
    }

    @Test
    @Transactional
    public void getAllApplicationsByTradeLicenseNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where tradeLicenseNo is not null
        defaultApplicationShouldBeFound("tradeLicenseNo.specified=true");

        // Get all the applicationList where tradeLicenseNo is null
        defaultApplicationShouldNotBeFound("tradeLicenseNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByTradeLicenseNoContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where tradeLicenseNo contains DEFAULT_TRADE_LICENSE_NO
        defaultApplicationShouldBeFound("tradeLicenseNo.contains=" + DEFAULT_TRADE_LICENSE_NO);

        // Get all the applicationList where tradeLicenseNo contains UPDATED_TRADE_LICENSE_NO
        defaultApplicationShouldNotBeFound("tradeLicenseNo.contains=" + UPDATED_TRADE_LICENSE_NO);
    }

    @Test
    @Transactional
    public void getAllApplicationsByTradeLicenseNoNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where tradeLicenseNo does not contain DEFAULT_TRADE_LICENSE_NO
        defaultApplicationShouldNotBeFound("tradeLicenseNo.doesNotContain=" + DEFAULT_TRADE_LICENSE_NO);

        // Get all the applicationList where tradeLicenseNo does not contain UPDATED_TRADE_LICENSE_NO
        defaultApplicationShouldBeFound("tradeLicenseNo.doesNotContain=" + UPDATED_TRADE_LICENSE_NO);
    }


    @Test
    @Transactional
    public void getAllApplicationsByEnglishCorporateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCorporateName equals to DEFAULT_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldBeFound("englishCorporateName.equals=" + DEFAULT_ENGLISH_CORPORATE_NAME);

        // Get all the applicationList where englishCorporateName equals to UPDATED_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("englishCorporateName.equals=" + UPDATED_ENGLISH_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCorporateNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCorporateName not equals to DEFAULT_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("englishCorporateName.notEquals=" + DEFAULT_ENGLISH_CORPORATE_NAME);

        // Get all the applicationList where englishCorporateName not equals to UPDATED_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldBeFound("englishCorporateName.notEquals=" + UPDATED_ENGLISH_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCorporateNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCorporateName in DEFAULT_ENGLISH_CORPORATE_NAME or UPDATED_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldBeFound("englishCorporateName.in=" + DEFAULT_ENGLISH_CORPORATE_NAME + "," + UPDATED_ENGLISH_CORPORATE_NAME);

        // Get all the applicationList where englishCorporateName equals to UPDATED_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("englishCorporateName.in=" + UPDATED_ENGLISH_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCorporateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCorporateName is not null
        defaultApplicationShouldBeFound("englishCorporateName.specified=true");

        // Get all the applicationList where englishCorporateName is null
        defaultApplicationShouldNotBeFound("englishCorporateName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByEnglishCorporateNameContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCorporateName contains DEFAULT_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldBeFound("englishCorporateName.contains=" + DEFAULT_ENGLISH_CORPORATE_NAME);

        // Get all the applicationList where englishCorporateName contains UPDATED_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("englishCorporateName.contains=" + UPDATED_ENGLISH_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByEnglishCorporateNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where englishCorporateName does not contain DEFAULT_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("englishCorporateName.doesNotContain=" + DEFAULT_ENGLISH_CORPORATE_NAME);

        // Get all the applicationList where englishCorporateName does not contain UPDATED_ENGLISH_CORPORATE_NAME
        defaultApplicationShouldBeFound("englishCorporateName.doesNotContain=" + UPDATED_ENGLISH_CORPORATE_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicationsByArabicCorporateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCorporateName equals to DEFAULT_ARABIC_CORPORATE_NAME
        defaultApplicationShouldBeFound("arabicCorporateName.equals=" + DEFAULT_ARABIC_CORPORATE_NAME);

        // Get all the applicationList where arabicCorporateName equals to UPDATED_ARABIC_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("arabicCorporateName.equals=" + UPDATED_ARABIC_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCorporateNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCorporateName not equals to DEFAULT_ARABIC_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("arabicCorporateName.notEquals=" + DEFAULT_ARABIC_CORPORATE_NAME);

        // Get all the applicationList where arabicCorporateName not equals to UPDATED_ARABIC_CORPORATE_NAME
        defaultApplicationShouldBeFound("arabicCorporateName.notEquals=" + UPDATED_ARABIC_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCorporateNameIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCorporateName in DEFAULT_ARABIC_CORPORATE_NAME or UPDATED_ARABIC_CORPORATE_NAME
        defaultApplicationShouldBeFound("arabicCorporateName.in=" + DEFAULT_ARABIC_CORPORATE_NAME + "," + UPDATED_ARABIC_CORPORATE_NAME);

        // Get all the applicationList where arabicCorporateName equals to UPDATED_ARABIC_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("arabicCorporateName.in=" + UPDATED_ARABIC_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCorporateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCorporateName is not null
        defaultApplicationShouldBeFound("arabicCorporateName.specified=true");

        // Get all the applicationList where arabicCorporateName is null
        defaultApplicationShouldNotBeFound("arabicCorporateName.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByArabicCorporateNameContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCorporateName contains DEFAULT_ARABIC_CORPORATE_NAME
        defaultApplicationShouldBeFound("arabicCorporateName.contains=" + DEFAULT_ARABIC_CORPORATE_NAME);

        // Get all the applicationList where arabicCorporateName contains UPDATED_ARABIC_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("arabicCorporateName.contains=" + UPDATED_ARABIC_CORPORATE_NAME);
    }

    @Test
    @Transactional
    public void getAllApplicationsByArabicCorporateNameNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where arabicCorporateName does not contain DEFAULT_ARABIC_CORPORATE_NAME
        defaultApplicationShouldNotBeFound("arabicCorporateName.doesNotContain=" + DEFAULT_ARABIC_CORPORATE_NAME);

        // Get all the applicationList where arabicCorporateName does not contain UPDATED_ARABIC_CORPORATE_NAME
        defaultApplicationShouldBeFound("arabicCorporateName.doesNotContain=" + UPDATED_ARABIC_CORPORATE_NAME);
    }


    @Test
    @Transactional
    public void getAllApplicationsByUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userType equals to DEFAULT_USER_TYPE
        defaultApplicationShouldBeFound("userType.equals=" + DEFAULT_USER_TYPE);

        // Get all the applicationList where userType equals to UPDATED_USER_TYPE
        defaultApplicationShouldNotBeFound("userType.equals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userType not equals to DEFAULT_USER_TYPE
        defaultApplicationShouldNotBeFound("userType.notEquals=" + DEFAULT_USER_TYPE);

        // Get all the applicationList where userType not equals to UPDATED_USER_TYPE
        defaultApplicationShouldBeFound("userType.notEquals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userType in DEFAULT_USER_TYPE or UPDATED_USER_TYPE
        defaultApplicationShouldBeFound("userType.in=" + DEFAULT_USER_TYPE + "," + UPDATED_USER_TYPE);

        // Get all the applicationList where userType equals to UPDATED_USER_TYPE
        defaultApplicationShouldNotBeFound("userType.in=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userType is not null
        defaultApplicationShouldBeFound("userType.specified=true");

        // Get all the applicationList where userType is null
        defaultApplicationShouldNotBeFound("userType.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByUserTypeContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userType contains DEFAULT_USER_TYPE
        defaultApplicationShouldBeFound("userType.contains=" + DEFAULT_USER_TYPE);

        // Get all the applicationList where userType contains UPDATED_USER_TYPE
        defaultApplicationShouldNotBeFound("userType.contains=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userType does not contain DEFAULT_USER_TYPE
        defaultApplicationShouldNotBeFound("userType.doesNotContain=" + DEFAULT_USER_TYPE);

        // Get all the applicationList where userType does not contain UPDATED_USER_TYPE
        defaultApplicationShouldBeFound("userType.doesNotContain=" + UPDATED_USER_TYPE);
    }


    @Test
    @Transactional
    public void getAllApplicationsByUserTypeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userTypeDescription equals to DEFAULT_USER_TYPE_DESCRIPTION
        defaultApplicationShouldBeFound("userTypeDescription.equals=" + DEFAULT_USER_TYPE_DESCRIPTION);

        // Get all the applicationList where userTypeDescription equals to UPDATED_USER_TYPE_DESCRIPTION
        defaultApplicationShouldNotBeFound("userTypeDescription.equals=" + UPDATED_USER_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userTypeDescription not equals to DEFAULT_USER_TYPE_DESCRIPTION
        defaultApplicationShouldNotBeFound("userTypeDescription.notEquals=" + DEFAULT_USER_TYPE_DESCRIPTION);

        // Get all the applicationList where userTypeDescription not equals to UPDATED_USER_TYPE_DESCRIPTION
        defaultApplicationShouldBeFound("userTypeDescription.notEquals=" + UPDATED_USER_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userTypeDescription in DEFAULT_USER_TYPE_DESCRIPTION or UPDATED_USER_TYPE_DESCRIPTION
        defaultApplicationShouldBeFound("userTypeDescription.in=" + DEFAULT_USER_TYPE_DESCRIPTION + "," + UPDATED_USER_TYPE_DESCRIPTION);

        // Get all the applicationList where userTypeDescription equals to UPDATED_USER_TYPE_DESCRIPTION
        defaultApplicationShouldNotBeFound("userTypeDescription.in=" + UPDATED_USER_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userTypeDescription is not null
        defaultApplicationShouldBeFound("userTypeDescription.specified=true");

        // Get all the applicationList where userTypeDescription is null
        defaultApplicationShouldNotBeFound("userTypeDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByUserTypeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userTypeDescription contains DEFAULT_USER_TYPE_DESCRIPTION
        defaultApplicationShouldBeFound("userTypeDescription.contains=" + DEFAULT_USER_TYPE_DESCRIPTION);

        // Get all the applicationList where userTypeDescription contains UPDATED_USER_TYPE_DESCRIPTION
        defaultApplicationShouldNotBeFound("userTypeDescription.contains=" + UPDATED_USER_TYPE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserTypeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userTypeDescription does not contain DEFAULT_USER_TYPE_DESCRIPTION
        defaultApplicationShouldNotBeFound("userTypeDescription.doesNotContain=" + DEFAULT_USER_TYPE_DESCRIPTION);

        // Get all the applicationList where userTypeDescription does not contain UPDATED_USER_TYPE_DESCRIPTION
        defaultApplicationShouldBeFound("userTypeDescription.doesNotContain=" + UPDATED_USER_TYPE_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllApplicationsByUserRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userRole equals to DEFAULT_USER_ROLE
        defaultApplicationShouldBeFound("userRole.equals=" + DEFAULT_USER_ROLE);

        // Get all the applicationList where userRole equals to UPDATED_USER_ROLE
        defaultApplicationShouldNotBeFound("userRole.equals=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userRole not equals to DEFAULT_USER_ROLE
        defaultApplicationShouldNotBeFound("userRole.notEquals=" + DEFAULT_USER_ROLE);

        // Get all the applicationList where userRole not equals to UPDATED_USER_ROLE
        defaultApplicationShouldBeFound("userRole.notEquals=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserRoleIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userRole in DEFAULT_USER_ROLE or UPDATED_USER_ROLE
        defaultApplicationShouldBeFound("userRole.in=" + DEFAULT_USER_ROLE + "," + UPDATED_USER_ROLE);

        // Get all the applicationList where userRole equals to UPDATED_USER_ROLE
        defaultApplicationShouldNotBeFound("userRole.in=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userRole is not null
        defaultApplicationShouldBeFound("userRole.specified=true");

        // Get all the applicationList where userRole is null
        defaultApplicationShouldNotBeFound("userRole.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByUserRoleContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userRole contains DEFAULT_USER_ROLE
        defaultApplicationShouldBeFound("userRole.contains=" + DEFAULT_USER_ROLE);

        // Get all the applicationList where userRole contains UPDATED_USER_ROLE
        defaultApplicationShouldNotBeFound("userRole.contains=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByUserRoleNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where userRole does not contain DEFAULT_USER_ROLE
        defaultApplicationShouldNotBeFound("userRole.doesNotContain=" + DEFAULT_USER_ROLE);

        // Get all the applicationList where userRole does not contain UPDATED_USER_ROLE
        defaultApplicationShouldBeFound("userRole.doesNotContain=" + UPDATED_USER_ROLE);
    }


    @Test
    @Transactional
    public void getAllApplicationsByApplicationCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCriteria equals to DEFAULT_APPLICATION_CRITERIA
        defaultApplicationShouldBeFound("applicationCriteria.equals=" + DEFAULT_APPLICATION_CRITERIA);

        // Get all the applicationList where applicationCriteria equals to UPDATED_APPLICATION_CRITERIA
        defaultApplicationShouldNotBeFound("applicationCriteria.equals=" + UPDATED_APPLICATION_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCriteriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCriteria not equals to DEFAULT_APPLICATION_CRITERIA
        defaultApplicationShouldNotBeFound("applicationCriteria.notEquals=" + DEFAULT_APPLICATION_CRITERIA);

        // Get all the applicationList where applicationCriteria not equals to UPDATED_APPLICATION_CRITERIA
        defaultApplicationShouldBeFound("applicationCriteria.notEquals=" + UPDATED_APPLICATION_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCriteria in DEFAULT_APPLICATION_CRITERIA or UPDATED_APPLICATION_CRITERIA
        defaultApplicationShouldBeFound("applicationCriteria.in=" + DEFAULT_APPLICATION_CRITERIA + "," + UPDATED_APPLICATION_CRITERIA);

        // Get all the applicationList where applicationCriteria equals to UPDATED_APPLICATION_CRITERIA
        defaultApplicationShouldNotBeFound("applicationCriteria.in=" + UPDATED_APPLICATION_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCriteria is not null
        defaultApplicationShouldBeFound("applicationCriteria.specified=true");

        // Get all the applicationList where applicationCriteria is null
        defaultApplicationShouldNotBeFound("applicationCriteria.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByApplicationCriteriaContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCriteria contains DEFAULT_APPLICATION_CRITERIA
        defaultApplicationShouldBeFound("applicationCriteria.contains=" + DEFAULT_APPLICATION_CRITERIA);

        // Get all the applicationList where applicationCriteria contains UPDATED_APPLICATION_CRITERIA
        defaultApplicationShouldNotBeFound("applicationCriteria.contains=" + UPDATED_APPLICATION_CRITERIA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationCriteriaNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where applicationCriteria does not contain DEFAULT_APPLICATION_CRITERIA
        defaultApplicationShouldNotBeFound("applicationCriteria.doesNotContain=" + DEFAULT_APPLICATION_CRITERIA);

        // Get all the applicationList where applicationCriteria does not contain UPDATED_APPLICATION_CRITERIA
        defaultApplicationShouldBeFound("applicationCriteria.doesNotContain=" + UPDATED_APPLICATION_CRITERIA);
    }


    @Test
    @Transactional
    public void getAllApplicationsByPersonaIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where persona equals to DEFAULT_PERSONA
        defaultApplicationShouldBeFound("persona.equals=" + DEFAULT_PERSONA);

        // Get all the applicationList where persona equals to UPDATED_PERSONA
        defaultApplicationShouldNotBeFound("persona.equals=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where persona not equals to DEFAULT_PERSONA
        defaultApplicationShouldNotBeFound("persona.notEquals=" + DEFAULT_PERSONA);

        // Get all the applicationList where persona not equals to UPDATED_PERSONA
        defaultApplicationShouldBeFound("persona.notEquals=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where persona in DEFAULT_PERSONA or UPDATED_PERSONA
        defaultApplicationShouldBeFound("persona.in=" + DEFAULT_PERSONA + "," + UPDATED_PERSONA);

        // Get all the applicationList where persona equals to UPDATED_PERSONA
        defaultApplicationShouldNotBeFound("persona.in=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where persona is not null
        defaultApplicationShouldBeFound("persona.specified=true");

        // Get all the applicationList where persona is null
        defaultApplicationShouldNotBeFound("persona.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByPersonaContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where persona contains DEFAULT_PERSONA
        defaultApplicationShouldBeFound("persona.contains=" + DEFAULT_PERSONA);

        // Get all the applicationList where persona contains UPDATED_PERSONA
        defaultApplicationShouldNotBeFound("persona.contains=" + UPDATED_PERSONA);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where persona does not contain DEFAULT_PERSONA
        defaultApplicationShouldNotBeFound("persona.doesNotContain=" + DEFAULT_PERSONA);

        // Get all the applicationList where persona does not contain UPDATED_PERSONA
        defaultApplicationShouldBeFound("persona.doesNotContain=" + UPDATED_PERSONA);
    }


    @Test
    @Transactional
    public void getAllApplicationsByPersonaVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where personaVersion equals to DEFAULT_PERSONA_VERSION
        defaultApplicationShouldBeFound("personaVersion.equals=" + DEFAULT_PERSONA_VERSION);

        // Get all the applicationList where personaVersion equals to UPDATED_PERSONA_VERSION
        defaultApplicationShouldNotBeFound("personaVersion.equals=" + UPDATED_PERSONA_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where personaVersion not equals to DEFAULT_PERSONA_VERSION
        defaultApplicationShouldNotBeFound("personaVersion.notEquals=" + DEFAULT_PERSONA_VERSION);

        // Get all the applicationList where personaVersion not equals to UPDATED_PERSONA_VERSION
        defaultApplicationShouldBeFound("personaVersion.notEquals=" + UPDATED_PERSONA_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaVersionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where personaVersion in DEFAULT_PERSONA_VERSION or UPDATED_PERSONA_VERSION
        defaultApplicationShouldBeFound("personaVersion.in=" + DEFAULT_PERSONA_VERSION + "," + UPDATED_PERSONA_VERSION);

        // Get all the applicationList where personaVersion equals to UPDATED_PERSONA_VERSION
        defaultApplicationShouldNotBeFound("personaVersion.in=" + UPDATED_PERSONA_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where personaVersion is not null
        defaultApplicationShouldBeFound("personaVersion.specified=true");

        // Get all the applicationList where personaVersion is null
        defaultApplicationShouldNotBeFound("personaVersion.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByPersonaVersionContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where personaVersion contains DEFAULT_PERSONA_VERSION
        defaultApplicationShouldBeFound("personaVersion.contains=" + DEFAULT_PERSONA_VERSION);

        // Get all the applicationList where personaVersion contains UPDATED_PERSONA_VERSION
        defaultApplicationShouldNotBeFound("personaVersion.contains=" + UPDATED_PERSONA_VERSION);
    }

    @Test
    @Transactional
    public void getAllApplicationsByPersonaVersionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where personaVersion does not contain DEFAULT_PERSONA_VERSION
        defaultApplicationShouldNotBeFound("personaVersion.doesNotContain=" + DEFAULT_PERSONA_VERSION);

        // Get all the applicationList where personaVersion does not contain UPDATED_PERSONA_VERSION
        defaultApplicationShouldBeFound("personaVersion.doesNotContain=" + UPDATED_PERSONA_VERSION);
    }


    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicationList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy is not null
        defaultApplicationShouldBeFound("createdBy.specified=true");

        // Get all the applicationList where createdBy is null
        defaultApplicationShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy contains DEFAULT_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the applicationList where createdBy contains UPDATED_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultApplicationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the applicationList where createdBy does not contain UPDATED_CREATED_BY
        defaultApplicationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultApplicationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultApplicationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultApplicationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultApplicationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the applicationList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where createdDate is not null
        defaultApplicationShouldBeFound("createdDate.specified=true");

        // Get all the applicationList where createdDate is null
        defaultApplicationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApplicationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the applicationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedBy is not null
        defaultApplicationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the applicationList where lastModifiedBy is null
        defaultApplicationShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApplicationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApplicationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApplicationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApplicationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultApplicationShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the applicationList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList where lastModifiedDate is not null
        defaultApplicationShouldBeFound("lastModifiedDate.specified=true");

        // Get all the applicationList where lastModifiedDate is null
        defaultApplicationShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationsByApplicationPhaseIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);
        ApplicationPhase applicationPhase = ApplicationPhaseResourceIT.createEntity(em);
        em.persist(applicationPhase);
        em.flush();
        application.addApplicationPhase(applicationPhase);
        applicationRepository.saveAndFlush(application);
        Long applicationPhaseId = applicationPhase.getId();

        // Get all the applicationList where applicationPhase equals to applicationPhaseId
        defaultApplicationShouldBeFound("applicationPhaseId.equals=" + applicationPhaseId);

        // Get all the applicationList where applicationPhase equals to applicationPhaseId + 1
        defaultApplicationShouldNotBeFound("applicationPhaseId.equals=" + (applicationPhaseId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationsByServiceRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);
        ServiceRequest serviceRequest = ServiceRequestResourceIT.createEntity(em);
        em.persist(serviceRequest);
        em.flush();
        application.addServiceRequest(serviceRequest);
        applicationRepository.saveAndFlush(application);
        Long serviceRequestId = serviceRequest.getId();

        // Get all the applicationList where serviceRequest equals to serviceRequestId
        defaultApplicationShouldBeFound("serviceRequestId.equals=" + serviceRequestId);

        // Get all the applicationList where serviceRequest equals to serviceRequestId + 1
        defaultApplicationShouldNotBeFound("serviceRequestId.equals=" + (serviceRequestId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationsByApplicationViolationIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);
        ApplicationViolation applicationViolation = ApplicationViolationResourceIT.createEntity(em);
        em.persist(applicationViolation);
        em.flush();
        application.addApplicationViolation(applicationViolation);
        applicationRepository.saveAndFlush(application);
        Long applicationViolationId = applicationViolation.getId();

        // Get all the applicationList where applicationViolation equals to applicationViolationId
        defaultApplicationShouldBeFound("applicationViolationId.equals=" + applicationViolationId);

        // Get all the applicationList where applicationViolation equals to applicationViolationId + 1
        defaultApplicationShouldNotBeFound("applicationViolationId.equals=" + (applicationViolationId + 1));
    }


    @Test
    @Transactional
    public void getAllApplicationsByApplicationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);
        ApplicationType applicationType = ApplicationTypeResourceIT.createEntity(em);
        em.persist(applicationType);
        em.flush();
        application.setApplicationType(applicationType);
        applicationRepository.saveAndFlush(application);
        Long applicationTypeId = applicationType.getId();

        // Get all the applicationList where applicationType equals to applicationTypeId
        defaultApplicationShouldBeFound("applicationTypeId.equals=" + applicationTypeId);

        // Get all the applicationList where applicationType equals to applicationTypeId + 1
        defaultApplicationShouldNotBeFound("applicationTypeId.equals=" + (applicationTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationShouldBeFound(String filter) throws Exception {
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].activePhase").value(hasItem(DEFAULT_ACTIVE_PHASE.toString())))
            .andExpect(jsonPath("$.[*].confirmedBy").value(hasItem(DEFAULT_CONFIRMED_BY)))
            .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].rejectedBy").value(hasItem(DEFAULT_REJECTED_BY)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)))
            .andExpect(jsonPath("$.[*].rejectionDate").value(hasItem(DEFAULT_REJECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].channelDescription").value(hasItem(DEFAULT_CHANNEL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].englishCustomerName").value(hasItem(DEFAULT_ENGLISH_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].arabicCustomerName").value(hasItem(DEFAULT_ARABIC_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].tradeLicenseNo").value(hasItem(DEFAULT_TRADE_LICENSE_NO)))
            .andExpect(jsonPath("$.[*].englishCorporateName").value(hasItem(DEFAULT_ENGLISH_CORPORATE_NAME)))
            .andExpect(jsonPath("$.[*].arabicCorporateName").value(hasItem(DEFAULT_ARABIC_CORPORATE_NAME)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].userTypeDescription").value(hasItem(DEFAULT_USER_TYPE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE)))
            .andExpect(jsonPath("$.[*].applicationCriteria").value(hasItem(DEFAULT_APPLICATION_CRITERIA)))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)))
            .andExpect(jsonPath("$.[*].personaVersion").value(hasItem(DEFAULT_PERSONA_VERSION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restApplicationMockMvc.perform(get("/api/applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationShouldNotBeFound(String filter) throws Exception {
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationMockMvc.perform(get("/api/applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .status(UPDATED_STATUS)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusDate(UPDATED_STATUS_DATE)
            .activePhase(UPDATED_ACTIVE_PHASE)
            .confirmedBy(UPDATED_CONFIRMED_BY)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .rejectedBy(UPDATED_REJECTED_BY)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .rejectionDate(UPDATED_REJECTION_DATE)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelDescription(UPDATED_CHANNEL_DESCRIPTION)
            .customerId(UPDATED_CUSTOMER_ID)
            .englishCustomerName(UPDATED_ENGLISH_CUSTOMER_NAME)
            .arabicCustomerName(UPDATED_ARABIC_CUSTOMER_NAME)
            .tradeLicenseNo(UPDATED_TRADE_LICENSE_NO)
            .englishCorporateName(UPDATED_ENGLISH_CORPORATE_NAME)
            .arabicCorporateName(UPDATED_ARABIC_CORPORATE_NAME)
            .userType(UPDATED_USER_TYPE)
            .userTypeDescription(UPDATED_USER_TYPE_DESCRIPTION)
            .userRole(UPDATED_USER_ROLE)
            .applicationCriteria(UPDATED_APPLICATION_CRITERIA)
            .persona(UPDATED_PERSONA)
            .personaVersion(UPDATED_PERSONA_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ApplicationDTO applicationDTO = applicationMapper.toDto(updatedApplication);

        restApplicationMockMvc.perform(put("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplication.getStatusDescription()).isEqualTo(UPDATED_STATUS_DESCRIPTION);
        assertThat(testApplication.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testApplication.getActivePhase()).isEqualTo(UPDATED_ACTIVE_PHASE);
        assertThat(testApplication.getConfirmedBy()).isEqualTo(UPDATED_CONFIRMED_BY);
        assertThat(testApplication.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testApplication.getRejectedBy()).isEqualTo(UPDATED_REJECTED_BY);
        assertThat(testApplication.getRejectionReason()).isEqualTo(UPDATED_REJECTION_REASON);
        assertThat(testApplication.getRejectionDate()).isEqualTo(UPDATED_REJECTION_DATE);
        assertThat(testApplication.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testApplication.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testApplication.getChannelDescription()).isEqualTo(UPDATED_CHANNEL_DESCRIPTION);
        assertThat(testApplication.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testApplication.getEnglishCustomerName()).isEqualTo(UPDATED_ENGLISH_CUSTOMER_NAME);
        assertThat(testApplication.getArabicCustomerName()).isEqualTo(UPDATED_ARABIC_CUSTOMER_NAME);
        assertThat(testApplication.getTradeLicenseNo()).isEqualTo(UPDATED_TRADE_LICENSE_NO);
        assertThat(testApplication.getEnglishCorporateName()).isEqualTo(UPDATED_ENGLISH_CORPORATE_NAME);
        assertThat(testApplication.getArabicCorporateName()).isEqualTo(UPDATED_ARABIC_CORPORATE_NAME);
        assertThat(testApplication.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testApplication.getUserTypeDescription()).isEqualTo(UPDATED_USER_TYPE_DESCRIPTION);
        assertThat(testApplication.getUserRole()).isEqualTo(UPDATED_USER_ROLE);
        assertThat(testApplication.getApplicationCriteria()).isEqualTo(UPDATED_APPLICATION_CRITERIA);
        assertThat(testApplication.getPersona()).isEqualTo(UPDATED_PERSONA);
        assertThat(testApplication.getPersonaVersion()).isEqualTo(UPDATED_PERSONA_VERSION);
        assertThat(testApplication.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplication.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApplication.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApplication.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put("/api/applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc.perform(delete("/api/applications/{id}", application.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
