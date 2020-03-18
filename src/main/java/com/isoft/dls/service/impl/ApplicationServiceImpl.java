package com.isoft.dls.service.impl;

import com.isoft.dls.common.errors.SystemException;
import com.isoft.dls.common.util.CommonUtil;
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.enumeration.ApplicationStatus;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.domain.type.MultilingualJsonType;
import com.isoft.dls.security.SecurityUtils;
import com.isoft.dls.service.ApplicationPhaseService;
import com.isoft.dls.service.ApplicationService;
import com.isoft.dls.domain.Application;
import com.isoft.dls.repository.ApplicationRepository;
import com.isoft.dls.service.dto.ApplicationDTO;
import com.isoft.dls.service.dto.ApplicationPhaseDTO;
import com.isoft.dls.service.mapper.ApplicationMapper;
import com.isoft.dls.web.rest.errors.NullValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Application}.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

//    private final SecurityUtils securityUtils;

    private final ApplicationPhaseService applicationPhaseService;

    private final CommonUtil commonUtil;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper
//        , SecurityUtils securityUtils
        , ApplicationPhaseService applicationPhaseService, CommonUtil commonUtil) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
//        this.securityUtils = securityUtils;
        this.applicationPhaseService = applicationPhaseService;
        this.commonUtil = commonUtil;
    }

    /*
     * Validate create application.
     *
     * @param applicationDTO
     */
    private void validateCreateApplication(ApplicationDTO applicationDTO) {

        if (applicationDTO == null) {
            throw new SystemException("applicationDTO cannot be null");
        }

        if (applicationDTO.getApplicationTypeId() == null) {
            throw new SystemException("application type ID cannot be null");
        }

//        if (applicationDTO.getApplicationCriteria() == null) {
//            throw new SystemException("application criteria cannot be null");
//        }

        if (applicationDTO.getId() != null) {
            throw new SystemException("application ID must not be provided");
        }

        if (applicationDTO.getStatus() != null) {
            throw new SystemException("application status must not be provided");
        }

        if (applicationDTO.getStatusDate() != null) {
            throw new SystemException("application status date must not be provided");
        }

//        if (applicationDTO.getStatusDescription() != null) {
//            throw new SystemException("application status description must not be provided");
//        }

        if (applicationDTO.getChannelCode() != null) {
            throw new SystemException("application channel code must not be provided");
        }

//        if (applicationDTO.getUserId() != null) {
//            throw new SystemException("application customer ID must not be provided");
//        }

        if (applicationDTO.getArabicCustomerName() != null) {
            throw new SystemException("application arabic customer name must not be provided");
        }

        if (applicationDTO.getEnglishCustomerName() != null) {
            throw new SystemException("application english customer name must not be provided");
        }

        if (applicationDTO.getActivePhase() != null) {
            throw new SystemException("application active phase must not be provided");
        }

        if (applicationDTO.getProcessInstanceId() != null) {
            throw new SystemException("application process instance id must not be provided");
        }

        checkCorporateInfo(applicationDTO);
        checkNextPhaseInfo(applicationDTO);
        checkUserInfo(applicationDTO);
    }

    /**
     * Check Corporate info
     *
     * @param applicationDTO Application DTO
     */
    private void checkCorporateInfo(ApplicationDTO applicationDTO) {

        if (applicationDTO.getTradeLicenseNo() != null) {
            throw new SystemException("application trade license number must not be provided");
        }

        if (applicationDTO.getArabicCorporateName() != null) {
            throw new SystemException("application arabic corporate name must not be provided");
        }

        if (applicationDTO.getEnglishCorporateName() != null) {
            throw new SystemException("application english corporate name must not be provided");
        }

    }

    /**
     * Check user info
     *
     * @param applicationDTO Application DTO
     */
    private void checkUserInfo(ApplicationDTO applicationDTO) {

        if (applicationDTO.getUserRole() != null) {
            throw new SystemException("application user role must not be provided");
        }

        if (applicationDTO.getUserType() != null) {
            throw new SystemException("application user type must not be provided");
        }

        if (applicationDTO.getUserTypeDescription() != null) {
            throw new SystemException("application user type description must not be provided");
        }

    }

    /**
     * Check Next Phase info
     *
     * @param applicationDTO Application DTO
     */
    private void checkNextPhaseInfo(ApplicationDTO applicationDTO) {

        if (applicationDTO.getConfirmedBy() != null) {
            throw new SystemException("application confirmed by must not be provided");
        }

        if (applicationDTO.getConfirmationDate() != null) {
            throw new SystemException("application confirmation date must not be provided");
        }

        if (applicationDTO.getRejectedBy() != null) {
            throw new SystemException("application rejected by must not be provided");
        }

        if (applicationDTO.getRejectionDate() != null) {
            throw new SystemException("application rejection date must not be provided");
        }

        if (applicationDTO.getRejectionReason() != null) {
            throw new SystemException("application rejection reason must not be provided");
        }

        if (applicationDTO.getServiceRequests() != null && !applicationDTO.getServiceRequests().isEmpty()) {
            throw new SystemException("application service requests must not be provided");
        }
    }

    /**
     * Validate Update Application.
     *
     * @param applicationDTO
     */
    private void validateUpdateApplication(ApplicationDTO applicationDTO) {

        if (applicationDTO == null) {
            throw new SystemException("applicationDTO cannot be null");
        }

        if (applicationDTO.getApplicationTypeId() == null) {
            throw new NullValueException("application type ID");
        }

        if (applicationDTO.getId() == null) {
            throw new NullValueException("application ID");
        }

        if (applicationDTO.getStatus() == null) {
            throw new NullValueException("application status");
        }

        if (applicationDTO.getStatusDate() == null) {
            throw new NullValueException("application status date");
        }

        if (applicationDTO.getStatusDescription() == null) {
            throw new NullValueException("application status description");
        }

        if (applicationDTO.getUserRole() == null) {
            throw new NullValueException("application user role");
        }

        if (applicationDTO.getUserType() == null) {
            throw new NullValueException("application user type");
        }

        if (applicationDTO.getUserTypeDescription() == null) {
            throw new NullValueException("application user type description");
        }

        if (applicationDTO.getChannelCode() == null) {
            throw new NullValueException("application channel code");
        }

        if (applicationDTO.getActivePhase() == null) {
            throw new NullValueException("application active phase");
        }

        if (applicationDTO.getApplicationCriteria() == null) {
            throw new NullValueException("application criteria");
        }
    }
    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApplicationDTO save(ApplicationDTO applicationDTO) {
        log.debug("Request to save Application : {}", applicationDTO);
        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);
        return applicationMapper.toDto(application);
    }

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applications");
        return applicationRepository.findAll(pageable)
            .map(applicationMapper::toDto);
    }

    /**
     * Get one application by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        return applicationRepository.findById(id)
            .map(applicationMapper::toDto);
    }

    /**
     * Delete the application by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.deleteById(id);
    }
    /**
     * Update Application Phase Criteria
     *
     * @param applicationDTO
     * @param phase : Active Phase
     * @param criteria
     * @return the persisted entity
     */
    @Override
    public ApplicationDTO updateAppPhaseCriteria(Optional<ApplicationDTO> applicationDTO, PhaseType phase, String criteria) {

        Instant now = Instant.now();
        /* ********************************************************* */
        /* ****************** Get Id By applicationDTO **************** */
        /* ********************************************************* */
        if(!applicationDTO.isPresent()) {
            return null;
        }
        ApplicationDTO appDTO = applicationDTO.get();
        Long id = applicationDTO.get().getId();

        /* ********************************************************* */
        /* ***************** Close Current Phase ******************* */
        /* ********************************************************* */
        ApplicationPhaseDTO applicationPhaseDTO = applicationPhaseService.getFirstByApplicationIdOrderByPhaseSequenceDesc(id);
        applicationPhaseDTO.setEndDate(now);
        applicationPhaseService.save(applicationPhaseDTO);

        /* ********************************************************* */
        /* ********************* Open Next Phase ******************* */
        /* ********************************************************* */
        ApplicationPhaseDTO newApplicationPhaseDTO = new ApplicationPhaseDTO();
        newApplicationPhaseDTO.setApplicationId(id);
        newApplicationPhaseDTO.setType(phase);
        newApplicationPhaseDTO.setStartDate(now);
        newApplicationPhaseDTO.setSequence(applicationPhaseDTO.getSequence()+1);
        newApplicationPhaseDTO.setPersona(applicationPhaseDTO.getPersona());

        if(phase.equals(PhaseType.HUMAN_TASK_APPROVAL) || phase.equals(PhaseType.APPLICATION_REJECTION)) {
            newApplicationPhaseDTO.setEndDate(now);
        }

        applicationPhaseService.save(newApplicationPhaseDTO);

        validateUpdateApplication(appDTO);

        /* ******************************************************************* */
        /* ********************* Update Application Phase ******************** */
        /* ******************************************************************* */

        Optional<String> user = SecurityUtils.getCurrentUserLogin();

        if(!user.isPresent()) {
            return null;
        }

        if(!phase.equals(appDTO.getActivePhase().value())) {
            appDTO.setActivePhase(phase);
            if(appDTO.getActivePhase().equals(PhaseType.HUMAN_TASK_APPROVAL)) {
                appDTO.setStatus(ApplicationStatus.COMPLETED);
                appDTO.setConfirmationDate(now);
                appDTO.setStatusDate(now);
                appDTO.setConfirmedBy(user.get());
            } else {
                appDTO.setStatus(ApplicationStatus.UNDER_PROCESSING);
                appDTO.setConfirmationDate(now);
                appDTO.setStatusDate(now);
                appDTO.setConfirmedBy(user.get());
            }
        }

        if(appDTO.getStatus() != null) {
            setAppStatusDesc(appDTO);
        }
            //TODO : jsonType
//        setAppCriteria(criteria, appDTO, appDTO.getApplicationCriteria());

        Application application = applicationMapper.toEntity(appDTO);

        application = applicationRepository.saveJsonType(application);
        ApplicationDTO dto = applicationMapper.toDto(application);
        dto.setStatusDescription(appDTO.getStatusDescription());
        dto.setUserTypeDescription(appDTO.getUserTypeDescription());
        dto.setApplicationCriteria(appDTO.getApplicationCriteria());
        return dto;
        // multilingualJsonConverter
//        String statusDesc = new MultilingualJsonConverter().convertToDatabaseColumn(appDTO.getStatusDescription());
//        if(StringUtil.isNotBlank(criteria)) {
//            // variables
//            // Create User Delivery
//            String map[] = criteria.split(",");
//            String username = map[0];
//            String password = map[1];
//            String delivery_ID = map[2];
//            String tesTakerID = map[3];
//            String validFrom = map[4];
//            String validTo = map[5];
//            // applicationCriteriaJsonConverter
//            ApplicationCriteriaJsonType applicationCriteriaJsonType = appDTO.getApplicationCriteria();
//            applicationCriteriaJsonType.setUsername(username);
//            applicationCriteriaJsonType.setPassword(password);
//            applicationCriteriaJsonType.setTesTakerID(tesTakerID);
//            applicationCriteriaJsonType.setDelivery_ID(Long.valueOf(delivery_ID));
//            applicationCriteriaJsonType.setValidFrom(LocalDate.parse(validFrom));
//            applicationCriteriaJsonType.setValidTo(LocalDate.parse(validTo));
//
//            String appCriteria = new ApplicationCriteriaJsonConverter().convertToDatabaseColumn(applicationCriteriaJsonType);
//            System.out.println(appCriteria);
//            // update Active Phase Criteria
//            applicationRepository.updateActivePhaseCriteria(phase.value(),
//                appDTO.getStatus().value(),
//                appDTO.getConfirmedBy(),
//                now,
//                statusDesc,
//                appCriteria,
//                id);
//            return appDTO;
//        } else {
//            // update Active Phase
//            applicationRepository.updateActivePhase(phase.value(),
//                appDTO.getStatus().value(),
//                appDTO.getConfirmedBy(),
//                now,
//                statusDesc,
//                id);
//            return appDTO;
//        }
    }

    private void setAppCriteria(String criteria, ApplicationDTO appDTO, ApplicationCriteriaJsonType appCriteria) {
        if(StringUtil.isNotBlank(criteria)) {
            // variables
            // Create User Delivery
            String map[] = criteria.split(",");
            String username = map[0];
            String password = map[1];
            String delivery_ID = map[2];
            String tesTakerID = map[3];
            String validFrom = map[4];
            String validTo = map[5];
            // applicationCriteriaJsonConverter
            if(appCriteria == null)
                appCriteria = new ApplicationCriteriaJsonType();
            appCriteria.setUsername(username);
            appCriteria.setPassword(password);
            appCriteria.setTesTakerID(tesTakerID);
            appCriteria.setDelivery_ID(Long.valueOf(delivery_ID));
            appCriteria.setValidFrom(LocalDate.parse(validFrom));
            appCriteria.setValidTo(LocalDate.parse(validTo));
                //TODO : jsonType
//            appDTO.setApplicationCriteria(appCriteria);
        }
    }

    /**
     * set App Status Desc
     *
     * @param appDTO
     */
    private void setAppStatusDesc(ApplicationDTO appDTO) {
        MultilingualJsonType appStatusDesc = commonUtil.getDomainValueDescription(appDTO.getStatus().value(), ApplicationStatus.DOMAIN_CODE);

        if (appStatusDesc == null) {
            throw new NullValueException("application status description");
        }
        //TODO : jsonType
//        appDTO.setStatusDescription(appStatusDesc);
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
}
