package com.isoft.dls.service;

import com.isoft.dls.service.dto.SysDomainDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.SysDomain}.
 */
public interface SysDomainService {

    /**
     * Save a sysDomain.
     *
     * @param sysDomainDTO the entity to save.
     * @return the persisted entity.
     */
    SysDomainDTO save(SysDomainDTO sysDomainDTO);

    /**
     * Get all the sysDomains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysDomainDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sysDomain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysDomainDTO> findOne(Long id);

    /**
     * Delete the "id" sysDomain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
