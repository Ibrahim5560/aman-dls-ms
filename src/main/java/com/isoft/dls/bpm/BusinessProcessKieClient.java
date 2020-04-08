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

import com.isoft.dls.common.errors.SystemException;
import com.isoft.dls.common.util.CommonUtil;
import com.isoft.dls.common.util.RestTemplateUtil;
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.config.KieServerOperations;
import com.isoft.dls.domain.Application;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.enumeration.WebServiceName;
import com.isoft.dls.domain.enumeration.trn.ApplicationStatus;
import com.isoft.dls.security.SecurityUtils;
import com.isoft.dls.service.ApplicationService;
import com.isoft.dls.service.WebServiceService;
import com.isoft.dls.service.dto.ApplicationDTO;
import com.isoft.dls.service.mapper.ApplicationMapper;
import com.isoft.dls.web.rest.vm.bpm.*;
import com.isoft.dls.web.rest.vm.ntf.CustomerApplicationInfoVM;
import com.isoft.dls.web.rest.vm.ws.WebServiceInfoVM;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.api.model.instance.WorkItemInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

//import com.isoft.dls.proxy.rest.util.RestTemplateUtil;
//import com.isoft.dls.security.dto.ProfileContextDTO;

/**
 * Business Process Client
 *
 * @author Tariq Abu Amireh
 */
@Service
public class BusinessProcessKieClient implements BusinessProcess {

    /*
     * Constants
     */
    @Value("#{environment['bpm.context.containerId']}")
    private String containerId;
    @Value("#{environment['bpm.context.processId']}")
    private String processId;
    @Value("#{environment['bpm.context.username']}")
    private String userId;
    @Value("#{environment['bpm.context.password']}")
    private String password;
    @Value("#{environment['gateway.context.root']}")
    private String gatewayRoot;

    @Autowired
    private RestTemplate vanillaRestTemplate;

    @Autowired
    private CommonUtil commonUtil;
//
//    @Autowired
//    private SecurityUtils securityUtils;

    @Autowired
    private WebServiceService webServiceService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private KieServerOperations operations;

    @Value("#{environment['bpm.context.root']}")
    private String bpmContextRoot;

    @Autowired
    private Environment env;

    /**
     * Start learning file process
     *
     * @return learning file initiation response
     */
    @Override
    public ResponseEntity<LearningFileInitiationResponseVM> startLearningFileProcess(LearningFileInitiationRequestVM request) {

        String params = "{\"applicationReferenceNo\" : \"" + request.getApplicationReferenceNo() + "\"}";

        // Call BPM initiate process task..
        return initiateProcess(WebServiceName.CREATE_USER_DELIVERY,
            request.getApplicationReferenceNo(),
            params);

    }

    /**
     * Initiate process.
     *
     * @param applicationId Application id
     * @param params        custom params for process initiation
     * @return
     */
    @Override
    public ResponseEntity<LearningFileInitiationResponseVM> initiateProcess(WebServiceName serviceName, Long applicationId, String params) {
        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(serviceName);
//        if (webServiceInfoVM == null) {
//            throw new SystemException("No properties declared for web service BusinessProcessProxy.getUserInbox service");
//        }

        // variables
        Map<String, Object> variables = jsonStringToHashMap(params);
//        variables.put("url","http://localhost:8185/SimpleService/getDetails");
        variables.put("url",gatewayRoot+"services/amandlsms/api/getDetails/"+applicationId);
//        Long precessInstanceId = operations.startProcess(webServiceInfoVM.getSnapshotId()
//            , webServiceInfoVM.getBusinessProcessDefinitionId()
//            , variables);
        Long precessInstanceId = operations.startProcess(containerId
            , processId
            , variables);

        //TODO : UpdateAppProcessInstanceId
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(applicationId);
        if(applicationDTO.isPresent()){
            ApplicationDTO dto = applicationDTO.get();
            dto.setProcessInstanceId(precessInstanceId);
            applicationService.save(dto);
        }

        // responseVM
        LearningFileInitiationResponseVM responseVM = new LearningFileInitiationResponseVM();
        responseVM.setApplicationReferenceNo(applicationId);
        responseVM.setStatus(1);

        ResponseEntity<LearningFileInitiationResponseVM> responseEntity = ResponseEntity.ok()
//            .header(webServiceInfoVM.getUserName(), webServiceInfoVM.getPassword())
            .header(userId, password)
            .body(responseVM);

        if (responseEntity.getBody() != null && precessInstanceId != null) {
            responseEntity.getBody().setApplicationReferenceNo(applicationId);
        }

        return responseEntity;
    }
    /**
     * update Application Phase.
     *
     * @param applicationId Application id
     * @param activePhase
     * @param criteria
     * @return
     */
    @Override
    public ResponseEntity<CustomerApplicationInfoVM> updateApplicationPhaseCriteria(Long applicationId, String activePhase, String criteria) {
        if(StringUtil.isBlank(criteria)) {
            // update Application Phase
            updateAppPhase(applicationId, activePhase);
        } else {
            // update Application Phase Criteria
            updateAppPhaseCriteria(applicationId, activePhase, criteria);
        }
        return null;
    }

    /**
     * update App Phase.
     *  @param applicationId
     * @param activePhase
     */
    private ApplicationDTO updateAppPhase(Long applicationId, String activePhase) {
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(applicationId);
        if (applicationDTO != null ) {
            ApplicationDTO applicationDTO1 = applicationService.updateAppPhaseCriteria(applicationDTO, PhaseType.valueOf(activePhase), null);
            return applicationDTO1;
        }
        return null;
    }

    /**
     * update App Phase Criteria.
     *  @param applicationId
     * @param activePhase
     */
    private ApplicationDTO updateAppPhaseCriteria(Long applicationId, String activePhase, String criteria) {
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(applicationId);
        if (applicationDTO != null) {
            ApplicationDTO applicationDTO1 = applicationService.updateAppPhaseCriteria(applicationDTO, PhaseType.valueOf(activePhase), criteria);
            return applicationDTO1;
        }
        return null;
    }

    /**
     * Get User inbox
     *
     * @return Task Details Response
     */
    @Override
    public ResponseEntity<BpmUserInboxVM> getUserInbox(Integer pageSize, Integer offset) {

        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        offset = (offset == null || offset < 0) ? 0 : offset;

//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.USER_INBOX);

        // dataListVM
        List<BpmUserInboxDataVM> dataListVM = new ArrayList<>();
        // Post Rest Call..
        List<TaskSummary> taskSummaryList = operations.findTasksOwned(userId, offset, pageSize);
        for (TaskSummary summary : taskSummaryList) {
            // infoVM
            BpmUserInboxAssigneeInfoVM infoVM = new BpmUserInboxAssigneeInfoVM();
            infoVM.setWho(summary.getActualOwner());
            // dataVM
            BpmUserInboxDataVM dataVM = new BpmUserInboxDataVM();
            dataVM.setTaskAssignedTo(infoVM);
            dataVM.setInstanceId(summary.getProcessInstanceId());
            dataVM.setTaskId(summary.getId());
            dataVM.setTaskStatus(summary.getStatus());
            // dataListVM
            dataListVM.add(dataVM);
        }
        // listVM
        BpmUserInboxDataListVM listVM = new BpmUserInboxDataListVM();
        listVM.setDataList(dataListVM);
        listVM.setOffset(offset);
        listVM.setRequestedSize(pageSize);
        listVM.setSize(pageSize);
        listVM.setTotalCount(pageSize);
        listVM.setStatus("Active");
        // inboxVM
        BpmUserInboxVM inboxVM = new BpmUserInboxVM();
        inboxVM.setData(listVM);
        inboxVM.setStatus(ApplicationStatus.DRAFT.value());
        ResponseEntity<BpmUserInboxVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(inboxVM);
        return responseEntity;
    }

    /**
     * Driving school review approval (Finish Task)
     *
     * @return Application verification response
     */
    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> drivingSchoolAction(ApplicationVerificationRequestVM request) {
        // Set finish action parameters..

//        String params = "{\"drivingSchoolReviewStatus\" : \"" + request.getDrivingSchoolReviewStatus().value() + "\"}";
        String params = "{\"resultDto\": { \"com.myapp.myappproject.TestDto\": {\"id\": 100,\"name\": \"drivingSchoolAction\",\"price\": 500.0,\"arabicDescription\": \"arabicDescription\",\"englishDescription\": \"englishDescription\",\"code\": \"code\" }}}";

        // Call BPM finish task..
        return finishTask(request.getTaskId(), params);
    }

    /**
     * Payment collection (Finish Task)
     *
     * @return Application verification response
     */
    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> paymentCollection(PaymentCollectionRequestVM request) {
        // Set finish action parameters..
//        String params = "{\"paymentCollected\" : " + request.isPaymentCollected() + "}";
        String params = "{\"resultDto\": { \"com.myapp.myappproject.TestDto\": {\"id\": 100,\"name\": \"paymentCollection\",\"price\": 500.0,\"arabicDescription\": \"arabicDescription\",\"englishDescription\": \"englishDescription\",\"code\": \"code\" }}}";

        // Call BPM finish task..
        return finishTask(request.getTaskId(), params);
    }

    /**
     * Accept contract (Finish Task)
     *
     * @return Finish BPM task response
     */
    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> acceptContract(Long applicationId) {


        ResponseEntity<BpmActiveTaskInfoVM> activeTaskInfo = getActiveTask(applicationId);

        if (activeTaskInfo == null ||
            activeTaskInfo.getBody() == null ||
            activeTaskInfo.getBody().getData() == null ||
            activeTaskInfo.getBody().getData().getTasks() == null ||
            activeTaskInfo.getBody().getData().getTasks().isEmpty()) {

            throw new SystemException("No Active Task for this Application");
        }

        BpmActiveTaskVM activeTaskVM = activeTaskInfo.getBody().getData().getTasks().get(0);

//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.ACCEPT_CONTRACT);
//        if (webServiceInfoVM == null) {
//            throw new SystemException("No properties declared for web service BusinessProcessProxy.acceptContract service");
//        }

        String params = "{\"resultDto\": { \"com.myapp.myappproject.TestDto\": {\"id\": 100,\"name\": \"acceptContract\",\"price\": 500.0,\"arabicDescription\": \"arabicDescription\",\"englishDescription\": \"englishDescription\",\"code\": \"code\" }}}";
        return finishTask(activeTaskVM.getTaskId(), params);
    }

    /**
     * Driving school review approval (Finish Task)
     *
     * @return Application verification response
     */
    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> finishTask(Object... params) {
//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.FINISH_TASK);

        // Put Rest Call..
        Object[] argArray = params;
        // responseVM
        FinishBpmTaskResponseVM responseVM = new FinishBpmTaskResponseVM();
        responseVM.setStatusCode(new Long(1));
        // param
        String content = argArray[1].toString();
        Map<String, Object> param = jsonStringToHashMap(content);

        operations.startTask(containerId, (Long) argArray[0], userId);
        operations.completeTask(containerId, (Long) argArray[0], userId, param);
        ResponseEntity<FinishBpmTaskResponseVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(responseVM);
        return responseEntity;
    }

    /**
     * Driving school review approval (Resend Task)
     *
     * @return Application verification response
     */
    @Override
    public ResponseEntity<FinishBpmTaskResponseVM> resendTask(Object... params) {
//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.FINISH_TASK);

        // Put Rest Call..
        Object[] argArray = params;
        // responseVM
        FinishBpmTaskResponseVM responseVM = new FinishBpmTaskResponseVM();
        responseVM.setStatusCode(new Long(1));

        operations.startTask(containerId, (Long) argArray[0], userId);
        operations.completeTask(containerId, (Long) argArray[0], userId, null);
        ResponseEntity<FinishBpmTaskResponseVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(responseVM);
        return responseEntity;
    }

    /**
     * Claim bpm task
     *
     * @return Claim bpm task response VM
     */
    @Override
    public ResponseEntity<ClaimBpmTaskResponseVM> claimTask(Long taskId) {
//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.CLAIM_TASK);

        // Put Rest Call..
        ClaimBpmTaskResponseVM claimBpmTaskResponseVM = new ClaimBpmTaskResponseVM();
        claimBpmTaskResponseVM.setStatusCode(new Long(1));
        operations.claimTask(containerId, taskId, userId);
        ResponseEntity<ClaimBpmTaskResponseVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(claimBpmTaskResponseVM);
        return responseEntity;
    }

    /**
     * Cancel Claim bpm task
     *
     * @return Cancel Claim bpm task response VM
     */
    @Override
    public ResponseEntity<Void> cancelClaimTask(Long taskId) {
//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.CLAIM_TASK);

        // Put Rest Call..
        operations.releaseTask(containerId, taskId, userId);
        ResponseEntity<Void> responseEntity = new ResponseEntity<Void>(HttpStatus.OK);
        return responseEntity;
    }

    /**
     * Get Process Details
     *
     * @param processId Process id
     * @return Application verification response
     */
    @Override
    public ResponseEntity<BusinessProcessDetailsVM> getProcessDetails(Long processId) {
//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.PROCESS_DETAILS);

        // Put Rest Call..
        // detailsVM
        BusinessProcessDetailsVM detailsVM = new BusinessProcessDetailsVM();
        detailsVM.setStatusCode(new Long(1));

        List<WorkItemInstance> workItemInstanceList = operations.getWorkItemByProcessInstance(containerId, processId);
        ResponseEntity<BusinessProcessDetailsVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(detailsVM);
        return responseEntity;
    }

    /**
     * Task Details
     *
     * @param applicationId Application id
     * @return Task Details Response
     */
    @Override
    public ResponseEntity<BpmActiveTaskInfoVM> getActiveTask(Long applicationId) {

//        // Get web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.ACTIVE_TASK);

        // taskVMList
        List<BpmActiveTaskVM> taskVMList = new ArrayList<>();
        // variables
        String params = "{" +
            "\"updatedTestDto\": " +
            "{" +
            "\"com.myapp.myappproject.TestDto\" : " +
            "{                                                " +
            "  \"id\" : 0                                     " +
            "  , \"name\" : \"Ibrahim\"                           " +
            "  , \"price\" : 0.0                                " +
            "  , \"arabicDescription\" : \"arabic\"    " +
            "  , \"englishDescription\" : \"english\"  " +
            "  , \"code\" : \"code\"                              " +
            "} " +
            "}                                        " +
            "," +
            "\"id\": \"100\"," +
            "\"testDto\": " +
            "{" +
            "\"com.myapp.myappproject.TestDto\" : " +
            "{                                                " +
            "  \"id\" : 0                                     " +
            "  , \"name\" : \"Ibrahim\"                           " +
            "  , \"price\" : 0.0                                " +
            "  , \"arabicDescription\" : \"arabic\"    " +
            "  , \"englishDescription\" : \"english\"  " +
            "  , \"code\" : \"code\"                              " +
            "} " +
            "}" +
            "," +
            "\"map\": \" \"" +
            "}";
        Map<String, Object> variables = jsonStringToHashMap(params);
        Long processInstanceId = operations.startProcess(containerId, processId, variables);
        // status
        List<String> status = new ArrayList<>();
        status.add("Ready");
        // Get Rest Call..
        List<TaskSummary> taskSummaryList = operations.findTasksByStatusByProcessInstanceId(processInstanceId, status, 0, 10);
        if (taskSummaryList != null && taskSummaryList.size() > 0) {
            for (TaskSummary summary : taskSummaryList) {
                // taskVM
                BpmActiveTaskVM taskVM = new BpmActiveTaskVM();
                taskVM.setAssignedTo(summary.getActualOwner());
                taskVM.setTaskId(summary.getId());
                taskVM.setStatus(summary.getStatus());
                taskVM.setOwner(summary.getActualOwner());
                taskVM.setName(summary.getName());
                // taskVMList
                taskVMList.add(taskVM);
            }
        }
        // taskDataVM
        BpmActiveTaskDataVM taskDataVM = new BpmActiveTaskDataVM();
        taskDataVM.setTasks(taskVMList);
        // taskInfoVM
        BpmActiveTaskInfoVM taskInfoVM = new BpmActiveTaskInfoVM();
        taskInfoVM.setData(taskDataVM);

        ResponseEntity<BpmActiveTaskInfoVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(taskInfoVM);
        return responseEntity;
    }

    /**
     * Get BPM Task Details
     *
     * @param taskId
     * @return BPM Task Details
     */
    @Override
    public ResponseEntity<BpmTaskDetailsVM> getTaskDetails(Long taskId) {

//        // Get bpm task details web service info..
//        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.TASK_DETAILS);

        // get Rest Call..
        TaskInstance taskInstance = operations.findTaskById(taskId);
        if (taskInstance != null) {
            // detailsDataVM
            TaskDetailsDataVM detailsDataVM = new TaskDetailsDataVM();
            detailsDataVM.setTaskId(taskId);
            detailsDataVM.setProcessInstanceId(taskInstance.getProcessInstanceId());
            detailsDataVM.setAssignedToType(taskInstance.getTaskType());
            // detailsVM
            BpmTaskDetailsVM detailsVM = new BpmTaskDetailsVM();
            detailsVM.setTaskDetailsDataVM(detailsDataVM);
            ResponseEntity<BpmTaskDetailsVM> responseEntity = ResponseEntity.ok()
                .header(userId, password)
                .body(detailsVM);
            return responseEntity;
        }
        return null;
    }


    /**
     * jsonStringToHashMap
     *
     * @param content
     * @return
     */
    private Map<String, Object> jsonStringToHashMap(String content) {
        Map<String, Object> param = null;
        try {
            param = new ObjectMapper().readValue(content, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return param;
    }
//
//    /**
//     * Get agent userIdname
//     *
//     * @return agent userIdname
//     */
//    private String getAgentUsername() {
//
//        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
//        if (!currentUserLogin.isPresent()) {
//            throw new SystemException("Invalid current userId login info");
//        }
//
//        ProfileContextDTO profile = securityUtils.getUserProfile();
//
//        if (profile.getCorporateContextDTO() == null) {
//            throw new SystemException("Invalid corporate info for logged in User login");
//        }
//        String tradeLicenseNo = profile.getCorporateContextDTO().getTradeLicenseNo();
//
//        if (StringUtil.isBlank(tradeLicenseNo)) {
//            throw new SystemException("Invalid trade license no for current logged in User");
//        }
//
//        // Build the agent BPM User name..
//        String username = currentUserLogin.get();
//        username = username.replaceAll(".", "_");
//        return tradeLicenseNo + "_" + username;
//    }

    /**
     * get Exam Result.
     *
     * @return Task Details Response
     */
    @Override
    public ResponseEntity<BpmActiveTaskInfoVM> getExamResult(Long processInstanceId) {
        // Get web service info..
        WebServiceInfoVM webServiceInfoVM = commonUtil.getWebServiceInfo(WebServiceName.CREATE_USER_DELIVERY);
        if (webServiceInfoVM == null) {
            throw new SystemException("No properties declared for web service BusinessProcessProxy.getUserInbox service");
        }
        // taskVMList
        List<BpmActiveTaskVM> taskVMList = new ArrayList<>();

        // Get Rest Call..
        ResponseEntity<String> score = RestTemplateUtil.get(new RestTemplate(),
            webServiceInfoVM.getEndPoint(),
            webServiceInfoVM.getUserName(),
            webServiceInfoVM.getPassword(),
            String.class);
        // complete human task
        if (score != null) {
            String body = score.getBody();
            if (body != null) {
                Map<String, Object> param = jsonStringToHashMap(body);
                Long examResult = Long.valueOf(param.get("score").toString());
                if (examResult != null) {
                    // status
                    List<String> status = new ArrayList<>();
                    status.add("Ready");
                    List<ProcessInstance> processInstanceList = operations.findProcessInstances(webServiceInfoVM.getSnapshotId(), 0, 10);
                    if (processInstanceList != null && processInstanceList.size() > 0) {
                        for (ProcessInstance processInstance : processInstanceList) {
                            processInstanceId = processInstance.getId();
                            // Get Rest Call..
                            List<TaskSummary> taskSummaryList = operations.findTasksByStatusByProcessInstanceId(processInstanceId, status, 0, 10);
                            if (taskSummaryList != null && taskSummaryList.size() > 0) {
                                for (TaskSummary summary : taskSummaryList) {
                                    // taskVM
                                    BpmActiveTaskVM taskVM = new BpmActiveTaskVM();
                                    taskVM.setAssignedTo(summary.getActualOwner());
                                    taskVM.setAssignedToDisplayName(summary.getCreatedBy());
                                    taskVM.setTaskId(summary.getId());
                                    taskVM.setStatus(summary.getStatus());
                                    taskVM.setOwner(summary.getActualOwner());
                                    taskVM.setName(summary.getName());
                                    taskVM.setDueTime(LocalDateTime.ofInstant(summary.getActivationTime().toInstant(),
                                        ZoneId.systemDefault()));
                                    if (summary.getContainerId().equals(webServiceInfoVM.getSnapshotId())
                                        && summary.getProcessId().equals(webServiceInfoVM.getBusinessProcessDefinitionId())) {
                                        completeHumanTask(summary.getId(), param, webServiceInfoVM.getSnapshotId() , webServiceInfoVM.getUserName(), webServiceInfoVM.getPassword());
                                        if (examResult >= 50) {
                                            taskVM.setAssignedToType("ناجح");
                                        } else {
                                            taskVM.setAssignedToType("راسب");
                                        }
                                    }
                                    // taskVMList
                                    taskVMList.add(taskVM);
                                }
                            }
                        }
                    }
                }
            }
        }
        // taskDataVM
        BpmActiveTaskDataVM taskDataVM = new BpmActiveTaskDataVM();
        taskDataVM.setTasks(taskVMList);
        // taskInfoVM
        BpmActiveTaskInfoVM taskInfoVM = new BpmActiveTaskInfoVM();
        taskInfoVM.setData(taskDataVM);

        ResponseEntity<BpmActiveTaskInfoVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(taskInfoVM);
        return responseEntity;
    }


    /**
     * complete Human Task
     *
     * @param taskId
     * @param param
     * @param containerId
     * @param userId
     * @param password
     * @return
     */
    public ResponseEntity<FinishBpmTaskResponseVM> completeHumanTask(Long taskId, Map param, String containerId, String userId, String password) {
        // responseVM
        FinishBpmTaskResponseVM responseVM = new FinishBpmTaskResponseVM();
        responseVM.setStatusCode(new Long(1));

        operations.startTask(containerId, taskId, userId);
        operations.completeTask(containerId, taskId, userId, param);

        ResponseEntity<FinishBpmTaskResponseVM> responseEntity = ResponseEntity.ok()
            .header(userId, password)
            .body(responseVM);
        return responseEntity;
    }

}
