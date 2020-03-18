package com.isoft.dls.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isoft.dls.domain.ErrorLog;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ErrorLogRepository;
import com.isoft.dls.service.dto.ErrorLogCriteria;
import com.isoft.dls.service.dto.ErrorLogDTO;
import com.isoft.dls.service.mapper.ErrorLogMapper;

/**
 * Service for executing complex queries for {@link ErrorLog} entities in the database.
 * The main input is a {@link ErrorLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ErrorLogDTO} or a {@link Page} of {@link ErrorLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ErrorLogQueryService extends QueryService<ErrorLog> {

    private final Logger log = LoggerFactory.getLogger(ErrorLogQueryService.class);

    private final ErrorLogRepository errorLogRepository;

    private final ErrorLogMapper errorLogMapper;

    public ErrorLogQueryService(ErrorLogRepository errorLogRepository, ErrorLogMapper errorLogMapper) {
        this.errorLogRepository = errorLogRepository;
        this.errorLogMapper = errorLogMapper;
    }

    /**
     * Return a {@link List} of {@link ErrorLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ErrorLogDTO> findByCriteria(ErrorLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ErrorLog> specification = createSpecification(criteria);
        return errorLogMapper.toDto(errorLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ErrorLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ErrorLogDTO> findByCriteria(ErrorLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ErrorLog> specification = createSpecification(criteria);
        return errorLogRepository.findAll(specification, page)
            .map(errorLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ErrorLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ErrorLog> specification = createSpecification(criteria);
        return errorLogRepository.count(specification);
    }

    /**
     * Function to convert {@link ErrorLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ErrorLog> createSpecification(ErrorLogCriteria criteria) {
        Specification<ErrorLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ErrorLog_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), ErrorLog_.source));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ErrorLog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ErrorLog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ErrorLog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ErrorLog_.lastModifiedDate));
            }
        }
        return specification;
    }
}
