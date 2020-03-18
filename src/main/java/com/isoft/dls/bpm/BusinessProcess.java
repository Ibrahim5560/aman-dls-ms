/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:20 AM  - File created.
 */

package com.isoft.dls.bpm;

import com.isoft.dls.domain.enumeration.WebServiceName;
import com.isoft.dls.web.rest.vm.bpm.*;
import com.isoft.dls.web.rest.vm.ntf.CustomerApplicationInfoVM;
import org.springframework.http.ResponseEntity;

public interface BusinessProcess {
    ResponseEntity<LearningFileInitiationResponseVM> startLearningFileProcess(LearningFileInitiationRequestVM request);

    ResponseEntity<LearningFileInitiationResponseVM> initiateProcess(WebServiceName serviceName, Long applicationId, String params);

    ResponseEntity<CustomerApplicationInfoVM> updateApplicationPhaseCriteria(Long applicationId, String activePhase, String criteria);

    ResponseEntity<BpmUserInboxVM> getUserInbox(Integer pageSize, Integer offset);

    ResponseEntity<FinishBpmTaskResponseVM> drivingSchoolAction(ApplicationVerificationRequestVM request);

    ResponseEntity<FinishBpmTaskResponseVM> paymentCollection(PaymentCollectionRequestVM request);

    ResponseEntity<FinishBpmTaskResponseVM> acceptContract(Long applicationId);

    ResponseEntity<FinishBpmTaskResponseVM> finishTask(Object... params);

    ResponseEntity<FinishBpmTaskResponseVM> resendTask(Object... params);

    ResponseEntity<ClaimBpmTaskResponseVM> claimTask(Long taskId);

    ResponseEntity<Void> cancelClaimTask(Long taskId);

    ResponseEntity<BusinessProcessDetailsVM> getProcessDetails(Long processId);

    ResponseEntity<BpmActiveTaskInfoVM> getActiveTask(Long applicationId);

    ResponseEntity<BpmTaskDetailsVM> getTaskDetails(Long taskId);

    ResponseEntity<BpmActiveTaskInfoVM> getExamResult(Long processInstanceId);
}
