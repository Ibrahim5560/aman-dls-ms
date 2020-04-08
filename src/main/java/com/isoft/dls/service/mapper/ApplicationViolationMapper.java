package com.isoft.dls.service.mapper;


import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ApplicationViolationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationViolation} and its DTO {@link ApplicationViolationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceRequestMapper.class, ApplicationMapper.class})
public interface ApplicationViolationMapper extends EntityMapper<ApplicationViolationDTO, ApplicationViolation> {

    @Mapping(source = "serviceRequest.id", target = "serviceRequestId")
    @Mapping(source = "application.id", target = "applicationId")
    ApplicationViolationDTO toDto(ApplicationViolation applicationViolation);

    @Mapping(source = "serviceRequestId", target = "serviceRequest")
    @Mapping(source = "applicationId", target = "application")
    ApplicationViolation toEntity(ApplicationViolationDTO applicationViolationDTO);

    default ApplicationViolation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationViolation applicationViolation = new ApplicationViolation();
        applicationViolation.setId(id);
        return applicationViolation;
    }
}
