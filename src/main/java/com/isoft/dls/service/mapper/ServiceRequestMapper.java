package com.isoft.dls.service.mapper;


import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ServiceRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceRequest} and its DTO {@link ServiceRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationMapper.class})
public interface ServiceRequestMapper extends EntityMapper<ServiceRequestDTO, ServiceRequest> {

    @Mapping(source = "reversedBy.id", target = "reversedById")
    @Mapping(source = "application.id", target = "applicationId")
    ServiceRequestDTO toDto(ServiceRequest serviceRequest);

    @Mapping(target = "applicationViolations", ignore = true)
    @Mapping(target = "removeApplicationViolation", ignore = true)
    @Mapping(source = "reversedById", target = "reversedBy")
    @Mapping(source = "applicationId", target = "application")
    ServiceRequest toEntity(ServiceRequestDTO serviceRequestDTO);

    default ServiceRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setId(id);
        return serviceRequest;
    }
}
