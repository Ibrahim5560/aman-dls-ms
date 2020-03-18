///*
// * Copyright (c) ISOFT 2020.
// * Ferdous Tower (Takreer Building) , Salam Street
// * Abu Dhabi, United Arab Emirates
// * P.O. Box: 32326
// * All Rights Reserved.
// *
// * ver    Developer          	Date              Comments
// * ----- -----------------  	----------       -----------------
// * 1.00  Eng. Ibrahim Hassanin 3/18/20 8:57 AM  - File created.
// */
//
//package com.isoft.dls.web.rest.bpm;
//
//import ae.rta.dls.portal.gateway.common.util.bpm.TaskDetailsResponse;
//import ae.rta.dls.portal.gateway.service.bpm.BusinessProcessService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.URISyntaxException;
//
///**
// * REST controller for managing BpmRestLog.
// * @Auther Tariq Abu Amireh
// */
//@RestController
//@RequestMapping("/api/bpm")
//public class BusinessProcessResource {
//
//    private final Logger log = LoggerFactory.getLogger(BusinessProcessResource.class);
//
//    private final BusinessProcessService businessProcessService;
//
//    public BusinessProcessResource(BusinessProcessService businessProcessService) {
//        this.businessProcessService = businessProcessService;
//    }
//
//    /**
//     * POST  /bpm-rest-logs : Create a new brmRestLog.
//     *
//     * @param id
//     * @return the ResponseEntity with status 201 (Created) and with body the new brmRestLogDTO, or with status 400 (Bad Request) if the brmRestLog has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @GetMapping("/task/{id}")
//    public TaskDetailsResponse taskDetails(@PathVariable String id) throws URISyntaxException {
//        log.debug("REST request to get BpmRestLog : {}", id);
//
//        TaskDetailsResponse taskDetailsResponse = businessProcessService.getTaskDetails(id);
//        if (taskDetailsResponse != null) {
//            String msg = String.format("Status >>>> %s" , taskDetailsResponse.getStatus());
//            log.info(msg);
//
//            msg = String.format("CreationDate >>>> %s" , taskDetailsResponse.getCreationDate());
//            log.info(msg);
//
//            msg = String.format("Name >>>> %s" , taskDetailsResponse.getName());
//            log.info(msg);
//
//            msg = String.format("inner status  >>>> %s" , taskDetailsResponse.getStatus());
//            log.info(msg);
//        }
//
//        return taskDetailsResponse;
//
//    }
//}
