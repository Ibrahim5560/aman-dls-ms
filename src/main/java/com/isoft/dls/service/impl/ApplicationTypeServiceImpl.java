package com.isoft.dls.service.impl;

import com.isoft.dls.service.ApplicationTypeService;
import com.isoft.dls.domain.ApplicationType;
import com.isoft.dls.repository.ApplicationTypeRepository;
import com.isoft.dls.service.dto.ApplicationTypeDTO;
import com.isoft.dls.service.mapper.ApplicationTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationType}.
 */
@Service
@Transactional
public class ApplicationTypeServiceImpl implements ApplicationTypeService {

    private final Logger log = LoggerFactory.getLogger(ApplicationTypeServiceImpl.class);

    private final ApplicationTypeRepository applicationTypeRepository;

    private final ApplicationTypeMapper applicationTypeMapper;

    public ApplicationTypeServiceImpl(ApplicationTypeRepository applicationTypeRepository, ApplicationTypeMapper applicationTypeMapper) {
        this.applicationTypeRepository = applicationTypeRepository;
        this.applicationTypeMapper = applicationTypeMapper;
    }

    /**
     * Save a applicationType.
     *
     * @param applicationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplicationTypeDTO save(ApplicationTypeDTO applicationTypeDTO) {
        log.debug("Request to save ApplicationType : {}", applicationTypeDTO);
        ApplicationType applicationType = applicationTypeMapper.toEntity(applicationTypeDTO);
        applicationType = applicationTypeRepository.save(applicationType);
        return applicationTypeMapper.toDto(applicationType);
    }

    /**
     * Get all the applicationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationTypes");
        return applicationTypeRepository.findAll(pageable)
            .map(applicationTypeMapper::toDto);
    }

    /**
     * Get one applicationType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationTypeDTO> findOne(Long id) {
        log.debug("Request to get ApplicationType : {}", id);
        return applicationTypeRepository.findById(id)
            .map(applicationTypeMapper::toDto);
    }

    /**
     * Delete the applicationType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationType : {}", id);
        applicationTypeRepository.deleteById(id);
    }
}
