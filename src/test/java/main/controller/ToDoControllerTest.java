package main.controller;

import main.AbstractIntegrationTest;
import main.Storage;
import main.model.Action;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isToString;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ToDoControllerTest extends AbstractIntegrationTest {

    private Action action;
    private final ToDoController controller = new ToDoController();

    @Before
    public void setUp(){
        action = new Action();
        action.setName("Job 1");
        action.setDate(String.valueOf(LocalDate.now()));
    }

    @Test
    public void shouldReturnSomething() throws Exception {
        Storage.addAction(action);

        mockMvc.perform(MockMvcRequestBuilders.get("/actions/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[1].name", stringContainsInOrder("Job 1")));

    }

    @Test
    public void shouldAddSomething() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/actions/")
                        .param("name","Action 1")
                        .param("date","2020-01-01"))
                .andExpect(status().isOk());
        assertEquals(3, Storage.getAllActions().size());
    }

    @Test
    public void shouldDeleteSomething() throws Exception {
        Storage.addAction(action);


        mockMvc.perform(MockMvcRequestBuilders.delete("/actions/1"));

        assertNull(Storage.getAction(1));
    }

    @Test
    public void shouldPutSomething() throws Exception {
        int id = 2;
        Storage.addAction(action);

        String newName = "Sleep";
        String newDate = "2020-01-01";

        mockMvc.perform(MockMvcRequestBuilders.put("/actions/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(getActionJSON(id,newName,newDate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        assertEquals(newName, Objects.requireNonNull(Storage.getAction(id)).getName());
    }

    private String getActionJSON(int id, String newName, String newDate){
        return "{\"id\":\"" + id + "\", \"name\":\"" + newName + "\", \"date\":\"" + newDate + "\" }";
    }

    @Test
    public void tryDeleteUnsetEntity() throws Exception {
        Storage.addAction(action);

        mockMvc.perform(MockMvcRequestBuilders.delete("/actions/10"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @After
    public void after(){

    }
}
