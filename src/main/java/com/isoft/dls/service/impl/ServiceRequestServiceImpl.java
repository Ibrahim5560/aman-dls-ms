package com.isoft.dls.service.impl;

import com.isoft.dls.service.ServiceRequestService;
import com.isoft.dls.domain.ServiceRequest;
import com.isoft.dls.repository.ServiceRequestRepository;
import com.isoft.dls.service.dto.ServiceRequestDTO;
import com.isoft.dls.service.mapper.ServiceRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceRequest}.
 */
@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository, ServiceRequestMapper serviceRequestMapper) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestMapper = serviceRequestMapper;
    }

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to save ServiceRequest : {}", serviceRequestDTO);
        ServiceRequest serviceRequest = serviceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        return serviceRequestMapper.toDto(serviceRequest);
    }

    /**
     * Get all the serviceRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRequests");
        return serviceRequestRepository.findAll(pageable)
            .map(serviceRequestMapper::toDto);
    }

    /**
     * Get one serviceRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findOne(Long id) {
        log.debug("Request to get ServiceRequest : {}", id);
        return serviceRequestRepository.findById(id)
            .map(serviceRequestMapper::toDto);
    }

    /**
     * Delete the serviceRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceRequest : {}", id);
        serviceRequestRepository.deleteById(id);
    }
}
