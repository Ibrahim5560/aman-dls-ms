package com.isoft.dls.service;

import com.isoft.dls.service.dto.ApplicationTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.ApplicationType}.
 */
public interface ApplicationTypeService {

    /**
     * Save a applicationType.
     *
     * @param applicationTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationTypeDTO save(ApplicationTypeDTO applicationTypeDTO);

    /**
     * Get all the applicationTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applicationType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationTypeDTO> findOne(Long id);

    /**
     * Delete the "id" applicationType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
