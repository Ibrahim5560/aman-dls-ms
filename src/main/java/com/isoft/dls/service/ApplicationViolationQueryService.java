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

import com.isoft.dls.domain.ApplicationViolation;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ApplicationViolationRepository;
import com.isoft.dls.service.dto.ApplicationViolationCriteria;
import com.isoft.dls.service.dto.ApplicationViolationDTO;
import com.isoft.dls.service.mapper.ApplicationViolationMapper;

/**
 * Service for executing complex queries for {@link ApplicationViolation} entities in the database.
 * The main input is a {@link ApplicationViolationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationViolationDTO} or a {@link Page} of {@link ApplicationViolationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationViolationQueryService extends QueryService<ApplicationViolation> {

    private final Logger log = LoggerFactory.getLogger(ApplicationViolationQueryService.class);

    private final ApplicationViolationRepository applicationViolationRepository;

    private final ApplicationViolationMapper applicationViolationMapper;

    public ApplicationViolationQueryService(ApplicationViolationRepository applicationViolationRepository, ApplicationViolationMapper applicationViolationMapper) {
        this.applicationViolationRepository = applicationViolationRepository;
        this.applicationViolationMapper = applicationViolationMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationViolationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationViolationDTO> findByCriteria(ApplicationViolationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationViolation> specification = createSpecification(criteria);
        return applicationViolationMapper.toDto(applicationViolationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationViolationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationViolationDTO> findByCriteria(ApplicationViolationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationViolation> specification = createSpecification(criteria);
        return applicationViolationRepository.findAll(specification, page)
            .map(applicationViolationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationViolationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationViolation> specification = createSpecification(criteria);
        return applicationViolationRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationViolationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationViolation> createSpecification(ApplicationViolationCriteria criteria) {
        Specification<ApplicationViolation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationViolation_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ApplicationViolation_.code));
            }
            if (criteria.getIsEligibleForExemption() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsEligibleForExemption(), ApplicationViolation_.isEligibleForExemption));
            }
            if (criteria.getIsExempted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsExempted(), ApplicationViolation_.isExempted));
            }
            if (criteria.getViolationLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getViolationLevel(), ApplicationViolation_.violationLevel));
            }
            if (criteria.getExemptionProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExemptionProcessId(), ApplicationViolation_.exemptionProcessId));
            }
            if (criteria.getExemptedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExemptedBy(), ApplicationViolation_.exemptedBy));
            }
            if (criteria.getExemptionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExemptionDate(), ApplicationViolation_.exemptionDate));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationId(), ApplicationViolation_.applicationId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationViolation_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ApplicationViolation_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ApplicationViolation_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ApplicationViolation_.lastModifiedDate));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(ApplicationViolation_.application, JoinType.LEFT).get(Application_.id)));
            }
        }
        return specification;
    }
}
