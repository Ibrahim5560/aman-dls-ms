package com.isoft.dls.service.impl;

import com.isoft.dls.service.WebServicePropertyService;
import com.isoft.dls.domain.WebServiceProperty;
import com.isoft.dls.repository.WebServicePropertyRepository;
import com.isoft.dls.service.dto.WebServicePropertyDTO;
import com.isoft.dls.service.mapper.WebServicePropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WebServiceProperty}.
 */
@Service
@Transactional
public class WebServicePropertyServiceImpl implements WebServicePropertyService {

    private final Logger log = LoggerFactory.getLogger(WebServicePropertyServiceImpl.class);

    private final WebServicePropertyRepository webServicePropertyRepository;

    private final WebServicePropertyMapper webServicePropertyMapper;

    public WebServicePropertyServiceImpl(WebServicePropertyRepository webServicePropertyRepository, WebServicePropertyMapper webServicePropertyMapper) {
        this.webServicePropertyRepository = webServicePropertyRepository;
        this.webServicePropertyMapper = webServicePropertyMapper;
    }

    /**
     * Save a webServiceProperty.
     *
     * @param webServicePropertyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WebServicePropertyDTO save(WebServicePropertyDTO webServicePropertyDTO) {
        log.debug("Request to save WebServiceProperty : {}", webServicePropertyDTO);
        WebServiceProperty webServiceProperty = webServicePropertyMapper.toEntity(webServicePropertyDTO);
        webServiceProperty = webServicePropertyRepository.save(webServiceProperty);
        return webServicePropertyMapper.toDto(webServiceProperty);
    }

    /**
     * Get all the webServiceProperties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebServicePropertyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebServiceProperties");
        return webServicePropertyRepository.findAll(pageable)
            .map(webServicePropertyMapper::toDto);
    }


    /**
     * Get one webServiceProperty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WebServicePropertyDTO> findOne(Long id) {
        log.debug("Request to get WebServiceProperty : {}", id);
        return webServicePropertyRepository.findById(id)
            .map(webServicePropertyMapper::toDto);
    }

    /**
     * Delete the webServiceProperty by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebServiceProperty : {}", id);
        webServicePropertyRepository.deleteById(id);
    }
}
