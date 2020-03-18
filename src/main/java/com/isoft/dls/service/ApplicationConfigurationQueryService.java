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

import com.isoft.dls.domain.ApplicationConfiguration;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ApplicationConfigurationRepository;
import com.isoft.dls.service.dto.ApplicationConfigurationCriteria;
import com.isoft.dls.service.dto.ApplicationConfigurationDTO;
import com.isoft.dls.service.mapper.ApplicationConfigurationMapper;

/**
 * Service for executing complex queries for {@link ApplicationConfiguration} entities in the database.
 * The main input is a {@link ApplicationConfigurationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationConfigurationDTO} or a {@link Page} of {@link ApplicationConfigurationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationConfigurationQueryService extends QueryService<ApplicationConfiguration> {

    private final Logger log = LoggerFactory.getLogger(ApplicationConfigurationQueryService.class);

    private final ApplicationConfigurationRepository applicationConfigurationRepository;

    private final ApplicationConfigurationMapper applicationConfigurationMapper;

    public ApplicationConfigurationQueryService(ApplicationConfigurationRepository applicationConfigurationRepository, ApplicationConfigurationMapper applicationConfigurationMapper) {
        this.applicationConfigurationRepository = applicationConfigurationRepository;
        this.applicationConfigurationMapper = applicationConfigurationMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationConfigurationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationConfigurationDTO> findByCriteria(ApplicationConfigurationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ApplicationConfiguration> specification = createSpecification(criteria);
        return applicationConfigurationMapper.toDto(applicationConfigurationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationConfigurationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationConfigurationDTO> findByCriteria(ApplicationConfigurationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ApplicationConfiguration> specification = createSpecification(criteria);
        return applicationConfigurationRepository.findAll(specification, page)
            .map(applicationConfigurationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationConfigurationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ApplicationConfiguration> specification = createSpecification(criteria);
        return applicationConfigurationRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationConfigurationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ApplicationConfiguration> createSpecification(ApplicationConfigurationCriteria criteria) {
        Specification<ApplicationConfiguration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicationConfiguration_.id));
            }
            if (criteria.getConfigKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigKey(), ApplicationConfiguration_.configKey));
            }
            if (criteria.getConfigValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigValue(), ApplicationConfiguration_.configValue));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ApplicationConfiguration_.description));
            }
            if (criteria.getCached() != null) {
                specification = specification.and(buildSpecification(criteria.getCached(), ApplicationConfiguration_.cached));
            }
            if (criteria.getEncrypted() != null) {
                specification = specification.and(buildSpecification(criteria.getEncrypted(), ApplicationConfiguration_.encrypted));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ApplicationConfiguration_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ApplicationConfiguration_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ApplicationConfiguration_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ApplicationConfiguration_.lastModifiedDate));
            }
        }
        return specification;
    }
}
