package com.isoft.dls.repository;

import com.isoft.dls.domain.WebServiceProperty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WebServiceProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebServicePropertyRepository extends JpaRepository<WebServiceProperty, Long>, JpaSpecificationExecutor<WebServiceProperty> {

}
