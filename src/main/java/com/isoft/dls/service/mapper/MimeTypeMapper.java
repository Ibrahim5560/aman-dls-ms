package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.MimeTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MimeType} and its DTO {@link MimeTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MimeTypeMapper extends EntityMapper<MimeTypeDTO, MimeType> {



    default MimeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        MimeType mimeType = new MimeType();
        mimeType.setId(id);
        return mimeType;
    }
}
