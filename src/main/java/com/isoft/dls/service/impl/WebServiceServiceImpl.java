package com.isoft.dls.service.impl;

import com.isoft.dls.domain.enumeration.WebServiceName;
import com.isoft.dls.service.WebServiceService;
import com.isoft.dls.domain.WebService;
import com.isoft.dls.repository.WebServiceRepository;
import com.isoft.dls.service.dto.WebServiceDTO;
import com.isoft.dls.service.mapper.WebServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WebService}.
 */
@Service
@Transactional
public class WebServiceServiceImpl implements WebServiceService {

    private final Logger log = LoggerFactory.getLogger(WebServiceServiceImpl.class);

    private final WebServiceRepository webServiceRepository;

    private final WebServiceMapper webServiceMapper;

    public WebServiceServiceImpl(WebServiceRepository webServiceRepository, WebServiceMapper webServiceMapper) {
        this.webServiceRepository = webServiceRepository;
        this.webServiceMapper = webServiceMapper;
    }

    /**
     * Save a webService.
     *
     * @param webServiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WebServiceDTO save(WebServiceDTO webServiceDTO) {
        log.debug("Request to save WebService : {}", webServiceDTO);
        WebService webService = webServiceMapper.toEntity(webServiceDTO);
        webService = webServiceRepository.save(webService);
        return webServiceMapper.toDto(webService);
    }

    /**
     * Get all the webServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebServices");
        return webServiceRepository.findAll(pageable)
            .map(webServiceMapper::toDto);
    }


    /**
     * Get one webService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WebServiceDTO> findOne(Long id) {
        log.debug("Request to get WebService : {}", id);
        return webServiceRepository.findById(id)
            .map(webServiceMapper::toDto);
    }

    /**
     * Delete the webService by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebService : {}", id);
        webServiceRepository.deleteById(id);
    }

    @Override
//    @Transactional(readOnly = true)
    public Optional<WebServiceDTO> findByName(WebServiceName name) {
        log.debug("Request to get WebService by name : {}", name);
        return webServiceRepository.findByName(name).map(webServiceMapper::toDto);
    }

    /**
     * Find by class name
     *
     * @param className
     * @return the persisted entity
     */
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<WebServiceDTO> findOne(String className) {
//        log.debug("Request to get WebService by className : {}", className);
//        return webServiceRepository.findByClassName(className).map(webServiceMapper::toDto);
//    }
}
