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

import com.isoft.dls.domain.ApplicationType;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ApplicationTypeRepository;
import com.isoft.dls.service.dto.ApplicationTypeCriteria;
import com.isoft.dls.service.dto.ApplicationTypeDTO;
import com.isoft.dls.service.mapper.ApplicationTypeMapper;

/**
 * Service for executing complex queries for {@link ApplicationType} entities in the database.
 * The main input is a {@link ApplicationTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationTypeDTO} or a {@link Page} of {@link ApplicationTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationTypeQueryService extends QueryService<ApplicationType> {

    private final Logger log = LoggerFactory.getLogger(ApplicationTypeQueryService.class);

    private final ApplicationTypeRepository applicationTypeRepository;

    private final ApplicationTypeMapper applicationTypeMapper;

    public ApplicationTypeQueryService(ApplicationTypeRepository applicationTypeRepository, ApplicationTypeMapper applicationTypeMapper) {
        this.applicationTypeRepository = applicationTypeRepository;
        this.applicationTypeMapper = applicationTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationTypeDTO> findByCriteria(ApplicationTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationType> specification = createSpecification(criteria);
        return applicationTypeMapper.toDto(applicationTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationTypeDTO> findByCriteria(ApplicationTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationType> specification = createSpecification(criteria);
        return applicationTypeRepository.findAll(specification, page)
            .map(applicationTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationType> specification = createSpecification(criteria);
        return applicationTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationType> createSpecification(ApplicationTypeCriteria criteria) {
        Specification<ApplicationType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationType_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ApplicationType_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ApplicationType_.status));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), ApplicationType_.sortOrder));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationType_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ApplicationType_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ApplicationType_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ApplicationType_.lastModifiedDate));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(ApplicationType_.applications, JoinType.LEFT).get(Application_.id)));
            }
        }
        return specification;
    }
}
