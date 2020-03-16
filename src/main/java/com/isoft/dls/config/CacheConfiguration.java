package com.isoft.dls.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.isoft.dls.domain.Application.class.getName());
            createCache(cm, com.isoft.dls.domain.Application.class.getName() + ".applicationPhases");
            createCache(cm, com.isoft.dls.domain.Application.class.getName() + ".serviceRequests");
            createCache(cm, com.isoft.dls.domain.Application.class.getName() + ".applicationViolations");
            createCache(cm, com.isoft.dls.domain.ApplicationType.class.getName());
            createCache(cm, com.isoft.dls.domain.ApplicationPhase.class.getName());
            createCache(cm, com.isoft.dls.domain.ServiceRequest.class.getName());
            createCache(cm, com.isoft.dls.domain.ServiceRequest.class.getName() + ".applicationViolations");
            createCache(cm, com.isoft.dls.domain.ApplicationViolation.class.getName());
            createCache(cm, com.isoft.dls.domain.SysDomain.class.getName());
            createCache(cm, com.isoft.dls.domain.SysDomain.class.getName() + ".sysDomainValues");
            createCache(cm, com.isoft.dls.domain.SysDomainValue.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
