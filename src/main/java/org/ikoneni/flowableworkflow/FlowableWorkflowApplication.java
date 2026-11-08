package org.ikoneni.flowableworkflow;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowableWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableWorkflowApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        return strings -> {
            for (ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().list()) {
                runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(), "duplicate");
            }
            System.out.println("Number of process definitions : "
                    + repositoryService.createProcessDefinitionQuery().count());
            System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
            runtimeService.startProcessInstanceByKey("oneTaskProcess");
            System.out.println("Number of tasks after process start: "
                    + taskService.createTaskQuery().count());
        };
    }
}
