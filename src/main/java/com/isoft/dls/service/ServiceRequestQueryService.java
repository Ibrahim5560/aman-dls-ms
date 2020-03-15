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

import com.isoft.dls.domain.ServiceRequest;
import com.isoft.dls.domain.*; // for static metamodels
import com.isoft.dls.repository.ServiceRequestRepository;
import com.isoft.dls.service.dto.ServiceRequestCriteria;
import com.isoft.dls.service.dto.ServiceRequestDTO;
import com.isoft.dls.service.mapper.ServiceRequestMapper;

/**
 * Service for executing complex queries for {@link ServiceRequest} entities in the database.
 * The main input is a {@link ServiceRequestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceRequestDTO} or a {@link Page} of {@link ServiceRequestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceRequestQueryService extends QueryService<ServiceRequest> {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestQueryService.class);

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    public ServiceRequestQueryService(ServiceRequestRepository serviceRequestRepository, ServiceRequestMapper serviceRequestMapper) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestMapper = serviceRequestMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceRequestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceRequestDTO> findByCriteria(ServiceRequestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceRequest> specification = createSpecification(criteria);
        return serviceRequestMapper.toDto(serviceRequestRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceRequestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceRequestDTO> findByCriteria(ServiceRequestCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceRequest> specification = createSpecification(criteria);
        return serviceRequestRepository.findAll(specification, page)
            .map(serviceRequestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceRequestCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceRequest> specification = createSpecification(criteria);
        return serviceRequestRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceRequestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceRequest> createSpecification(ServiceRequestCriteria criteria) {
        Specification<ServiceRequest> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceRequest_.id));
            }
            if (criteria.getServiceCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceCode(), ServiceRequest_.serviceCode));
            }
            if (criteria.getPhaseType() != null) {
                specification = specification.and(buildSpecification(criteria.getPhaseType(), ServiceRequest_.phaseType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ServiceRequest_.status));
            }
            if (criteria.getStatusDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusDescription(), ServiceRequest_.statusDescription));
            }
            if (criteria.getStatusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusDate(), ServiceRequest_.statusDate));
            }
            if (criteria.getTotalFeeAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalFeeAmount(), ServiceRequest_.totalFeeAmount));
            }
            if (criteria.getPaidBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaidBy(), ServiceRequest_.paidBy));
            }
            if (criteria.getPaymentMethod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentMethod(), ServiceRequest_.paymentMethod));
            }
            if (criteria.getPaymentReference() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentReference(), ServiceRequest_.paymentReference));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDate(), ServiceRequest_.paymentDate));
            }
            if (criteria.getRejectedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedBy(), ServiceRequest_.rejectedBy));
            }
            if (criteria.getRejectionReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectionReason(), ServiceRequest_.rejectionReason));
            }
            if (criteria.getRejectionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRejectionDate(), ServiceRequest_.rejectionDate));
            }
            if (criteria.getProcessInstanceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessInstanceId(), ServiceRequest_.processInstanceId));
            }
            if (criteria.getApplicationViolationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationViolationId(),
                    root -> root.join(ServiceRequest_.applicationViolations, JoinType.LEFT).get(ApplicationViolation_.id)));
            }
            if (criteria.getReversedById() != null) {
                specification = specification.and(buildSpecification(criteria.getReversedById(),
                    root -> root.join(ServiceRequest_.reversedBy, JoinType.LEFT).get(ServiceRequest_.id)));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(ServiceRequest_.application, JoinType.LEFT).get(Application_.id)));
            }
        }
        return specification;
    }
}
