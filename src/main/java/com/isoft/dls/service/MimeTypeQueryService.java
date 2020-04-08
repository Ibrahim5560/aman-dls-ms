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

import com.isoft.dls.domain.MimeType;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.MimeTypeRepository;
import com.isoft.dls.service.dto.MimeTypeCriteria;
import com.isoft.dls.service.dto.MimeTypeDTO;
import com.isoft.dls.service.mapper.MimeTypeMapper;

/**
 * Service for executing complex queries for {@link MimeType} entities in the database.
 * The main input is a {@link MimeTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MimeTypeDTO} or a {@link Page} of {@link MimeTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MimeTypeQueryService extends QueryService<MimeType> {

    private final Logger log = LoggerFactory.getLogger(MimeTypeQueryService.class);

    private final MimeTypeRepository mimeTypeRepository;

    private final MimeTypeMapper mimeTypeMapper;

    public MimeTypeQueryService(MimeTypeRepository mimeTypeRepository, MimeTypeMapper mimeTypeMapper) {
        this.mimeTypeRepository = mimeTypeRepository;
        this.mimeTypeMapper = mimeTypeMapper;
    }

    /**
     * Return a {@link List} of {@link MimeTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MimeTypeDTO> findByCriteria(MimeTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MimeType> specification = createSpecification(criteria);
        return mimeTypeMapper.toDto(mimeTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MimeTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MimeTypeDTO> findByCriteria(MimeTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MimeType> specification = createSpecification(criteria);
        return mimeTypeRepository.findAll(specification, page)
            .map(mimeTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MimeTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MimeType> specification = createSpecification(criteria);
        return mimeTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link MimeTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MimeType> createSpecification(MimeTypeCriteria criteria) {
        Specification<MimeType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MimeType_.id));
            }
            if (criteria.getExtension() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtension(), MimeType_.extension));
            }
            if (criteria.getContentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentType(), MimeType_.contentType));
            }
            if (criteria.getMaximumSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaximumSize(), MimeType_.maximumSize));
            }
        }
        return specification;
    }
}
