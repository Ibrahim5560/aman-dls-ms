package com.isoft.dls.web.rest;

import com.isoft.dls.AmanDlsmsApp;
import com.isoft.dls.config.SecurityBeanOverrideConfiguration;
import com.isoft.dls.domain.MimeType;
import com.isoft.dls.repository.MimeTypeRepository;
import com.isoft.dls.service.MimeTypeService;
import com.isoft.dls.service.dto.MimeTypeDTO;
import com.isoft.dls.service.mapper.MimeTypeMapper;
import com.isoft.dls.web.rest.errors.ExceptionTranslator;
import com.isoft.dls.service.dto.MimeTypeCriteria;
import com.isoft.dls.service.MimeTypeQueryService;

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
import java.util.List;

import static com.isoft.dls.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MimeTypeResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AmanDlsmsApp.class})
public class MimeTypeResourceIT {

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAXIMUM_SIZE = 1;
    private static final Integer UPDATED_MAXIMUM_SIZE = 2;
    private static final Integer SMALLER_MAXIMUM_SIZE = 1 - 1;

    @Autowired
    private MimeTypeRepository mimeTypeRepository;

    @Autowired
    private MimeTypeMapper mimeTypeMapper;

    @Autowired
    private MimeTypeService mimeTypeService;

    @Autowired
    private MimeTypeQueryService mimeTypeQueryService;

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

    private MockMvc restMimeTypeMockMvc;

    private MimeType mimeType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MimeTypeResource mimeTypeResource = new MimeTypeResource(mimeTypeService, mimeTypeQueryService);
        this.restMimeTypeMockMvc = MockMvcBuilders.standaloneSetup(mimeTypeResource)
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
    public static MimeType createEntity(EntityManager em) {
        MimeType mimeType = new MimeType()
            .extension(DEFAULT_EXTENSION)
            .contentType(DEFAULT_CONTENT_TYPE)
            .maximumSize(DEFAULT_MAXIMUM_SIZE);
        return mimeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MimeType createUpdatedEntity(EntityManager em) {
        MimeType mimeType = new MimeType()
            .extension(UPDATED_EXTENSION)
            .contentType(UPDATED_CONTENT_TYPE)
            .maximumSize(UPDATED_MAXIMUM_SIZE);
        return mimeType;
    }

    @BeforeEach
    public void initTest() {
        mimeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMimeType() throws Exception {
        int databaseSizeBeforeCreate = mimeTypeRepository.findAll().size();

        // Create the MimeType
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);
        restMimeTypeMockMvc.perform(post("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MimeType testMimeType = mimeTypeList.get(mimeTypeList.size() - 1);
        assertThat(testMimeType.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testMimeType.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testMimeType.getMaximumSize()).isEqualTo(DEFAULT_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void createMimeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mimeTypeRepository.findAll().size();

        // Create the MimeType with an existing ID
        mimeType.setId(1L);
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMimeTypeMockMvc.perform(post("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mimeTypeRepository.findAll().size();
        // set the field null
        mimeType.setExtension(null);

        // Create the MimeType, which fails.
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        restMimeTypeMockMvc.perform(post("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mimeTypeRepository.findAll().size();
        // set the field null
        mimeType.setContentType(null);

        // Create the MimeType, which fails.
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        restMimeTypeMockMvc.perform(post("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaximumSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mimeTypeRepository.findAll().size();
        // set the field null
        mimeType.setMaximumSize(null);

        // Create the MimeType, which fails.
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        restMimeTypeMockMvc.perform(post("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMimeTypes() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList
        restMimeTypeMockMvc.perform(get("/api/mime-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mimeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].maximumSize").value(hasItem(DEFAULT_MAXIMUM_SIZE)));
    }

    @Test
    @Transactional
    public void getMimeType() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get the mimeType
        restMimeTypeMockMvc.perform(get("/api/mime-types/{id}", mimeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mimeType.getId().intValue()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.maximumSize").value(DEFAULT_MAXIMUM_SIZE));
    }


    @Test
    @Transactional
    public void getMimeTypesByIdFiltering() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        Long id = mimeType.getId();

        defaultMimeTypeShouldBeFound("id.equals=" + id);
        defaultMimeTypeShouldNotBeFound("id.notEquals=" + id);

        defaultMimeTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMimeTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultMimeTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMimeTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMimeTypesByExtensionIsEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where extension equals to DEFAULT_EXTENSION
        defaultMimeTypeShouldBeFound("extension.equals=" + DEFAULT_EXTENSION);

        // Get all the mimeTypeList where extension equals to UPDATED_EXTENSION
        defaultMimeTypeShouldNotBeFound("extension.equals=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByExtensionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where extension not equals to DEFAULT_EXTENSION
        defaultMimeTypeShouldNotBeFound("extension.notEquals=" + DEFAULT_EXTENSION);

        // Get all the mimeTypeList where extension not equals to UPDATED_EXTENSION
        defaultMimeTypeShouldBeFound("extension.notEquals=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByExtensionIsInShouldWork() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where extension in DEFAULT_EXTENSION or UPDATED_EXTENSION
        defaultMimeTypeShouldBeFound("extension.in=" + DEFAULT_EXTENSION + "," + UPDATED_EXTENSION);

        // Get all the mimeTypeList where extension equals to UPDATED_EXTENSION
        defaultMimeTypeShouldNotBeFound("extension.in=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByExtensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where extension is not null
        defaultMimeTypeShouldBeFound("extension.specified=true");

        // Get all the mimeTypeList where extension is null
        defaultMimeTypeShouldNotBeFound("extension.specified=false");
    }
                @Test
    @Transactional
    public void getAllMimeTypesByExtensionContainsSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where extension contains DEFAULT_EXTENSION
        defaultMimeTypeShouldBeFound("extension.contains=" + DEFAULT_EXTENSION);

        // Get all the mimeTypeList where extension contains UPDATED_EXTENSION
        defaultMimeTypeShouldNotBeFound("extension.contains=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByExtensionNotContainsSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where extension does not contain DEFAULT_EXTENSION
        defaultMimeTypeShouldNotBeFound("extension.doesNotContain=" + DEFAULT_EXTENSION);

        // Get all the mimeTypeList where extension does not contain UPDATED_EXTENSION
        defaultMimeTypeShouldBeFound("extension.doesNotContain=" + UPDATED_EXTENSION);
    }


    @Test
    @Transactional
    public void getAllMimeTypesByContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where contentType equals to DEFAULT_CONTENT_TYPE
        defaultMimeTypeShouldBeFound("contentType.equals=" + DEFAULT_CONTENT_TYPE);

        // Get all the mimeTypeList where contentType equals to UPDATED_CONTENT_TYPE
        defaultMimeTypeShouldNotBeFound("contentType.equals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByContentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where contentType not equals to DEFAULT_CONTENT_TYPE
        defaultMimeTypeShouldNotBeFound("contentType.notEquals=" + DEFAULT_CONTENT_TYPE);

        // Get all the mimeTypeList where contentType not equals to UPDATED_CONTENT_TYPE
        defaultMimeTypeShouldBeFound("contentType.notEquals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where contentType in DEFAULT_CONTENT_TYPE or UPDATED_CONTENT_TYPE
        defaultMimeTypeShouldBeFound("contentType.in=" + DEFAULT_CONTENT_TYPE + "," + UPDATED_CONTENT_TYPE);

        // Get all the mimeTypeList where contentType equals to UPDATED_CONTENT_TYPE
        defaultMimeTypeShouldNotBeFound("contentType.in=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where contentType is not null
        defaultMimeTypeShouldBeFound("contentType.specified=true");

        // Get all the mimeTypeList where contentType is null
        defaultMimeTypeShouldNotBeFound("contentType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMimeTypesByContentTypeContainsSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where contentType contains DEFAULT_CONTENT_TYPE
        defaultMimeTypeShouldBeFound("contentType.contains=" + DEFAULT_CONTENT_TYPE);

        // Get all the mimeTypeList where contentType contains UPDATED_CONTENT_TYPE
        defaultMimeTypeShouldNotBeFound("contentType.contains=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByContentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where contentType does not contain DEFAULT_CONTENT_TYPE
        defaultMimeTypeShouldNotBeFound("contentType.doesNotContain=" + DEFAULT_CONTENT_TYPE);

        // Get all the mimeTypeList where contentType does not contain UPDATED_CONTENT_TYPE
        defaultMimeTypeShouldBeFound("contentType.doesNotContain=" + UPDATED_CONTENT_TYPE);
    }


    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize equals to DEFAULT_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.equals=" + DEFAULT_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize equals to UPDATED_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.equals=" + UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize not equals to DEFAULT_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.notEquals=" + DEFAULT_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize not equals to UPDATED_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.notEquals=" + UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsInShouldWork() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize in DEFAULT_MAXIMUM_SIZE or UPDATED_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.in=" + DEFAULT_MAXIMUM_SIZE + "," + UPDATED_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize equals to UPDATED_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.in=" + UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize is not null
        defaultMimeTypeShouldBeFound("maximumSize.specified=true");

        // Get all the mimeTypeList where maximumSize is null
        defaultMimeTypeShouldNotBeFound("maximumSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize is greater than or equal to DEFAULT_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.greaterThanOrEqual=" + DEFAULT_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize is greater than or equal to UPDATED_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.greaterThanOrEqual=" + UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize is less than or equal to DEFAULT_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.lessThanOrEqual=" + DEFAULT_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize is less than or equal to SMALLER_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.lessThanOrEqual=" + SMALLER_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize is less than DEFAULT_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.lessThan=" + DEFAULT_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize is less than UPDATED_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.lessThan=" + UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void getAllMimeTypesByMaximumSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        // Get all the mimeTypeList where maximumSize is greater than DEFAULT_MAXIMUM_SIZE
        defaultMimeTypeShouldNotBeFound("maximumSize.greaterThan=" + DEFAULT_MAXIMUM_SIZE);

        // Get all the mimeTypeList where maximumSize is greater than SMALLER_MAXIMUM_SIZE
        defaultMimeTypeShouldBeFound("maximumSize.greaterThan=" + SMALLER_MAXIMUM_SIZE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMimeTypeShouldBeFound(String filter) throws Exception {
        restMimeTypeMockMvc.perform(get("/api/mime-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mimeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].maximumSize").value(hasItem(DEFAULT_MAXIMUM_SIZE)));

        // Check, that the count call also returns 1
        restMimeTypeMockMvc.perform(get("/api/mime-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMimeTypeShouldNotBeFound(String filter) throws Exception {
        restMimeTypeMockMvc.perform(get("/api/mime-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMimeTypeMockMvc.perform(get("/api/mime-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMimeType() throws Exception {
        // Get the mimeType
        restMimeTypeMockMvc.perform(get("/api/mime-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMimeType() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        int databaseSizeBeforeUpdate = mimeTypeRepository.findAll().size();

        // Update the mimeType
        MimeType updatedMimeType = mimeTypeRepository.findById(mimeType.getId()).get();
        // Disconnect from session so that the updates on updatedMimeType are not directly saved in db
        em.detach(updatedMimeType);
        updatedMimeType
            .extension(UPDATED_EXTENSION)
            .contentType(UPDATED_CONTENT_TYPE)
            .maximumSize(UPDATED_MAXIMUM_SIZE);
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(updatedMimeType);

        restMimeTypeMockMvc.perform(put("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeUpdate);
        MimeType testMimeType = mimeTypeList.get(mimeTypeList.size() - 1);
        assertThat(testMimeType.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testMimeType.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testMimeType.getMaximumSize()).isEqualTo(UPDATED_MAXIMUM_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingMimeType() throws Exception {
        int databaseSizeBeforeUpdate = mimeTypeRepository.findAll().size();

        // Create the MimeType
        MimeTypeDTO mimeTypeDTO = mimeTypeMapper.toDto(mimeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMimeTypeMockMvc.perform(put("/api/mime-types")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mimeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MimeType in the database
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMimeType() throws Exception {
        // Initialize the database
        mimeTypeRepository.saveAndFlush(mimeType);

        int databaseSizeBeforeDelete = mimeTypeRepository.findAll().size();

        // Delete the mimeType
        restMimeTypeMockMvc.perform(delete("/api/mime-types/{id}", mimeType.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MimeType> mimeTypeList = mimeTypeRepository.findAll();
        assertThat(mimeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
