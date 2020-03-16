package com.isoft.dls.repository;

import com.isoft.dls.domain.SysDomain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDomainRepository extends JpaRepository<SysDomain, Long>, JpaSpecificationExecutor<SysDomain> {

}
