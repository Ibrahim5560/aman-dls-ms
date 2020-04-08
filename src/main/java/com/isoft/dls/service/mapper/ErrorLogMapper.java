package com.isoft.dls.service.mapper;

import com.isoft.dls.domain.*;
import com.isoft.dls.service.dto.ErrorLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ErrorLog} and its DTO {@link ErrorLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ErrorLogMapper extends EntityMapper<ErrorLogDTO, ErrorLog> {



    default ErrorLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        ErrorLog errorLog = new ErrorLog();
        errorLog.setId(id);
        return errorLog;
    }
}
