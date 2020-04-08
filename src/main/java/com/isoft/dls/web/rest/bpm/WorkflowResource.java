/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 8:57 AM  - File created.
 */

package com.isoft.dls.web.rest.bpm;

import com.isoft.dls.bpm.BusinessProcess;
import com.isoft.dls.bpm.BusinessProcessClient;
import com.isoft.dls.bpm.BusinessProcessKieClient;
import com.isoft.dls.common.errors.SystemException;
import com.isoft.dls.common.util.CommonUtil;
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.service.ApplicationService;
import com.isoft.dls.service.SysDomainValueService;
import com.isoft.dls.service.dto.ApplicationDTO;
import com.isoft.dls.web.rest.errors.ValidationException;
import com.isoft.dls.web.rest.errors.ValidationType;
import com.isoft.dls.web.rest.vm.bpm.*;
import com.isoft.dls.web.rest.vm.ntf.CustomerApplicationInfoVM;
import com.isoft.dls.web.rest.vm.trn.ApplicationCustomerInfoVM;
import com.isoft.dls.web.rest.vm.trn.ProcessInstanceApplicationVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

//
//import com.isoft.dls.proxy.rest.feign.prd.LearningFileRestProxy;
//import com.isoft.dls.proxy.rest.feign.sdm.LicenseCategoryRestProxy;
//import com.isoft.dls.proxy.rest.feign.trf.EyeTestResultRestProxy;
//import com.isoft.dls.proxy.rest.feign.trf.LicenseApplicationViewRestProxy;
//import com.isoft.dls.proxy.rest.feign.trn.ApplicationRestProxy;
//import com.isoft.dls.proxy.rest.feign.trn.ServiceRequestRestProxy;
//import com.isoft.dls.proxy.rest.template.bpm.BusinessProcess;
//import com.isoft.dls.proxy.rest.template.bpm.BusinessProcessClient;
//import com.isoft.dls.proxy.rest.template.bpm.BusinessProcessKieClient;
//import com.isoft.dls.proxy.rest.template.trf.TrafficTransactionClient;
//import com.isoft.dls.service.dto.trn.PaymentRequestDTO;
//import com.isoft.dls.service.dto.um.CustomerDTO;
//import com.isoft.dls.service.sys.DomainValueService;
//import com.isoft.dls.service.trn.PaymentRequestService;
//import com.isoft.dls.service.um.CustomerService;
//import com.isoft.dls.web.rest.errors.ApplicationNotFoundException;
//import com.isoft.dls.web.rest.errors.CustomerNotFoundException;
//import com.isoft.dls.web.rest.vm.trf.*;
//import com.isoft.dls.web.rest.vm.trf.ServiceVM;
//import com.isoft.dls.web.rest.vm.uts.LicenseCategoryVM;

/**
 * REST controller for managing BpmRestLog.
 * @Auther Tariq Abu Amireh
 */
@RestController
@RequestMapping("/api")
public class WorkflowResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowResource.class);


    @Autowired
    private SysDomainValueService domainValueService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private BusinessProcessKieClient kieClient;

    @Autowired
    private BusinessProcessClient ibmClient;

    private BusinessProcess businessProcessClient;

    @Value("#{environment['bpm.context.clientType']}")
    private String clientType;

    private static final String ENTITY_NAME = "amanDlsmsApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostConstruct
    public void init() {
        if(clientType.equals("IBM")){
            this.businessProcessClient = ibmClient;
        } else {
            this.businessProcessClient = kieClient;
        }
    }

    /**
     * GET  /bpm-rest-logs : Create a new brmRestLog.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/active-task/{applicationId}")
    public ResponseEntity<BpmActiveTaskInfoVM> activeTask(@PathVariable Long applicationId) {
        return businessProcessClient.getActiveTask(applicationId);
    }

    /**
     * GET  /bpm-rest-logs : Create a new brmRestLog.
     *
     * @param userInboxSearchRequestVM : User Inbox Search Request VM
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-inbox")
    public ResponseEntity<BpmUserInboxInfoVM> searchUserInbox(@RequestBody UserInboxSearchRequestVM userInboxSearchRequestVM) {
        log.debug("REST request to userInboxSearchRequestVM : {} ", userInboxSearchRequestVM);

        // Get process tasks inbox for current user.

        ResponseEntity<BpmUserInboxVM> bpmUserInboxResponse = businessProcessClient.getUserInbox(
                userInboxSearchRequestVM.getPageSize()+50,
                userInboxSearchRequestVM.getOffset());

        BpmUserInboxVM bpmUserInboxVM = bpmUserInboxResponse.getBody();

        // Retrieve the related application for each process instance..
        BpmUserInboxInfoVM bpmUserInboxInfoVM = new BpmUserInboxInfoVM();

        if (bpmUserInboxVM == null || bpmUserInboxVM.getData() == null || bpmUserInboxVM.getData().getDataList() == null) {
            return new ResponseEntity<>(bpmUserInboxInfoVM, OK);
        }

        // Filter Processes..
        List<ProcessInstanceApplicationVM> applications =
                filterProcesses(bpmUserInboxVM.getData().getDataList(),userInboxSearchRequestVM);

        bpmUserInboxInfoVM.setOffset(bpmUserInboxVM.getData().getOffset());
        bpmUserInboxInfoVM.setTotalCount(bpmUserInboxVM.getData().getTotalCount());
        bpmUserInboxInfoVM.setRequestedSize(userInboxSearchRequestVM.getPageSize());
        bpmUserInboxInfoVM.setRetrivedSize(applications.size());
        bpmUserInboxInfoVM.setApplications(applications);

        return new ResponseEntity<>(bpmUserInboxInfoVM, OK);
    }

    /**
     * Filter Processes.
     *
     * @param dataList Data list
     * @return List of process instances application VM
     */
    private List<ProcessInstanceApplicationVM> filterProcesses(List<BpmUserInboxDataVM> dataList,UserInboxSearchRequestVM userInboxSearchRequestVM) {
        List<ProcessInstanceApplicationVM> applications = new ArrayList<>();

        for (BpmUserInboxDataVM bpmUserInboxDataVM : dataList) {

            checkBpmUserInboxDataMandatoryFields(bpmUserInboxDataVM);

            Long taskId = bpmUserInboxDataVM.getTaskId();
            BpmUserInboxAssigneeInfoVM bpmUserInboxAssigneeInfoVM = bpmUserInboxDataVM.getTaskAssignedTo();

            ProcessInstanceAppInfoRequestVM processInstanceAppInfoRequestVM = new ProcessInstanceAppInfoRequestVM();
            processInstanceAppInfoRequestVM.setProcessInstanceId(bpmUserInboxDataVM.getInstanceId());
            processInstanceAppInfoRequestVM.setApplicationDateFrom(userInboxSearchRequestVM.getApplicationDateFrom());
            processInstanceAppInfoRequestVM.setApplicationDateTo(userInboxSearchRequestVM.getApplicationDateTo());
            processInstanceAppInfoRequestVM.setEmiratesId(userInboxSearchRequestVM.getEmiratesId());
            processInstanceAppInfoRequestVM.setLicenseCategoryCode(userInboxSearchRequestVM.getLicenseCategoryCode());
            processInstanceAppInfoRequestVM.setMobileNo(userInboxSearchRequestVM.getMobileNo());
            processInstanceAppInfoRequestVM.setStatus(userInboxSearchRequestVM.getStatus());
            processInstanceAppInfoRequestVM.setApplicationReferenceNo(userInboxSearchRequestVM.getApplicationReferenceNo());
            processInstanceAppInfoRequestVM.setPageSize(userInboxSearchRequestVM.getPageSize());
            processInstanceAppInfoRequestVM.setOffset(userInboxSearchRequestVM.getOffset());

            // Get related application info..
            ResponseEntity<ProcessInstanceApplicationVM> processInstanceAppResponseEntity = null;//applicationService.getProcessInstanceApplicationByInfo(processInstanceAppInfoRequestVM);
            if(processInstanceAppResponseEntity == null || processInstanceAppResponseEntity.getBody() == null) {
                continue;
            }
            ProcessInstanceApplicationVM application = processInstanceAppResponseEntity.getBody();
            if(application.getApplicationCriteria() == null) {
                continue;
            }
            application.setCustomerInfo(getApplicationCustomerInfoVM(application));

//            if (application.getActivePhase() != null &&
//                ! PhaseType.DRIVING_LEARNING_FILE_DS_AUDIT.value().equals(application.getActivePhase().value())) {
//
//                application.setApplicationFeesList(getApplicationFees(application.getId()));
//            }

            if(bpmUserInboxAssigneeInfoVM != null
                    && StringUtil.isNotBlank(bpmUserInboxAssigneeInfoVM.getType())
                    && bpmUserInboxAssigneeInfoVM.getType().equalsIgnoreCase("user")) {
                application.setAssigned(true);
            }

            application.setTaskId(taskId);
            applications.add(application);

            if(applications.size() >= userInboxSearchRequestVM.getPageSize()) {
                break;
            }
        }

        return applications;
    }

    /**
     * Check Bpm User Inbox Data Mandatory Fields
     * @param bpmUserInboxDataVM
     */
    private void checkBpmUserInboxDataMandatoryFields(BpmUserInboxDataVM bpmUserInboxDataVM) {

        if(bpmUserInboxDataVM.getInstanceId() == null) {
            throw new SystemException("Invalid process Instance Id : {} ", bpmUserInboxDataVM.getInstanceId());
        }
        if(bpmUserInboxDataVM.getTaskId() == null) {
            throw new SystemException("Invalid process task Id. Process Id : {} / Task Id : {} ", bpmUserInboxDataVM.getInstanceId(),bpmUserInboxDataVM.getInstanceId());
        }
    }

    /**
     * Get gender info
     *
     * @param application Process instance application VM
     * @return Customer gender info
     */
    private ApplicationCustomerInfoVM getApplicationCustomerInfoVM(ProcessInstanceApplicationVM application) {

//        CustomerDTO customerDto = null;
//        ApplicationCustomerInfoVM applicationCustomerInfoVM = new ApplicationCustomerInfoVM();
//
//        if(StringUtil.isNotBlank(application.getApplicationCriteria().getEidNumber())) {
//            Optional<CustomerDTO> customer = customerService.findByEidNumber(application.getApplicationCriteria().getEidNumber());
//            if(!customer.isPresent()) {
//                return null;
//            }
//            customerDto = customer.get();
//        } else if(StringUtil.isNotBlank(application.getApplicationCriteria().getPassportNo())
//                && application.getApplicationCriteria().getPassportExpiryDate() != null
//                && application.getApplicationCriteria().getBirthdate() != null) {
//
//            Optional<CustomerDTO> customer = customerService.findByPassportInfo(application.getApplicationCriteria().getPassportNo(),
//                    application.getApplicationCriteria().getNationalityCode(),
//                    application.getApplicationCriteria().getBirthdate());
//
//            if(!customer.isPresent()) {
//                return null;
//            }
//            customerDto = customer.get();
//        }
//
//        if(customerDto == null){
//            return null;
//        }
//
//        // Set User Profile info, gender, profession....
//        setUserProfileInfo(customerDto, applicationCustomerInfoVM);
//
//        if(StringUtil.isNotBlank(customerDto.getArabicSponsorName())){
//            applicationCustomerInfoVM.setArabicSponsorName(customerDto.getArabicSponsorName());
//        }
//        if(StringUtil.isNotBlank(customerDto.getEnglishSponsorName())){
//            applicationCustomerInfoVM.setEnglishSponsorName(customerDto.getEnglishSponsorName());
//        }
//
//        return applicationCustomerInfoVM;
        return null;
    }

//    /**
//     * Set User Profile Profile Info
//     * @param customerDto
//     */
//    private void setUserProfileInfo(CustomerDTO customerDto, ApplicationCustomerInfoVM applicationCustomerInfoVM) {
//
//        if(customerDto.getUserProfile() != null ){
//            if(customerDto.getUserProfile().getGender() != null){
//                applicationCustomerInfoVM.setGender(customerDto.getUserProfile().getGender());
//            }
//            if(StringUtil.isNotBlank(customerDto.getUserProfile().getGender().value())){
//                applicationCustomerInfoVM.setGenderDescription(domainValueService.getDomainValue(customerDto.getUserProfile().getGender().value(), Gender.DOMAIN_CODE));
//            }
//            if(StringUtil.isNotBlank(customerDto.getUserProfile().getProfessionCode())){
//                applicationCustomerInfoVM.setProfessionCode(customerDto.getUserProfile().getProfessionCode());
//            }
//            if(customerDto.getUserProfile().getProfession() != null && customerDto.getUserProfile().getProfession().getName() != null){
//                applicationCustomerInfoVM.setProfessionDescription(customerDto.getUserProfile().getProfession().getName());
//            }
//            if(StringUtil.isNotBlank(customerDto.getUserProfile().getEmail())){
//                applicationCustomerInfoVM.setEmail(customerDto.getUserProfile().getEmail());
//            }
//        }
//    }

    /**
     * GET  /bpm-rest-logs : Create a new brmRestLog.
     *
     * @param applicationReferenceNo : Application reference no
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/initiate-process/{applicationReferenceNo}")
    public ResponseEntity<LearningFileInitiationResponseVM> startLearningFileProcess(@PathVariable Long applicationReferenceNo) throws URISyntaxException {
        log.debug("REST request to userInbox : {} ", applicationReferenceNo);

        LearningFileInitiationRequestVM vm = new LearningFileInitiationRequestVM();
        vm.setApplicationReferenceNo(applicationReferenceNo);
        return businessProcessClient.startLearningFileProcess(vm);
    }

    /*
     * POST  /driving-school-action : Driving School Action.
     *
     * @param applicationVerificationRequestVM : Application Verification Request VM
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/driving-school-action")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\")")
    public ResponseEntity<FinishBpmTaskResponseVM> drivingSchoolAction(@Valid @RequestBody ApplicationVerificationRequestVM applicationVerificationRequestVM) {
        log.debug("REST request to userInbox : {} ", applicationVerificationRequestVM);

        // Verify application..
        return businessProcessClient.drivingSchoolAction(applicationVerificationRequestVM);
    }

    /* POST  /accept-contract : Accept Contract.
     *
     * @param applicationVerificationRequestVM : Application Verification Request VM
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accept-contract")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ResponseEntity<FinishBpmTaskResponseVM> acceptContract(@Valid @RequestBody ContractAcceptanceVM contractAcceptanceVM) {
        log.debug("REST request to accept contract ");

        // Get customer application by application Id
//        ResponseEntity<CustomerApplicationSummaryVM> customerApplication =
//                                applicationRestProxy.getApplicationCriteriaByApplicationId(contractAcceptanceVM.getTaskId());

//        if (customerApplication == null ||
//            customerApplication.getBody() == null ||
//            customerApplication.getBody().getApplicationCriteria() == null ||
//            StringUtil.isBlank(customerApplication.getBody().getApplicationCriteria().getEidNumber())) {
//
//            throw new SystemException("Application with id {} not found" , contractAcceptanceVM.getTaskId());
//        }
//
//        String emiratesId = customerApplication.getBody().getApplicationCriteria().getEidNumber();
//        ResponseEntity<CustomerProfileSummaryVM> activeLearningFile = learningFileRestProxy.getActiveLearningFile(emiratesId);
//
//        if (activeLearningFile != null && activeLearningFile.getBody() != null && activeLearningFile.getBody().getApplicationId() != null) {
//            //Has active learning file
//            throw new ValidationException("error.customerDemographic.hasActiveLearningFile");
//        }
//
////        Check if customer has valid eye test result or not by EID_NUMBER
//        ResponseEntity<EyeTestResultVM> eyeTestResultResponse = eyeTestResultRestProxy.getEyeTestResult(emiratesId);
//        if (eyeTestResultResponse == null || eyeTestResultResponse.getBody() == null || eyeTestResultResponse.getBody().getTestDate() == null) {
//            throw new ValidationException("error.customerDemographic.withoutValidEyeTest",ValidationType.WARNING);
//        }
//
//        //. Check if customer already has an under processing license application in Dubai
//        ResponseEntity<Void> hasUnderProcessingLicenseApplicationInETraffic = licenseApplicationViewRestProxy.hasUnderProcessingLicenseApplicationByEmiratesId(emiratesId);
//        if (hasUnderProcessingLicenseApplicationInETraffic.getStatusCodeValue() == HttpStatus.OK.value()) {
//            throw new ValidationException("error.LearningFile.alreadyFoundUnderProcessingLicenseApplication");
//        }
//
//        // Verify application..
//        return businessProcessClient.acceptContract(contractAcceptanceVM.getTaskId());
        return null;
    }

    /**
     * POST  /payment-clearance : Payment clearance.
     *
     * @param paymentCollectionRequestVM : Application payment clearance request VM
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-collection")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\")")
    public ResponseEntity<FinishBpmTaskResponseVM> paymentCollection(@Valid @RequestBody PaymentCollectionRequestVM paymentCollectionRequestVM) {
        log.debug("REST request to payment clearance : {} ", paymentCollectionRequestVM);

        // Payment collection..
        return businessProcessClient.paymentCollection(paymentCollectionRequestVM);
    }

    /**
     * POST  /finish-task : Finish Task.
     *
     * @param finishTaskRequestVM : Received finish task request VM
     * @return the ResponseEntity with body the finish bpm task response VM
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/finish-task")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<FinishBpmTaskResponseVM> finishTask(@Valid @RequestBody FinishTaskRequestVM finishTaskRequestVM) throws URISyntaxException {
        // Finish Task..
        return businessProcessClient.finishTask(finishTaskRequestVM.getTaskId(),null);
    }


    /**
     * POST  /resend-task : Resend Task.
     *
     * @param finishTaskRequestVM : Received finish task request VM
     * @return the ResponseEntity with body the finish bpm task response VM
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resend-task")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ResponseEntity<FinishBpmTaskResponseVM> resendTask(@Valid @RequestBody FinishTaskRequestVM finishTaskRequestVM) throws URISyntaxException {
        // Finish Task..
        return businessProcessClient.resendTask(finishTaskRequestVM.getTaskId(),null);
    }


    /**
     * POST  /process-details : Process details.
     *
     * @param processId : Process instance id
     * @return the ResponseEntity with body the business process details VM
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/process/details/{processId}")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\")")
    public ResponseEntity<BusinessProcessDetailsVM> processDetails(@PathVariable Long processId) throws URISyntaxException {
        // Get Process Details..
        return businessProcessClient.getProcessDetails(processId);
    }

    /**
     * POST  /task/claim : Task claim.
     *
     * @param claimBpmTaskRequestVM : Claim bpm task request VM
     * @return the ResponseEntity with body the business process details VM
     */
    @PostMapping("/task/claim")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\")")
    public ResponseEntity<ClaimBpmTaskResponseVM> claimTask(@Valid @RequestBody ClaimBpmTaskRequestVM claimBpmTaskRequestVM) {

        ResponseEntity<BpmTaskDetailsVM> taskDetailsResponse = businessProcessClient.getTaskDetails(claimBpmTaskRequestVM.getTaskId());

        if (taskDetailsResponse == null ||
            taskDetailsResponse.getBody() == null ||
            taskDetailsResponse.getBody().getTaskDetailsDataVM() == null) {
            throw new SystemException("BPM Task with id {} not found" , claimBpmTaskRequestVM.getTaskId());
        }

        String assignedToType = taskDetailsResponse.getBody().getTaskDetailsDataVM().getAssignedToType();
        if (StringUtil.isNotBlank(assignedToType) && "user".equalsIgnoreCase(assignedToType)) {
            throw new ValidationException("error.bpm.taskIsClaimed", ValidationType.WARNING);
        }

        // Claim task..
        return businessProcessClient.claimTask(claimBpmTaskRequestVM.getTaskId());
    }

    /**
     * POST  /task/cancel-claim : Task cancel claim.
     *
     * @param claimBpmTaskRequestVM : Claim bpm task request VM
     * @return the ResponseEntity with body the business process details VM
     */
    @PostMapping("/task/cancel-claim")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.DRIVING_SCHOOL_USER_ROLE + "\")")
    public ResponseEntity<Void> cancelClaimTask(@Valid @RequestBody ClaimBpmTaskRequestVM claimBpmTaskRequestVM) {
        // Cancel Claim task..

        return businessProcessClient.cancelClaimTask(claimBpmTaskRequestVM.getTaskId());
    }

    /**
     * Post  /create-payment-request : Create payment request.
     *
     * @param applicationReferenceNo
     * @return the ResponseEntity with status 200 (Created) with status 404 (Not Found) if there's no data
     */
    @PostMapping("/create-payment-request/{applicationReferenceNo}")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<Void> createPaymentRequest(@PathVariable Long applicationReferenceNo) {

        log.debug("REST request to createPaymentRequest applicationReferenceNo : {}", applicationReferenceNo);

        // if there's payment request(transaction paid), then skip this API
//        Optional<PaymentRequestDTO> paymentRequest = paymentRequestService.findByApplicationId(applicationReferenceNo);
//
//        // Check if the transaction already created and paid
//        if(paymentRequest.isPresent()) {
//            return new ResponseEntity<>( HttpStatus.OK );
//        }
//
//        /* ******************************************************************* */
//        /* ************* Retrieve verified and lock service request*********** */
//        /* ******************************************************************* */
//
//        ResponseEntity<CreatePaymentRequestVM> paymentRequestVM = getVerifiedAndLockedServiceRequest(applicationReferenceNo);
//
//        if(paymentRequestVM.getBody() == null ||
//            paymentRequestVM.getBody().getServiceRequests() == null ||
//            paymentRequestVM.getBody().getServiceRequests().isEmpty()) {
//            throw new SystemException("There is no verified  service Request for provided application: " + applicationReferenceNo);
//        }
//
//        paymentRequestVM.getBody().getServiceRequests().forEach(serviceRequest -> {
//
//            if(!ServiceRequestStatus.VERIFIED_AND_LOCKED.value().equals(serviceRequest.getStatus().value())) {
//                throw new SystemException("Service Request must be verified for provided application: " + applicationReferenceNo);
//            }
//        });
//
//        /* ******************************************************************* */
//        /* ************ Create Transaction Service *************************** */
//        /* ******************************************************************* */
//
//        // Get Customer by eid number if exists
//        CustomerDTO customer = getCustomer(paymentRequestVM.getBody().getApplicationCriteria().getEidNumber());
//
//        TrafficTransactionFeesVM trafficTransactionFeesVM = fillPaymentInfo(paymentRequestVM.getBody(), customer);
//
//        ResponseEntity<TrafficTransactionFeesVM> paymentResponse = trafficTransactionClient.payTransactionByEwallet(trafficTransactionFeesVM);
//
//        if(paymentResponse == null || paymentResponse.getBody() == null ||
//            StringUtil.isBlank(paymentResponse.getBody().getPaymentStatus()) ||
//            paymentResponse.getBody().getTransactionId() == null ||
//            !paymentResponse.getBody().getPaymentStatus().equalsIgnoreCase("CONFIRMED")) {
//
//            if (paymentResponse != null && paymentResponse.getBody() != null && paymentResponse.getBody().getViolations() != null
//                && !paymentResponse.getBody().getViolations().isEmpty()) {
//
//                throw new SystemException(paymentResponse.getBody().getViolations().get(0).getDescriptionEn());
//            } else {
//                throw new SystemException("An error occurred while pay the service in e-traffic system");
//            }
//        }
//
//        /* ******************************************************************* */
//        /* ************ Insert payment request record for the paid  requests ** */
//        /* ******************************************************************* */
//
//        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
//        paymentRequestDTO.setApplicationId(paymentRequestVM.getBody().getApplicationId());
//        paymentRequestDTO.setPaymentReference(paymentResponse.getBody().getTransactionId());
//        paymentRequestDTO.setPaymentDate(paymentResponse.getBody().getPaymentDate());
//        paymentRequestDTO.setPaymentMethod(paymentResponse.getBody().getPaymentType());
//        paymentRequestService.save(paymentRequestDTO);
//
//        // Update Tcn Fees Collected to be true
//        customer.setTcnFeesCollected(Boolean.TRUE);
//        customerService.save(customer);

        return new ResponseEntity<>( HttpStatus.OK );
    }

    /**
     * Post  /confirm-service-request : confirm service request.
     *
     * @param applicationReferenceNo
     * @return the ResponseEntity with status 200 (Created) with status 404 (Not Found) if there's no data
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/confirm-service-request/{applicationReferenceNo}")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<Void> confirmServiceRequest(@PathVariable Long applicationReferenceNo) {

        log.debug("REST request to confirmServiceRequest applicationReferenceNo : {}", applicationReferenceNo);

        /* ******************************************************************* */
        /* ********** Retrieve verified and locked service request *********** */
        /* ******************************************************************* */

//        ResponseEntity<CreatePaymentRequestVM> verifiedServiceRequest = getVerifiedAndLockedServiceRequest(applicationReferenceNo);
//
//        if(verifiedServiceRequest == null || verifiedServiceRequest.getBody() == null ||
//            verifiedServiceRequest.getBody().getServiceRequests() == null ||
//            verifiedServiceRequest.getBody().getServiceRequests().isEmpty()) {
//            return new ResponseEntity<>( HttpStatus.OK );
//        }

        // Get Customer by eid number if exists
//        CustomerDTO customerDTO = getCustomer(verifiedServiceRequest.getBody().getApplicationCriteria().getEidNumber());
//
//        CustomerInfoVM customerInfoVM = new CustomerInfoVM(customerDTO);
//
//        // if there's payment request(transaction paid), then skip this API
//        Optional<PaymentRequestDTO> paymentRequest = paymentRequestService.findByApplicationId(applicationReferenceNo);
//
//        // Check if the transaction already created and paid
//        if(!paymentRequest.isPresent()) {
//            throw new SystemException("No payment request found for provided application: " + applicationReferenceNo);
//        }
//
//        customerInfoVM.setPaymentReference(paymentRequest.get().getPaymentReference());
//        customerInfoVM.setPaymentMethod(paymentRequest.get().getPaymentMethod());
//        customerInfoVM.setPaymentDate(paymentRequest.get().getPaymentDate());
//        // Confirm all verified service requests
//        serviceRequestRestProxy.confirmAllVerifiedServiceRequests(customerInfoVM, applicationReferenceNo);

        return new ResponseEntity<>( HttpStatus.OK );
    }

    /**
     * Post  /create-traffic-file/ : Create Traffic File.
     *
     * @param applicationReferenceNo : Application reference no
     * @return the ResponseEntity with status 200 (Created) with status 404 (Not Found) if there's no data
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-traffic-file/{applicationReferenceNo}")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.BPM_ROLE+ "\")")
    public ResponseEntity<Void> createTrafficFile(@PathVariable Long applicationReferenceNo) throws URISyntaxException {

        log.debug("REST request to createTrafficFile applicationReferenceNo : {}", applicationReferenceNo);

        /* ******************************************************************* */
        /* ************ Create Traffic File *************************** */
        /* ******************************************************************* */

        // If the traffic file code exists on customer entity then skip
//        CustomerDTO customer = getCustomer(applicationReferenceNo);
//
//        if(customer.getTrafficCodeNo() != null) {
//            return new ResponseEntity<>( HttpStatus.OK );
//        }
//
//        TrafficFileVM trafficFileVM = getCreateTrafficFileVM(applicationReferenceNo);
//
//        ResponseEntity<TrafficFileVM> trafficFileResponse = trafficTransactionClient.createTrafficFile(trafficFileVM);
//
//        if(trafficFileResponse == null || trafficFileResponse.getBody() == null ||
//                trafficFileResponse.getBody().getTrafficNo() == null) {
//
//            if (trafficFileResponse != null && trafficFileResponse.getBody() != null && trafficFileResponse.getBody().getViolations() != null
//                && !trafficFileResponse.getBody().getViolations().isEmpty()) {
//                throw new SystemException(trafficFileResponse.getBody().getViolations().get(0).getDescriptionEn());
//            } else {
//                throw new SystemException("An error occurred while creating traffic file in e-traffic system");
//            }
//        }
//
//        // Update Customer record with new traffic file number, and update TCN Fees Collected column
//        customer.setTrafficCodeNo(trafficFileResponse.getBody().getTrafficNo());
//        customer.setIssuedByApplicationNo(applicationReferenceNo);
//        customerService.save(customer);

        return new ResponseEntity<>( HttpStatus.OK );
    }

    /**
     * Fill Payment info
     * @param createPaymentRequestVM
     * @return CollectTransactionFeesVM
     */
//    private TrafficTransactionFeesVM fillPaymentInfo(CreatePaymentRequestVM createPaymentRequestVM, CustomerDTO customerDTO) {
//
//        TrafficTransactionFeesVM trafficTransactionFeesVM = new TrafficTransactionFeesVM();
//
//        //Set Application Id
//        trafficTransactionFeesVM.setDlsApplicationId(createPaymentRequestVM.getApplicationId());
//
//        // Set corporate id (corporate trade license number)
//        if(createPaymentRequestVM.getApplicationCriteria() != null && createPaymentRequestVM.getApplicationCriteria().getCorporate() != null) {
//            trafficTransactionFeesVM.setDlsCorporateId(NumberUtil.toLong(createPaymentRequestVM.getApplicationCriteria().getCorporate().getTradeLicenseNo()));
//        }
//
//        trafficTransactionFeesVM.setArabicCustomer(createPaymentRequestVM.getArabicCustomerName());
//        trafficTransactionFeesVM.setEnglishCustomer(createPaymentRequestVM.getEnglishCustomerName());
//        trafficTransactionFeesVM.setTrafficCodeNo(customerDTO.getTrafficCodeNo());
//
//        // Set Service Details
//        List<ServiceVM> serviceVMList = new ArrayList<>();
//        createPaymentRequestVM.getServiceRequests().forEach(serviceRequest -> {
//
//            if(serviceRequest.getFeeDocument() == null || serviceRequest.getFeeDocument().getFeesListVM() == null ||
//                    serviceRequest.getFeeDocument().getFeesListVM().getList() == null ||
//                    serviceRequest.getFeeDocument().getFeesListVM().getList().isEmpty()) {
//                return ;
//            }
//
//            // Set service details
//            ServiceVM serviceVM = new ServiceVM();
//            serviceVM.setServiceRequestId(StringUtil.getString(serviceRequest.getId()));
//            serviceVM.setServiceCode(serviceRequest.getServiceCode());
//            serviceVM.setServiceNameAr(serviceRequest.getServiceDetails().getName().getAr());
//            serviceVM.setServiceNameEn(serviceRequest.getServiceDetails().getName().getEn());
//            if(serviceRequest.getServiceCode().equals(ServiceCode.OPEN_DRIVING_LEARNING_FILE.value())) {
//                serviceVM.setMain(Boolean.TRUE);
//            }
//            serviceVM.setServiceFeeVM(getServiceFee(serviceRequest.getFeeDocument().getFeesListVM()));
//
//            serviceVMList.add(serviceVM);
//        });
//
//        trafficTransactionFeesVM.setServiceVM(serviceVMList);
//
//        return trafficTransactionFeesVM;
//    }

    /**
     * Get Service Fee
     * @param feesListVM
     * @return list of service Fees VM
     */
//    private List<ServiceFeeVM> getServiceFee(FeesListVM feesListVM) {
//
//        List<ServiceFeeVM> list = new ArrayList<>();
//
//        feesListVM.getList().forEach(fee -> {
//
//            ServiceFeeVM serviceFeeVM = new ServiceFeeVM();
//            serviceFeeVM.setFeeCode(fee.getCode());
//            serviceFeeVM.setFeeNameAr(fee.getDescriptionAr());
//            serviceFeeVM.setFeeNameEn(fee.getDescriptionEn());
//            serviceFeeVM.setFeeAmount(NumberUtil.createDouble(StringUtil.getString(fee.getAmount())));
//            serviceFeeVM.setExempted(fee.getExempted());
//
//            list.add(serviceFeeVM);
//        });
//
//        return list;
//    }

    /**
     * Get create traffic file VM
     * @param applicationId
     * @return CreateTrafficFileVM
     */
//    private TrafficFileVM getCreateTrafficFileVM(Long applicationId){
//
//        CustomerDTO customerDTO = getCustomer(applicationId);
//
//        ResponseEntity<CustomerApplicationVM> customerApplicationVM = applicationRestProxy.getCustomerApplication(applicationId);
//
//        if(customerApplicationVM == null || customerApplicationVM.getBody() == null) {
//            throw new SystemException("No customer exists for provided application: " + applicationId);
//        }
//
//        TrafficFileVM trafficFileVM = new TrafficFileVM();
//
//        trafficFileVM.setDlsApplicationId(applicationId);
//
//        if(customerApplicationVM.getBody().getApplicationCriteria() != null &&
//            customerApplicationVM.getBody().getApplicationCriteria().getCorporate() != null) {
//
//            trafficFileVM.setDlsCorporateId(NumberUtil.toLong(customerApplicationVM.getBody().getApplicationCriteria().
//                getCorporate().getTradeLicenseNo()));
//        }
//        trafficFileVM.setBirthDate(DateUtil.formatDateTime(customerDTO.getBirthdate()));
//        trafficFileVM.setPassportNo(customerDTO.getPassportNo());
//        trafficFileVM.setPassportExpiryDate(DateUtil.formatDateTime(customerDTO.getPassportExpiryDate()));
//        if(customerDTO.getImmigrationFileType() != null) {
//            trafficFileVM.setImmigrationFileType(customerDTO.getImmigrationFileType().value());
//        }
//
//
//        trafficFileVM.setResidencyNo(customerDTO.getResidencyNo());
//        trafficFileVM.setResidencyExpiryDate(DateUtil.formatDateTime(customerDTO.getResidencyExpiryDate()));
//        if(customerDTO.getSponsorType() != null) {
//            trafficFileVM.setSponsorType(customerDTO.getSponsorType().value());
//        }
//
//        trafficFileVM.setArabicSponsorName(customerDTO.getArabicSponsorName());
//        trafficFileVM.setEnglishSponsorName(customerDTO.getArabicSponsorName());
//        trafficFileVM.setUnifiedNo(customerDTO.getUnifiedNo());
//        String emiratesId = customerDTO.getEidNumber();
//
//        // Set ApplicationCriteriaInfo
//        setApplicationCriteriaInfo(trafficFileVM, customerApplicationVM.getBody().getApplicationCriteria());
//
//        if (!StringUtil.isBlank(emiratesId) && emiratesId.indexOf('-') != -1) {
//            emiratesId = emiratesId.replace("-", "");
//        }
//        trafficFileVM.setEidNumber(emiratesId);
//
//        trafficFileVM.setEidExpiryDate(DateUtil.formatDateTime(customerDTO.getEidExpiryDate()));
//        trafficFileVM.setEnglishFullName(customerDTO.getUserProfile().getEnglishFullName());
//        trafficFileVM.setArabicFullName(customerDTO.getUserProfile().getArabicFullName());
//        trafficFileVM.setMobileNo(NumberUtil.toLong(customerDTO.getUserProfile().getMobileNo()));
//        trafficFileVM.setEmail(customerDTO.getUserProfile().getEmail());
//
//        if(customerDTO.getUserProfile().getGender() != null) {
//            trafficFileVM.setGender(customerDTO.getUserProfile().getGender().value());
//        }
//
//        trafficFileVM.setImage(customerDTO.getUserProfile().getPhoto());
//        trafficFileVM.setImmigrationFileSourceEmirateCode(customerDTO.getResidencyStateCode());
//        trafficFileVM.setProfessionCode(customerDTO.getUserProfile().getProfessionCode());
//        trafficFileVM.setNationalityCountryCode(customerDTO.getUserProfile().getNationalityCountryCode());
//        if(!StringUtil.isBlank(customerDTO.getUserProfile().getNationalityCountryCode()) &&
//            customerDTO.getUserProfile().getNationalityCountryCode().equalsIgnoreCase("ARE")) {
//
//            trafficFileVM.setEmiCode("DXB");
//        } else {
//
//            trafficFileVM.setEmiCode(customerDTO.getResidencyStateCode());
//        }
//
//        // if there's payment request(transaction paid), then skip this API
//        Optional<PaymentRequestDTO> paymentRequest = paymentRequestService.findByApplicationId(applicationId);
//
//        // Set paid transaction id in order to updaet fee_collection_status in etraffic system
//        if(paymentRequest.isPresent()) {
//            trafficFileVM.setPaymentTrsId(paymentRequest.get().getPaymentReference());
//        }
//        return trafficFileVM;
//    }
//
//    /**
//     * Set Application Criteria Info
//     * @param trafficFileVM
//     * @param applicationCriteria
//     */
//    private void setApplicationCriteriaInfo(TrafficFileVM trafficFileVM, ApplicationCriteriaJsonType applicationCriteria) {
//
//        if(applicationCriteria != null) {
//
//            if(applicationCriteria.getCorporate() != null) {
//
//                trafficFileVM.setDlsCorporateId(NumberUtil.toLong(applicationCriteria
//                    .getCorporate().getTradeLicenseNo()));
//            }
//
//            if(!StringUtil.isBlank(applicationCriteria.getLicenseCategoryCode())) {
//
//                ResponseEntity<LicenseCategoryVM> licenseCategoryVM = licenseCategoryRestProxy.getLicenseCategoryByCode(applicationCriteria.getLicenseCategoryCode());
//
//                if(licenseCategoryVM != null && licenseCategoryVM.getBody() != null &&
//                    licenseCategoryVM.getBody().getGlobalLicenseCategoryVM() != null) {
//
//                    trafficFileVM.setDlsLicenseCategoryId(licenseCategoryVM.getBody().getGlobalLicenseCategoryVM().getId());
//                }
//            }
//        }
//    }
//
//    /**
//     * Get Customer
//     * @param applicationId
//     * @return customerDTO
//     */
//    private CustomerDTO getCustomer(Long applicationId) {
//
//        ResponseEntity<CustomerApplicationVM> customerApplicationVM = applicationRestProxy.getCustomerApplication(applicationId);
//
//        if (customerApplicationVM == null || customerApplicationVM.getBody() == null
//            || customerApplicationVM.getBody().getApplicationCriteria() == null) {
//            throw new ApplicationNotFoundException();
//        }
//
//        Optional<CustomerDTO> customer = customerService.findByEidNumber(customerApplicationVM.getBody().getApplicationCriteria().getEidNumber());
//
//        if (!customer.isPresent()) {
//            throw new CustomerNotFoundException();
//        }
//
//        return customer.get();
//    }
//
//    /**
//     * Get Customer
//     * @param emiratesId
//     * @return customerDTO
//     */
//    private CustomerDTO getCustomer(String emiratesId) {
//
//        Optional<CustomerDTO> customer = customerService.findByEidNumber(emiratesId);
//
//        if (!customer.isPresent()) {
//            throw new CustomerNotFoundException();
//        }
//
//        return customer.get();
//    }
//
//    /**
//     * Get Verified and lockec service request
//     * @param applicationId
//     * @return CreatePaymentRequestVM
//     */
//    private ResponseEntity<CreatePaymentRequestVM> getVerifiedAndLockedServiceRequest(Long applicationId) {
//
//        return serviceRequestRestProxy.getVerifiedAndLockedServiceRequest(applicationId);
//
//    }
//
//    /**
//     * Getter for application's service request fees
//     *
//     * @param applicationId
//     * @return list of fee related to application's service request
//     */
//    private List<FeeVM> getApplicationFees (Long applicationId) {
//        ResponseEntity<List<PaymentServiceRequestVM>> paymentServiceRequestVMResponseEntity = serviceRequestRestProxy.getServiceRequestByApplicationId(applicationId);
//
//        if (paymentServiceRequestVMResponseEntity == null ||
//            !paymentServiceRequestVMResponseEntity.hasBody() ||
//            paymentServiceRequestVMResponseEntity.getBody().isEmpty()) {
//
//            return Collections.emptyList();
//        }
//
//        List<FeeVM> serviceRequestFees = new ArrayList();
//        for (PaymentServiceRequestVM paymentServiceRequestVM : paymentServiceRequestVMResponseEntity.getBody()) {
//
//            if (paymentServiceRequestVM == null ||
//                paymentServiceRequestVM.getFeeDocument() == null ||
//                paymentServiceRequestVM.getFeeDocument().getFeesListVM() == null ||
//                paymentServiceRequestVM.getFeeDocument().getFeesListVM().getList() == null ||
//                paymentServiceRequestVM.getFeeDocument().getFeesListVM().getList().isEmpty()) {
//
//                continue;
//            }
//
//            Set<FeeVM> feeVMSet = paymentServiceRequestVM.getFeeDocument().getFeesListVM().getList();
//            for (FeeVM feeVM : feeVMSet) {
//                if (feeVM == null ||
//                    feeVM.getAmount() == null ||
//                    feeVM.getAmount() <= 0 ||
//                    StringUtil.isBlank(feeVM.getDescriptionAr()) ||
//                    StringUtil.isBlank(feeVM.getDescriptionEn()) ) {
//
//                    continue;
//                }
//
//                serviceRequestFees.add(feeVM);
//            }
//        }
//
//        return serviceRequestFees;
//    }

    /**
     * get Exam Result.
     *
     * @param processInstanceId
     * @return
     */
    @GetMapping("/getExamResult/{processInstanceId}")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ResponseEntity<BpmActiveTaskInfoVM> getExamResult(@PathVariable Long processInstanceId) {
        return businessProcessClient.getExamResult(processInstanceId);
    }

    /**
     * get Details.
     *
     * @param applicationReferenceNo
     * @return
     */
    @GetMapping("/getDetails/{applicationReferenceNo}")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.VERIFIED_ROLE + "\")")
    public ApplicationCriteriaJsonType getDetails(@PathVariable Long applicationReferenceNo) {
//        return businessProcessClient.getExamResult(processInstanceId);
        //----------------------------
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(applicationReferenceNo);
        if (applicationDTO == null ) {
            throw new SystemException("No Application Criteria exists for provided application: " + applicationReferenceNo);
        }
        if (applicationDTO.get().getApplicationCriteria() != null) {
            //TODO : jsonType
//            return applicationDTO.get().getApplicationCriteria();
            return null;
        }
        //----------------------------
        return null;
    }

    /**
     * GET  /bpm-rest-logs : Create a new brmRestLog.
     *
     * @param applicationReferenceNo : Application reference no
     * @param activePhase : active Phase
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("applications/{applicationReferenceNo}/activePhase/{activePhase}/updateAppPhase")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CustomerApplicationInfoVM> updateAppPhase(@PathVariable Long applicationReferenceNo, @PathVariable String activePhase) throws URISyntaxException {
        log.debug("REST request to userInbox : {} and active phase {} ", applicationReferenceNo  , activePhase);
        return businessProcessClient.updateApplicationPhaseCriteria(applicationReferenceNo, activePhase, null);
    }


    /**
     * GET  /bpm-rest-logs : Create a new brmRestLog.
     *
     * @param applicationReferenceNo : Application reference no
     * @param activePhase : active Phase
     * @param criteria
     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("applications/{applicationReferenceNo}/activePhase/{activePhase}/criteria/{criteria}/updateAppPhase")
    //@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CustomerApplicationInfoVM> updateAppPhaseCriteria(@PathVariable Long applicationReferenceNo, @PathVariable String activePhase, @PathVariable String criteria) throws URISyntaxException {
        log.debug("REST request to userInbox : {} and active phase {} and criteria {} ", applicationReferenceNo  , activePhase, criteria);
        return businessProcessClient.updateApplicationPhaseCriteria(applicationReferenceNo, activePhase, criteria);
    }

}
