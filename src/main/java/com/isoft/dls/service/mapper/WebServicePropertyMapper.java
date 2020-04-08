package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.WebServicePropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebServiceProperty} and its DTO {@link WebServicePropertyDTO}.
 */
@Mapper(componentModel = "spring", uses = {WebServiceMapper.class})
public interface WebServicePropertyMapper extends EntityMapper<WebServicePropertyDTO, WebServiceProperty> {

    @Mapping(source = "webService.id", target = "webServiceId")
    WebServicePropertyDTO toDto(WebServiceProperty webServiceProperty);

    @Mapping(source = "webServiceId", target = "webService")
    WebServiceProperty toEntity(WebServicePropertyDTO webServicePropertyDTO);

    default WebServiceProperty fromId(Long id) {
        if (id == null) {
            return null;
        }
        WebServiceProperty webServiceProperty = new WebServiceProperty();
        webServiceProperty.setId(id);
        return webServiceProperty;
    }
}
