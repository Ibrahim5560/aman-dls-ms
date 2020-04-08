package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.WebServiceProperty;
import com.isoft.dls.domain.WebService;
import com.isoft.dls.repository.WebServicePropertyRepository;
import com.isoft.dls.service.WebServicePropertyService;
import com.isoft.dls.service.dto.WebServicePropertyDTO;
import com.isoft.dls.service.mapper.WebServicePropertyMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.WebServicePropertyCriteria;
import com.isoft.dls.service.WebServicePropertyQueryService;

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

import com.isoft.dls.domain.enumeration.WebServicePropertyName;
/**
 * Integration tests for the {@link WebServicePropertyResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class WebServicePropertyResourceIT {

    private static final WebServicePropertyName DEFAULT_NAME = WebServicePropertyName.USERNAME;
    private static final WebServicePropertyName UPDATED_NAME = WebServicePropertyName.PASSWORD;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

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
    private WebServicePropertyRepository webServicePropertyRepository;

    @Autowired
    private WebServicePropertyMapper webServicePropertyMapper;

    @Autowired
    private WebServicePropertyService webServicePropertyService;

    @Autowired
    private WebServicePropertyQueryService webServicePropertyQueryService;

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

    private MockMvc restWebServicePropertyMockMvc;

    private WebServiceProperty webServiceProperty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WebServicePropertyResource webServicePropertyResource = new WebServicePropertyResource(webServicePropertyService, webServicePropertyQueryService);
        this.restWebServicePropertyMockMvc = MockMvcBuilders.standaloneSetup(webServicePropertyResource)
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
    public static WebServiceProperty createEntity(EntityManager em) {
        WebServiceProperty webServiceProperty = new WebServiceProperty()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .technicalRemarks(DEFAULT_TECHNICAL_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return webServiceProperty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebServiceProperty createUpdatedEntity(EntityManager em) {
        WebServiceProperty webServiceProperty = new WebServiceProperty()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .technicalRemarks(UPDATED_TECHNICAL_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return webServiceProperty;
    }

    @BeforeEach
    public void initTest() {
        webServiceProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebServiceProperty() throws Exception {
        int databaseSizeBeforeCreate = webServicePropertyRepository.findAll().size();

        // Create the WebServiceProperty
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(webServiceProperty);
        restWebServicePropertyMockMvc.perform(post("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isCreated());

        // Validate the WebServiceProperty in the database
        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeCreate + 1);
        WebServiceProperty testWebServiceProperty = webServicePropertyList.get(webServicePropertyList.size() - 1);
        assertThat(testWebServiceProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWebServiceProperty.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testWebServiceProperty.getTechnicalRemarks()).isEqualTo(DEFAULT_TECHNICAL_REMARKS);
        assertThat(testWebServiceProperty.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWebServiceProperty.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWebServiceProperty.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWebServiceProperty.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createWebServicePropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webServicePropertyRepository.findAll().size();

        // Create the WebServiceProperty with an existing ID
        webServiceProperty.setId(1L);
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(webServiceProperty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebServicePropertyMockMvc.perform(post("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebServiceProperty in the database
        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServicePropertyRepository.findAll().size();
        // set the field null
        webServiceProperty.setName(null);

        // Create the WebServiceProperty, which fails.
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(webServiceProperty);

        restWebServicePropertyMockMvc.perform(post("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServicePropertyRepository.findAll().size();
        // set the field null
        webServiceProperty.setCreatedBy(null);

        // Create the WebServiceProperty, which fails.
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(webServiceProperty);

        restWebServicePropertyMockMvc.perform(post("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = webServicePropertyRepository.findAll().size();
        // set the field null
        webServiceProperty.setCreatedDate(null);

        // Create the WebServiceProperty, which fails.
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(webServiceProperty);

        restWebServicePropertyMockMvc.perform(post("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isBadRequest());

        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWebServiceProperties() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webServiceProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getWebServiceProperty() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get the webServiceProperty
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties/{id}", webServiceProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webServiceProperty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.technicalRemarks").value(DEFAULT_TECHNICAL_REMARKS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getWebServicePropertiesByIdFiltering() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        Long id = webServiceProperty.getId();

        defaultWebServicePropertyShouldBeFound("id.equals=" + id);
        defaultWebServicePropertyShouldNotBeFound("id.notEquals=" + id);

        defaultWebServicePropertyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWebServicePropertyShouldNotBeFound("id.greaterThan=" + id);

        defaultWebServicePropertyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWebServicePropertyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWebServicePropertiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where name equals to DEFAULT_NAME
        defaultWebServicePropertyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the webServicePropertyList where name equals to UPDATED_NAME
        defaultWebServicePropertyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where name not equals to DEFAULT_NAME
        defaultWebServicePropertyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the webServicePropertyList where name not equals to UPDATED_NAME
        defaultWebServicePropertyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWebServicePropertyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the webServicePropertyList where name equals to UPDATED_NAME
        defaultWebServicePropertyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where name is not null
        defaultWebServicePropertyShouldBeFound("name.specified=true");

        // Get all the webServicePropertyList where name is null
        defaultWebServicePropertyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where value equals to DEFAULT_VALUE
        defaultWebServicePropertyShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the webServicePropertyList where value equals to UPDATED_VALUE
        defaultWebServicePropertyShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where value not equals to DEFAULT_VALUE
        defaultWebServicePropertyShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the webServicePropertyList where value not equals to UPDATED_VALUE
        defaultWebServicePropertyShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultWebServicePropertyShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the webServicePropertyList where value equals to UPDATED_VALUE
        defaultWebServicePropertyShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where value is not null
        defaultWebServicePropertyShouldBeFound("value.specified=true");

        // Get all the webServicePropertyList where value is null
        defaultWebServicePropertyShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicePropertiesByValueContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where value contains DEFAULT_VALUE
        defaultWebServicePropertyShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the webServicePropertyList where value contains UPDATED_VALUE
        defaultWebServicePropertyShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where value does not contain DEFAULT_VALUE
        defaultWebServicePropertyShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the webServicePropertyList where value does not contain UPDATED_VALUE
        defaultWebServicePropertyShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllWebServicePropertiesByTechnicalRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where technicalRemarks equals to DEFAULT_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldBeFound("technicalRemarks.equals=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServicePropertyList where technicalRemarks equals to UPDATED_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldNotBeFound("technicalRemarks.equals=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByTechnicalRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where technicalRemarks not equals to DEFAULT_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldNotBeFound("technicalRemarks.notEquals=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServicePropertyList where technicalRemarks not equals to UPDATED_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldBeFound("technicalRemarks.notEquals=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByTechnicalRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where technicalRemarks in DEFAULT_TECHNICAL_REMARKS or UPDATED_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldBeFound("technicalRemarks.in=" + DEFAULT_TECHNICAL_REMARKS + "," + UPDATED_TECHNICAL_REMARKS);

        // Get all the webServicePropertyList where technicalRemarks equals to UPDATED_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldNotBeFound("technicalRemarks.in=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByTechnicalRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where technicalRemarks is not null
        defaultWebServicePropertyShouldBeFound("technicalRemarks.specified=true");

        // Get all the webServicePropertyList where technicalRemarks is null
        defaultWebServicePropertyShouldNotBeFound("technicalRemarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicePropertiesByTechnicalRemarksContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where technicalRemarks contains DEFAULT_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldBeFound("technicalRemarks.contains=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServicePropertyList where technicalRemarks contains UPDATED_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldNotBeFound("technicalRemarks.contains=" + UPDATED_TECHNICAL_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByTechnicalRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where technicalRemarks does not contain DEFAULT_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldNotBeFound("technicalRemarks.doesNotContain=" + DEFAULT_TECHNICAL_REMARKS);

        // Get all the webServicePropertyList where technicalRemarks does not contain UPDATED_TECHNICAL_REMARKS
        defaultWebServicePropertyShouldBeFound("technicalRemarks.doesNotContain=" + UPDATED_TECHNICAL_REMARKS);
    }


    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdBy equals to DEFAULT_CREATED_BY
        defaultWebServicePropertyShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the webServicePropertyList where createdBy equals to UPDATED_CREATED_BY
        defaultWebServicePropertyShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdBy not equals to DEFAULT_CREATED_BY
        defaultWebServicePropertyShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the webServicePropertyList where createdBy not equals to UPDATED_CREATED_BY
        defaultWebServicePropertyShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultWebServicePropertyShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the webServicePropertyList where createdBy equals to UPDATED_CREATED_BY
        defaultWebServicePropertyShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdBy is not null
        defaultWebServicePropertyShouldBeFound("createdBy.specified=true");

        // Get all the webServicePropertyList where createdBy is null
        defaultWebServicePropertyShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdBy contains DEFAULT_CREATED_BY
        defaultWebServicePropertyShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the webServicePropertyList where createdBy contains UPDATED_CREATED_BY
        defaultWebServicePropertyShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdBy does not contain DEFAULT_CREATED_BY
        defaultWebServicePropertyShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the webServicePropertyList where createdBy does not contain UPDATED_CREATED_BY
        defaultWebServicePropertyShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdDate equals to DEFAULT_CREATED_DATE
        defaultWebServicePropertyShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the webServicePropertyList where createdDate equals to UPDATED_CREATED_DATE
        defaultWebServicePropertyShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultWebServicePropertyShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the webServicePropertyList where createdDate not equals to UPDATED_CREATED_DATE
        defaultWebServicePropertyShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultWebServicePropertyShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the webServicePropertyList where createdDate equals to UPDATED_CREATED_DATE
        defaultWebServicePropertyShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where createdDate is not null
        defaultWebServicePropertyShouldBeFound("createdDate.specified=true");

        // Get all the webServicePropertyList where createdDate is null
        defaultWebServicePropertyShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServicePropertyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServicePropertyList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the webServicePropertyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedBy is not null
        defaultWebServicePropertyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the webServicePropertyList where lastModifiedBy is null
        defaultWebServicePropertyShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServicePropertyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the webServicePropertyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWebServicePropertyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultWebServicePropertyShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the webServicePropertyList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultWebServicePropertyShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultWebServicePropertyShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the webServicePropertyList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultWebServicePropertyShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultWebServicePropertyShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the webServicePropertyList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultWebServicePropertyShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        // Get all the webServicePropertyList where lastModifiedDate is not null
        defaultWebServicePropertyShouldBeFound("lastModifiedDate.specified=true");

        // Get all the webServicePropertyList where lastModifiedDate is null
        defaultWebServicePropertyShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWebServicePropertiesByWebServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);
        WebService webService = WebServiceResourceIT.createEntity(em);
        em.persist(webService);
        em.flush();
        webServiceProperty.setWebService(webService);
        webServicePropertyRepository.saveAndFlush(webServiceProperty);
        Long webServiceId = webService.getId();

        // Get all the webServicePropertyList where webService equals to webServiceId
        defaultWebServicePropertyShouldBeFound("webServiceId.equals=" + webServiceId);

        // Get all the webServicePropertyList where webService equals to webServiceId + 1
        defaultWebServicePropertyShouldNotBeFound("webServiceId.equals=" + (webServiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWebServicePropertyShouldBeFound(String filter) throws Exception {
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webServiceProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].technicalRemarks").value(hasItem(DEFAULT_TECHNICAL_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWebServicePropertyShouldNotBeFound(String filter) throws Exception {
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWebServiceProperty() throws Exception {
        // Get the webServiceProperty
        restWebServicePropertyMockMvc.perform(get("/api/web-service-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebServiceProperty() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        int databaseSizeBeforeUpdate = webServicePropertyRepository.findAll().size();

        // Update the webServiceProperty
        WebServiceProperty updatedWebServiceProperty = webServicePropertyRepository.findById(webServiceProperty.getId()).get();
        // Disconnect from session so that the updates on updatedWebServiceProperty are not directly saved in db
        em.detach(updatedWebServiceProperty);
        updatedWebServiceProperty
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .technicalRemarks(UPDATED_TECHNICAL_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(updatedWebServiceProperty);

        restWebServicePropertyMockMvc.perform(put("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isOk());

        // Validate the WebServiceProperty in the database
        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeUpdate);
        WebServiceProperty testWebServiceProperty = webServicePropertyList.get(webServicePropertyList.size() - 1);
        assertThat(testWebServiceProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWebServiceProperty.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWebServiceProperty.getTechnicalRemarks()).isEqualTo(UPDATED_TECHNICAL_REMARKS);
        assertThat(testWebServiceProperty.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWebServiceProperty.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWebServiceProperty.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWebServiceProperty.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWebServiceProperty() throws Exception {
        int databaseSizeBeforeUpdate = webServicePropertyRepository.findAll().size();

        // Create the WebServiceProperty
        WebServicePropertyDTO webServicePropertyDTO = webServicePropertyMapper.toDto(webServiceProperty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebServicePropertyMockMvc.perform(put("/api/web-service-properties")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webServicePropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebServiceProperty in the database
        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWebServiceProperty() throws Exception {
        // Initialize the database
        webServicePropertyRepository.saveAndFlush(webServiceProperty);

        int databaseSizeBeforeDelete = webServicePropertyRepository.findAll().size();

        // Delete the webServiceProperty
        restWebServicePropertyMockMvc.perform(delete("/api/web-service-properties/{id}", webServiceProperty.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WebServiceProperty> webServicePropertyList = webServicePropertyRepository.findAll();
        assertThat(webServicePropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
