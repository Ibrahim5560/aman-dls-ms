package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.ApplicationType;
import com.isoft.dls.repository.ApplicationTypeRepository;
import com.isoft.dls.service.ApplicationTypeService;
import com.isoft.dls.service.dto.ApplicationTypeDTO;
import com.isoft.dls.service.mapper.ApplicationTypeMapper;
import com.isoft.dls.service.dto.ApplicationTypeCriteria;
import com.isoft.dls.service.ApplicationTypeQueryService;

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

/**
 * Integration tests for the {@link ApplicationTypeResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ApplicationTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DESCRIPTION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPTION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DESCRIPTION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPTION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SUMMARY = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SUMMARY = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SUMMARY_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SUMMARY_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_SORT_ORDER = 1L;
    private static final Long UPDATED_SORT_ORDER = 2L;
    private static final Long SMALLER_SORT_ORDER = 1L - 1L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplicationTypeRepository applicationTypeRepository;

    @Autowired
    private ApplicationTypeMapper applicationTypeMapper;

    @Autowired
    private ApplicationTypeService applicationTypeService;

    @Autowired
    private ApplicationTypeQueryService applicationTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationTypeMockMvc;

    private ApplicationType applicationType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationType createEntity(EntityManager em) {
        ApplicationType applicationType = new ApplicationType()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .descriptionContentType(DEFAULT_DESCRIPTION_CONTENT_TYPE)
            .summary(DEFAULT_SUMMARY)
            .summaryContentType(DEFAULT_SUMMARY_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .sortOrder(DEFAULT_SORT_ORDER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return applicationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationType createUpdatedEntity(EntityManager em) {
        ApplicationType applicationType = new ApplicationType()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .descriptionContentType(UPDATED_DESCRIPTION_CONTENT_TYPE)
            .summary(UPDATED_SUMMARY)
            .summaryContentType(UPDATED_SUMMARY_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .sortOrder(UPDATED_SORT_ORDER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return applicationType;
    }

    @BeforeEach
    public void initTest() {
        applicationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationType() throws Exception {
        int databaseSizeBeforeCreate = applicationTypeRepository.findAll().size();

        // Create the ApplicationType
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);
        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationType testApplicationType = applicationTypeList.get(applicationTypeList.size() - 1);
        assertThat(testApplicationType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApplicationType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApplicationType.getDescriptionContentType()).isEqualTo(DEFAULT_DESCRIPTION_CONTENT_TYPE);
        assertThat(testApplicationType.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testApplicationType.getSummaryContentType()).isEqualTo(DEFAULT_SUMMARY_CONTENT_TYPE);
        assertThat(testApplicationType.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplicationType.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testApplicationType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testApplicationType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testApplicationType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testApplicationType.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createApplicationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationTypeRepository.findAll().size();

        // Create the ApplicationType with an existing ID
        applicationType.setId(1L);
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setCode(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setStatus(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setSortOrder(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setCreatedBy(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationTypeRepository.findAll().size();
        // set the field null
        applicationType.setCreatedDate(null);

        // Create the ApplicationType, which fails.
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        restApplicationTypeMockMvc.perform(post("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicationTypes() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList
        restApplicationTypeMockMvc.perform(get("/api/application-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descriptionContentType").value(hasItem(DEFAULT_DESCRIPTION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPTION))))
            .andExpect(jsonPath("$.[*].summaryContentType").value(hasItem(DEFAULT_SUMMARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(Base64Utils.encodeToString(DEFAULT_SUMMARY))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationType() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get the applicationType
        restApplicationTypeMockMvc.perform(get("/api/application-types/{id}", applicationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.descriptionContentType").value(DEFAULT_DESCRIPTION_CONTENT_TYPE))
            .andExpect(jsonPath("$.description").value(Base64Utils.encodeToString(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.summaryContentType").value(DEFAULT_SUMMARY_CONTENT_TYPE))
            .andExpect(jsonPath("$.summary").value(Base64Utils.encodeToString(DEFAULT_SUMMARY)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getApplicationTypesByIdFiltering() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        Long id = applicationType.getId();

        defaultApplicationTypeShouldBeFound("id.equals=" + id);
        defaultApplicationTypeShouldNotBeFound("id.notEquals=" + id);

        defaultApplicationTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApplicationTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultApplicationTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApplicationTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApplicationTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where code equals to DEFAULT_CODE
        defaultApplicationTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the applicationTypeList where code equals to UPDATED_CODE
        defaultApplicationTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where code not equals to DEFAULT_CODE
        defaultApplicationTypeShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the applicationTypeList where code not equals to UPDATED_CODE
        defaultApplicationTypeShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultApplicationTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the applicationTypeList where code equals to UPDATED_CODE
        defaultApplicationTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where code is not null
        defaultApplicationTypeShouldBeFound("code.specified=true");

        // Get all the applicationTypeList where code is null
        defaultApplicationTypeShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where code contains DEFAULT_CODE
        defaultApplicationTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the applicationTypeList where code contains UPDATED_CODE
        defaultApplicationTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where code does not contain DEFAULT_CODE
        defaultApplicationTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the applicationTypeList where code does not contain UPDATED_CODE
        defaultApplicationTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllApplicationTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where status equals to DEFAULT_STATUS
        defaultApplicationTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the applicationTypeList where status equals to UPDATED_STATUS
        defaultApplicationTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where status not equals to DEFAULT_STATUS
        defaultApplicationTypeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the applicationTypeList where status not equals to UPDATED_STATUS
        defaultApplicationTypeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultApplicationTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the applicationTypeList where status equals to UPDATED_STATUS
        defaultApplicationTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where status is not null
        defaultApplicationTypeShouldBeFound("status.specified=true");

        // Get all the applicationTypeList where status is null
        defaultApplicationTypeShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationTypesByStatusContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where status contains DEFAULT_STATUS
        defaultApplicationTypeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the applicationTypeList where status contains UPDATED_STATUS
        defaultApplicationTypeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where status does not contain DEFAULT_STATUS
        defaultApplicationTypeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the applicationTypeList where status does not contain UPDATED_STATUS
        defaultApplicationTypeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder equals to UPDATED_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder equals to UPDATED_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder is not null
        defaultApplicationTypeShouldBeFound("sortOrder.specified=true");

        // Get all the applicationTypeList where sortOrder is null
        defaultApplicationTypeShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder is less than UPDATED_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultApplicationTypeShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the applicationTypeList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultApplicationTypeShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }


    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultApplicationTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the applicationTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultApplicationTypeShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the applicationTypeList where createdBy not equals to UPDATED_CREATED_BY
        defaultApplicationTypeShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultApplicationTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the applicationTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultApplicationTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdBy is not null
        defaultApplicationTypeShouldBeFound("createdBy.specified=true");

        // Get all the applicationTypeList where createdBy is null
        defaultApplicationTypeShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationTypesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdBy contains DEFAULT_CREATED_BY
        defaultApplicationTypeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the applicationTypeList where createdBy contains UPDATED_CREATED_BY
        defaultApplicationTypeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultApplicationTypeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the applicationTypeList where createdBy does not contain UPDATED_CREATED_BY
        defaultApplicationTypeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultApplicationTypeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationTypeList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationTypeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultApplicationTypeShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the applicationTypeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultApplicationTypeShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultApplicationTypeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the applicationTypeList where createdDate equals to UPDATED_CREATED_DATE
        defaultApplicationTypeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where createdDate is not null
        defaultApplicationTypeShouldBeFound("createdDate.specified=true");

        // Get all the applicationTypeList where createdDate is null
        defaultApplicationTypeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultApplicationTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultApplicationTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the applicationTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultApplicationTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedBy is not null
        defaultApplicationTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the applicationTypeList where lastModifiedBy is null
        defaultApplicationTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultApplicationTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultApplicationTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultApplicationTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the applicationTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultApplicationTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationTypeShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationTypeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationTypeShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultApplicationTypeShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the applicationTypeList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationTypeShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultApplicationTypeShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the applicationTypeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultApplicationTypeShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllApplicationTypesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        // Get all the applicationTypeList where lastModifiedDate is not null
        defaultApplicationTypeShouldBeFound("lastModifiedDate.specified=true");

        // Get all the applicationTypeList where lastModifiedDate is null
        defaultApplicationTypeShouldNotBeFound("lastModifiedDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApplicationTypeShouldBeFound(String filter) throws Exception {
        restApplicationTypeMockMvc.perform(get("/api/application-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descriptionContentType").value(hasItem(DEFAULT_DESCRIPTION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPTION))))
            .andExpect(jsonPath("$.[*].summaryContentType").value(hasItem(DEFAULT_SUMMARY_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(Base64Utils.encodeToString(DEFAULT_SUMMARY))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restApplicationTypeMockMvc.perform(get("/api/application-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApplicationTypeShouldNotBeFound(String filter) throws Exception {
        restApplicationTypeMockMvc.perform(get("/api/application-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApplicationTypeMockMvc.perform(get("/api/application-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApplicationType() throws Exception {
        // Get the applicationType
        restApplicationTypeMockMvc.perform(get("/api/application-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationType() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        int databaseSizeBeforeUpdate = applicationTypeRepository.findAll().size();

        // Update the applicationType
        ApplicationType updatedApplicationType = applicationTypeRepository.findById(applicationType.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationType are not directly saved in db
        em.detach(updatedApplicationType);
        updatedApplicationType
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .descriptionContentType(UPDATED_DESCRIPTION_CONTENT_TYPE)
            .summary(UPDATED_SUMMARY)
            .summaryContentType(UPDATED_SUMMARY_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .sortOrder(UPDATED_SORT_ORDER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(updatedApplicationType);

        restApplicationTypeMockMvc.perform(put("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeUpdate);
        ApplicationType testApplicationType = applicationTypeList.get(applicationTypeList.size() - 1);
        assertThat(testApplicationType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApplicationType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApplicationType.getDescriptionContentType()).isEqualTo(UPDATED_DESCRIPTION_CONTENT_TYPE);
        assertThat(testApplicationType.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testApplicationType.getSummaryContentType()).isEqualTo(UPDATED_SUMMARY_CONTENT_TYPE);
        assertThat(testApplicationType.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplicationType.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testApplicationType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testApplicationType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testApplicationType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testApplicationType.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationType() throws Exception {
        int databaseSizeBeforeUpdate = applicationTypeRepository.findAll().size();

        // Create the ApplicationType
        ApplicationTypeDTO applicationTypeDTO = applicationTypeMapper.toDto(applicationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationTypeMockMvc.perform(put("/api/application-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(applicationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationType in the database
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationType() throws Exception {
        // Initialize the database
        applicationTypeRepository.saveAndFlush(applicationType);

        int databaseSizeBeforeDelete = applicationTypeRepository.findAll().size();

        // Delete the applicationType
        restApplicationTypeMockMvc.perform(delete("/api/application-types/{id}", applicationType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationType> applicationTypeList = applicationTypeRepository.findAll();
        assertThat(applicationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
