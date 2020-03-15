package com.isoft.dls.repository;

import com.isoft.dls.domain.ApplicationViolation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationViolation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationViolationRepository extends JpaRepository<ApplicationViolation, Long>, JpaSpecificationExecutor<ApplicationViolation> {
}
