package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.SysDomainValue;
import com.isoft.dls.domain.SysDomain;
import com.isoft.dls.repository.SysDomainValueRepository;
import com.isoft.dls.service.SysDomainValueService;
import com.isoft.dls.service.dto.SysDomainValueDTO;
import com.isoft.dls.service.mapper.SysDomainValueMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.SysDomainValueCriteria;
import com.isoft.dls.service.SysDomainValueQueryService;

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

/**
 * Integration tests for the {@link SysDomainValueResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class SysDomainValueResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final Integer DEFAULT_DOMAIN_ID = 1;
    private static final Integer UPDATED_DOMAIN_ID = 2;
    private static final Integer SMALLER_DOMAIN_ID = 1 - 1;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SysDomainValueRepository sysDomainValueRepository;

    @Autowired
    private SysDomainValueMapper sysDomainValueMapper;

    @Autowired
    private SysDomainValueService sysDomainValueService;

    @Autowired
    private SysDomainValueQueryService sysDomainValueQueryService;

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

    private MockMvc restSysDomainValueMockMvc;

    private SysDomainValue sysDomainValue;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysDomainValueResource sysDomainValueResource = new SysDomainValueResource(sysDomainValueService, sysDomainValueQueryService);
        this.restSysDomainValueMockMvc = MockMvcBuilders.standaloneSetup(sysDomainValueResource)
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
    public static SysDomainValue createEntity(EntityManager em) {
        SysDomainValue sysDomainValue = new SysDomainValue()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .sortOrder(DEFAULT_SORT_ORDER)
            .domainId(DEFAULT_DOMAIN_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sysDomainValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysDomainValue createUpdatedEntity(EntityManager em) {
        SysDomainValue sysDomainValue = new SysDomainValue()
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .domainId(UPDATED_DOMAIN_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sysDomainValue;
    }

    @BeforeEach
    public void initTest() {
        sysDomainValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysDomainValue() throws Exception {
        int databaseSizeBeforeCreate = sysDomainValueRepository.findAll().size();

        // Create the SysDomainValue
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);
        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isCreated());

        // Validate the SysDomainValue in the database
        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeCreate + 1);
        SysDomainValue testSysDomainValue = sysDomainValueList.get(sysDomainValueList.size() - 1);
        assertThat(testSysDomainValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSysDomainValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSysDomainValue.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testSysDomainValue.getDomainId()).isEqualTo(DEFAULT_DOMAIN_ID);
        assertThat(testSysDomainValue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysDomainValue.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysDomainValue.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSysDomainValue.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSysDomainValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysDomainValueRepository.findAll().size();

        // Create the SysDomainValue with an existing ID
        sysDomainValue.setId(1L);
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysDomainValue in the database
        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainValueRepository.findAll().size();
        // set the field null
        sysDomainValue.setValue(null);

        // Create the SysDomainValue, which fails.
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainValueRepository.findAll().size();
        // set the field null
        sysDomainValue.setDescription(null);

        // Create the SysDomainValue, which fails.
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainValueRepository.findAll().size();
        // set the field null
        sysDomainValue.setSortOrder(null);

        // Create the SysDomainValue, which fails.
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomainIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainValueRepository.findAll().size();
        // set the field null
        sysDomainValue.setDomainId(null);

        // Create the SysDomainValue, which fails.
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainValueRepository.findAll().size();
        // set the field null
        sysDomainValue.setCreatedBy(null);

        // Create the SysDomainValue, which fails.
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainValueRepository.findAll().size();
        // set the field null
        sysDomainValue.setCreatedDate(null);

        // Create the SysDomainValue, which fails.
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        restSysDomainValueMockMvc.perform(post("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSysDomainValues() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDomainValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].domainId").value(hasItem(DEFAULT_DOMAIN_ID)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSysDomainValue() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get the sysDomainValue
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values/{id}", sysDomainValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysDomainValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.domainId").value(DEFAULT_DOMAIN_ID))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getSysDomainValuesByIdFiltering() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        Long id = sysDomainValue.getId();

        defaultSysDomainValueShouldBeFound("id.equals=" + id);
        defaultSysDomainValueShouldNotBeFound("id.notEquals=" + id);

        defaultSysDomainValueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSysDomainValueShouldNotBeFound("id.greaterThan=" + id);

        defaultSysDomainValueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSysDomainValueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where value equals to DEFAULT_VALUE
        defaultSysDomainValueShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the sysDomainValueList where value equals to UPDATED_VALUE
        defaultSysDomainValueShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where value not equals to DEFAULT_VALUE
        defaultSysDomainValueShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the sysDomainValueList where value not equals to UPDATED_VALUE
        defaultSysDomainValueShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultSysDomainValueShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the sysDomainValueList where value equals to UPDATED_VALUE
        defaultSysDomainValueShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where value is not null
        defaultSysDomainValueShouldBeFound("value.specified=true");

        // Get all the sysDomainValueList where value is null
        defaultSysDomainValueShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainValuesByValueContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where value contains DEFAULT_VALUE
        defaultSysDomainValueShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the sysDomainValueList where value contains UPDATED_VALUE
        defaultSysDomainValueShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where value does not contain DEFAULT_VALUE
        defaultSysDomainValueShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the sysDomainValueList where value does not contain UPDATED_VALUE
        defaultSysDomainValueShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where description equals to DEFAULT_DESCRIPTION
        defaultSysDomainValueShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainValueList where description equals to UPDATED_DESCRIPTION
        defaultSysDomainValueShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where description not equals to DEFAULT_DESCRIPTION
        defaultSysDomainValueShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainValueList where description not equals to UPDATED_DESCRIPTION
        defaultSysDomainValueShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSysDomainValueShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sysDomainValueList where description equals to UPDATED_DESCRIPTION
        defaultSysDomainValueShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where description is not null
        defaultSysDomainValueShouldBeFound("description.specified=true");

        // Get all the sysDomainValueList where description is null
        defaultSysDomainValueShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainValuesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where description contains DEFAULT_DESCRIPTION
        defaultSysDomainValueShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainValueList where description contains UPDATED_DESCRIPTION
        defaultSysDomainValueShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where description does not contain DEFAULT_DESCRIPTION
        defaultSysDomainValueShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainValueList where description does not contain UPDATED_DESCRIPTION
        defaultSysDomainValueShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder equals to UPDATED_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder equals to UPDATED_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder is not null
        defaultSysDomainValueShouldBeFound("sortOrder.specified=true");

        // Get all the sysDomainValueList where sortOrder is null
        defaultSysDomainValueShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder is less than UPDATED_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultSysDomainValueShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the sysDomainValueList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultSysDomainValueShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId equals to DEFAULT_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.equals=" + DEFAULT_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId equals to UPDATED_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.equals=" + UPDATED_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId not equals to DEFAULT_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.notEquals=" + DEFAULT_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId not equals to UPDATED_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.notEquals=" + UPDATED_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId in DEFAULT_DOMAIN_ID or UPDATED_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.in=" + DEFAULT_DOMAIN_ID + "," + UPDATED_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId equals to UPDATED_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.in=" + UPDATED_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId is not null
        defaultSysDomainValueShouldBeFound("domainId.specified=true");

        // Get all the sysDomainValueList where domainId is null
        defaultSysDomainValueShouldNotBeFound("domainId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId is greater than or equal to DEFAULT_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.greaterThanOrEqual=" + DEFAULT_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId is greater than or equal to UPDATED_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.greaterThanOrEqual=" + UPDATED_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId is less than or equal to DEFAULT_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.lessThanOrEqual=" + DEFAULT_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId is less than or equal to SMALLER_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.lessThanOrEqual=" + SMALLER_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsLessThanSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId is less than DEFAULT_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.lessThan=" + DEFAULT_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId is less than UPDATED_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.lessThan=" + UPDATED_DOMAIN_ID);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByDomainIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where domainId is greater than DEFAULT_DOMAIN_ID
        defaultSysDomainValueShouldNotBeFound("domainId.greaterThan=" + DEFAULT_DOMAIN_ID);

        // Get all the sysDomainValueList where domainId is greater than SMALLER_DOMAIN_ID
        defaultSysDomainValueShouldBeFound("domainId.greaterThan=" + SMALLER_DOMAIN_ID);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdBy equals to DEFAULT_CREATED_BY
        defaultSysDomainValueShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainValueList where createdBy equals to UPDATED_CREATED_BY
        defaultSysDomainValueShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdBy not equals to DEFAULT_CREATED_BY
        defaultSysDomainValueShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainValueList where createdBy not equals to UPDATED_CREATED_BY
        defaultSysDomainValueShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSysDomainValueShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the sysDomainValueList where createdBy equals to UPDATED_CREATED_BY
        defaultSysDomainValueShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdBy is not null
        defaultSysDomainValueShouldBeFound("createdBy.specified=true");

        // Get all the sysDomainValueList where createdBy is null
        defaultSysDomainValueShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdBy contains DEFAULT_CREATED_BY
        defaultSysDomainValueShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainValueList where createdBy contains UPDATED_CREATED_BY
        defaultSysDomainValueShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSysDomainValueShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainValueList where createdBy does not contain UPDATED_CREATED_BY
        defaultSysDomainValueShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSysDomainValueShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the sysDomainValueList where createdDate equals to UPDATED_CREATED_DATE
        defaultSysDomainValueShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultSysDomainValueShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the sysDomainValueList where createdDate not equals to UPDATED_CREATED_DATE
        defaultSysDomainValueShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSysDomainValueShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the sysDomainValueList where createdDate equals to UPDATED_CREATED_DATE
        defaultSysDomainValueShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where createdDate is not null
        defaultSysDomainValueShouldBeFound("createdDate.specified=true");

        // Get all the sysDomainValueList where createdDate is null
        defaultSysDomainValueShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainValueShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainValueList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSysDomainValueShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainValueShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainValueList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSysDomainValueShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSysDomainValueShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the sysDomainValueList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSysDomainValueShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedBy is not null
        defaultSysDomainValueShouldBeFound("lastModifiedBy.specified=true");

        // Get all the sysDomainValueList where lastModifiedBy is null
        defaultSysDomainValueShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainValueShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainValueList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSysDomainValueShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainValueShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainValueList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSysDomainValueShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSysDomainValueShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the sysDomainValueList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainValueShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSysDomainValueShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the sysDomainValueList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainValueShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainValueShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the sysDomainValueList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainValueShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        // Get all the sysDomainValueList where lastModifiedDate is not null
        defaultSysDomainValueShouldBeFound("lastModifiedDate.specified=true");

        // Get all the sysDomainValueList where lastModifiedDate is null
        defaultSysDomainValueShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysDomainValuesBySysDomainIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);
        SysDomain sysDomain = SysDomainResourceIT.createEntity(em);
        em.persist(sysDomain);
        em.flush();
        sysDomainValue.setSysDomain(sysDomain);
        sysDomainValueRepository.saveAndFlush(sysDomainValue);
        Long sysDomainId = sysDomain.getId();

        // Get all the sysDomainValueList where sysDomain equals to sysDomainId
        defaultSysDomainValueShouldBeFound("sysDomainId.equals=" + sysDomainId);

        // Get all the sysDomainValueList where sysDomain equals to sysDomainId + 1
        defaultSysDomainValueShouldNotBeFound("sysDomainId.equals=" + (sysDomainId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSysDomainValueShouldBeFound(String filter) throws Exception {
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDomainValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].domainId").value(hasItem(DEFAULT_DOMAIN_ID)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSysDomainValueShouldNotBeFound(String filter) throws Exception {
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSysDomainValue() throws Exception {
        // Get the sysDomainValue
        restSysDomainValueMockMvc.perform(get("/api/sys-domain-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysDomainValue() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        int databaseSizeBeforeUpdate = sysDomainValueRepository.findAll().size();

        // Update the sysDomainValue
        SysDomainValue updatedSysDomainValue = sysDomainValueRepository.findById(sysDomainValue.getId()).get();
        // Disconnect from session so that the updates on updatedSysDomainValue are not directly saved in db
        em.detach(updatedSysDomainValue);
        updatedSysDomainValue
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .domainId(UPDATED_DOMAIN_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(updatedSysDomainValue);

        restSysDomainValueMockMvc.perform(put("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isOk());

        // Validate the SysDomainValue in the database
        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeUpdate);
        SysDomainValue testSysDomainValue = sysDomainValueList.get(sysDomainValueList.size() - 1);
        assertThat(testSysDomainValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSysDomainValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysDomainValue.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testSysDomainValue.getDomainId()).isEqualTo(UPDATED_DOMAIN_ID);
        assertThat(testSysDomainValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysDomainValue.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysDomainValue.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysDomainValue.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSysDomainValue() throws Exception {
        int databaseSizeBeforeUpdate = sysDomainValueRepository.findAll().size();

        // Create the SysDomainValue
        SysDomainValueDTO sysDomainValueDTO = sysDomainValueMapper.toDto(sysDomainValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysDomainValueMockMvc.perform(put("/api/sys-domain-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysDomainValue in the database
        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysDomainValue() throws Exception {
        // Initialize the database
        sysDomainValueRepository.saveAndFlush(sysDomainValue);

        int databaseSizeBeforeDelete = sysDomainValueRepository.findAll().size();

        // Delete the sysDomainValue
        restSysDomainValueMockMvc.perform(delete("/api/sys-domain-values/{id}", sysDomainValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysDomainValue> sysDomainValueList = sysDomainValueRepository.findAll();
        assertThat(sysDomainValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
