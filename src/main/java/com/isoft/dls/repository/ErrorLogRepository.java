package com.isoft.dls.repository;

import com.isoft.dls.domain.ErrorLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ErrorLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long>, JpaSpecificationExecutor<ErrorLog> {

}
