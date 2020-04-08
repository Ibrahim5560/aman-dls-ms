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

import com.isoft.dls.domain.WebService;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.WebServiceRepository;
import com.isoft.dls.service.dto.WebServiceCriteria;
import com.isoft.dls.service.dto.WebServiceDTO;
import com.isoft.dls.service.mapper.WebServiceMapper;

/**
 * Service for executing complex queries for {@link WebService} entities in the database.
 * The main input is a {@link WebServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WebServiceDTO} or a {@link Page} of {@link WebServiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WebServiceQueryService extends QueryService<WebService> {

    private final Logger log = LoggerFactory.getLogger(WebServiceQueryService.class);

    private final WebServiceRepository webServiceRepository;

    private final WebServiceMapper webServiceMapper;

    public WebServiceQueryService(WebServiceRepository webServiceRepository, WebServiceMapper webServiceMapper) {
        this.webServiceRepository = webServiceRepository;
        this.webServiceMapper = webServiceMapper;
    }

    /**
     * Return a {@link List} of {@link WebServiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WebServiceDTO> findByCriteria(WebServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WebService> specification = createSpecification(criteria);
        return webServiceMapper.toDto(webServiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WebServiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WebServiceDTO> findByCriteria(WebServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WebService> specification = createSpecification(criteria);
        return webServiceRepository.findAll(specification, page)
            .map(webServiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WebServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WebService> specification = createSpecification(criteria);
        return webServiceRepository.count(specification);
    }

    /**
     * Function to convert {@link WebServiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WebService> createSpecification(WebServiceCriteria criteria) {
        Specification<WebService> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WebService_.id));
            }
            if (criteria.getEndPoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndPoint(), WebService_.endPoint));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildSpecification(criteria.getName(), WebService_.name));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), WebService_.enabled));
            }
            if (criteria.getTechnicalRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnicalRemarks(), WebService_.technicalRemarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), WebService_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), WebService_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WebService_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), WebService_.lastModifiedDate));
            }
            if (criteria.getWebServicePropertyId() != null) {
                specification = specification.and(buildSpecification(criteria.getWebServicePropertyId(),
                    root -> root.join(WebService_.webServiceProperties, JoinType.LEFT).get(WebServiceProperty_.id)));
            }
        }
        return specification;
    }
}
