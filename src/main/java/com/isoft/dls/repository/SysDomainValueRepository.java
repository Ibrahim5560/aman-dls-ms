package com.isoft.dls.repository;

import com.isoft.dls.domain.SysDomainValue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the SysDomainValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDomainValueRepository extends JpaRepository<SysDomainValue, Long>, JpaSpecificationExecutor<SysDomainValue> {

    /**
     * Enum for the domain value cached methods
     */
    enum DomainValueCache {
        DOMAIN_VALUE_BY_DOMAIN_ID_CACHE;
        public static final String DOMAIN_VALUE_BY_DOMAIN_ID = "findByValueAndDomainId";
    }

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainId the Related Domain Id
     * @return the entity
     */
    @Cacheable(cacheNames = SysDomainValueRepository.DomainValueCache.DOMAIN_VALUE_BY_DOMAIN_ID)
    Optional<SysDomainValue> findByValueAndDomainId(String value, Long domainId);
}
