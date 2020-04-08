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

import com.isoft.dls.domain.type.ServiceDocumentJsonType;
import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@ApiModel(description = "TrafficFileRequest (trn_service_request) entity. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceRequestVM implements ViewModel {

    @JsonProperty("serviceReferenceNo")
    private Long id;

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @NotNull
    @JsonProperty("serviceCode")
    private String serviceCode;

    @NotNull
    @Lob
    @JsonProperty("serviceDocument")
    private ServiceDocumentJsonType serviceDocument;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public ServiceDocumentJsonType getServiceDocument() {
        return serviceDocument;
    }

    public void setServiceDocument(ServiceDocumentJsonType serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRequestVM serviceRequestVM = (ServiceRequestVM) o;
        if (serviceRequestVM.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestVM.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
                "id=" + getId() +
                ", serviceDocument='" + getServiceDocument() + "'" +
                ", application=" + getApplicationId() +
                ", serviceCode=" + getServiceCode() +
                ", statusDate=" + getStatusDate() +
                "}";
    }
}
