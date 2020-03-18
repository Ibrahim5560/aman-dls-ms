package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.WebService;
import com.isoft.dls.domain.WebServiceProperty;
import com.isoft.dls.repository.WebServiceRepository;
import com.isoft.dls.service.WebServiceService;
import com.isoft.dls.service.dto.WebServiceDTO;
import com.isoft.dls.service.mapper.WebServiceMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.WebServiceCriteria;
import com.isoft.dls.service.WebServiceQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.isoft.dls.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.dls.domain.enumeration.WebServiceName;
/**
 * Integration tests for the {@link WebServiceResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class WebServiceResourceIT {

    private static final String DEFAULT_END_POINT = "AAAAAAAAAA";
    private static final String UPDATED_END_POINT = "BBBBBBBBBB";

    private static final WebServiceName DEFAULT_NAME = WebServiceName.INITIATE_PROCESS;
    private static final WebServiceName UPDATED_NAME = WebServiceName.USER_INBOX;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_TECHNICAL_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WebServiceRepository webServiceRepository;

    @Autowired
    private WebServiceMapper webServiceMapper;

    @Autowired
    private WebServiceService webServiceService;

    @Autowired
    private WebServiceQueryService webServiceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWebServiceMockMvc;

    private WebService webService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WebServiceResource webServiceResource = new WebServiceResource(webServiceService, webServiceQueryService);
        this.restWebServiceMockMvc = MockMvcBuilders.standaloneSetup(webServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebService createEntity(EntityManager em) {
        WebService webService = new WebService()
            .endPoint(DEFAULT_END_POINT)
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return webService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebService createUpdatedEntity(EntityManager em) {
        WebService webService = new WebService()
            .endPoint(UPDATED_END_POINT)
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .technicalRemarks(UPDATED_TECHNICAL_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return webService;
    }

    @BeforeEach
    public void initTest() {
        webService = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebService() throws Exception {
        int databaseSizeBeforeCreate = webServiceRepository.findAll().size();

        // Create the WebService
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(webService);
        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeCreate + 1);
        WebService testWebService = webServiceList.get(webServiceList.size() - 1);
        assertThat(testWebService.getEndPoint()).isEqualTo(DEFAULT_END_POINT);
        assertThat(testWebService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWebService.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testWebService.getTechnicalRemarks()).isEqualTo(DEFAULT_TECHNICAL_REMARKS);
        assertThat(testWebService.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWebService.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWebService.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWebService.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createWebServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webServiceRepository.findAll().size();

        // Create the WebService with an existing ID
        webService.setId(1L);
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(webService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServiceRepository.findAll().size();
        // set the field null
        webService.setName(null);

        // Create the WebService, which fails.
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(webService);

        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isBadRequest());

        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServiceRepository.findAll().size();
        // set the field null
        webService.setCreatedBy(null);

        // Create the WebService, which fails.
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(webService);

        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isBadRequest());

        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServiceRepository.findAll().size();
        // set the field null
        webService.setCreatedDate(null);

        // Create the WebService, which fails.
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(webService);

        restWebServiceMockMvc.perform(post("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isBadRequest());

        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebServices() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList
        restWebServiceMockMvc.perform(get("/api/web-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webService.getId().intValue())))
            .andExpect(jsonPath("$.[*].endPoint").value(hasItem(DEFAULT_END_POINT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getWebService() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get the webService
        restWebServiceMockMvc.perform(get("/api/web-services/{id}", webService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webService.getId().intValue()))
            .andExpect(jsonPath("$.endPoint").value(DEFAULT_END_POINT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.technicalRemarks").value(DEFAULT_TECHNICAL_REMARKS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getWebServicesByIdFiltering() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        Long id = webService.getId();

        defaultWebServiceShouldBeFound("id.equals=" + id);
        defaultWebServiceShouldNotBeFound("id.notEquals=" + id);

        defaultWebServiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWebServiceShouldNotBeFound("id.greaterThan=" + id);

        defaultWebServiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWebServiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWebServicesByEndPointIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where endPoint equals to DEFAULT_END_POINT
        defaultWebServiceShouldBeFound("endPoint.equals=" + DEFAULT_END_POINT);

        // Get all the webServiceList where endPoint equals to UPDATED_END_POINT
        defaultWebServiceShouldNotBeFound("endPoint.equals=" + UPDATED_END_POINT);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEndPointIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where endPoint not equals to DEFAULT_END_POINT
        defaultWebServiceShouldNotBeFound("endPoint.notEquals=" + DEFAULT_END_POINT);

        // Get all the webServiceList where endPoint not equals to UPDATED_END_POINT
        defaultWebServiceShouldBeFound("endPoint.notEquals=" + UPDATED_END_POINT);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEndPointIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where endPoint in DEFAULT_END_POINT or UPDATED_END_POINT
        defaultWebServiceShouldBeFound("endPoint.in=" + DEFAULT_END_POINT + "," + UPDATED_END_POINT);

        // Get all the webServiceList where endPoint equals to UPDATED_END_POINT
        defaultWebServiceShouldNotBeFound("endPoint.in=" + UPDATED_END_POINT);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEndPointIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where endPoint is not null
        defaultWebServiceShouldBeFound("endPoint.specified=true");

        // Get all the webServiceList where endPoint is null
        defaultWebServiceShouldNotBeFound("endPoint.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicesByEndPointContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where endPoint contains DEFAULT_END_POINT
        defaultWebServiceShouldBeFound("endPoint.contains=" + DEFAULT_END_POINT);

        // Get all the webServiceList where endPoint contains UPDATED_END_POINT
        defaultWebServiceShouldNotBeFound("endPoint.contains=" + UPDATED_END_POINT);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEndPointNotContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where endPoint does not contain DEFAULT_END_POINT
        defaultWebServiceShouldNotBeFound("endPoint.doesNotContain=" + DEFAULT_END_POINT);

        // Get all the webServiceList where endPoint does not contain UPDATED_END_POINT
        defaultWebServiceShouldBeFound("endPoint.doesNotContain=" + UPDATED_END_POINT);
    }


    @Test
    @Transactional
    public void getAllWebServicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where name equals to DEFAULT_NAME
        defaultWebServiceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the webServiceList where name equals to UPDATED_NAME
        defaultWebServiceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWebServicesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where name not equals to DEFAULT_NAME
        defaultWebServiceShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the webServiceList where name not equals to UPDATED_NAME
        defaultWebServiceShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWebServicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWebServiceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the webServiceList where name equals to UPDATED_NAME
        defaultWebServiceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWebServicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where name is not null
        defaultWebServiceShouldBeFound("name.specified=true");

        // Get all the webServiceList where name is null
        defaultWebServiceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where enabled equals to DEFAULT_ENABLED
        defaultWebServiceShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the webServiceList where enabled equals to UPDATED_ENABLED
        defaultWebServiceShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where enabled not equals to DEFAULT_ENABLED
        defaultWebServiceShouldNotBeFound("enabled.notEquals=" + DEFAULT_ENABLED);

        // Get all the webServiceList where enabled not equals to UPDATED_ENABLED
        defaultWebServiceShouldBeFound("enabled.notEquals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultWebServiceShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the webServiceList where enabled equals to UPDATED_ENABLED
        defaultWebServiceShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllWebServicesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where enabled is not null
        defaultWebServiceShouldBeFound("enabled.specified=true");

        // Get all the webServiceList where enabled is null
        defaultWebServiceShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicesByTechnicalRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where technicalRemarks equals to DEFAULT_TECHNICAL_REMARKS
        defaultWebServiceShouldBeFound("technicalRemarks.equals=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServiceList where technicalRemarks equals to UPDATED_TECHNICAL_REMARKS
        defaultWebServiceShouldNotBeFound("technicalRemarks.equals=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicesByTechnicalRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where technicalRemarks not equals to DEFAULT_TECHNICAL_REMARKS
        defaultWebServiceShouldNotBeFound("technicalRemarks.notEquals=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServiceList where technicalRemarks not equals to UPDATED_TECHNICAL_REMARKS
        defaultWebServiceShouldBeFound("technicalRemarks.notEquals=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicesByTechnicalRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where technicalRemarks in DEFAULT_TECHNICAL_REMARKS or UPDATED_TECHNICAL_REMARKS
        defaultWebServiceShouldBeFound("technicalRemarks.in=" + DEFAULT_TECHNICAL_REMARKS + "," + UPDATED_TECHNICAL_REMARKS);

        // Get all the webServiceList where technicalRemarks equals to UPDATED_TECHNICAL_REMARKS
        defaultWebServiceShouldNotBeFound("technicalRemarks.in=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicesByTechnicalRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where technicalRemarks is not null
        defaultWebServiceShouldBeFound("technicalRemarks.specified=true");

        // Get all the webServiceList where technicalRemarks is null
        defaultWebServiceShouldNotBeFound("technicalRemarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicesByTechnicalRemarksContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where technicalRemarks contains DEFAULT_TECHNICAL_REMARKS
        defaultWebServiceShouldBeFound("technicalRemarks.contains=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServiceList where technicalRemarks contains UPDATED_TECHNICAL_REMARKS
        defaultWebServiceShouldNotBeFound("technicalRemarks.contains=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicesByTechnicalRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where technicalRemarks does not contain DEFAULT_TECHNICAL_REMARKS
        defaultWebServiceShouldNotBeFound("technicalRemarks.doesNotContain=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServiceList where technicalRemarks does not contain UPDATED_TECHNICAL_REMARKS
        defaultWebServiceShouldBeFound("technicalRemarks.doesNotContain=" + UPDATED_TECHNICAL_REMARKS);
    }


    @Test
    @Transactional
    public void getAllWebServicesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdBy equals to DEFAULT_CREATED_BY
        defaultWebServiceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the webServiceList where createdBy equals to UPDATED_CREATED_BY
        defaultWebServiceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdBy not equals to DEFAULT_CREATED_BY
        defaultWebServiceShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the webServiceList where createdBy not equals to UPDATED_CREATED_BY
        defaultWebServiceShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultWebServiceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the webServiceList where createdBy equals to UPDATED_CREATED_BY
        defaultWebServiceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdBy is not null
        defaultWebServiceShouldBeFound("createdBy.specified=true");

        // Get all the webServiceList where createdBy is null
        defaultWebServiceShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdBy contains DEFAULT_CREATED_BY
        defaultWebServiceShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the webServiceList where createdBy contains UPDATED_CREATED_BY
        defaultWebServiceShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdBy does not contain DEFAULT_CREATED_BY
        defaultWebServiceShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the webServiceList where createdBy does not contain UPDATED_CREATED_BY
        defaultWebServiceShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllWebServicesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdDate equals to DEFAULT_CREATED_DATE
        defaultWebServiceShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the webServiceList where createdDate equals to UPDATED_CREATED_DATE
        defaultWebServiceShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultWebServiceShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the webServiceList where createdDate not equals to UPDATED_CREATED_DATE
        defaultWebServiceShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultWebServiceShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the webServiceList where createdDate equals to UPDATED_CREATED_DATE
        defaultWebServiceShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where createdDate is not null
        defaultWebServiceShouldBeFound("createdDate.specified=true");

        // Get all the webServiceList where createdDate is null
        defaultWebServiceShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWebServiceShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServiceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWebServiceShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultWebServiceShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServiceList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultWebServiceShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWebServiceShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the webServiceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWebServiceShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedBy is not null
        defaultWebServiceShouldBeFound("lastModifiedBy.specified=true");

        // Get all the webServiceList where lastModifiedBy is null
        defaultWebServiceShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWebServiceShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServiceList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWebServiceShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWebServiceShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServiceList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWebServiceShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultWebServiceShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the webServiceList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultWebServiceShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultWebServiceShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the webServiceList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultWebServiceShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultWebServiceShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the webServiceList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultWebServiceShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        // Get all the webServiceList where lastModifiedDate is not null
        defaultWebServiceShouldBeFound("lastModifiedDate.specified=true");

        // Get all the webServiceList where lastModifiedDate is null
        defaultWebServiceShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicesByWebServicePropertyIsEqualToSomething() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);
        WebServiceProperty webServiceProperty = WebServicePropertyResourceIT.createEntity(em);
        em.persist(webServiceProperty);
        em.flush();
        webService.addWebServiceProperty(webServiceProperty);
        webServiceRepository.saveAndFlush(webService);
        Long webServicePropertyId = webServiceProperty.getId();

        // Get all the webServiceList where webServiceProperty equals to webServicePropertyId
        defaultWebServiceShouldBeFound("webServicePropertyId.equals=" + webServicePropertyId);

        // Get all the webServiceList where webServiceProperty equals to webServicePropertyId + 1
        defaultWebServiceShouldNotBeFound("webServicePropertyId.equals=" + (webServicePropertyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWebServiceShouldBeFound(String filter) throws Exception {
        restWebServiceMockMvc.perform(get("/api/web-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webService.getId().intValue())))
            .andExpect(jsonPath("$.[*].endPoint").value(hasItem(DEFAULT_END_POINT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restWebServiceMockMvc.perform(get("/api/web-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWebServiceShouldNotBeFound(String filter) throws Exception {
        restWebServiceMockMvc.perform(get("/api/web-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWebServiceMockMvc.perform(get("/api/web-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWebService() throws Exception {
        // Get the webService
        restWebServiceMockMvc.perform(get("/api/web-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebService() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        int databaseSizeBeforeUpdate = webServiceRepository.findAll().size();

        // Update the webService
        WebService updatedWebService = webServiceRepository.findById(webService.getId()).get();
        // Disconnect from session so that the updates on updatedWebService are not directly saved in db
        em.detach(updatedWebService);
        updatedWebService
            .endPoint(UPDATED_END_POINT)
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .technicalRemarks(UPDATED_TECHNICAL_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(updatedWebService);

        restWebServiceMockMvc.perform(put("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isOk());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeUpdate);
        WebService testWebService = webServiceList.get(webServiceList.size() - 1);
        assertThat(testWebService.getEndPoint()).isEqualTo(UPDATED_END_POINT);
        assertThat(testWebService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWebService.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testWebService.getTechnicalRemarks()).isEqualTo(UPDATED_TECHNICAL_REMARKS);
        assertThat(testWebService.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWebService.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWebService.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWebService.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWebService() throws Exception {
        int databaseSizeBeforeUpdate = webServiceRepository.findAll().size();

        // Create the WebService
        WebServiceDTO webServiceDTO = webServiceMapper.toDto(webService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebServiceMockMvc.perform(put("/api/web-services")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebService in the database
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebService() throws Exception {
        // Initialize the database
        webServiceRepository.saveAndFlush(webService);

        int databaseSizeBeforeDelete = webServiceRepository.findAll().size();

        // Delete the webService
        restWebServiceMockMvc.perform(delete("/api/web-services/{id}", webService.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebService> webServiceList = webServiceRepository.findAll();
        assertThat(webServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
