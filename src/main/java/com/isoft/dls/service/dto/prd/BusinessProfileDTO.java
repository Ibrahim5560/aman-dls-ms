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
//import com.isoft.dls.domain.util.type.BusinessProfileProductJsonType;
//import com.isoft.dls.service.dto.AbstractAuditingDTO;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import io.swagger.annotations.ApiModel;
//
//import javax.persistence.Lob;
//import java.io.Serializable;
//import java.util.Objects;
//
///**
// * A DTO for the BusinessProfile entity.
// */
//@ApiModel(description = "BusinessProfile (prd_business_profile) entity. @author Yousef Abu Amireh / Rami Nassar.")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class BusinessProfileDTO extends AbstractAuditingDTO implements Serializable {
//
//    private Long id;
//
//    @Lob
//    private BusinessProfileProductJsonType productDocument;
//
//    private String remarks;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public BusinessProfileProductJsonType getProductDocument() {
//        return productDocument;
//    }
//
//    public void setProductDocument(BusinessProfileProductJsonType productDocument) {
//        this.productDocument = productDocument;
//    }
//
//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
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
//        BusinessProfileDTO businessProfileDTO = (BusinessProfileDTO) o;
//        if (businessProfileDTO.getId() == null || getId() == null) {
//            return false;
//        }
//        return Objects.equals(getId(), businessProfileDTO.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(getId());
//    }
//
//    @Override
//    public String toString() {
//        return "BusinessProfileDTO{" +
//            "id=" + getId() +
//            ", productDocument='" + getProductDocument() + "'" +
//            ", remarks='" + getRemarks() + "'" +
//            "}";
//    }
//}
