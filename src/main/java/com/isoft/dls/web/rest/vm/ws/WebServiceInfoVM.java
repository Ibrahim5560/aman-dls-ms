/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 3:04 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.ws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.isoft.dls.common.errors.SystemException;
import com.isoft.dls.common.util.NumberUtil;
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.enumeration.WebServicePropertyName;
import com.isoft.dls.service.dto.WebServiceDTO;
import com.isoft.dls.service.dto.WebServicePropertyDTO;
import com.isoft.dls.web.rest.vm.util.ViewModel;
import io.swagger.annotations.ApiModel;

/**
 * A VM of web service info
 */
@ApiModel(description = "Web service info vm. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebServiceInfoVM implements ViewModel {

    private String endPoint;

    private String userName;

    private String password;

    private String businessProcessDefinitionId;

    private String snapshotId;

    private Integer timeout;

    public WebServiceInfoVM() {
        //Default Constructor
    }

    public WebServiceInfoVM(WebServiceDTO webServiceDTO) {
        if (webServiceDTO == null) {
            throw new SystemException("Web service DTO can not be null");
        }

        setEndPoint(webServiceDTO.getEndPoint());

        /** Loop to fill-out web service properties */
        for (WebServicePropertyDTO webServicePropertyDTO : webServiceDTO.getWebServiceProperties()) {

            if (webServicePropertyDTO == null ||
                webServicePropertyDTO.getName() == null ||
                StringUtil.isBlank(webServicePropertyDTO.getName().value())) {
                continue;
            }

            if (WebServicePropertyName.USERNAME.value().equals(webServicePropertyDTO.getName().value())) {
                setUserName(webServicePropertyDTO.getValue());
            } else if (WebServicePropertyName.PASSWORD.value().equals(webServicePropertyDTO.getName().value())) {
                setPassword(webServicePropertyDTO.getValue());
            } else if (WebServicePropertyName.BPM_SNAPSHOT_ID.value().equals(webServicePropertyDTO.getName().value())) {
                setSnapshotId(webServicePropertyDTO.getValue());
            } else if (WebServicePropertyName.BPM_BPD_ID.value().equals(webServicePropertyDTO.getName().value())) {
                setBusinessProcessDefinitionId(webServicePropertyDTO.getValue());
            } else if (WebServicePropertyName.TIMEOUT.value().equals(webServicePropertyDTO.getName().value())) {
                setTimeout(NumberUtil.toInteger(webServicePropertyDTO.getValue()));
            }
        }
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessProcessDefinitionId() {
        return businessProcessDefinitionId;
    }

    public void setBusinessProcessDefinitionId(String businessProcessDefinitionId) {
        this.businessProcessDefinitionId = businessProcessDefinitionId;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "WebServiceInfoVM {" +
            "endPoint='" + getEndPoint() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", businessProcessDefinitionId='" + getBusinessProcessDefinitionId() + "'" +
            ", snapshotId='" + getSnapshotId() + "'" +
            ", timeout='" + getTimeout() + "'" +
            "}";
    }
}
