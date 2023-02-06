package com.crud.tasks;

import com.crud.tasks.domain.TaskDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasksApplication {

	public static void main(String[] args) {

		System.out.println("Test");

		SpringApplication.run(TasksApplication.class, args);
	}

}
