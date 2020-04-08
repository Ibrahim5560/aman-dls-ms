package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.SysDomain;
import com.isoft.dls.domain.SysDomainValue;
import com.isoft.dls.repository.SysDomainRepository;
import com.isoft.dls.service.SysDomainService;
import com.isoft.dls.service.dto.SysDomainDTO;
import com.isoft.dls.service.mapper.SysDomainMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.SysDomainCriteria;
import com.isoft.dls.service.SysDomainQueryService;

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
 * Integration tests for the {@link SysDomainResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class SysDomainResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SysDomainRepository sysDomainRepository;

    @Autowired
    private SysDomainMapper sysDomainMapper;

    @Autowired
    private SysDomainService sysDomainService;

    @Autowired
    private SysDomainQueryService sysDomainQueryService;

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

    private MockMvc restSysDomainMockMvc;

    private SysDomain sysDomain;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysDomainResource sysDomainResource = new SysDomainResource(sysDomainService, sysDomainQueryService);
        this.restSysDomainMockMvc = MockMvcBuilders.standaloneSetup(sysDomainResource)
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
    public static SysDomain createEntity(EntityManager em) {
        SysDomain sysDomain = new SysDomain()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return sysDomain;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysDomain createUpdatedEntity(EntityManager em) {
        SysDomain sysDomain = new SysDomain()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return sysDomain;
    }

    @BeforeEach
    public void initTest() {
        sysDomain = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysDomain() throws Exception {
        int databaseSizeBeforeCreate = sysDomainRepository.findAll().size();

        // Create the SysDomain
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);
        restSysDomainMockMvc.perform(post("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the SysDomain in the database
        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeCreate + 1);
        SysDomain testSysDomain = sysDomainList.get(sysDomainList.size() - 1);
        assertThat(testSysDomain.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSysDomain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSysDomain.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSysDomain.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSysDomain.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSysDomain.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSysDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysDomainRepository.findAll().size();

        // Create the SysDomain with an existing ID
        sysDomain.setId(1L);
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysDomainMockMvc.perform(post("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysDomain in the database
        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainRepository.findAll().size();
        // set the field null
        sysDomain.setCode(null);

        // Create the SysDomain, which fails.
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);

        restSysDomainMockMvc.perform(post("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainRepository.findAll().size();
        // set the field null
        sysDomain.setDescription(null);

        // Create the SysDomain, which fails.
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);

        restSysDomainMockMvc.perform(post("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainRepository.findAll().size();
        // set the field null
        sysDomain.setCreatedBy(null);

        // Create the SysDomain, which fails.
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);

        restSysDomainMockMvc.perform(post("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDomainRepository.findAll().size();
        // set the field null
        sysDomain.setCreatedDate(null);

        // Create the SysDomain, which fails.
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);

        restSysDomainMockMvc.perform(post("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isBadRequest());

        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSysDomains() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList
        restSysDomainMockMvc.perform(get("/api/sys-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSysDomain() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get the sysDomain
        restSysDomainMockMvc.perform(get("/api/sys-domains/{id}", sysDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysDomain.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getSysDomainsByIdFiltering() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        Long id = sysDomain.getId();

        defaultSysDomainShouldBeFound("id.equals=" + id);
        defaultSysDomainShouldNotBeFound("id.notEquals=" + id);

        defaultSysDomainShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSysDomainShouldNotBeFound("id.greaterThan=" + id);

        defaultSysDomainShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSysDomainShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSysDomainsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where code equals to DEFAULT_CODE
        defaultSysDomainShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the sysDomainList where code equals to UPDATED_CODE
        defaultSysDomainShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where code not equals to DEFAULT_CODE
        defaultSysDomainShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the sysDomainList where code not equals to UPDATED_CODE
        defaultSysDomainShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSysDomainShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the sysDomainList where code equals to UPDATED_CODE
        defaultSysDomainShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where code is not null
        defaultSysDomainShouldBeFound("code.specified=true");

        // Get all the sysDomainList where code is null
        defaultSysDomainShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainsByCodeContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where code contains DEFAULT_CODE
        defaultSysDomainShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the sysDomainList where code contains UPDATED_CODE
        defaultSysDomainShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where code does not contain DEFAULT_CODE
        defaultSysDomainShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the sysDomainList where code does not contain UPDATED_CODE
        defaultSysDomainShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSysDomainsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where description equals to DEFAULT_DESCRIPTION
        defaultSysDomainShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainList where description equals to UPDATED_DESCRIPTION
        defaultSysDomainShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where description not equals to DEFAULT_DESCRIPTION
        defaultSysDomainShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainList where description not equals to UPDATED_DESCRIPTION
        defaultSysDomainShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSysDomainShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sysDomainList where description equals to UPDATED_DESCRIPTION
        defaultSysDomainShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where description is not null
        defaultSysDomainShouldBeFound("description.specified=true");

        // Get all the sysDomainList where description is null
        defaultSysDomainShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where description contains DEFAULT_DESCRIPTION
        defaultSysDomainShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainList where description contains UPDATED_DESCRIPTION
        defaultSysDomainShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where description does not contain DEFAULT_DESCRIPTION
        defaultSysDomainShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sysDomainList where description does not contain UPDATED_DESCRIPTION
        defaultSysDomainShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSysDomainsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdBy equals to DEFAULT_CREATED_BY
        defaultSysDomainShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainList where createdBy equals to UPDATED_CREATED_BY
        defaultSysDomainShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdBy not equals to DEFAULT_CREATED_BY
        defaultSysDomainShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainList where createdBy not equals to UPDATED_CREATED_BY
        defaultSysDomainShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSysDomainShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the sysDomainList where createdBy equals to UPDATED_CREATED_BY
        defaultSysDomainShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdBy is not null
        defaultSysDomainShouldBeFound("createdBy.specified=true");

        // Get all the sysDomainList where createdBy is null
        defaultSysDomainShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdBy contains DEFAULT_CREATED_BY
        defaultSysDomainShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainList where createdBy contains UPDATED_CREATED_BY
        defaultSysDomainShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSysDomainShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the sysDomainList where createdBy does not contain UPDATED_CREATED_BY
        defaultSysDomainShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllSysDomainsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSysDomainShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the sysDomainList where createdDate equals to UPDATED_CREATED_DATE
        defaultSysDomainShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultSysDomainShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the sysDomainList where createdDate not equals to UPDATED_CREATED_DATE
        defaultSysDomainShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSysDomainShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the sysDomainList where createdDate equals to UPDATED_CREATED_DATE
        defaultSysDomainShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where createdDate is not null
        defaultSysDomainShouldBeFound("createdDate.specified=true");

        // Get all the sysDomainList where createdDate is null
        defaultSysDomainShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSysDomainShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSysDomainShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSysDomainShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the sysDomainList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSysDomainShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedBy is not null
        defaultSysDomainShouldBeFound("lastModifiedBy.specified=true");

        // Get all the sysDomainList where lastModifiedBy is null
        defaultSysDomainShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSysDomainShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSysDomainShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the sysDomainList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSysDomainShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSysDomainShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the sysDomainList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSysDomainShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the sysDomainList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the sysDomainList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSysDomainShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSysDomainsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        // Get all the sysDomainList where lastModifiedDate is not null
        defaultSysDomainShouldBeFound("lastModifiedDate.specified=true");

        // Get all the sysDomainList where lastModifiedDate is null
        defaultSysDomainShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysDomainsBySysDomainValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);
        SysDomainValue sysDomainValue = SysDomainValueResourceIT.createEntity(em);
        em.persist(sysDomainValue);
        em.flush();
        sysDomain.addSysDomainValue(sysDomainValue);
        sysDomainRepository.saveAndFlush(sysDomain);
        Long sysDomainValueId = sysDomainValue.getId();

        // Get all the sysDomainList where sysDomainValue equals to sysDomainValueId
        defaultSysDomainShouldBeFound("sysDomainValueId.equals=" + sysDomainValueId);

        // Get all the sysDomainList where sysDomainValue equals to sysDomainValueId + 1
        defaultSysDomainShouldNotBeFound("sysDomainValueId.equals=" + (sysDomainValueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSysDomainShouldBeFound(String filter) throws Exception {
        restSysDomainMockMvc.perform(get("/api/sys-domains?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restSysDomainMockMvc.perform(get("/api/sys-domains/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSysDomainShouldNotBeFound(String filter) throws Exception {
        restSysDomainMockMvc.perform(get("/api/sys-domains?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSysDomainMockMvc.perform(get("/api/sys-domains/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSysDomain() throws Exception {
        // Get the sysDomain
        restSysDomainMockMvc.perform(get("/api/sys-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysDomain() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        int databaseSizeBeforeUpdate = sysDomainRepository.findAll().size();

        // Update the sysDomain
        SysDomain updatedSysDomain = sysDomainRepository.findById(sysDomain.getId()).get();
        // Disconnect from session so that the updates on updatedSysDomain are not directly saved in db
        em.detach(updatedSysDomain);
        updatedSysDomain
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(updatedSysDomain);

        restSysDomainMockMvc.perform(put("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isOk());

        // Validate the SysDomain in the database
        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeUpdate);
        SysDomain testSysDomain = sysDomainList.get(sysDomainList.size() - 1);
        assertThat(testSysDomain.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSysDomain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysDomain.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSysDomain.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSysDomain.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSysDomain.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSysDomain() throws Exception {
        int databaseSizeBeforeUpdate = sysDomainRepository.findAll().size();

        // Create the SysDomain
        SysDomainDTO sysDomainDTO = sysDomainMapper.toDto(sysDomain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysDomainMockMvc.perform(put("/api/sys-domains")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysDomain in the database
        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysDomain() throws Exception {
        // Initialize the database
        sysDomainRepository.saveAndFlush(sysDomain);

        int databaseSizeBeforeDelete = sysDomainRepository.findAll().size();

        // Delete the sysDomain
        restSysDomainMockMvc.perform(delete("/api/sys-domains/{id}", sysDomain.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysDomain> sysDomainList = sysDomainRepository.findAll();
        assertThat(sysDomainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
