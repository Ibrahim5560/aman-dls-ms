package com.isoft.dls.service;

import com.isoft.dls.service.dto.SysDomainValueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.dls.domain.SysDomainValue}.
 */
public interface SysDomainValueService {

    /**
     * Save a sysDomainValue.
     *
     * @param sysDomainValueDTO the entity to save.
     * @return the persisted entity.
     */
    SysDomainValueDTO save(SysDomainValueDTO sysDomainValueDTO);

    /**
     * Get all the sysDomainValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SysDomainValueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sysDomainValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SysDomainValueDTO> findOne(Long id);

    /**
     * Delete the "id" sysDomainValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
