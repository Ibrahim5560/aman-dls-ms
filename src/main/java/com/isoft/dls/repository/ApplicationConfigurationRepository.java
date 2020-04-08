package com.isoft.dls.repository;

import com.isoft.dls.domain.ApplicationConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ApplicationConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfiguration, Long>, JpaSpecificationExecutor<ApplicationConfiguration> {


    enum ApplicationConfigurationCashe{
        GET_ALL_APPLICATION_CONFIGURATION;
        public static final String APPLICATION_CONFIGURATION_BY_CONFIG_KEY = "applicationConfigurationByConfigKey";
    }

    @Cacheable(cacheNames = ApplicationConfigurationCashe.APPLICATION_CONFIGURATION_BY_CONFIG_KEY, key = "#p0", unless="#result == null or #result.cached == false")
    Optional<ApplicationConfiguration> getByConfigKey(String key);

    @Cacheable(cacheNames = ApplicationConfigurationCashe.APPLICATION_CONFIGURATION_BY_CONFIG_KEY, key = "#p0", unless="#result == null or #result.cached == false")
    Optional<ApplicationConfiguration> getByConfigKeyIgnoreCase(String key);

    boolean existsByConfigKey(String configKey);
}
