///*
// * Copyright (c) ISOFT 2020.
// * Ferdous Tower (Takreer Building) , Salam Street
// * Abu Dhabi, United Arab Emirates
// * P.O. Box: 32326
// * All Rights Reserved.
// *
// * ver    Developer          	Date              Comments
// * ----- -----------------  	----------       -----------------
// * 1.00  Eng. Ibrahim Hassanin 3/18/20 5:39 AM  - File created.
// */
//
//package com.isoft.dls.service.dto.prd;
//
//import com.isoft.dls.domain.type.LearningFileProductJsonType;
//import com.isoft.dls.service.dto.AbstractAuditingDTO;
//import com.isoft.dls.service.dto.ApplicationDTO;
//import com.isoft.dls.service.dto.trn.ApplicationDTO;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModel;
//
//import javax.persistence.Lob;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.Objects;
//
///**
// * A DTO for the LearningFile entity.
// */
//@ApiModel(description = "LearningFile (prd_learning_file) entity. @author Mena Emiel.")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class LearningFileDTO extends AbstractAuditingDTO implements Serializable {
//
//    @JsonIgnore
//    private Long id;
//
//    @NotNull
//    @JsonProperty("personaCategoryCode")
//    private String personaCategoryCode;
//
//    @NotNull
//    @JsonProperty("personaVersionCode")
//    private String personaVersionCode;
//
//    @NotNull
//    @JsonProperty("serviceReferenceNo")
//    private Long serviceRequestId;
//
//    @NotNull
//    @JsonProperty("applicationReferenceNo")
//    private Long applicationId;
//
//    @JsonProperty("applicationDetails")
//    private ApplicationDTO applicationDetails;
//
//    @Lob
//    @JsonProperty("productDocument")
//    private LearningFileProductJsonType productDocument;
//
//    @JsonProperty("technicalRemarks")
//    private String technicalRemarks;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getPersonaCategoryCode() {
//        return personaCategoryCode;
//    }
//
//    public void setPersonaCategoryCode(String personaCategoryCode) {
//        this.personaCategoryCode = personaCategoryCode;
//    }
//
//    public String getPersonaVersionCode() {
//        return personaVersionCode;
//    }
//
//    public void setPersonaVersionCode(String personaVersionCode) {
//        this.personaVersionCode = personaVersionCode;
//    }
//
//    public Long getServiceRequestId() {
//        return serviceRequestId;
//    }
//
//    public void setServiceRequestId(Long serviceRequestId) {
//        this.serviceRequestId = serviceRequestId;
//    }
//
//    public Long getApplicationId() {
//        return applicationId;
//    }
//
//    public void setApplicationId(Long applicationId) {
//        this.applicationId = applicationId;
//    }
//
//    public ApplicationDTO getApplicationDetails() {
//        return applicationDetails;
//    }
//
//    public void setApplicationDetails(ApplicationDTO applicationDetails) {
//        this.applicationDetails = applicationDetails;
//    }
//
//    public LearningFileProductJsonType getProductDocument() {
//        return productDocument;
//    }
//
//    public void setProductDocument(LearningFileProductJsonType productDocument) {
//        this.productDocument = productDocument;
//    }
//
//    public String getTechnicalRemarks() {
//        return technicalRemarks;
//    }
//
//    public void setTechnicalRemarks(String technicalRemarks) {
//        this.technicalRemarks = technicalRemarks;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        LearningFileDTO learningFileDTO = (LearningFileDTO) o;
//        if (learningFileDTO.getId() == null || getId() == null) {
//            return false;
//        }
//        return Objects.equals(getId(), learningFileDTO.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(getId());
//    }
//
//    @Override
//    public String toString() {
//        return "LearningFileDTO{" +
//            "id=" + getId() +
//            ", personaCategoryCode='" + getPersonaCategoryCode() + "'" +
//            ", personaVersionCode='" + getPersonaVersionCode() + "'" +
//            ", serviceRequestId=" + getServiceRequestId() +
//            ", applicationId=" + getApplicationId() +
//            ", applicationDetails=" + getApplicationDetails() +
//            ", productDocument='" + getProductDocument() + "'" +
//            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
//            "}";
//    }
//}
