package com.isoft.dls.service;

import com.isoft.dls.service.dto.ServiceRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.ServiceRequest}.
 */
public interface ServiceRequestService {

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO);

    /**
     * Get all the serviceRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" serviceRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceRequestDTO> findOne(Long id);

    /**
     * Delete the "id" serviceRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
