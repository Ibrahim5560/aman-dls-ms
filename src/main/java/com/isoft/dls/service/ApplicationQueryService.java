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

import com.isoft.dls.domain.Application;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ApplicationRepository;
import com.isoft.dls.service.dto.ApplicationCriteria;
import com.isoft.dls.service.dto.ApplicationDTO;
import com.isoft.dls.service.mapper.ApplicationMapper;

/**
 * Service for executing complex queries for {@link Application} entities in the database.
 * The main input is a {@link ApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ApplicationDTO} or a {@link Page} of {@link ApplicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApplicationQueryService extends QueryService<Application> {

    private final Logger log = LoggerFactory.getLogger(ApplicationQueryService.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    public ApplicationQueryService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    /**
     * Return a {@link List} of {@link ApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ApplicationDTO> findByCriteria(ApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationMapper.toDto(applicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findByCriteria(ApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.findAll(specification, page)
            .map(applicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Application> specification = createSpecification(criteria);
        return applicationRepository.count(specification);
    }

    /**
     * Function to convert {@link ApplicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Application> createSpecification(ApplicationCriteria criteria) {
        Specification<Application> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Application_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Application_.status));
            }
            if (criteria.getStatusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusDate(), Application_.statusDate));
            }
            if (criteria.getActivePhase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivePhase(), Application_.activePhase));
            }
            if (criteria.getConfirmedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfirmedBy(), Application_.confirmedBy));
            }
            if (criteria.getConfirmationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConfirmationDate(), Application_.confirmationDate));
            }
            if (criteria.getRejectedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedBy(), Application_.rejectedBy));
            }
            if (criteria.getRejectionReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectionReason(), Application_.rejectionReason));
            }
            if (criteria.getRejectionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRejectionDate(), Application_.rejectionDate));
            }
            if (criteria.getProcessInstanceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessInstanceId(), Application_.processInstanceId));
            }
            if (criteria.getChannelCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChannelCode(), Application_.channelCode));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), Application_.userId));
            }
            if (criteria.getEnglishCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnglishCustomerName(), Application_.englishCustomerName));
            }
            if (criteria.getArabicCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArabicCustomerName(), Application_.arabicCustomerName));
            }
            if (criteria.getTradeLicenseNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradeLicenseNo(), Application_.tradeLicenseNo));
            }
            if (criteria.getEnglishCorporateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnglishCorporateName(), Application_.englishCorporateName));
            }
            if (criteria.getArabicCorporateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArabicCorporateName(), Application_.arabicCorporateName));
            }
            if (criteria.getUserType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserType(), Application_.userType));
            }
            if (criteria.getUserRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserRole(), Application_.userRole));
            }
            if (criteria.getApplicationTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationTypeId(), Application_.applicationTypeId));
            }
            if (criteria.getPersona() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersona(), Application_.persona));
            }
            if (criteria.getPersonaVersion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonaVersion(), Application_.personaVersion));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Application_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Application_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Application_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Application_.lastModifiedDate));
            }
            if (criteria.getSynchedEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSynchedEntityId(), Application_.synchedEntityId));
            }
            if (criteria.getApplicationPhaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationPhaseId(),
                    root -> root.join(Application_.applicationPhases, JoinType.LEFT).get(ApplicationPhase_.id)));
            }
            if (criteria.getApplicationViolationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationViolationId(),
                    root -> root.join(Application_.applicationViolations, JoinType.LEFT).get(ApplicationViolation_.id)));
            }
            if (criteria.getApplicationTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationTypeId(),
                    root -> root.join(Application_.applicationType, JoinType.LEFT).get(ApplicationType_.id)));
            }
        }
        return specification;
    }
}
