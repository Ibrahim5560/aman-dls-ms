package com.isoft.dls.service.impl;

import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.ApplicationConfiguration;
import com.isoft.dls.repository.ApplicationConfigurationRepository;
import com.isoft.dls.service.ApplicationConfigurationService;
import com.isoft.dls.service.dto.ApplicationConfigurationDTO;
import com.isoft.dls.service.mapper.ApplicationConfigurationMapper;
import org.postgresql.shaded.com.ongres.scram.common.util.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ApplicationConfiguration}.
 */
@Service
@Transactional
public class ApplicationConfigurationServiceImpl implements ApplicationConfigurationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationConfigurationServiceImpl.class);

    private final ApplicationConfigurationRepository applicationConfigurationRepository;

    private final ApplicationConfigurationMapper applicationConfigurationMapper;

//    private final CryptoUtil cryptoUtil;

    private final Environment environment;

    private final CacheManager cacheManager;

    public ApplicationConfigurationServiceImpl(ApplicationConfigurationRepository applicationConfigurationRepository,
                                               ApplicationConfigurationMapper applicationConfigurationMapper
//      ,                                        CryptoUtil cryptoUtil
        ,Environment environment,CacheManager cacheManager) {
        this.applicationConfigurationRepository = applicationConfigurationRepository;
        this.applicationConfigurationMapper = applicationConfigurationMapper;
//        this.cryptoUtil = cryptoUtil;
        this.environment = environment;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a applicationConfiguration.
     *
     * @param applicationConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplicationConfigurationDTO save(ApplicationConfigurationDTO applicationConfigurationDTO) {
        log.debug("Request to save ApplicationConfiguration : {}", applicationConfigurationDTO);
        ApplicationConfiguration applicationConfiguration = applicationConfigurationMapper.toEntity(applicationConfigurationDTO);
        applicationConfiguration = applicationConfigurationRepository.save(applicationConfiguration);
        return applicationConfigurationMapper.toDto(applicationConfiguration);
    }

    /**
     * Get all the applicationConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplicationConfigurations");
        return applicationConfigurationRepository.findAll(pageable)
            .map(applicationConfigurationMapper::toDto);
    }


    /**
     * Get one applicationConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationConfigurationDTO> findOne(Long id) {
        log.debug("Request to get ApplicationConfiguration : {}", id);
        return applicationConfigurationRepository.findById(id)
            .map(applicationConfigurationMapper::toDto);
    }

    /**
     * Delete the applicationConfiguration by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationConfiguration : {}", id);
        applicationConfigurationRepository.deleteById(id);
    }

    /**
     * Get the "key" applicationConfiguration.
     *
     * @param key the key of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationConfigurationDTO> getConfiguration(String key) {
        log.debug("Request to get Application Configuration By Name: {}", key);

        String propertyValue = environment.getProperty(key);
        if(!StringUtil.isEmpty(propertyValue)) {
            ApplicationConfigurationDTO appConfiguration = new ApplicationConfigurationDTO();
            appConfiguration.setConfigKey(key);
            appConfiguration.setConfigValue(propertyValue);
            return Optional.of(appConfiguration);
        }

        Optional<ApplicationConfigurationDTO> appConfiguration = applicationConfigurationRepository.getByConfigKey(key)
            .map(applicationConfigurationMapper::toDto);

        if(appConfiguration.isPresent() && appConfiguration.get().isEncrypted().booleanValue()) {
            appConfiguration.get().setConfigValue(appConfiguration.get().getConfigValue());
//                cryptoUtil.decrypt(appConfiguration.get().getConfigValue()));
        }
        return appConfiguration;
    }

    /**
     * Clear Configuration Caches..
     *
     * @param applicationConfiguration Application Configuration Entity Instance.
     */
    private void clearConfigurationCaches(ApplicationConfiguration applicationConfiguration) {
        Objects.requireNonNull(cacheManager.getCache(ApplicationConfigurationRepository.ApplicationConfigurationCashe.GET_ALL_APPLICATION_CONFIGURATION.APPLICATION_CONFIGURATION_BY_CONFIG_KEY))
            .evict(applicationConfiguration.getConfigKey());
    }
}
