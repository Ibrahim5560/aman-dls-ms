/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 1:09 PM  - File created.
 */

package com.isoft.dls.config;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.*;
import org.kie.server.client.helper.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class KieServerConfiguration {

    // REST API base URL, credentials, and marshalling format
    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
    @Value("#{environment['bpm.context.root']}")
    private String url ;
    @Value("#{environment['bpm.context.username']}")
    private String username;
    @Value("#{environment['bpm.context.password']}")
    private String password;

    public DroolsServicesClientBuilder brm() {
        DroolsServicesClientBuilder brm = new DroolsServicesClientBuilder();
        return brm;
    }

    public JBPMServicesClientBuilder bpm() {
        JBPMServicesClientBuilder bpm = new JBPMServicesClientBuilder();
        return bpm;
    }

    public JBPMUIServicesClientBuilder bpm_ui() {
        JBPMUIServicesClientBuilder bpm_ui = new JBPMUIServicesClientBuilder();
        return bpm_ui;
    }

    public CaseServicesClientBuilder caseMgmt() {
        CaseServicesClientBuilder caseMgmt = new CaseServicesClientBuilder();
        return caseMgmt;
    }

    public DMNServicesClientBuilder dmn() {
        DMNServicesClientBuilder dmn = new DMNServicesClientBuilder();
        return dmn;
    }

    public OptaplannerServicesClientBuilder brp() {
        OptaplannerServicesClientBuilder brp = new OptaplannerServicesClientBuilder();
        return brp;
    }

    @Bean
    public KieServicesConfiguration config() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
        config.setMarshallingFormat(FORMAT);

        // set capabilities so the client will not to try to get them from the server
        List<String> capabilities = new ArrayList<>();
        capabilities.add("BRM");
        capabilities.add("BPM");
        capabilities.add("BPM-UI");
        capabilities.add("BRP");
        capabilities.add("DMN");
        capabilities.add("CaseMgmt");
//            config.setCapabilities(Collections.emptyList());
        config.setCapabilities(capabilities);

        //If you use custom classes, such as Obj.class, add them to the configuration.
        Set<Class<?>> extraClassList = new HashSet<Class<?>>();
//        extraClassList.add(TestDto.class);
//        extraClassList.add(Product.class);
//        extraClassList.add(UserDeliveryDTO.class);
        config.addExtraClasses(extraClassList);

        brm().build(config, RuleServicesClient.class.getClassLoader());
        bpm().build(config, ProcessServicesClient.class.getClassLoader());
        bpm_ui().build(config, UIServicesClient.class.getClassLoader());
        caseMgmt().build(config, CaseServicesClient.class.getClassLoader());
        dmn().build(config, DMNServicesClient.class.getClassLoader());
        brp().build(config, SolverServicesClient.class.getClassLoader());
        return config;
    }

    @Bean
    public KieServicesClient kieServicesClient(KieServicesConfiguration config) {
        try {
            // KIE client for common operations
            KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(config);
            return kieServicesClient;
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating configuration", e);
        }
    }

    @Bean
    public RuleServicesClient ruleClient(KieServicesClient kieServicesClient) {
        RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
        return ruleClient;
    }

    @Bean
    public DMNServicesClient dmnClient(KieServicesClient kieServicesClient) {
        DMNServicesClient dmnClient = kieServicesClient.getServicesClient(DMNServicesClient.class);
        return dmnClient;
    }

    @Bean
    public CaseServicesClient caseClient(KieServicesClient kieServicesClient) {
        CaseServicesClient caseClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
        return caseClient;
    }

    @Bean
    public DocumentServicesClient documentClient(KieServicesClient kieServicesClient) {
        DocumentServicesClient documentClient = kieServicesClient.getServicesClient(DocumentServicesClient.class);
        return documentClient;
    }

    @Bean
    public JobServicesClient jobClient(KieServicesClient kieServicesClient) {
        JobServicesClient jobClient = kieServicesClient.getServicesClient(JobServicesClient.class);
        return jobClient;
    }

    @Bean
    public ProcessServicesClient processClient(KieServicesClient kieServicesClient) {
        ProcessServicesClient processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        return processClient;
    }

    @Bean
    public QueryServicesClient queryClient(KieServicesClient kieServicesClient) {
        QueryServicesClient queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
        return queryClient;
    }

    @Bean
    public UIServicesClient uiClient(KieServicesClient kieServicesClient) {
        UIServicesClient uiClient = kieServicesClient.getServicesClient(UIServicesClient.class);
        return uiClient;
    }

    @Bean
    public UserTaskServicesClient userTaskClient(KieServicesClient kieServicesClient) {
        UserTaskServicesClient userTaskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
        return userTaskClient;
    }

    @Bean
    public SolverServicesClient solverClient(KieServicesClient kieServicesClient) {
        SolverServicesClient solverClient = kieServicesClient.getServicesClient(SolverServicesClient.class);
        return solverClient;
    }
}
