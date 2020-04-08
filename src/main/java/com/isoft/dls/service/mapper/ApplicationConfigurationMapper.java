package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ApplicationConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationConfiguration} and its DTO {@link ApplicationConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationConfigurationMapper extends EntityMapper<ApplicationConfigurationDTO, ApplicationConfiguration> {



    default ApplicationConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        applicationConfiguration.setId(id);
        return applicationConfiguration;
    }
}
