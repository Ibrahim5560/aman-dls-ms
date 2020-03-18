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

import io.github.jhipster.config.JHipsterProperties;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.instance.*;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KieServerOperations {

    private final Logger logger = LoggerFactory.getLogger(KieServerOperations.class);
    private final JHipsterProperties jHipsterProperties;
    // KIE client for common operations
    private final KieServicesClient kieServicesClient;
    // Rules client
    private final RuleServicesClient ruleClient;
    // Process automation clients
    private final ProcessServicesClient processClient;
    private final UserTaskServicesClient userTaskClient;

    public KieServerOperations(JHipsterProperties jhipsterProperties,
                               KieServicesClient kieServicesClient,
                               RuleServicesClient ruleClient,
                               ProcessServicesClient processClient,
                               UserTaskServicesClient userTaskClient) {

        this.jHipsterProperties = jhipsterProperties;
        this.kieServicesClient = kieServicesClient;
        this.ruleClient = ruleClient;
        this.processClient = processClient;
        this.userTaskClient = userTaskClient;
    }

    /**
     * 1) startProcess
     * @param containerId
     * @param processId
     * @param variables
     * @return
     */
    public Long startProcess(String containerId, String processId, Map<String, Object> variables) {
        // 1) startProcess
        return processClient.startProcess(containerId, processId, variables);
    }

    /**
     * 2) getWorkItemByProcessInstance
     * @param containerId
     * @param processInstanceId
     * @return
     */
    public List<WorkItemInstance> getWorkItemByProcessInstance(String containerId, Long processInstanceId) {
        // 2) getWorkItemByProcessInstance
        return processClient.getWorkItemByProcessInstance(containerId, processInstanceId);
    }

    /**
     * 3) completeWorkItem
     * @param containerId
     * @param processInstanceId
     * @param workItemId
     * @param results
     */
    public void completeWorkItem(String containerId, Long processInstanceId, Long workItemId, Map<String, Object> results) {
        // 3) completeWorkItem
        processClient.completeWorkItem(containerId, processInstanceId, workItemId, results);
    }

    /**
     * 3) abortWorkItem
     * @param containerId
     * @param processInstanceId
     * @param workItemId
     */
    public void abortWorkItem(String containerId, Long processInstanceId, Long workItemId) {
        // 3) abortWorkItem
        processClient.abortWorkItem(containerId, processInstanceId, workItemId);
    }

    /**
     * 4) findVariableHistory
     * @param containerId
     * @param varName
     * @param processInstanceId
     * @return
     */
    public List<VariableInstance> findVariableHistory(String containerId, String varName, Long processInstanceId) {
        // 4) findVariableHistory
        return processClient.findVariableHistory(containerId, processInstanceId, varName, 0, 20);
    }

    /**
     * 4) getProcessInstanceVariables
     * @param containerId
     * @param processInstanceId
     * @return
     */
    public Map<String, Object> getProcessInstanceVariables(String containerId, Long processInstanceId) {
        // 4) getProcessInstanceVariables
        return processClient.getProcessInstanceVariables(containerId, processInstanceId);
    }

    /**
     * 4) findVariablesCurrentState
     * @param containerId
     * @param processInstanceId
     * @return
     */
    public List<VariableInstance> findVariablesCurrentState(String containerId, Long processInstanceId) {
        // 4) findVariableHistory
        return processClient.findVariablesCurrentState(containerId, processInstanceId);
    }


    /**
     * 4) findProcessInstances
     * @param containerId
     * @param page
     * @param pageSize
     * @return
     */
    public List<ProcessInstance> findProcessInstances(String containerId, Integer page, Integer pageSize) {
        // 4) findVariableHistory
        return processClient.findProcessInstances(containerId, page, pageSize);
    }

    /**
     * @param containerId
     * @param taskId
     * @param userId
     */
    public void activateTask(String containerId, Long taskId, String userId) {
        userTaskClient.activateTask(containerId, taskId, userId);
    }
    /**
     * @param containerId
     * @param taskId
     * @param userId
     */
    public void startTask(String containerId, Long taskId, String userId) {
        userTaskClient.startTask(containerId, taskId, userId);
    }
    /**
     * @param containerId
     * @param taskId
     * @param userId
     */
    public void stopTask(String containerId, Long taskId, String userId) {
        userTaskClient.stopTask(containerId, taskId, userId);
    }
    /**
     * @param containerId
     * @param taskId
     * @param userId
     */
    public void releaseTask(String containerId, Long taskId, String userId) {
        userTaskClient.releaseTask(containerId, taskId, userId);
    }
    /**
     * @param containerId
     * @param taskId
     * @param userId
     */
    public void claimTask(String containerId, Long taskId, String userId) {
        userTaskClient.claimTask(containerId, taskId, userId);
    }
    /**
     * @param containerId
     * @param taskId
     * @param userId
     * @param params
     */
    public void completeTask(String containerId, Long taskId, String userId, Map<String, Object> params) {
        userTaskClient.completeTask(containerId, taskId, userId,params);
    }

    /**
     * @param workItemId
     * @return
     */
    public TaskInstance findTaskByWorkItemId(Long workItemId) {
        return userTaskClient.findTaskByWorkItemId(workItemId);
    }
    /**
     * @param taskId
     * @return
     */
    public TaskInstance findTaskById(Long taskId) {
        return userTaskClient.findTaskById(taskId);
    }
    /**
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public List<TaskSummary> findTasksOwned(String userId, Integer page, Integer pageSize) {
        return userTaskClient.findTasksOwned(userId,page,pageSize);
    }
    /**
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public List<TaskSummary> findTasksAssignedAsPotentialOwner(String userId, Integer page, Integer pageSize) {
        return userTaskClient.findTasksAssignedAsPotentialOwner(userId,page,pageSize);
    }
    /**
     * @param userId
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    public List<TaskSummary> findTasksAssignedAsPotentialOwner(String userId, List<String> status, Integer page, Integer pageSize) {
        return userTaskClient.findTasksAssignedAsPotentialOwner(userId, status,page,pageSize);
    }
    /**
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public List<TaskSummary> findTasks(String userId, Integer page, Integer pageSize) {
        return userTaskClient.findTasks(userId,page,pageSize);
    }
    /**
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public List<TaskSummary> findTasksOwned(String userId, List<String> status, Integer page, Integer pageSize) {
        return userTaskClient.findTasksOwned(userId,page,pageSize);
    }
    /**
     * @param processInstanceId
     * @param status
     * @param page
     * @param pageSize
     * @return
     */
    public List<TaskSummary> findTasksByStatusByProcessInstanceId(Long processInstanceId, List<String> status, Integer page, Integer pageSize) {
        return userTaskClient.findTasksByStatusByProcessInstanceId(processInstanceId,status,page,pageSize);
    }

    /**
     * listContainers
     */
    public void listContainers() {
        KieContainerResourceList containersList = kieServicesClient.listContainers().getResult();
        List<KieContainerResource> kieContainers = containersList.getContainers();
        System.out.println("Available containers: ");
        for (KieContainerResource container : kieContainers) {
            System.out.println("\t" + container.getContainerId() + " (" + container.getReleaseId() + ")");
        }
    }

    /**
     * listCapabilities
     */
    public void listCapabilities() {
        KieServerInfo serverInfo = kieServicesClient.getServerInfo().getResult();
        System.out.print("Server capabilities:");
        for (String capability : serverInfo.getCapabilities()) {
            System.out.print(" " + capability);
        }
        System.out.println();
    }
}
