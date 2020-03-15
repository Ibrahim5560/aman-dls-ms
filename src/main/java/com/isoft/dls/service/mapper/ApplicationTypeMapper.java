package com.isoft.dls.service.mapper;


import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ApplicationTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationType} and its DTO {@link ApplicationTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationTypeMapper extends EntityMapper<ApplicationTypeDTO, ApplicationType> {


    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "removeApplication", ignore = true)
    ApplicationType toEntity(ApplicationTypeDTO applicationTypeDTO);

    default ApplicationType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationType applicationType = new ApplicationType();
        applicationType.setId(id);
        return applicationType;
    }
}
