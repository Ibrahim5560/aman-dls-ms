package com.isoft.dls.service;

import com.isoft.dls.domain.enumeration.WebServiceName;
import com.isoft.dls.service.dto.WebServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.WebService}.
 */
public interface WebServiceService {

    /**
     * Save a webService.
     *
     * @param webServiceDTO the entity to save.
     * @return the persisted entity.
     */
    WebServiceDTO save(WebServiceDTO webServiceDTO);

    /**
     * Get all the webServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebServiceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" webService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebServiceDTO> findOne(Long id);

    /**
     * Delete the "id" webService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find By Name.
     *
     * @param name Web Service Name.
     *
     * @return WebServiceDTO.
     */
    Optional<WebServiceDTO> findByName(WebServiceName name);

    /**
     * Find by class name
     *
     * @param className
     * @return the persisted entity
     */
//    Optional<WebServiceDTO> findOne(String className);
}
