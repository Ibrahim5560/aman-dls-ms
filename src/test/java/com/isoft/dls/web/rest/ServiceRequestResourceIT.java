package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ServiceRequest;
import com.isoft.dls.domain.ApplicationViolation;
import com.isoft.dls.domain.ServiceRequest;
import com.isoft.dls.domain.Application;
import com.isoft.dls.repository.ServiceRequestRepository;
import com.isoft.dls.service.ServiceRequestService;
import com.isoft.dls.service.dto.ServiceRequestDTO;
import com.isoft.dls.service.mapper.ServiceRequestMapper;
import com.isoft.dls.service.dto.ServiceRequestCriteria;
import com.isoft.dls.service.ServiceRequestQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
import com.isoft.dls.domain.enumeration.ServiceRequestStatus;
/**
 * Integration tests for the {@link ServiceRequestResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ServiceRequestResourceIT {

    private static final String DEFAULT_SERVICE_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_FEE_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_FEE_DOCUMENT = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBBBBBBB";

    private static final PhaseType DEFAULT_PHASE_TYPE = PhaseType.CUSTOMER_ELIGIBILITY;
    private static final PhaseType UPDATED_PHASE_TYPE = PhaseType.DRIVING_LEARNING_FILE_PROCESSING;

    private static final ServiceRequestStatus DEFAULT_STATUS = ServiceRequestStatus.UNDER_PROCESSING;
    private static final ServiceRequestStatus UPDATED_STATUS = ServiceRequestStatus.VERIFIED_AND_LOCKED;

    private static final String DEFAULT_STATUS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_FEE_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_FEE_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_FEE_AMOUNT = 1D - 1D;

    private static final String DEFAULT_PAID_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAID_BY = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_METHOD = 1;
    private static final Integer UPDATED_PAYMENT_METHOD = 2;
    private static final Integer SMALLER_PAYMENT_METHOD = 1 - 1;

    private static final Long DEFAULT_PAYMENT_REFERENCE = 1L;
    private static final Long UPDATED_PAYMENT_REFERENCE = 2L;
    private static final Long SMALLER_PAYMENT_REFERENCE = 1L - 1L;

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REJECTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REJECTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REJECTION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECTION_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_REJECTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REJECTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PROCESS_INSTANCE_ID = 1L;
    private static final Long UPDATED_PROCESS_INSTANCE_ID = 2L;
    private static final Long SMALLER_PROCESS_INSTANCE_ID = 1L - 1L;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceRequestMapper serviceRequestMapper;

    @Autowired
    private ServiceRequestService serviceRequestService;

    @Autowired
    private ServiceRequestQueryService serviceRequestQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceRequestMockMvc;

    private ServiceRequest serviceRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRequest createEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .serviceDocument(DEFAULT_SERVICE_DOCUMENT)
            .feeDocument(DEFAULT_FEE_DOCUMENT)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .phaseType(DEFAULT_PHASE_TYPE)
            .status(DEFAULT_STATUS)
            .statusDescription(DEFAULT_STATUS_DESCRIPTION)
            .statusDate(DEFAULT_STATUS_DATE)
            .totalFeeAmount(DEFAULT_TOTAL_FEE_AMOUNT)
            .paidBy(DEFAULT_PAID_BY)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .paymentReference(DEFAULT_PAYMENT_REFERENCE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .rejectedBy(DEFAULT_REJECTED_BY)
            .rejectionReason(DEFAULT_REJECTION_REASON)
            .rejectionDate(DEFAULT_REJECTION_DATE)
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID);
        return serviceRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceRequest createUpdatedEntity(EntityManager em) {
        ServiceRequest serviceRequest = new ServiceRequest()
            .serviceDocument(UPDATED_SERVICE_DOCUMENT)
            .feeDocument(UPDATED_FEE_DOCUMENT)
            .serviceCode(UPDATED_SERVICE_CODE)
            .phaseType(UPDATED_PHASE_TYPE)
            .status(UPDATED_STATUS)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusDate(UPDATED_STATUS_DATE)
            .totalFeeAmount(UPDATED_TOTAL_FEE_AMOUNT)
            .paidBy(UPDATED_PAID_BY)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentReference(UPDATED_PAYMENT_REFERENCE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .rejectedBy(UPDATED_REJECTED_BY)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .rejectionDate(UPDATED_REJECTION_DATE)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID);
        return serviceRequest;
    }

    @BeforeEach
    public void initTest() {
        serviceRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceRequest() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);
        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getServiceDocument()).isEqualTo(DEFAULT_SERVICE_DOCUMENT);
        assertThat(testServiceRequest.getFeeDocument()).isEqualTo(DEFAULT_FEE_DOCUMENT);
        assertThat(testServiceRequest.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testServiceRequest.getPhaseType()).isEqualTo(DEFAULT_PHASE_TYPE);
        assertThat(testServiceRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testServiceRequest.getStatusDescription()).isEqualTo(DEFAULT_STATUS_DESCRIPTION);
        assertThat(testServiceRequest.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testServiceRequest.getTotalFeeAmount()).isEqualTo(DEFAULT_TOTAL_FEE_AMOUNT);
        assertThat(testServiceRequest.getPaidBy()).isEqualTo(DEFAULT_PAID_BY);
        assertThat(testServiceRequest.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testServiceRequest.getPaymentReference()).isEqualTo(DEFAULT_PAYMENT_REFERENCE);
        assertThat(testServiceRequest.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testServiceRequest.getRejectedBy()).isEqualTo(DEFAULT_REJECTED_BY);
        assertThat(testServiceRequest.getRejectionReason()).isEqualTo(DEFAULT_REJECTION_REASON);
        assertThat(testServiceRequest.getRejectionDate()).isEqualTo(DEFAULT_REJECTION_DATE);
        assertThat(testServiceRequest.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void createServiceRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest with an existing ID
        serviceRequest.setId(1L);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setServiceCode(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhaseTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setPhaseType(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setStatus(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setStatusDescription(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceRequestRepository.findAll().size();
        // set the field null
        serviceRequest.setStatusDate(null);

        // Create the ServiceRequest, which fails.
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        restServiceRequestMockMvc.perform(post("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceRequests() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList
        restServiceRequestMockMvc.perform(get("/api/service-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceDocument").value(hasItem(DEFAULT_SERVICE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].feeDocument").value(hasItem(DEFAULT_FEE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE)))
            .andExpect(jsonPath("$.[*].phaseType").value(hasItem(DEFAULT_PHASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalFeeAmount").value(hasItem(DEFAULT_TOTAL_FEE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidBy").value(hasItem(DEFAULT_PAID_BY)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].rejectedBy").value(hasItem(DEFAULT_REJECTED_BY)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)))
            .andExpect(jsonPath("$.[*].rejectionDate").value(hasItem(DEFAULT_REJECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get("/api/service-requests/{id}", serviceRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceRequest.getId().intValue()))
            .andExpect(jsonPath("$.serviceDocument").value(DEFAULT_SERVICE_DOCUMENT.toString()))
            .andExpect(jsonPath("$.feeDocument").value(DEFAULT_FEE_DOCUMENT.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE))
            .andExpect(jsonPath("$.phaseType").value(DEFAULT_PHASE_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusDescription").value(DEFAULT_STATUS_DESCRIPTION))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.totalFeeAmount").value(DEFAULT_TOTAL_FEE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paidBy").value(DEFAULT_PAID_BY))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.paymentReference").value(DEFAULT_PAYMENT_REFERENCE.intValue()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.rejectedBy").value(DEFAULT_REJECTED_BY))
            .andExpect(jsonPath("$.rejectionReason").value(DEFAULT_REJECTION_REASON))
            .andExpect(jsonPath("$.rejectionDate").value(DEFAULT_REJECTION_DATE.toString()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID.intValue()));
    }


    @Test
    @Transactional
    public void getServiceRequestsByIdFiltering() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        Long id = serviceRequest.getId();

        defaultServiceRequestShouldBeFound("id.equals=" + id);
        defaultServiceRequestShouldNotBeFound("id.notEquals=" + id);

        defaultServiceRequestShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceRequestShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceRequestShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceRequestShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByServiceCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where serviceCode equals to DEFAULT_SERVICE_CODE
        defaultServiceRequestShouldBeFound("serviceCode.equals=" + DEFAULT_SERVICE_CODE);

        // Get all the serviceRequestList where serviceCode equals to UPDATED_SERVICE_CODE
        defaultServiceRequestShouldNotBeFound("serviceCode.equals=" + UPDATED_SERVICE_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByServiceCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where serviceCode not equals to DEFAULT_SERVICE_CODE
        defaultServiceRequestShouldNotBeFound("serviceCode.notEquals=" + DEFAULT_SERVICE_CODE);

        // Get all the serviceRequestList where serviceCode not equals to UPDATED_SERVICE_CODE
        defaultServiceRequestShouldBeFound("serviceCode.notEquals=" + UPDATED_SERVICE_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByServiceCodeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where serviceCode in DEFAULT_SERVICE_CODE or UPDATED_SERVICE_CODE
        defaultServiceRequestShouldBeFound("serviceCode.in=" + DEFAULT_SERVICE_CODE + "," + UPDATED_SERVICE_CODE);

        // Get all the serviceRequestList where serviceCode equals to UPDATED_SERVICE_CODE
        defaultServiceRequestShouldNotBeFound("serviceCode.in=" + UPDATED_SERVICE_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByServiceCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where serviceCode is not null
        defaultServiceRequestShouldBeFound("serviceCode.specified=true");

        // Get all the serviceRequestList where serviceCode is null
        defaultServiceRequestShouldNotBeFound("serviceCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceRequestsByServiceCodeContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where serviceCode contains DEFAULT_SERVICE_CODE
        defaultServiceRequestShouldBeFound("serviceCode.contains=" + DEFAULT_SERVICE_CODE);

        // Get all the serviceRequestList where serviceCode contains UPDATED_SERVICE_CODE
        defaultServiceRequestShouldNotBeFound("serviceCode.contains=" + UPDATED_SERVICE_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByServiceCodeNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where serviceCode does not contain DEFAULT_SERVICE_CODE
        defaultServiceRequestShouldNotBeFound("serviceCode.doesNotContain=" + DEFAULT_SERVICE_CODE);

        // Get all the serviceRequestList where serviceCode does not contain UPDATED_SERVICE_CODE
        defaultServiceRequestShouldBeFound("serviceCode.doesNotContain=" + UPDATED_SERVICE_CODE);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByPhaseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where phaseType equals to DEFAULT_PHASE_TYPE
        defaultServiceRequestShouldBeFound("phaseType.equals=" + DEFAULT_PHASE_TYPE);

        // Get all the serviceRequestList where phaseType equals to UPDATED_PHASE_TYPE
        defaultServiceRequestShouldNotBeFound("phaseType.equals=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPhaseTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where phaseType not equals to DEFAULT_PHASE_TYPE
        defaultServiceRequestShouldNotBeFound("phaseType.notEquals=" + DEFAULT_PHASE_TYPE);

        // Get all the serviceRequestList where phaseType not equals to UPDATED_PHASE_TYPE
        defaultServiceRequestShouldBeFound("phaseType.notEquals=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPhaseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where phaseType in DEFAULT_PHASE_TYPE or UPDATED_PHASE_TYPE
        defaultServiceRequestShouldBeFound("phaseType.in=" + DEFAULT_PHASE_TYPE + "," + UPDATED_PHASE_TYPE);

        // Get all the serviceRequestList where phaseType equals to UPDATED_PHASE_TYPE
        defaultServiceRequestShouldNotBeFound("phaseType.in=" + UPDATED_PHASE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPhaseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where phaseType is not null
        defaultServiceRequestShouldBeFound("phaseType.specified=true");

        // Get all the serviceRequestList where phaseType is null
        defaultServiceRequestShouldNotBeFound("phaseType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where status equals to DEFAULT_STATUS
        defaultServiceRequestShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the serviceRequestList where status equals to UPDATED_STATUS
        defaultServiceRequestShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where status not equals to DEFAULT_STATUS
        defaultServiceRequestShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the serviceRequestList where status not equals to UPDATED_STATUS
        defaultServiceRequestShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultServiceRequestShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the serviceRequestList where status equals to UPDATED_STATUS
        defaultServiceRequestShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where status is not null
        defaultServiceRequestShouldBeFound("status.specified=true");

        // Get all the serviceRequestList where status is null
        defaultServiceRequestShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDescription equals to DEFAULT_STATUS_DESCRIPTION
        defaultServiceRequestShouldBeFound("statusDescription.equals=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the serviceRequestList where statusDescription equals to UPDATED_STATUS_DESCRIPTION
        defaultServiceRequestShouldNotBeFound("statusDescription.equals=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDescription not equals to DEFAULT_STATUS_DESCRIPTION
        defaultServiceRequestShouldNotBeFound("statusDescription.notEquals=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the serviceRequestList where statusDescription not equals to UPDATED_STATUS_DESCRIPTION
        defaultServiceRequestShouldBeFound("statusDescription.notEquals=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDescription in DEFAULT_STATUS_DESCRIPTION or UPDATED_STATUS_DESCRIPTION
        defaultServiceRequestShouldBeFound("statusDescription.in=" + DEFAULT_STATUS_DESCRIPTION + "," + UPDATED_STATUS_DESCRIPTION);

        // Get all the serviceRequestList where statusDescription equals to UPDATED_STATUS_DESCRIPTION
        defaultServiceRequestShouldNotBeFound("statusDescription.in=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDescription is not null
        defaultServiceRequestShouldBeFound("statusDescription.specified=true");

        // Get all the serviceRequestList where statusDescription is null
        defaultServiceRequestShouldNotBeFound("statusDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceRequestsByStatusDescriptionContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDescription contains DEFAULT_STATUS_DESCRIPTION
        defaultServiceRequestShouldBeFound("statusDescription.contains=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the serviceRequestList where statusDescription contains UPDATED_STATUS_DESCRIPTION
        defaultServiceRequestShouldNotBeFound("statusDescription.contains=" + UPDATED_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDescription does not contain DEFAULT_STATUS_DESCRIPTION
        defaultServiceRequestShouldNotBeFound("statusDescription.doesNotContain=" + DEFAULT_STATUS_DESCRIPTION);

        // Get all the serviceRequestList where statusDescription does not contain UPDATED_STATUS_DESCRIPTION
        defaultServiceRequestShouldBeFound("statusDescription.doesNotContain=" + UPDATED_STATUS_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDate equals to DEFAULT_STATUS_DATE
        defaultServiceRequestShouldBeFound("statusDate.equals=" + DEFAULT_STATUS_DATE);

        // Get all the serviceRequestList where statusDate equals to UPDATED_STATUS_DATE
        defaultServiceRequestShouldNotBeFound("statusDate.equals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDate not equals to DEFAULT_STATUS_DATE
        defaultServiceRequestShouldNotBeFound("statusDate.notEquals=" + DEFAULT_STATUS_DATE);

        // Get all the serviceRequestList where statusDate not equals to UPDATED_STATUS_DATE
        defaultServiceRequestShouldBeFound("statusDate.notEquals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDate in DEFAULT_STATUS_DATE or UPDATED_STATUS_DATE
        defaultServiceRequestShouldBeFound("statusDate.in=" + DEFAULT_STATUS_DATE + "," + UPDATED_STATUS_DATE);

        // Get all the serviceRequestList where statusDate equals to UPDATED_STATUS_DATE
        defaultServiceRequestShouldNotBeFound("statusDate.in=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where statusDate is not null
        defaultServiceRequestShouldBeFound("statusDate.specified=true");

        // Get all the serviceRequestList where statusDate is null
        defaultServiceRequestShouldNotBeFound("statusDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount equals to DEFAULT_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.equals=" + DEFAULT_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount equals to UPDATED_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.equals=" + UPDATED_TOTAL_FEE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount not equals to DEFAULT_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.notEquals=" + DEFAULT_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount not equals to UPDATED_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.notEquals=" + UPDATED_TOTAL_FEE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount in DEFAULT_TOTAL_FEE_AMOUNT or UPDATED_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.in=" + DEFAULT_TOTAL_FEE_AMOUNT + "," + UPDATED_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount equals to UPDATED_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.in=" + UPDATED_TOTAL_FEE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount is not null
        defaultServiceRequestShouldBeFound("totalFeeAmount.specified=true");

        // Get all the serviceRequestList where totalFeeAmount is null
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount is greater than or equal to DEFAULT_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount is greater than or equal to UPDATED_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.greaterThanOrEqual=" + UPDATED_TOTAL_FEE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount is less than or equal to DEFAULT_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.lessThanOrEqual=" + DEFAULT_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount is less than or equal to SMALLER_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.lessThanOrEqual=" + SMALLER_TOTAL_FEE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount is less than DEFAULT_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.lessThan=" + DEFAULT_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount is less than UPDATED_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.lessThan=" + UPDATED_TOTAL_FEE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByTotalFeeAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where totalFeeAmount is greater than DEFAULT_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldNotBeFound("totalFeeAmount.greaterThan=" + DEFAULT_TOTAL_FEE_AMOUNT);

        // Get all the serviceRequestList where totalFeeAmount is greater than SMALLER_TOTAL_FEE_AMOUNT
        defaultServiceRequestShouldBeFound("totalFeeAmount.greaterThan=" + SMALLER_TOTAL_FEE_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByPaidByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paidBy equals to DEFAULT_PAID_BY
        defaultServiceRequestShouldBeFound("paidBy.equals=" + DEFAULT_PAID_BY);

        // Get all the serviceRequestList where paidBy equals to UPDATED_PAID_BY
        defaultServiceRequestShouldNotBeFound("paidBy.equals=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaidByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paidBy not equals to DEFAULT_PAID_BY
        defaultServiceRequestShouldNotBeFound("paidBy.notEquals=" + DEFAULT_PAID_BY);

        // Get all the serviceRequestList where paidBy not equals to UPDATED_PAID_BY
        defaultServiceRequestShouldBeFound("paidBy.notEquals=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaidByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paidBy in DEFAULT_PAID_BY or UPDATED_PAID_BY
        defaultServiceRequestShouldBeFound("paidBy.in=" + DEFAULT_PAID_BY + "," + UPDATED_PAID_BY);

        // Get all the serviceRequestList where paidBy equals to UPDATED_PAID_BY
        defaultServiceRequestShouldNotBeFound("paidBy.in=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaidByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paidBy is not null
        defaultServiceRequestShouldBeFound("paidBy.specified=true");

        // Get all the serviceRequestList where paidBy is null
        defaultServiceRequestShouldNotBeFound("paidBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceRequestsByPaidByContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paidBy contains DEFAULT_PAID_BY
        defaultServiceRequestShouldBeFound("paidBy.contains=" + DEFAULT_PAID_BY);

        // Get all the serviceRequestList where paidBy contains UPDATED_PAID_BY
        defaultServiceRequestShouldNotBeFound("paidBy.contains=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaidByNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paidBy does not contain DEFAULT_PAID_BY
        defaultServiceRequestShouldNotBeFound("paidBy.doesNotContain=" + DEFAULT_PAID_BY);

        // Get all the serviceRequestList where paidBy does not contain UPDATED_PAID_BY
        defaultServiceRequestShouldBeFound("paidBy.doesNotContain=" + UPDATED_PAID_BY);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod equals to DEFAULT_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.equals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.equals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod not equals to DEFAULT_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.notEquals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod not equals to UPDATED_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.notEquals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod in DEFAULT_PAYMENT_METHOD or UPDATED_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.in=" + DEFAULT_PAYMENT_METHOD + "," + UPDATED_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.in=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod is not null
        defaultServiceRequestShouldBeFound("paymentMethod.specified=true");

        // Get all the serviceRequestList where paymentMethod is null
        defaultServiceRequestShouldNotBeFound("paymentMethod.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod is greater than or equal to DEFAULT_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.greaterThanOrEqual=" + DEFAULT_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod is greater than or equal to UPDATED_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.greaterThanOrEqual=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod is less than or equal to DEFAULT_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.lessThanOrEqual=" + DEFAULT_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod is less than or equal to SMALLER_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.lessThanOrEqual=" + SMALLER_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod is less than DEFAULT_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.lessThan=" + DEFAULT_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod is less than UPDATED_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.lessThan=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentMethodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentMethod is greater than DEFAULT_PAYMENT_METHOD
        defaultServiceRequestShouldNotBeFound("paymentMethod.greaterThan=" + DEFAULT_PAYMENT_METHOD);

        // Get all the serviceRequestList where paymentMethod is greater than SMALLER_PAYMENT_METHOD
        defaultServiceRequestShouldBeFound("paymentMethod.greaterThan=" + SMALLER_PAYMENT_METHOD);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference equals to DEFAULT_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.equals=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference equals to UPDATED_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.equals=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference not equals to DEFAULT_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.notEquals=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference not equals to UPDATED_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.notEquals=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference in DEFAULT_PAYMENT_REFERENCE or UPDATED_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.in=" + DEFAULT_PAYMENT_REFERENCE + "," + UPDATED_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference equals to UPDATED_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.in=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference is not null
        defaultServiceRequestShouldBeFound("paymentReference.specified=true");

        // Get all the serviceRequestList where paymentReference is null
        defaultServiceRequestShouldNotBeFound("paymentReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference is greater than or equal to DEFAULT_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.greaterThanOrEqual=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference is greater than or equal to UPDATED_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.greaterThanOrEqual=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference is less than or equal to DEFAULT_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.lessThanOrEqual=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference is less than or equal to SMALLER_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.lessThanOrEqual=" + SMALLER_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference is less than DEFAULT_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.lessThan=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference is less than UPDATED_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.lessThan=" + UPDATED_PAYMENT_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentReferenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentReference is greater than DEFAULT_PAYMENT_REFERENCE
        defaultServiceRequestShouldNotBeFound("paymentReference.greaterThan=" + DEFAULT_PAYMENT_REFERENCE);

        // Get all the serviceRequestList where paymentReference is greater than SMALLER_PAYMENT_REFERENCE
        defaultServiceRequestShouldBeFound("paymentReference.greaterThan=" + SMALLER_PAYMENT_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentDate equals to DEFAULT_PAYMENT_DATE
        defaultServiceRequestShouldBeFound("paymentDate.equals=" + DEFAULT_PAYMENT_DATE);

        // Get all the serviceRequestList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultServiceRequestShouldNotBeFound("paymentDate.equals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentDate not equals to DEFAULT_PAYMENT_DATE
        defaultServiceRequestShouldNotBeFound("paymentDate.notEquals=" + DEFAULT_PAYMENT_DATE);

        // Get all the serviceRequestList where paymentDate not equals to UPDATED_PAYMENT_DATE
        defaultServiceRequestShouldBeFound("paymentDate.notEquals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentDate in DEFAULT_PAYMENT_DATE or UPDATED_PAYMENT_DATE
        defaultServiceRequestShouldBeFound("paymentDate.in=" + DEFAULT_PAYMENT_DATE + "," + UPDATED_PAYMENT_DATE);

        // Get all the serviceRequestList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultServiceRequestShouldNotBeFound("paymentDate.in=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where paymentDate is not null
        defaultServiceRequestShouldBeFound("paymentDate.specified=true");

        // Get all the serviceRequestList where paymentDate is null
        defaultServiceRequestShouldNotBeFound("paymentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectedBy equals to DEFAULT_REJECTED_BY
        defaultServiceRequestShouldBeFound("rejectedBy.equals=" + DEFAULT_REJECTED_BY);

        // Get all the serviceRequestList where rejectedBy equals to UPDATED_REJECTED_BY
        defaultServiceRequestShouldNotBeFound("rejectedBy.equals=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectedBy not equals to DEFAULT_REJECTED_BY
        defaultServiceRequestShouldNotBeFound("rejectedBy.notEquals=" + DEFAULT_REJECTED_BY);

        // Get all the serviceRequestList where rejectedBy not equals to UPDATED_REJECTED_BY
        defaultServiceRequestShouldBeFound("rejectedBy.notEquals=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectedByIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectedBy in DEFAULT_REJECTED_BY or UPDATED_REJECTED_BY
        defaultServiceRequestShouldBeFound("rejectedBy.in=" + DEFAULT_REJECTED_BY + "," + UPDATED_REJECTED_BY);

        // Get all the serviceRequestList where rejectedBy equals to UPDATED_REJECTED_BY
        defaultServiceRequestShouldNotBeFound("rejectedBy.in=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectedBy is not null
        defaultServiceRequestShouldBeFound("rejectedBy.specified=true");

        // Get all the serviceRequestList where rejectedBy is null
        defaultServiceRequestShouldNotBeFound("rejectedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceRequestsByRejectedByContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectedBy contains DEFAULT_REJECTED_BY
        defaultServiceRequestShouldBeFound("rejectedBy.contains=" + DEFAULT_REJECTED_BY);

        // Get all the serviceRequestList where rejectedBy contains UPDATED_REJECTED_BY
        defaultServiceRequestShouldNotBeFound("rejectedBy.contains=" + UPDATED_REJECTED_BY);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectedByNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectedBy does not contain DEFAULT_REJECTED_BY
        defaultServiceRequestShouldNotBeFound("rejectedBy.doesNotContain=" + DEFAULT_REJECTED_BY);

        // Get all the serviceRequestList where rejectedBy does not contain UPDATED_REJECTED_BY
        defaultServiceRequestShouldBeFound("rejectedBy.doesNotContain=" + UPDATED_REJECTED_BY);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionReason equals to DEFAULT_REJECTION_REASON
        defaultServiceRequestShouldBeFound("rejectionReason.equals=" + DEFAULT_REJECTION_REASON);

        // Get all the serviceRequestList where rejectionReason equals to UPDATED_REJECTION_REASON
        defaultServiceRequestShouldNotBeFound("rejectionReason.equals=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionReason not equals to DEFAULT_REJECTION_REASON
        defaultServiceRequestShouldNotBeFound("rejectionReason.notEquals=" + DEFAULT_REJECTION_REASON);

        // Get all the serviceRequestList where rejectionReason not equals to UPDATED_REJECTION_REASON
        defaultServiceRequestShouldBeFound("rejectionReason.notEquals=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionReasonIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionReason in DEFAULT_REJECTION_REASON or UPDATED_REJECTION_REASON
        defaultServiceRequestShouldBeFound("rejectionReason.in=" + DEFAULT_REJECTION_REASON + "," + UPDATED_REJECTION_REASON);

        // Get all the serviceRequestList where rejectionReason equals to UPDATED_REJECTION_REASON
        defaultServiceRequestShouldNotBeFound("rejectionReason.in=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionReason is not null
        defaultServiceRequestShouldBeFound("rejectionReason.specified=true");

        // Get all the serviceRequestList where rejectionReason is null
        defaultServiceRequestShouldNotBeFound("rejectionReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceRequestsByRejectionReasonContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionReason contains DEFAULT_REJECTION_REASON
        defaultServiceRequestShouldBeFound("rejectionReason.contains=" + DEFAULT_REJECTION_REASON);

        // Get all the serviceRequestList where rejectionReason contains UPDATED_REJECTION_REASON
        defaultServiceRequestShouldNotBeFound("rejectionReason.contains=" + UPDATED_REJECTION_REASON);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionReasonNotContainsSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionReason does not contain DEFAULT_REJECTION_REASON
        defaultServiceRequestShouldNotBeFound("rejectionReason.doesNotContain=" + DEFAULT_REJECTION_REASON);

        // Get all the serviceRequestList where rejectionReason does not contain UPDATED_REJECTION_REASON
        defaultServiceRequestShouldBeFound("rejectionReason.doesNotContain=" + UPDATED_REJECTION_REASON);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionDate equals to DEFAULT_REJECTION_DATE
        defaultServiceRequestShouldBeFound("rejectionDate.equals=" + DEFAULT_REJECTION_DATE);

        // Get all the serviceRequestList where rejectionDate equals to UPDATED_REJECTION_DATE
        defaultServiceRequestShouldNotBeFound("rejectionDate.equals=" + UPDATED_REJECTION_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionDate not equals to DEFAULT_REJECTION_DATE
        defaultServiceRequestShouldNotBeFound("rejectionDate.notEquals=" + DEFAULT_REJECTION_DATE);

        // Get all the serviceRequestList where rejectionDate not equals to UPDATED_REJECTION_DATE
        defaultServiceRequestShouldBeFound("rejectionDate.notEquals=" + UPDATED_REJECTION_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionDateIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionDate in DEFAULT_REJECTION_DATE or UPDATED_REJECTION_DATE
        defaultServiceRequestShouldBeFound("rejectionDate.in=" + DEFAULT_REJECTION_DATE + "," + UPDATED_REJECTION_DATE);

        // Get all the serviceRequestList where rejectionDate equals to UPDATED_REJECTION_DATE
        defaultServiceRequestShouldNotBeFound("rejectionDate.in=" + UPDATED_REJECTION_DATE);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByRejectionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where rejectionDate is not null
        defaultServiceRequestShouldBeFound("rejectionDate.specified=true");

        // Get all the serviceRequestList where rejectionDate is null
        defaultServiceRequestShouldNotBeFound("rejectionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId equals to DEFAULT_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.equals=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId equals to UPDATED_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.equals=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId not equals to DEFAULT_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.notEquals=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId not equals to UPDATED_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.notEquals=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId in DEFAULT_PROCESS_INSTANCE_ID or UPDATED_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.in=" + DEFAULT_PROCESS_INSTANCE_ID + "," + UPDATED_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId equals to UPDATED_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.in=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId is not null
        defaultServiceRequestShouldBeFound("processInstanceId.specified=true");

        // Get all the serviceRequestList where processInstanceId is null
        defaultServiceRequestShouldNotBeFound("processInstanceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId is greater than or equal to DEFAULT_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.greaterThanOrEqual=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId is greater than or equal to UPDATED_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.greaterThanOrEqual=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId is less than or equal to DEFAULT_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.lessThanOrEqual=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId is less than or equal to SMALLER_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.lessThanOrEqual=" + SMALLER_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId is less than DEFAULT_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.lessThan=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId is less than UPDATED_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.lessThan=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceRequestsByProcessInstanceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        // Get all the serviceRequestList where processInstanceId is greater than DEFAULT_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldNotBeFound("processInstanceId.greaterThan=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the serviceRequestList where processInstanceId is greater than SMALLER_PROCESS_INSTANCE_ID
        defaultServiceRequestShouldBeFound("processInstanceId.greaterThan=" + SMALLER_PROCESS_INSTANCE_ID);
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByApplicationViolationIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);
        ApplicationViolation applicationViolation = ApplicationViolationResourceIT.createEntity(em);
        em.persist(applicationViolation);
        em.flush();
        serviceRequest.addApplicationViolation(applicationViolation);
        serviceRequestRepository.saveAndFlush(serviceRequest);
        Long applicationViolationId = applicationViolation.getId();

        // Get all the serviceRequestList where applicationViolation equals to applicationViolationId
        defaultServiceRequestShouldBeFound("applicationViolationId.equals=" + applicationViolationId);

        // Get all the serviceRequestList where applicationViolation equals to applicationViolationId + 1
        defaultServiceRequestShouldNotBeFound("applicationViolationId.equals=" + (applicationViolationId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByReversedByIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);
        ServiceRequest reversedBy = ServiceRequestResourceIT.createEntity(em);
        em.persist(reversedBy);
        em.flush();
        serviceRequest.setReversedBy(reversedBy);
        serviceRequestRepository.saveAndFlush(serviceRequest);
        Long reversedById = reversedBy.getId();

        // Get all the serviceRequestList where reversedBy equals to reversedById
        defaultServiceRequestShouldBeFound("reversedById.equals=" + reversedById);

        // Get all the serviceRequestList where reversedBy equals to reversedById + 1
        defaultServiceRequestShouldNotBeFound("reversedById.equals=" + (reversedById + 1));
    }


    @Test
    @Transactional
    public void getAllServiceRequestsByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);
        Application application = ApplicationResourceIT.createEntity(em);
        em.persist(application);
        em.flush();
        serviceRequest.setApplication(application);
        serviceRequestRepository.saveAndFlush(serviceRequest);
        Long applicationId = application.getId();

        // Get all the serviceRequestList where application equals to applicationId
        defaultServiceRequestShouldBeFound("applicationId.equals=" + applicationId);

        // Get all the serviceRequestList where application equals to applicationId + 1
        defaultServiceRequestShouldNotBeFound("applicationId.equals=" + (applicationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceRequestShouldBeFound(String filter) throws Exception {
        restServiceRequestMockMvc.perform(get("/api/service-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceDocument").value(hasItem(DEFAULT_SERVICE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].feeDocument").value(hasItem(DEFAULT_FEE_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE)))
            .andExpect(jsonPath("$.[*].phaseType").value(hasItem(DEFAULT_PHASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusDescription").value(hasItem(DEFAULT_STATUS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalFeeAmount").value(hasItem(DEFAULT_TOTAL_FEE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidBy").value(hasItem(DEFAULT_PAID_BY)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].paymentReference").value(hasItem(DEFAULT_PAYMENT_REFERENCE.intValue())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].rejectedBy").value(hasItem(DEFAULT_REJECTED_BY)))
            .andExpect(jsonPath("$.[*].rejectionReason").value(hasItem(DEFAULT_REJECTION_REASON)))
            .andExpect(jsonPath("$.[*].rejectionDate").value(hasItem(DEFAULT_REJECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID.intValue())));

        // Check, that the count call also returns 1
        restServiceRequestMockMvc.perform(get("/api/service-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceRequestShouldNotBeFound(String filter) throws Exception {
        restServiceRequestMockMvc.perform(get("/api/service-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceRequestMockMvc.perform(get("/api/service-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceRequest() throws Exception {
        // Get the serviceRequest
        restServiceRequestMockMvc.perform(get("/api/service-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Update the serviceRequest
        ServiceRequest updatedServiceRequest = serviceRequestRepository.findById(serviceRequest.getId()).get();
        // Disconnect from session so that the updates on updatedServiceRequest are not directly saved in db
        em.detach(updatedServiceRequest);
        updatedServiceRequest
            .serviceDocument(UPDATED_SERVICE_DOCUMENT)
            .feeDocument(UPDATED_FEE_DOCUMENT)
            .serviceCode(UPDATED_SERVICE_CODE)
            .phaseType(UPDATED_PHASE_TYPE)
            .status(UPDATED_STATUS)
            .statusDescription(UPDATED_STATUS_DESCRIPTION)
            .statusDate(UPDATED_STATUS_DATE)
            .totalFeeAmount(UPDATED_TOTAL_FEE_AMOUNT)
            .paidBy(UPDATED_PAID_BY)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentReference(UPDATED_PAYMENT_REFERENCE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .rejectedBy(UPDATED_REJECTED_BY)
            .rejectionReason(UPDATED_REJECTION_REASON)
            .rejectionDate(UPDATED_REJECTION_DATE)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID);
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(updatedServiceRequest);

        restServiceRequestMockMvc.perform(put("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
        ServiceRequest testServiceRequest = serviceRequestList.get(serviceRequestList.size() - 1);
        assertThat(testServiceRequest.getServiceDocument()).isEqualTo(UPDATED_SERVICE_DOCUMENT);
        assertThat(testServiceRequest.getFeeDocument()).isEqualTo(UPDATED_FEE_DOCUMENT);
        assertThat(testServiceRequest.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testServiceRequest.getPhaseType()).isEqualTo(UPDATED_PHASE_TYPE);
        assertThat(testServiceRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testServiceRequest.getStatusDescription()).isEqualTo(UPDATED_STATUS_DESCRIPTION);
        assertThat(testServiceRequest.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testServiceRequest.getTotalFeeAmount()).isEqualTo(UPDATED_TOTAL_FEE_AMOUNT);
        assertThat(testServiceRequest.getPaidBy()).isEqualTo(UPDATED_PAID_BY);
        assertThat(testServiceRequest.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testServiceRequest.getPaymentReference()).isEqualTo(UPDATED_PAYMENT_REFERENCE);
        assertThat(testServiceRequest.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testServiceRequest.getRejectedBy()).isEqualTo(UPDATED_REJECTED_BY);
        assertThat(testServiceRequest.getRejectionReason()).isEqualTo(UPDATED_REJECTION_REASON);
        assertThat(testServiceRequest.getRejectionDate()).isEqualTo(UPDATED_REJECTION_DATE);
        assertThat(testServiceRequest.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceRequest() throws Exception {
        int databaseSizeBeforeUpdate = serviceRequestRepository.findAll().size();

        // Create the ServiceRequest
        ServiceRequestDTO serviceRequestDTO = serviceRequestMapper.toDto(serviceRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceRequestMockMvc.perform(put("/api/service-requests").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceRequest in the database
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceRequest() throws Exception {
        // Initialize the database
        serviceRequestRepository.saveAndFlush(serviceRequest);

        int databaseSizeBeforeDelete = serviceRequestRepository.findAll().size();

        // Delete the serviceRequest
        restServiceRequestMockMvc.perform(delete("/api/service-requests/{id}", serviceRequest.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceRequest> serviceRequestList = serviceRequestRepository.findAll();
        assertThat(serviceRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
