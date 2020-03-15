package com.isoft.dls.service.mapper;


import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ApplicationPhaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationPhase} and its DTO {@link ApplicationPhaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationPhaseMapper extends EntityMapper<ApplicationPhaseDTO, ApplicationPhase> {



    default ApplicationPhase fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationPhase applicationPhase = new ApplicationPhase();
        applicationPhase.setId(id);
        return applicationPhase;
    }
}
