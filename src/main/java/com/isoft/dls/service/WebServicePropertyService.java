package com.isoft.dls.service;

import com.isoft.dls.service.dto.WebServicePropertyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.WebServiceProperty}.
 */
public interface WebServicePropertyService {

    /**
     * Save a webServiceProperty.
     *
     * @param webServicePropertyDTO the entity to save.
     * @return the persisted entity.
     */
    WebServicePropertyDTO save(WebServicePropertyDTO webServicePropertyDTO);

    /**
     * Get all the webServiceProperties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebServicePropertyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" webServiceProperty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebServicePropertyDTO> findOne(Long id);

    /**
     * Delete the "id" webServiceProperty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
