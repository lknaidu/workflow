package org.ikoneni.flowableworkflow.controller;

import lombok.AllArgsConstructor;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.ikoneni.flowableworkflow.dto.ProcessRequest;
import org.ikoneni.flowableworkflow.dto.TaskRepresentation;
import org.ikoneni.flowableworkflow.service.FlowableService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class WorkflowController {

    private final TaskService taskService;
    FlowableService flowableService;
    
    @PostMapping(value="/process", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ProcessInstance startProcessInstance(@RequestBody ProcessRequest processRequest) {
        return flowableService.startProcess(processRequest.getProcessKey());
    }

    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = flowableService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    @PostMapping("/completeTask")
    public void completeTask(@RequestBody TaskRepresentation taskRepresentation) {
        flowableService.completeTask(taskRepresentation.getId());
    }
}




   