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

import com.isoft.dls.domain.WebServiceProperty;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.WebServicePropertyRepository;
import com.isoft.dls.service.dto.WebServicePropertyCriteria;
import com.isoft.dls.service.dto.WebServicePropertyDTO;
import com.isoft.dls.service.mapper.WebServicePropertyMapper;

/**
 * Service for executing complex queries for {@link WebServiceProperty} entities in the database.
 * The main input is a {@link WebServicePropertyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WebServicePropertyDTO} or a {@link Page} of {@link WebServicePropertyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WebServicePropertyQueryService extends QueryService<WebServiceProperty> {

    private final Logger log = LoggerFactory.getLogger(WebServicePropertyQueryService.class);

    private final WebServicePropertyRepository webServicePropertyRepository;

    private final WebServicePropertyMapper webServicePropertyMapper;

    public WebServicePropertyQueryService(WebServicePropertyRepository webServicePropertyRepository, WebServicePropertyMapper webServicePropertyMapper) {
        this.webServicePropertyRepository = webServicePropertyRepository;
        this.webServicePropertyMapper = webServicePropertyMapper;
    }

    /**
     * Return a {@link List} of {@link WebServicePropertyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WebServicePropertyDTO> findByCriteria(WebServicePropertyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WebServiceProperty> specification = createSpecification(criteria);
        return webServicePropertyMapper.toDto(webServicePropertyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WebServicePropertyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WebServicePropertyDTO> findByCriteria(WebServicePropertyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WebServiceProperty> specification = createSpecification(criteria);
        return webServicePropertyRepository.findAll(specification, page)
            .map(webServicePropertyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WebServicePropertyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WebServiceProperty> specification = createSpecification(criteria);
        return webServicePropertyRepository.count(specification);
    }

    /**
     * Function to convert {@link WebServicePropertyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WebServiceProperty> createSpecification(WebServicePropertyCriteria criteria) {
        Specification<WebServiceProperty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WebServiceProperty_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildSpecification(criteria.getName(), WebServiceProperty_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), WebServiceProperty_.value));
            }
            if (criteria.getTechnicalRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnicalRemarks(), WebServiceProperty_.technicalRemarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), WebServiceProperty_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), WebServiceProperty_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WebServiceProperty_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), WebServiceProperty_.lastModifiedDate));
            }
            if (criteria.getWebServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getWebServiceId(),
                    root -> root.join(WebServiceProperty_.webService, JoinType.LEFT).get(WebService_.id)));
            }
        }
        return specification;
    }
}
