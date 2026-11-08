package org.ikoneni.flowableworkflow.service;

import lombok.AllArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FlowableService {
    private RuntimeService runtimeService;
    private TaskService taskService;

    public ProcessInstance startProcess(String processId) {
        return runtimeService.startProcessInstanceByKey(processId);
    }

    public List<Task> getTasks(){
        return taskService.createTaskQuery().list();
    }

    public List<Task> getTasks(String assignedUser){
        return taskService.createTaskQuery().taskAssignee(assignedUser).list();
    }

    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }
}
