package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTest {
    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Test
    public void sendInformationEmailTest() {
        //Given
        long size = 5;
        String taskOrTasks = "tasks";
        Mockito.when(taskRepository.count()).thenReturn(size);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
        ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        Mockito.verify(simpleEmailService).send(argument.capture());
        assertEquals("admin@example.com", argument.getValue().getMailTo());
        assertEquals("Tasks: Once a day email", argument.getValue().getSubject());
        assertEquals("Currently in database you got: " + size + taskOrTasks, argument.getValue().getMessage());
    }
}
