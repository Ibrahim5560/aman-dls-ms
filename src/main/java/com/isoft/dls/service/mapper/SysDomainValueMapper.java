package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.SysDomainValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysDomainValue} and its DTO {@link SysDomainValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {SysDomainMapper.class})
public interface SysDomainValueMapper extends EntityMapper<SysDomainValueDTO, SysDomainValue> {

    @Mapping(source = "sysDomain.id", target = "sysDomainId")
    SysDomainValueDTO toDto(SysDomainValue sysDomainValue);

    @Mapping(source = "sysDomainId", target = "sysDomain")
    SysDomainValue toEntity(SysDomainValueDTO sysDomainValueDTO);

    default SysDomainValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysDomainValue sysDomainValue = new SysDomainValue();
        sysDomainValue.setId(id);
        return sysDomainValue;
    }
}
