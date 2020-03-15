package com.isoft.dls.repository;

import com.isoft.dls.domain.ApplicationType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long>, JpaSpecificationExecutor<ApplicationType> {
}
