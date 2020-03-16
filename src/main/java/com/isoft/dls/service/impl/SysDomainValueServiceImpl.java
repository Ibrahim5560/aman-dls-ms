package com.isoft.dls.service.impl;

import com.isoft.dls.service.SysDomainValueService;
import com.isoft.dls.domain.SysDomainValue;
import com.isoft.dls.repository.SysDomainValueRepository;
import com.isoft.dls.service.dto.SysDomainValueDTO;
import com.isoft.dls.service.mapper.SysDomainValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    private final SysDomainValueMapper sysDomainValueMapper;

    public SysDomainValueServiceImpl(SysDomainValueRepository sysDomainValueRepository, SysDomainValueMapper sysDomainValueMapper) {
        this.sysDomainValueRepository = sysDomainValueRepository;
        this.sysDomainValueMapper = sysDomainValueMapper;
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
}
