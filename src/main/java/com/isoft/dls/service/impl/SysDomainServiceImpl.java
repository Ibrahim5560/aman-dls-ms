package com.isoft.dls.service.impl;

import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.service.SysDomainService;
import com.isoft.dls.domain.SysDomain;
import com.isoft.dls.repository.SysDomainRepository;
import com.isoft.dls.service.dto.SysDomainDTO;
import com.isoft.dls.service.dto.SysDomainValueDTO;
import com.isoft.dls.service.mapper.SysDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link SysDomain}.
 */
@Service
@Transactional
public class SysDomainServiceImpl implements SysDomainService {

    private final Logger log = LoggerFactory.getLogger(SysDomainServiceImpl.class);

    private final SysDomainRepository sysDomainRepository;

    private final SysDomainMapper sysDomainMapper;

    public SysDomainServiceImpl(SysDomainRepository sysDomainRepository, SysDomainMapper sysDomainMapper) {
        this.sysDomainRepository = sysDomainRepository;
        this.sysDomainMapper = sysDomainMapper;
    }

    /**
     * Save a sysDomain.
     *
     * @param sysDomainDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SysDomainDTO save(SysDomainDTO sysDomainDTO) {
        log.debug("Request to save SysDomain : {}", sysDomainDTO);
        SysDomain sysDomain = sysDomainMapper.toEntity(sysDomainDTO);
        sysDomain = sysDomainRepository.save(sysDomain);
        return sysDomainMapper.toDto(sysDomain);
    }

    /**
     * Get all the sysDomains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysDomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysDomains");
        return sysDomainRepository.findAll(pageable)
            .map(sysDomainMapper::toDto);
    }


    /**
     * Get one sysDomain by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysDomainDTO> findOne(Long id) {
        log.debug("Request to get SysDomain : {}", id);
        return sysDomainRepository.findById(id)
            .map(sysDomainMapper::toDto);
    }

    /**
     * Delete the sysDomain by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysDomain : {}", id);
        sysDomainRepository.deleteById(id);
    }

    /**
     * Get domain entity by "code"
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysDomainDTO> findOne(String code) {
        log.debug("Request to get Domain by code : {} ", code);
        return sysDomainRepository.findByCode(StringUtil.upperCase(code))
            .map(sysDomainMapper::toDto);
    }

    /**
     * Get domain entity by "code" with excluded values
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SysDomainDTO> findOneWithExcludedValues(String code, List<String> excludedValues) {
        log.debug("Request to get Domain with excluded values by code : {} ", code);

        Optional<SysDomainDTO> domainDTO = sysDomainRepository.findByCode(StringUtil.upperCase(code))
            .map(sysDomainMapper::toDto);

        Set<SysDomainValueDTO> filteredDomainValues = new HashSet<>();
        if(domainDTO.isPresent()) {
            SysDomainDTO domain = domainDTO.get();
            Set<SysDomainValueDTO> domainValues = domain.getDomainValues();
            domainValues.forEach(domainValueDTO -> {
                if(!excludedValues.contains(domainValueDTO.getValue())) {
                    filteredDomainValues.add(domainValueDTO);
                }
            });
        }
        if(domainDTO.isPresent()) {
            domainDTO.get().setDomainValues(filteredDomainValues);
        }

        return domainDTO;
    }
}
