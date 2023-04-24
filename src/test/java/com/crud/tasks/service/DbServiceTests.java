package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DbServiceTests {
    @Autowired
    private  DbService dbService;
    @Autowired
    private  TaskRepository taskRepository;



    @Test
    void testSaveTask(){

        //Given
        Task task1 = new Task(1L,"test1","test1");
        Task task2 = new Task(2L,"test2","test2");
        Task task3 = new Task(3L,"test3","test3");

        //When
        Task savedTask1 = dbService.saveTask(task1);
        Task savedTask2 = dbService.saveTask(task2);
        Task savedTask3 = dbService.saveTask(task3);
        List<Task> findtasks = taskRepository.findAll();


        //Then
        assertEquals(3,findtasks.size());

        //CleanUp
        taskRepository.deleteById(savedTask1.getId());
        taskRepository.deleteById(savedTask2.getId());
        taskRepository.deleteById(savedTask3.getId());
    }

    @Test
    void testGetAllTasks(){

        //Given
        Task task1 = new Task(1L,"test1","test1");
        Task task2 = new Task(2L,"test2","test2");
        Task task3 = new Task(3L,"test3","test3");

        //When
        Task savedTask1 = dbService.saveTask(task1);
        Task savedTask2 = dbService.saveTask(task2);
        Task savedTask3 = dbService.saveTask(task3);
        List<Task> findTasks = dbService.getAllTasks();


        //Then
        assertEquals(3,findTasks.size());
        assertEquals(task1.getTitle(),findTasks.get(0).getTitle());
        assertEquals(task2.getTitle(),findTasks.get(1).getTitle());
        assertEquals(task3.getTitle(),findTasks.get(2).getTitle());

        //CleanUp
        taskRepository.deleteById(savedTask1.getId());
        taskRepository.deleteById(savedTask2.getId());
        taskRepository.deleteById(savedTask3.getId());
    }

    @Test
    void testFindTaskById() throws TaskNotFoundException {
        //Given
        Task task1 = new Task(1L,"test1","test1");
        Task task2 = new Task(2L,"test2","test2");
        Task task3 = new Task(3L,"test3","test3");

        //When
        Task savedTask1 = dbService.saveTask(task1);
        Task savedTask2 = dbService.saveTask(task2);
        Task savedTask3 = dbService.saveTask(task3);
        Task findTaskById =  dbService.getTask(savedTask1.getId());


        //Then
        assertEquals("test1", findTaskById.getTitle());

        //CleanUp
        taskRepository.deleteById(savedTask1.getId());
        taskRepository.deleteById(savedTask2.getId());
        taskRepository.deleteById(savedTask3.getId());
    }

    @Test
    void testDeleteTask(){
        //Given
        Task task1 = new Task(1L,"test1","test1");
        Task task2 = new Task(2L,"test2","test2");
        Task task3 = new Task(3L,"test3","test3");

        //When
        Task savedTask1 = dbService.saveTask(task1);
        Task savedTask2 = dbService.saveTask(task2);
        Task savedTask3 = dbService.saveTask(task3);
        dbService.deleteTask(savedTask1);
        List<Task>listOfTasks = dbService.getAllTasks();

        //Then
        assertEquals(2,listOfTasks.size());

        //CleanUp
        taskRepository.deleteById(savedTask2.getId());
        taskRepository.deleteById(savedTask3.getId());
    }

    @Test
    void testDeleteTaskById(){
        //Given
        Task task1 = new Task(1L,"test1","test1");
        Task task2 = new Task(2L,"test2","test2");
        Task task3 = new Task(3L,"test3","test3");

        //When
        Task savedTask1 = dbService.saveTask(task1);
        Task savedTask2 = dbService.saveTask(task2);
        Task savedTask3 = dbService.saveTask(task3);
        dbService.deleteTaskById(savedTask1.getId());
        List<Task>listOfTasks = dbService.getAllTasks();

        //Then
        assertEquals(2,listOfTasks.size());

        //CleanUp
        taskRepository.deleteById(savedTask2.getId());
        taskRepository.deleteById(savedTask3.getId());
    }
}
