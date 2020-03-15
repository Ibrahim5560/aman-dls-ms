package com.isoft.dls.service.mapper;


import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Application} and its DTO {@link ApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationTypeMapper.class})
public interface ApplicationMapper extends EntityMapper<ApplicationDTO, Application> {

    @Mapping(source = "applicationType.id", target = "applicationTypeId")
    ApplicationDTO toDto(Application application);

    @Mapping(target = "applicationPhases", ignore = true)
    @Mapping(target = "removeApplicationPhase", ignore = true)
    @Mapping(target = "serviceRequests", ignore = true)
    @Mapping(target = "removeServiceRequest", ignore = true)
    @Mapping(target = "applicationViolations", ignore = true)
    @Mapping(target = "removeApplicationViolation", ignore = true)
    @Mapping(source = "applicationTypeId", target = "applicationType")
    Application toEntity(ApplicationDTO applicationDTO);

    default Application fromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
