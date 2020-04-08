/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.trn;

import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
/*
 * Create Service Request Response VM
 */

@ApiModel(description = "ServiceRequestResponse (trn_service_request) entity. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceRequestResponseVM implements ViewModel {

    @JsonProperty("serviceReferenceNo")
    private Long id;

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String toString() {
        return "ServiceRequestResponseVM{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                '}';
    }
}
