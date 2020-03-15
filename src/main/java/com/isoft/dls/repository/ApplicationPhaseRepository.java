package com.isoft.dls.repository;

import com.isoft.dls.domain.ApplicationPhase;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationPhase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationPhaseRepository extends JpaRepository<ApplicationPhase, Long>, JpaSpecificationExecutor<ApplicationPhase> {
}
