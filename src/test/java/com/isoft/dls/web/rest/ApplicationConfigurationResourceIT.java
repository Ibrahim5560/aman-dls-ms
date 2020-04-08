package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ApplicationConfiguration;
import com.isoft.dls.repository.ApplicationConfigurationRepository;
import com.isoft.dls.service.ApplicationConfigurationService;
import com.isoft.dls.service.dto.ApplicationConfigurationDTO;
import com.isoft.dls.service.mapper.ApplicationConfigurationMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.ApplicationConfigurationCriteria;
import com.isoft.dls.service.ApplicationConfigurationQueryService;

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
 * Integration tests for the {@link ApplicationConfigurationResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class ApplicationConfigurationResourceIT {

    private static final String DEFAULT_CONFIG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CACHED = false;
    private static final Boolean UPDATED_CACHED = true;

    private static final Boolean DEFAULT_ENCRYPTED = false;
    private static final Boolean UPDATED_ENCRYPTED = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicationConfigurationRepository applicationConfigurationRepository;

    @Autowired
    private ApplicationConfigurationMapper applicationConfigurationMapper;

    @Autowired
    private ApplicationConfigurationService applicationConfigurationService;

    @Autowired
    private ApplicationConfigurationQueryService applicationConfigurationQueryService;

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

    private MockMvc restApplicationConfigurationMockMvc;

    private ApplicationConfiguration applicationConfiguration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationConfigurationResource applicationConfigurationResource = new ApplicationConfigurationResource(applicationConfigurationService, applicationConfigurationQueryService);
        this.restApplicationConfigurationMockMvc = MockMvcBuilders.standaloneSetup(applicationConfigurationResource)
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
    public static ApplicationConfiguration createEntity(EntityManager em) {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration()
            .configKey(DEFAULT_CONFIG_KEY)
            .configValue(DEFAULT_CONFIG_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .cached(DEFAULT_CACHED)
            .encrypted(DEFAULT_ENCRYPTED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return applicationConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationConfiguration createUpdatedEntity(EntityManager em) {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration()
            .configKey(UPDATED_CONFIG_KEY)
            .configValue(UPDATED_CONFIG_VALUE)
            .description(UPDATED_DESCRIPTION)
            .cached(UPDATED_CACHED)
            .encrypted(UPDATED_ENCRYPTED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return applicationConfiguration;
    }

    @BeforeEach
    public void initTest() {
        applicationConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationConfiguration() throws Exception {
        int databaseSizeBeforeCreate = applicationConfigurationRepository.findAll().size();

        // Create the ApplicationConfiguration
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);
        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationConfiguration testApplicationConfiguration = applicationConfigurationList.get(applicationConfigurationList.size() - 1);
        assertThat(testApplicationConfiguration.getConfigKey()).isEqualTo(DEFAULT_CONFIG_KEY);
        assertThat(testApplicationConfiguration.getConfigValue()).isEqualTo(DEFAULT_CONFIG_VALUE);
        assertThat(testApplicationConfiguration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationConfiguration.isCached()).isEqualTo(DEFAULT_CACHED);
        assertThat(testApplicationConfiguration.isEncrypted()).isEqualTo(DEFAULT_ENCRYPTED);
        assertThat(testApplicationConfiguration.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationConfiguration.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApplicationConfiguration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApplicationConfiguration.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApplicationConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationConfigurationRepository.findAll().size();

        // Create the ApplicationConfiguration with an existing ID
        applicationConfiguration.setId(1L);
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkConfigKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setConfigKey(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfigValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setConfigValue(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setDescription(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCachedIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setCached(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEncryptedIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setEncrypted(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setCreatedBy(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationConfigurationRepository.findAll().size();
        // set the field null
        applicationConfiguration.setCreatedDate(null);

        // Create the ApplicationConfiguration, which fails.
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        restApplicationConfigurationMockMvc.perform(post("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurations() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].configKey").value(hasItem(DEFAULT_CONFIG_KEY)))
            .andExpect(jsonPath("$.[*].configValue").value(hasItem(DEFAULT_CONFIG_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cached").value(hasItem(DEFAULT_CACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].encrypted").value(hasItem(DEFAULT_ENCRYPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getApplicationConfiguration() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get the applicationConfiguration
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations/{id}", applicationConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.configKey").value(DEFAULT_CONFIG_KEY))
            .andExpect(jsonPath("$.configValue").value(DEFAULT_CONFIG_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cached").value(DEFAULT_CACHED.booleanValue()))
            .andExpect(jsonPath("$.encrypted").value(DEFAULT_ENCRYPTED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getApplicationConfigurationsByIdFiltering() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        Long id = applicationConfiguration.getId();

        defaultApplicationConfigurationShouldBeFound("id.equals=" + id);
        defaultApplicationConfigurationShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationConfigurationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationConfigurationShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationConfigurationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationConfigurationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configKey equals to DEFAULT_CONFIG_KEY
        defaultApplicationConfigurationShouldBeFound("configKey.equals=" + DEFAULT_CONFIG_KEY);

        // Get all the applicationConfigurationList where configKey equals to UPDATED_CONFIG_KEY
        defaultApplicationConfigurationShouldNotBeFound("configKey.equals=" + UPDATED_CONFIG_KEY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configKey not equals to DEFAULT_CONFIG_KEY
        defaultApplicationConfigurationShouldNotBeFound("configKey.notEquals=" + DEFAULT_CONFIG_KEY);

        // Get all the applicationConfigurationList where configKey not equals to UPDATED_CONFIG_KEY
        defaultApplicationConfigurationShouldBeFound("configKey.notEquals=" + UPDATED_CONFIG_KEY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigKeyIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configKey in DEFAULT_CONFIG_KEY or UPDATED_CONFIG_KEY
        defaultApplicationConfigurationShouldBeFound("configKey.in=" + DEFAULT_CONFIG_KEY + "," + UPDATED_CONFIG_KEY);

        // Get all the applicationConfigurationList where configKey equals to UPDATED_CONFIG_KEY
        defaultApplicationConfigurationShouldNotBeFound("configKey.in=" + UPDATED_CONFIG_KEY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configKey is not null
        defaultApplicationConfigurationShouldBeFound("configKey.specified=true");

        // Get all the applicationConfigurationList where configKey is null
        defaultApplicationConfigurationShouldNotBeFound("configKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigKeyContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configKey contains DEFAULT_CONFIG_KEY
        defaultApplicationConfigurationShouldBeFound("configKey.contains=" + DEFAULT_CONFIG_KEY);

        // Get all the applicationConfigurationList where configKey contains UPDATED_CONFIG_KEY
        defaultApplicationConfigurationShouldNotBeFound("configKey.contains=" + UPDATED_CONFIG_KEY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigKeyNotContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configKey does not contain DEFAULT_CONFIG_KEY
        defaultApplicationConfigurationShouldNotBeFound("configKey.doesNotContain=" + DEFAULT_CONFIG_KEY);

        // Get all the applicationConfigurationList where configKey does not contain UPDATED_CONFIG_KEY
        defaultApplicationConfigurationShouldBeFound("configKey.doesNotContain=" + UPDATED_CONFIG_KEY);
    }


    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigValueIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configValue equals to DEFAULT_CONFIG_VALUE
        defaultApplicationConfigurationShouldBeFound("configValue.equals=" + DEFAULT_CONFIG_VALUE);

        // Get all the applicationConfigurationList where configValue equals to UPDATED_CONFIG_VALUE
        defaultApplicationConfigurationShouldNotBeFound("configValue.equals=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configValue not equals to DEFAULT_CONFIG_VALUE
        defaultApplicationConfigurationShouldNotBeFound("configValue.notEquals=" + DEFAULT_CONFIG_VALUE);

        // Get all the applicationConfigurationList where configValue not equals to UPDATED_CONFIG_VALUE
        defaultApplicationConfigurationShouldBeFound("configValue.notEquals=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigValueIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configValue in DEFAULT_CONFIG_VALUE or UPDATED_CONFIG_VALUE
        defaultApplicationConfigurationShouldBeFound("configValue.in=" + DEFAULT_CONFIG_VALUE + "," + UPDATED_CONFIG_VALUE);

        // Get all the applicationConfigurationList where configValue equals to UPDATED_CONFIG_VALUE
        defaultApplicationConfigurationShouldNotBeFound("configValue.in=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configValue is not null
        defaultApplicationConfigurationShouldBeFound("configValue.specified=true");

        // Get all the applicationConfigurationList where configValue is null
        defaultApplicationConfigurationShouldNotBeFound("configValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigValueContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configValue contains DEFAULT_CONFIG_VALUE
        defaultApplicationConfigurationShouldBeFound("configValue.contains=" + DEFAULT_CONFIG_VALUE);

        // Get all the applicationConfigurationList where configValue contains UPDATED_CONFIG_VALUE
        defaultApplicationConfigurationShouldNotBeFound("configValue.contains=" + UPDATED_CONFIG_VALUE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByConfigValueNotContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where configValue does not contain DEFAULT_CONFIG_VALUE
        defaultApplicationConfigurationShouldNotBeFound("configValue.doesNotContain=" + DEFAULT_CONFIG_VALUE);

        // Get all the applicationConfigurationList where configValue does not contain UPDATED_CONFIG_VALUE
        defaultApplicationConfigurationShouldBeFound("configValue.doesNotContain=" + UPDATED_CONFIG_VALUE);
    }


    @Test
    @Transactional
    public void getAllApplicationConfigurationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where description equals to DEFAULT_DESCRIPTION
        defaultApplicationConfigurationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the applicationConfigurationList where description equals to UPDATED_DESCRIPTION
        defaultApplicationConfigurationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where description not equals to DEFAULT_DESCRIPTION
        defaultApplicationConfigurationShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the applicationConfigurationList where description not equals to UPDATED_DESCRIPTION
        defaultApplicationConfigurationShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultApplicationConfigurationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the applicationConfigurationList where description equals to UPDATED_DESCRIPTION
        defaultApplicationConfigurationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where description is not null
        defaultApplicationConfigurationShouldBeFound("description.specified=true");

        // Get all the applicationConfigurationList where description is null
        defaultApplicationConfigurationShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationConfigurationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where description contains DEFAULT_DESCRIPTION
        defaultApplicationConfigurationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the applicationConfigurationList where description contains UPDATED_DESCRIPTION
        defaultApplicationConfigurationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where description does not contain DEFAULT_DESCRIPTION
        defaultApplicationConfigurationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the applicationConfigurationList where description does not contain UPDATED_DESCRIPTION
        defaultApplicationConfigurationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCachedIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where cached equals to DEFAULT_CACHED
        defaultApplicationConfigurationShouldBeFound("cached.equals=" + DEFAULT_CACHED);

        // Get all the applicationConfigurationList where cached equals to UPDATED_CACHED
        defaultApplicationConfigurationShouldNotBeFound("cached.equals=" + UPDATED_CACHED);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCachedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where cached not equals to DEFAULT_CACHED
        defaultApplicationConfigurationShouldNotBeFound("cached.notEquals=" + DEFAULT_CACHED);

        // Get all the applicationConfigurationList where cached not equals to UPDATED_CACHED
        defaultApplicationConfigurationShouldBeFound("cached.notEquals=" + UPDATED_CACHED);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCachedIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where cached in DEFAULT_CACHED or UPDATED_CACHED
        defaultApplicationConfigurationShouldBeFound("cached.in=" + DEFAULT_CACHED + "," + UPDATED_CACHED);

        // Get all the applicationConfigurationList where cached equals to UPDATED_CACHED
        defaultApplicationConfigurationShouldNotBeFound("cached.in=" + UPDATED_CACHED);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCachedIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where cached is not null
        defaultApplicationConfigurationShouldBeFound("cached.specified=true");

        // Get all the applicationConfigurationList where cached is null
        defaultApplicationConfigurationShouldNotBeFound("cached.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByEncryptedIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where encrypted equals to DEFAULT_ENCRYPTED
        defaultApplicationConfigurationShouldBeFound("encrypted.equals=" + DEFAULT_ENCRYPTED);

        // Get all the applicationConfigurationList where encrypted equals to UPDATED_ENCRYPTED
        defaultApplicationConfigurationShouldNotBeFound("encrypted.equals=" + UPDATED_ENCRYPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByEncryptedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where encrypted not equals to DEFAULT_ENCRYPTED
        defaultApplicationConfigurationShouldNotBeFound("encrypted.notEquals=" + DEFAULT_ENCRYPTED);

        // Get all the applicationConfigurationList where encrypted not equals to UPDATED_ENCRYPTED
        defaultApplicationConfigurationShouldBeFound("encrypted.notEquals=" + UPDATED_ENCRYPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByEncryptedIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where encrypted in DEFAULT_ENCRYPTED or UPDATED_ENCRYPTED
        defaultApplicationConfigurationShouldBeFound("encrypted.in=" + DEFAULT_ENCRYPTED + "," + UPDATED_ENCRYPTED);

        // Get all the applicationConfigurationList where encrypted equals to UPDATED_ENCRYPTED
        defaultApplicationConfigurationShouldNotBeFound("encrypted.in=" + UPDATED_ENCRYPTED);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByEncryptedIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where encrypted is not null
        defaultApplicationConfigurationShouldBeFound("encrypted.specified=true");

        // Get all the applicationConfigurationList where encrypted is null
        defaultApplicationConfigurationShouldNotBeFound("encrypted.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationConfigurationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationConfigurationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationConfigurationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicationConfigurationShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicationConfigurationList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicationConfigurationShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationConfigurationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationConfigurationList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationConfigurationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdBy is not null
        defaultApplicationConfigurationShouldBeFound("createdBy.specified=true");

        // Get all the applicationConfigurationList where createdBy is null
        defaultApplicationConfigurationShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdBy contains DEFAULT_CREATED_BY
        defaultApplicationConfigurationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the applicationConfigurationList where createdBy contains UPDATED_CREATED_BY
        defaultApplicationConfigurationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultApplicationConfigurationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the applicationConfigurationList where createdBy does not contain UPDATED_CREATED_BY
        defaultApplicationConfigurationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultApplicationConfigurationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationConfigurationList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationConfigurationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultApplicationConfigurationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationConfigurationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultApplicationConfigurationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultApplicationConfigurationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the applicationConfigurationList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationConfigurationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where createdDate is not null
        defaultApplicationConfigurationShouldBeFound("createdDate.specified=true");

        // Get all the applicationConfigurationList where createdDate is null
        defaultApplicationConfigurationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationConfigurationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationConfigurationList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the applicationConfigurationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedBy is not null
        defaultApplicationConfigurationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the applicationConfigurationList where lastModifiedBy is null
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationConfigurationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationConfigurationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApplicationConfigurationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationConfigurationShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationConfigurationList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationConfigurationList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationConfigurationShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultApplicationConfigurationShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the applicationConfigurationList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationConfigurationsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        // Get all the applicationConfigurationList where lastModifiedDate is not null
        defaultApplicationConfigurationShouldBeFound("lastModifiedDate.specified=true");

        // Get all the applicationConfigurationList where lastModifiedDate is null
        defaultApplicationConfigurationShouldNotBeFound("lastModifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationConfigurationShouldBeFound(String filter) throws Exception {
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].configKey").value(hasItem(DEFAULT_CONFIG_KEY)))
            .andExpect(jsonPath("$.[*].configValue").value(hasItem(DEFAULT_CONFIG_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cached").value(hasItem(DEFAULT_CACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].encrypted").value(hasItem(DEFAULT_ENCRYPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationConfigurationShouldNotBeFound(String filter) throws Exception {
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationConfiguration() throws Exception {
        // Get the applicationConfiguration
        restApplicationConfigurationMockMvc.perform(get("/api/application-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationConfiguration() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        int databaseSizeBeforeUpdate = applicationConfigurationRepository.findAll().size();

        // Update the applicationConfiguration
        ApplicationConfiguration updatedApplicationConfiguration = applicationConfigurationRepository.findById(applicationConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationConfiguration are not directly saved in db
        em.detach(updatedApplicationConfiguration);
        updatedApplicationConfiguration
            .configKey(UPDATED_CONFIG_KEY)
            .configValue(UPDATED_CONFIG_VALUE)
            .description(UPDATED_DESCRIPTION)
            .cached(UPDATED_CACHED)
            .encrypted(UPDATED_ENCRYPTED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(updatedApplicationConfiguration);

        restApplicationConfigurationMockMvc.perform(put("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeUpdate);
        ApplicationConfiguration testApplicationConfiguration = applicationConfigurationList.get(applicationConfigurationList.size() - 1);
        assertThat(testApplicationConfiguration.getConfigKey()).isEqualTo(UPDATED_CONFIG_KEY);
        assertThat(testApplicationConfiguration.getConfigValue()).isEqualTo(UPDATED_CONFIG_VALUE);
        assertThat(testApplicationConfiguration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationConfiguration.isCached()).isEqualTo(UPDATED_CACHED);
        assertThat(testApplicationConfiguration.isEncrypted()).isEqualTo(UPDATED_ENCRYPTED);
        assertThat(testApplicationConfiguration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationConfiguration.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApplicationConfiguration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApplicationConfiguration.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = applicationConfigurationRepository.findAll().size();

        // Create the ApplicationConfiguration
        ApplicationConfigurationDTO applicationConfigurationDTO = applicationConfigurationMapper.toDto(applicationConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationConfigurationMockMvc.perform(put("/api/application-configurations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationConfiguration in the database
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationConfiguration() throws Exception {
        // Initialize the database
        applicationConfigurationRepository.saveAndFlush(applicationConfiguration);

        int databaseSizeBeforeDelete = applicationConfigurationRepository.findAll().size();

        // Delete the applicationConfiguration
        restApplicationConfigurationMockMvc.perform(delete("/api/application-configurations/{id}", applicationConfiguration.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationConfiguration> applicationConfigurationList = applicationConfigurationRepository.findAll();
        assertThat(applicationConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
