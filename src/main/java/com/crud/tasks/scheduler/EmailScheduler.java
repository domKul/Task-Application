package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailScheduler {
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";
    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String taskOrTasks =(size==1)?"task":"tasks";
        simpleEmailService.send(
                new Mail(adminConfig.getAdminMail(), SUBJECT, "Currently in database you got: " + size + taskOrTasks,null
                )
        );
    }
}
