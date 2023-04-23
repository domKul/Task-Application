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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
@Import(TaskMapper.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private DbService dbService;




    @Test
    void shouldGetAllTasks() throws Exception{
        //Given
        List<Task> tasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1"),
                new Task(2L, "Task 2", "Description 2"));
        given(dbService.getAllTasks()).willReturn(tasks);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tasks/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"));


    }

    @Test
    void shouldGetTask() throws Exception{
        //Given
        Task task = new Task(1L,"task","content");
        when(dbService.getTask(anyLong())).thenReturn(task);


        //When & Then
        mockMvc
                .perform(get("/v1/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title", Matchers.is("task")))
                .andExpect(jsonPath("$.content", Matchers.is("content")));


    }

    @Test
    void shouldReturnErrorWhenWrongId() throws Exception {
        //Given
        Task task = new Task(1L,"task","content");
        when(dbService.getTask(1L)).thenReturn(task);
        when(dbService.getTask(2L)).thenThrow(TaskNotFoundException.class);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/2")
                        .contentType(MediaType.APPLICATION_JSON))
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
        Long id = 1L;
        TaskDto taskDto = new TaskDto(id, "task", "content");

        Task task = new Task(id, "task", "content");
        Task updatedTask = new Task(id, "updatedTask", "updatedContent");

        when(dbService.saveTask(task)).thenReturn(updatedTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updatedTask")));
    }

    @Test
    void shouldCreateNewTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L,"task","content");
        Task task = new Task(1L,"task","content");

        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

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