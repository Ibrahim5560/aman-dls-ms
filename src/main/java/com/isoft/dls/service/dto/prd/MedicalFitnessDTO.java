/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 5:39 AM  - File created.
 */

package com.isoft.dls.service.dto.prd;

import com.isoft.dls.service.dto.AbstractAuditingDTO;
import io.swagger.annotations.ApiModel;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MedicalFitness entity.
 */
@ApiModel(description = "DrivingLicense (prd_medical_fitness) entity. @author Mena Emiel.")
public class MedicalFitnessDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long serviceRequestId;

    @NotNull
    private Long applicationId;

    @Lob
    private String productDocument;

    private String technicalRemarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getProductDocument() {
        return productDocument;
    }

    public void setProductDocument(String productDocument) {
        this.productDocument = productDocument;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicalFitnessDTO medicalFitnessDTO = (MedicalFitnessDTO) o;
        if (medicalFitnessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalFitnessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalFitnessDTO{" +
            "id=" + getId() +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            ", productDocument='" + getProductDocument() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            "}";
    }
}
