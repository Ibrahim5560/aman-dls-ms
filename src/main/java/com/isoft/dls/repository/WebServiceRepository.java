package com.isoft.dls.repository;

import com.isoft.dls.domain.WebService;
import com.isoft.dls.domain.enumeration.WebServiceName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the WebService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebServiceRepository extends JpaRepository<WebService, Long>, JpaSpecificationExecutor<WebService> {

    Optional<WebService> findByName(WebServiceName name);

//    Optional<WebService> findByClassName(String className);
}
