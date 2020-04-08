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

import com.isoft.dls.domain.SysDomain;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.SysDomainRepository;
import com.isoft.dls.service.dto.SysDomainCriteria;
import com.isoft.dls.service.dto.SysDomainDTO;
import com.isoft.dls.service.mapper.SysDomainMapper;

/**
 * Service for executing complex queries for {@link SysDomain} entities in the database.
 * The main input is a {@link SysDomainCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SysDomainDTO} or a {@link Page} of {@link SysDomainDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SysDomainQueryService extends QueryService<SysDomain> {

    private final Logger log = LoggerFactory.getLogger(SysDomainQueryService.class);

    private final SysDomainRepository sysDomainRepository;

    private final SysDomainMapper sysDomainMapper;

    public SysDomainQueryService(SysDomainRepository sysDomainRepository, SysDomainMapper sysDomainMapper) {
        this.sysDomainRepository = sysDomainRepository;
        this.sysDomainMapper = sysDomainMapper;
    }

    /**
     * Return a {@link List} of {@link SysDomainDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysDomainDTO> findByCriteria(SysDomainCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysDomain> specification = createSpecification(criteria);
        return sysDomainMapper.toDto(sysDomainRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysDomainDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysDomainDTO> findByCriteria(SysDomainCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysDomain> specification = createSpecification(criteria);
        return sysDomainRepository.findAll(specification, page)
            .map(sysDomainMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysDomainCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysDomain> specification = createSpecification(criteria);
        return sysDomainRepository.count(specification);
    }

    /**
     * Function to convert {@link SysDomainCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysDomain> createSpecification(SysDomainCriteria criteria) {
        Specification<SysDomain> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysDomain_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SysDomain_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SysDomain_.description));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SysDomain_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SysDomain_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SysDomain_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SysDomain_.lastModifiedDate));
            }
            if (criteria.getSysDomainValueId() != null) {
                specification = specification.and(buildSpecification(criteria.getSysDomainValueId(),
                    root -> root.join(SysDomain_.sysDomainValues, JoinType.LEFT).get(SysDomainValue_.id)));
            }
        }
        return specification;
    }
}
