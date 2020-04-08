package com.isoft.dls.repository;

import com.isoft.dls.domain.SysDomain;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the SysDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDomainRepository extends JpaRepository<SysDomain, Long>, JpaSpecificationExecutor<SysDomain> {


    /**
     * Enum for the domain cached methods
     */
    public enum DomainCache {
        DOMAIN_BY_CODE_CACHE;
        public static final String DOMAIN_BY_CODE = "domainByCode";
    }

    /**
     * Get the "domain" entity by "code".
     *
     * @param code domain code
     * @return the entity
     */
//    @EntityGraph(attributePaths = "domainValues")
    @Cacheable(cacheNames = DomainCache.DOMAIN_BY_CODE)
    Optional<SysDomain> findByCode(String code);
}
