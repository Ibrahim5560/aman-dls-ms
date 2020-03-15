package com.isoft.dls.service.mapper;


import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ApplicationViolationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationViolation} and its DTO {@link ApplicationViolationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationViolationMapper extends EntityMapper<ApplicationViolationDTO, ApplicationViolation> {



    default ApplicationViolation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationViolation applicationViolation = new ApplicationViolation();
        applicationViolation.setId(id);
        return applicationViolation;
    }
}
