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

import com.isoft.dls.domain.SysDomainValue;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.SysDomainValueRepository;
import com.isoft.dls.service.dto.SysDomainValueCriteria;
import com.isoft.dls.service.dto.SysDomainValueDTO;
import com.isoft.dls.service.mapper.SysDomainValueMapper;

/**
 * Service for executing complex queries for {@link SysDomainValue} entities in the database.
 * The main input is a {@link SysDomainValueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SysDomainValueDTO} or a {@link Page} of {@link SysDomainValueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SysDomainValueQueryService extends QueryService<SysDomainValue> {

    private final Logger log = LoggerFactory.getLogger(SysDomainValueQueryService.class);

    private final SysDomainValueRepository sysDomainValueRepository;

    private final SysDomainValueMapper sysDomainValueMapper;

    public SysDomainValueQueryService(SysDomainValueRepository sysDomainValueRepository, SysDomainValueMapper sysDomainValueMapper) {
        this.sysDomainValueRepository = sysDomainValueRepository;
        this.sysDomainValueMapper = sysDomainValueMapper;
    }

    /**
     * Return a {@link List} of {@link SysDomainValueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysDomainValueDTO> findByCriteria(SysDomainValueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysDomainValue> specification = createSpecification(criteria);
        return sysDomainValueMapper.toDto(sysDomainValueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysDomainValueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysDomainValueDTO> findByCriteria(SysDomainValueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysDomainValue> specification = createSpecification(criteria);
        return sysDomainValueRepository.findAll(specification, page)
            .map(sysDomainValueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysDomainValueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysDomainValue> specification = createSpecification(criteria);
        return sysDomainValueRepository.count(specification);
    }

    /**
     * Function to convert {@link SysDomainValueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysDomainValue> createSpecification(SysDomainValueCriteria criteria) {
        Specification<SysDomainValue> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysDomainValue_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), SysDomainValue_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SysDomainValue_.description));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), SysDomainValue_.sortOrder));
            }
            if (criteria.getDomainId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDomainId(), SysDomainValue_.domainId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SysDomainValue_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SysDomainValue_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SysDomainValue_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SysDomainValue_.lastModifiedDate));
            }
            if (criteria.getSysDomainId() != null) {
                specification = specification.and(buildSpecification(criteria.getSysDomainId(),
                    root -> root.join(SysDomainValue_.sysDomain, JoinType.LEFT).get(SysDomain_.id)));
            }
        }
        return specification;
    }
}
