package com.isoft.dls.repository;

import com.isoft.dls.domain.ServiceRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long>, JpaSpecificationExecutor<ServiceRequest> {
}
