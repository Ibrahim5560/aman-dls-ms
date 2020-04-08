package com.isoft.dls.service;

import com.isoft.dls.domain.type.MultilingualJsonType;
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

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the multilingual json type of the the given value
     */
    MultilingualJsonType getDomainValueDescription(String value, String domainCode);


    /**
     * Get one Domain Value by Value and domain code.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the entity
     */
    MultilingualJsonType getDomainValue(String value, String domainCode);
}
