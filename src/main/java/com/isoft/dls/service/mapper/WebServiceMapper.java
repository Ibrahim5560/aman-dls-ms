package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.WebServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebService} and its DTO {@link WebServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebServiceMapper extends EntityMapper<WebServiceDTO, WebService> {


    @Mapping(target = "webServiceProperties", ignore = true)
    @Mapping(target = "removeWebServiceProperty", ignore = true)
    WebService toEntity(WebServiceDTO webServiceDTO);

    default WebService fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebService webService = new WebService();
        webService.setId(id);
        return webService;
    }
}
