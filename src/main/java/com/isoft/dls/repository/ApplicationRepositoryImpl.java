
/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:08 PM  - File created.
 */

package com.isoft.dls.repository;

import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.Application;
import com.isoft.dls.domain.util.ApplicationCriteriaJsonConverter;
import com.isoft.dls.domain.util.MultilingualJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Spring Data  repository for the Application entity.
 */

public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {

    @Autowired
    private ApplicationRepository applicationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Application saveJsonType(Application entity) {
        //TODO : jsonType
        // MultilingualJsonConverter - statusDesc
        String statusDesc = "";//new MultilingualJsonConverter().convertToDatabaseColumn(entity.getStatusDescription());
        // MultilingualJsonConverter - userTypeDesc
        String userTypeDesc = "";//new MultilingualJsonConverter().convertToDatabaseColumn(entity.getUserTypeDescription());
        // ApplicationCriteriaJsonConverter - appCriteria
        String appCriteria = "";//new ApplicationCriteriaJsonConverter().convertToDatabaseColumn(entity.getApplicationCriteria());

        Query query = entityManager.createNativeQuery("update trn_application set active_phase = ?1 , status = ?2 , confirmed_by = ?3" +
            "       , status_date = ?4, confirmation_date = ?4, last_modified_date = ?4" +
            "       , status_description =  cast (?5 AS JSON)" +
            "       , application_criteria =  cast (?6 AS JSON)" +
            "        where id = ?7");

        if(entity.getId() != null) {
            if(StringUtil.isBlank(appCriteria)){
                // update Active Phase
                query = entityManager.createNativeQuery("update trn_application set active_phase = ?1 , status = ?2 , confirmed_by = ?3" +
                    "       , status_date = ?4, confirmation_date = ?4, last_modified_date = ?4" +
                    "       , status_description =  cast (?5 AS JSON)" +
                    "        where id = ?6");
                query.setParameter(6, entity.getId());
            } else {
                // update Active Phase Criteria
                query.setParameter(6, appCriteria);
                query.setParameter(7, entity.getId());
            }
            query.setParameter(1, entity.getActivePhase().value());
            query.setParameter(2, entity.getStatus().value());
            query.setParameter(3, entity.getConfirmedBy());
            query.setParameter(4, entity.getStatusDate());
            query.setParameter(5, statusDesc);
            query.executeUpdate();
            return entity;
        } else {
            Application app = getApplication(entity);
            entity = applicationRepository.save(app);
            return entity;
        }
    }

    private Application getApplication(Application entity) {
        Application app = new Application();
        app.setUserType(entity.getUserType());
        app.setChannelCode(entity.getChannelCode());
        app.setUserRole(entity.getUserRole());
        app.setConfirmedBy(entity.getConfirmedBy());
        app.setApplicationType(entity.getApplicationType());
        app.setApplicationPhases(entity.getApplicationPhases());
        app.setApplicationViolations(entity.getApplicationViolations());
        app.setPersona(entity.getPersona());
        app.setPersonaVersion(entity.getPersonaVersion());
        app.setStatus(entity.getStatus());
        app.setStatusDate(entity.getStatusDate());
        app.setActivePhase(entity.getActivePhase());
        app.setArabicCorporateName(entity.getArabicCorporateName());
        app.setEnglishCorporateName(entity.getEnglishCorporateName());
        app.setEnglishCustomerName(entity.getEnglishCustomerName());
        app.setArabicCustomerName(entity.getArabicCustomerName());
        app.setRejectedBy(entity.getRejectedBy());
        app.setRejectionDate(entity.getRejectionDate());
        app.setRejectionReason(entity.getRejectionReason());
        app.setServiceRequests(entity.getServiceRequests());
//        app.setUserId(entity.getUserId());
        app.setProcessInstanceId(entity.getProcessInstanceId());
//        app.setSynchedEntityId(entity.getSynchedEntityId());
        app.setConfirmationDate(entity.getConfirmationDate());
        app.setTradeLicenseNo(entity.getTradeLicenseNo());
        return app;
    }
}
