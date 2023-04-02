package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskMapperTest {

    private TaskMapper taskMapper = new TaskMapper();

    @Test
    void testMappingTaskToDto(){

        //Given
        Task task = new Task(1L,"test","test");

        //When
        TaskDto mappedTask = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(task.getTitle(),mappedTask.getTitle());
        assertEquals(task.getId(),mappedTask.getId());
        assertEquals(task.getContent(),mappedTask.getContent());
    }

    @Test
    void testMappingTaskDtoToTask(){
        //Given
        TaskDto taskDto = new TaskDto(2L,"test title","test content");

        //When
        Task mappedTaskDto = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(taskDto.getTitle(),mappedTaskDto.getTitle());
        assertEquals(taskDto.getId(),mappedTaskDto.getId());
        assertEquals(taskDto.getContent(),mappedTaskDto.getContent());
    }

    @Test
    void testMappingToTaskDtoList(){
        //Given
        Task task1 = new Task(3L,"test 1","test 1");
        Task task2 = new Task(4L,"test 2","test 2");
        List<Task> listOfTasks = List.of(task1,task2);

        //When
        List<TaskDto> mappedList = taskMapper.mapToTaskDtoList(listOfTasks);
        System.out.println(mappedList);

        //Then
        assertEquals(2,mappedList.size());
        assertEquals(listOfTasks.get(0).getId(),mappedList.get(0).getId());
        assertEquals(listOfTasks.get(1).getId(),mappedList.get(1).getId());

    }

}
