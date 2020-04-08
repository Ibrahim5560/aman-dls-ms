package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.SysDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysDomain} and its DTO {@link SysDomainDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysDomainMapper extends EntityMapper<SysDomainDTO, SysDomain> {


    @Mapping(target = "sysDomainValues", ignore = true)
    @Mapping(target = "removeSysDomainValue", ignore = true)
    SysDomain toEntity(SysDomainDTO sysDomainDTO);

    default SysDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysDomain sysDomain = new SysDomain();
        sysDomain.setId(id);
        return sysDomain;
    }
}
