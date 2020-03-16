package com.isoft.dls.repository;

import com.isoft.dls.domain.SysDomainValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SysDomainValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDomainValueRepository extends JpaRepository<SysDomainValue, Long>, JpaSpecificationExecutor<SysDomainValue> {

}
