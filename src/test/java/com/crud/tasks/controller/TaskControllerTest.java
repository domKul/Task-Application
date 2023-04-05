package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.domain.service.DbService;
import com.crud.tasks.mapper.TaskMapper;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;


    @Test
    void shouldGetAllTasks() throws Exception{
        //Give
        List<Task>taskList = List.of(new Task(1L,"task1","content1"),
                new Task(2L,"task2","content2"));
        List<TaskDto>taskListDto = List.of(new TaskDto(1L,"task1","content1"),
                new TaskDto(2L,"task2","content2"));

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void shouldGetTask() throws Exception{
        //Given
        Task task = new Task(1L,"task","content");
        TaskDto taskDto = new TaskDto(1L,"taskDto","content");

        when(dbService.getTask(anyLong())).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("taskDto")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("content")));
    }

    @Test
    void shouldThrowException() throws Exception {
        //Given
        Task task = new Task(1L,"task","content");
        when(dbService.getTask(1L)).thenReturn(task);
        when(dbService.getTask(2L)).thenThrow(TaskNotFoundException.class);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/2")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        doNothing().when(dbService).deleteTaskById(anyLong());

        //When & Then
        mockMvc.perform(delete("/v1/tasks/" + anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task 1", "Description 1");
        Task task = new Task(1L,"task","content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                         .put("/v1/tasks/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateNewTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L,"task","content");
        Task task = new Task(1L,"task","content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}