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

import com.isoft.dls.common.util.CommonUtil;
import com.isoft.dls.config.KieServerOperations;
import com.isoft.dls.domain.enumeration.WebServiceName;
import com.isoft.dls.security.SecurityUtils;
import com.isoft.dls.service.WebServiceService;
import com.isoft.dls.web.rest.vm.bpm.*;
import com.isoft.dls.web.rest.vm.ntf.CustomerApplicationInfoVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Business Process Client
 *
 * @author Tariq Abu Amireh
 */
@Service
public class BusinessProcessClient implements BusinessProcess {

    /*
     * Constants
     */
    private static final String CLAIM_TASK = "claim";
    private static final String CANCEL_CLAIM_TASK = "cancel";
    private static final String DEVELOPMENT_BRANCH_ID = "2063.89d7ca51-bf89-4cd6-a9a6-71abc21f8e9c";
    private static final String DEVELOPMENT_INITIATE_PROCESS_SERVICE_URL = "/rest/bpm/wle/v1/process/?action=start&bpdId={bpdId}&branchId={branchId}&params={params}&parts=executionTree";
//
//    @Autowired
//    private RestTemplate bpmRestTemplate;

//    @Autowired
//    private CommonUtil commonUtil;
//
//    @Autowired
//    private SecurityUtils securityUtils;
//
//    @Autowired
//    private WebServiceService webServiceService;
//
//    @Autowired
//    private KieServerOperations operations;

    @Value("#{environment['bpm.context.root']}")
    private String bpmContextRoot;

    @Autowired
    private Environment env;

    @Override
    public ResponseEntity<LearningFileInitiationResponseVM> startLearningFileProcess(LearningFileInitiationRequestVM request) {
        return null;
    }

    @Override
    public ResponseEntity<LearningFileInitiationResponseVM> initiateProcess(WebServiceName serviceName, Long applicationId, String params) {
        return null;
    }

    @Override
    public ResponseEntity<CustomerApplicationInfoVM> updateApplicationPhaseCriteria(Long applicationId, String activePhase, String criteria) {
        return null;
    }

    @Override
    public ResponseEntity<BpmUserInboxVM> getUserInbox(Integer pageSize, Integer offset) {
        return null;
    }

    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> drivingSchoolAction(ApplicationVerificationRequestVM request) {
        return null;
    }

    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> paymentCollection(PaymentCollectionRequestVM request) {
        return null;
    }

    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> acceptContract(Long applicationId) {
        return null;
    }

    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> finishTask(Object... params) {
        return null;
    }

    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> resendTask(Object... params) {
        return null;
    }

    @Override
    public ResponseEntity<ClaimBpmTaskResponseVM> claimTask(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> cancelClaimTask(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<BusinessProcessDetailsVM> getProcessDetails(Long processId) {
        return null;
    }

    @Override
    public ResponseEntity<BpmActiveTaskInfoVM> getActiveTask(Long applicationId) {
        return null;
    }

    @Override
    public ResponseEntity<BpmTaskDetailsVM> getTaskDetails(Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity<BpmActiveTaskInfoVM> getExamResult(Long processInstanceId) {
        return null;
    }
}
