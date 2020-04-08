package com.isoft.dls.service.impl;

import com.isoft.dls.service.ErrorLogService;
import com.isoft.dls.domain.ErrorLog;
import com.isoft.dls.repository.ErrorLogRepository;
import com.isoft.dls.service.dto.ErrorLogDTO;
import com.isoft.dls.service.mapper.ErrorLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ErrorLog}.
 */
@Service
@Transactional
public class ErrorLogServiceImpl implements ErrorLogService {

    private final Logger log = LoggerFactory.getLogger(ErrorLogServiceImpl.class);

    private final ErrorLogRepository errorLogRepository;

    private final ErrorLogMapper errorLogMapper;

    public ErrorLogServiceImpl(ErrorLogRepository errorLogRepository, ErrorLogMapper errorLogMapper) {
        this.errorLogRepository = errorLogRepository;
        this.errorLogMapper = errorLogMapper;
    }

    /**
     * Save a errorLog.
     *
     * @param errorLogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ErrorLogDTO save(ErrorLogDTO errorLogDTO) {
        log.debug("Request to save ErrorLog : {}", errorLogDTO);
        ErrorLog errorLog = errorLogMapper.toEntity(errorLogDTO);
        errorLog = errorLogRepository.save(errorLog);
        return errorLogMapper.toDto(errorLog);
    }

    /**
     * Get all the errorLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ErrorLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ErrorLogs");
        return errorLogRepository.findAll(pageable)
            .map(errorLogMapper::toDto);
    }


    /**
     * Get one errorLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ErrorLogDTO> findOne(Long id) {
        log.debug("Request to get ErrorLog : {}", id);
        return errorLogRepository.findById(id)
            .map(errorLogMapper::toDto);
    }

    /**
     * Delete the errorLog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ErrorLog : {}", id);
        errorLogRepository.deleteById(id);
    }
}
