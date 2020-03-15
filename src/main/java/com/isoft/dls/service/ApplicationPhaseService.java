package com.isoft.dls.service;

import com.isoft.dls.service.dto.ApplicationPhaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.ApplicationPhase}.
 */
public interface ApplicationPhaseService {

    /**
     * Save a applicationPhase.
     *
     * @param applicationPhaseDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationPhaseDTO save(ApplicationPhaseDTO applicationPhaseDTO);

    /**
     * Get all the applicationPhases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationPhaseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applicationPhase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationPhaseDTO> findOne(Long id);

    /**
     * Delete the "id" applicationPhase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
