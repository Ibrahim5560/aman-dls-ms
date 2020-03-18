package com.isoft.dls.service.impl;

import com.isoft.dls.domain.type.MultilingualJsonType;
import com.isoft.dls.repository.SysDomainRepository;
import com.isoft.dls.service.SysDomainService;
import com.isoft.dls.service.SysDomainValueService;
import com.isoft.dls.domain.SysDomainValue;
import com.isoft.dls.repository.SysDomainValueRepository;
import com.isoft.dls.service.dto.SysDomainDTO;
import com.isoft.dls.service.dto.SysDomainValueDTO;
import com.isoft.dls.service.mapper.SysDomainValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysDomainValue}.
 */
@Service
@Transactional
public class SysDomainValueServiceImpl implements SysDomainValueService {

    private final Logger log = LoggerFactory.getLogger(SysDomainValueServiceImpl.class);

    private final SysDomainValueRepository sysDomainValueRepository;

    private final SysDomainService sysDomainService;

//    private final CacheManager cacheManager;

    private final SysDomainValueMapper sysDomainValueMapper;

    public SysDomainValueServiceImpl(SysDomainValueRepository sysDomainValueRepository, SysDomainValueMapper sysDomainValueMapper,
                                     SysDomainService sysDomainService
//                                    ,CacheManager cacheManager
    ) {
        this.sysDomainValueRepository = sysDomainValueRepository;
        this.sysDomainValueMapper = sysDomainValueMapper;
        this.sysDomainService = sysDomainService;
//        this.cacheManager = cacheManager;
    }

    /**
     * Save a sysDomainValue.
     *
     * @param sysDomainValueDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysDomainValueDTO save(SysDomainValueDTO sysDomainValueDTO) {
        log.debug("Request to save SysDomainValue : {}", sysDomainValueDTO);
        SysDomainValue sysDomainValue = sysDomainValueMapper.toEntity(sysDomainValueDTO);
        sysDomainValue = sysDomainValueRepository.save(sysDomainValue);
        return sysDomainValueMapper.toDto(sysDomainValue);
    }

    /**
     * Get all the sysDomainValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysDomainValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysDomainValues");
        return sysDomainValueRepository.findAll(pageable)
            .map(sysDomainValueMapper::toDto);
    }


    /**
     * Get one sysDomainValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysDomainValueDTO> findOne(Long id) {
        log.debug("Request to get SysDomainValue : {}", id);
        return sysDomainValueRepository.findById(id)
            .map(sysDomainValueMapper::toDto);
    }

    /**
     * Delete the sysDomainValue by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysDomainValue : {}", id);
        sysDomainValueRepository.deleteById(id);
    }

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the multilingual json type of the the given value
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public MultilingualJsonType getDomainValueDescription(String value,String domainCode) {
        log.debug("Request to get DomainValue : {} / domainCode : {}", value,domainCode);
        Optional<SysDomainDTO> domain = sysDomainService.findOne(domainCode);

        if(!domain.isPresent()) {
            return null;
        }
        Optional<SysDomainValueDTO> domainValue = sysDomainValueRepository.findByValueAndDomainId(value,domain.get().getId())
            .map(sysDomainValueMapper::toDto);

        if(!domainValue.isPresent()) {
            return null;
        }
        //TODO : jsonType
//        return domainValue.get().getDescription();
        return null;
    }


    /**
     * Evict All Related Caches
     */
    public void evictAll() {
//        cacheManager.getCache(SysDomainValueRepository.DomainValueCache.DOMAIN_VALUE_BY_DOMAIN_ID).clear();
    }

    /**
     * Get one Domain Value by Value and domain code.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MultilingualJsonType getDomainValue(String value, String domainCode) {

        Optional<SysDomainDTO> domain = sysDomainService.findOne(domainCode);

        if(!domain.isPresent()) {
            return null;
        }
        Optional<SysDomainValueDTO> domainValue = sysDomainValueRepository.findByValueAndDomainId(value,domain.get().getId())
            .map(sysDomainValueMapper::toDto);

        if(!domainValue.isPresent()) {
            return null;
        }
        //TODO : jsonType
//        return domainValue.get().getDescription();
        return null;
    }
}
