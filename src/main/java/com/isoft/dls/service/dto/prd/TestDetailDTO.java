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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.domain.enumeration.trn.TestResult;
import com.isoft.dls.domain.enumeration.trn.TestStatus;
import com.isoft.dls.service.dto.AbstractAuditingDTO;

import java.time.LocalDateTime;

/**
 * Test Detail DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TestDetailDTO extends AbstractAuditingDTO {

    @JsonProperty("status")
    private TestStatus status;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("result")
    private TestResult result;

    @JsonProperty("resultDate")
    private LocalDateTime resultDate;

    @JsonProperty("currentTest")
    private Boolean currentTest;

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }

    public LocalDateTime getResultDate() {
        return resultDate;
    }

    public void setResultDate(LocalDateTime resultDate) {
        this.resultDate = resultDate;
    }

    public Boolean getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(Boolean currentTest) {
        this.currentTest = currentTest;
    }

    @Override
    public String toString() {
        return "TestDetailDTO{" +
            "status=" + status +
            ", statusDate=" + statusDate +
            ", result=" + result +
            ", resultDate=" + resultDate +
            ", currentTest=" + currentTest +
            '}';
    }
}
