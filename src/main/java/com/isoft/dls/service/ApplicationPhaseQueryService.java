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

import com.isoft.dls.domain.ApplicationPhase;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ApplicationPhaseRepository;
import com.isoft.dls.service.dto.ApplicationPhaseCriteria;
import com.isoft.dls.service.dto.ApplicationPhaseDTO;
import com.isoft.dls.service.mapper.ApplicationPhaseMapper;

/**
 * Service for executing complex queries for {@link ApplicationPhase} entities in the database.
 * The main input is a {@link ApplicationPhaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationPhaseDTO} or a {@link Page} of {@link ApplicationPhaseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationPhaseQueryService extends QueryService<ApplicationPhase> {

    private final Logger log = LoggerFactory.getLogger(ApplicationPhaseQueryService.class);

    private final ApplicationPhaseRepository applicationPhaseRepository;

    private final ApplicationPhaseMapper applicationPhaseMapper;

    public ApplicationPhaseQueryService(ApplicationPhaseRepository applicationPhaseRepository, ApplicationPhaseMapper applicationPhaseMapper) {
        this.applicationPhaseRepository = applicationPhaseRepository;
        this.applicationPhaseMapper = applicationPhaseMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationPhaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationPhaseDTO> findByCriteria(ApplicationPhaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationPhase> specification = createSpecification(criteria);
        return applicationPhaseMapper.toDto(applicationPhaseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationPhaseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationPhaseDTO> findByCriteria(ApplicationPhaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationPhase> specification = createSpecification(criteria);
        return applicationPhaseRepository.findAll(specification, page)
            .map(applicationPhaseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationPhaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationPhase> specification = createSpecification(criteria);
        return applicationPhaseRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationPhaseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationPhase> createSpecification(ApplicationPhaseCriteria criteria) {
        Specification<ApplicationPhase> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationPhase_.id));
            }
            if (criteria.getPhaseType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhaseType(), ApplicationPhase_.phaseType));
            }
            if (criteria.getPhaseSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhaseSequence(), ApplicationPhase_.phaseSequence));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ApplicationPhase_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ApplicationPhase_.endDate));
            }
            if (criteria.getPersona() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersona(), ApplicationPhase_.persona));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationId(), ApplicationPhase_.applicationId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationPhase_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ApplicationPhase_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ApplicationPhase_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ApplicationPhase_.lastModifiedDate));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(ApplicationPhase_.application, JoinType.LEFT).get(Application_.id)));
            }
        }
        return specification;
    }
}
