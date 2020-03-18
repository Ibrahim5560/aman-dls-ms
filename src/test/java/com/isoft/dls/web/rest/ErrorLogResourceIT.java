package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ErrorLog;
import com.isoft.dls.repository.ErrorLogRepository;
import com.isoft.dls.service.ErrorLogService;
import com.isoft.dls.service.dto.ErrorLogDTO;
import com.isoft.dls.service.mapper.ErrorLogMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.ErrorLogCriteria;
import com.isoft.dls.service.ErrorLogQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link ErrorLogResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class ErrorLogResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogMapper errorLogMapper;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogQueryService errorLogQueryService;

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

    private MockMvc restErrorLogMockMvc;

    private ErrorLog errorLog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ErrorLogResource errorLogResource = new ErrorLogResource(errorLogService, errorLogQueryService);
        this.restErrorLogMockMvc = MockMvcBuilders.standaloneSetup(errorLogResource)
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
    public static ErrorLog createEntity(EntityManager em) {
        ErrorLog errorLog = new ErrorLog()
            .source(DEFAULT_SOURCE)
            .cause(DEFAULT_CAUSE)
            .message(DEFAULT_MESSAGE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return errorLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ErrorLog createUpdatedEntity(EntityManager em) {
        ErrorLog errorLog = new ErrorLog()
            .source(UPDATED_SOURCE)
            .cause(UPDATED_CAUSE)
            .message(UPDATED_MESSAGE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return errorLog;
    }

    @BeforeEach
    public void initTest() {
        errorLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createErrorLog() throws Exception {
        int databaseSizeBeforeCreate = errorLogRepository.findAll().size();

        // Create the ErrorLog
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);
        restErrorLogMockMvc.perform(post("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isCreated());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeCreate + 1);
        ErrorLog testErrorLog = errorLogList.get(errorLogList.size() - 1);
        assertThat(testErrorLog.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testErrorLog.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testErrorLog.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testErrorLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testErrorLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testErrorLog.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testErrorLog.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createErrorLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = errorLogRepository.findAll().size();

        // Create the ErrorLog with an existing ID
        errorLog.setId(1L);
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restErrorLogMockMvc.perform(post("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = errorLogRepository.findAll().size();
        // set the field null
        errorLog.setSource(null);

        // Create the ErrorLog, which fails.
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);

        restErrorLogMockMvc.perform(post("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isBadRequest());

        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = errorLogRepository.findAll().size();
        // set the field null
        errorLog.setCreatedBy(null);

        // Create the ErrorLog, which fails.
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);

        restErrorLogMockMvc.perform(post("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isBadRequest());

        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = errorLogRepository.findAll().size();
        // set the field null
        errorLog.setCreatedDate(null);

        // Create the ErrorLog, which fails.
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);

        restErrorLogMockMvc.perform(post("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isBadRequest());

        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllErrorLogs() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList
        restErrorLogMockMvc.perform(get("/api/error-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errorLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getErrorLog() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get the errorLog
        restErrorLogMockMvc.perform(get("/api/error-logs/{id}", errorLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(errorLog.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getErrorLogsByIdFiltering() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        Long id = errorLog.getId();

        defaultErrorLogShouldBeFound("id.equals=" + id);
        defaultErrorLogShouldNotBeFound("id.notEquals=" + id);

        defaultErrorLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultErrorLogShouldNotBeFound("id.greaterThan=" + id);

        defaultErrorLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultErrorLogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllErrorLogsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where source equals to DEFAULT_SOURCE
        defaultErrorLogShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the errorLogList where source equals to UPDATED_SOURCE
        defaultErrorLogShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where source not equals to DEFAULT_SOURCE
        defaultErrorLogShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the errorLogList where source not equals to UPDATED_SOURCE
        defaultErrorLogShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultErrorLogShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the errorLogList where source equals to UPDATED_SOURCE
        defaultErrorLogShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where source is not null
        defaultErrorLogShouldBeFound("source.specified=true");

        // Get all the errorLogList where source is null
        defaultErrorLogShouldNotBeFound("source.specified=false");
    }
                @Test
    @Transactional
    public void getAllErrorLogsBySourceContainsSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where source contains DEFAULT_SOURCE
        defaultErrorLogShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the errorLogList where source contains UPDATED_SOURCE
        defaultErrorLogShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where source does not contain DEFAULT_SOURCE
        defaultErrorLogShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the errorLogList where source does not contain UPDATED_SOURCE
        defaultErrorLogShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }


    @Test
    @Transactional
    public void getAllErrorLogsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdBy equals to DEFAULT_CREATED_BY
        defaultErrorLogShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the errorLogList where createdBy equals to UPDATED_CREATED_BY
        defaultErrorLogShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdBy not equals to DEFAULT_CREATED_BY
        defaultErrorLogShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the errorLogList where createdBy not equals to UPDATED_CREATED_BY
        defaultErrorLogShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultErrorLogShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the errorLogList where createdBy equals to UPDATED_CREATED_BY
        defaultErrorLogShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdBy is not null
        defaultErrorLogShouldBeFound("createdBy.specified=true");

        // Get all the errorLogList where createdBy is null
        defaultErrorLogShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllErrorLogsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdBy contains DEFAULT_CREATED_BY
        defaultErrorLogShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the errorLogList where createdBy contains UPDATED_CREATED_BY
        defaultErrorLogShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdBy does not contain DEFAULT_CREATED_BY
        defaultErrorLogShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the errorLogList where createdBy does not contain UPDATED_CREATED_BY
        defaultErrorLogShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllErrorLogsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdDate equals to DEFAULT_CREATED_DATE
        defaultErrorLogShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the errorLogList where createdDate equals to UPDATED_CREATED_DATE
        defaultErrorLogShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultErrorLogShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the errorLogList where createdDate not equals to UPDATED_CREATED_DATE
        defaultErrorLogShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultErrorLogShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the errorLogList where createdDate equals to UPDATED_CREATED_DATE
        defaultErrorLogShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where createdDate is not null
        defaultErrorLogShouldBeFound("createdDate.specified=true");

        // Get all the errorLogList where createdDate is null
        defaultErrorLogShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultErrorLogShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the errorLogList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultErrorLogShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultErrorLogShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the errorLogList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultErrorLogShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultErrorLogShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the errorLogList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultErrorLogShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedBy is not null
        defaultErrorLogShouldBeFound("lastModifiedBy.specified=true");

        // Get all the errorLogList where lastModifiedBy is null
        defaultErrorLogShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultErrorLogShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the errorLogList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultErrorLogShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultErrorLogShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the errorLogList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultErrorLogShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultErrorLogShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the errorLogList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultErrorLogShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultErrorLogShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the errorLogList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultErrorLogShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultErrorLogShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the errorLogList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultErrorLogShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllErrorLogsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList where lastModifiedDate is not null
        defaultErrorLogShouldBeFound("lastModifiedDate.specified=true");

        // Get all the errorLogList where lastModifiedDate is null
        defaultErrorLogShouldNotBeFound("lastModifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultErrorLogShouldBeFound(String filter) throws Exception {
        restErrorLogMockMvc.perform(get("/api/error-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errorLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restErrorLogMockMvc.perform(get("/api/error-logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultErrorLogShouldNotBeFound(String filter) throws Exception {
        restErrorLogMockMvc.perform(get("/api/error-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restErrorLogMockMvc.perform(get("/api/error-logs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingErrorLog() throws Exception {
        // Get the errorLog
        restErrorLogMockMvc.perform(get("/api/error-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateErrorLog() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        int databaseSizeBeforeUpdate = errorLogRepository.findAll().size();

        // Update the errorLog
        ErrorLog updatedErrorLog = errorLogRepository.findById(errorLog.getId()).get();
        // Disconnect from session so that the updates on updatedErrorLog are not directly saved in db
        em.detach(updatedErrorLog);
        updatedErrorLog
            .source(UPDATED_SOURCE)
            .cause(UPDATED_CAUSE)
            .message(UPDATED_MESSAGE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(updatedErrorLog);

        restErrorLogMockMvc.perform(put("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isOk());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeUpdate);
        ErrorLog testErrorLog = errorLogList.get(errorLogList.size() - 1);
        assertThat(testErrorLog.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testErrorLog.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testErrorLog.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testErrorLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testErrorLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testErrorLog.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testErrorLog.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingErrorLog() throws Exception {
        int databaseSizeBeforeUpdate = errorLogRepository.findAll().size();

        // Create the ErrorLog
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrorLogMockMvc.perform(put("/api/error-logs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteErrorLog() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        int databaseSizeBeforeDelete = errorLogRepository.findAll().size();

        // Delete the errorLog
        restErrorLogMockMvc.perform(delete("/api/error-logs/{id}", errorLog.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
