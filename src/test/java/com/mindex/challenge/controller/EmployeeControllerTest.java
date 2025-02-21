package com.mindex.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.fixtures.EmployeeFixtures;
import com.mindex.challenge.service.EmployeeService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @InjectMocks private EmployeeController employeeController;
    @Mock private EmployeeService employeeService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void create_Success() throws Exception {
        var request = EmployeeFixtures.createEmployeeCreationRequest();

        mockMvc.perform(
                        post("/employee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(employeeService).create(request);
    }

    @Test
    void read_InvalidId_BadRequest() throws Exception {
        mockMvc.perform(get("/employee/123")).andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(employeeService);
    }

    @Test
    void read_ValidId_Success() throws Exception {
        UUID randomUUID = UUID.randomUUID();

        mockMvc.perform(get("/employee/%s".formatted(randomUUID)))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(employeeService).fetch(randomUUID);
    }

    @Test
    void update_InvalidId_BadRequest() throws Exception {
        mockMvc.perform(patch("/employee/123")).andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(employeeService);
    }

    @Test
    void update_ValidId_Success() throws Exception {
        var request = EmployeeFixtures.createEmployeeUpdateRequest();
        UUID randomUUID = UUID.randomUUID();

        mockMvc.perform(
                        patch("/employee/%s".formatted(randomUUID))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }
}
