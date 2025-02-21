package com.mindex.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindex.challenge.fixtures.EmployeeFixtures;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationRequest;
import com.mindex.challenge.service.EmployeeCompensationService;
import java.util.Set;
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
class EmployeeCompensationControllerTest {
    @InjectMocks private EmployeeCompensationController controller;

    @Mock private EmployeeCompensationService compensationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createCompensationForEmployee_InvalidId_BadRequest() throws Exception {
        mockMvc.perform(
                        post("/employee/123/compensation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                Set.of(
                                                        EmployeeFixtures
                                                                .createEmployeeCompensationRequest()))))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(compensationService);
    }

    @Test
    void createCompensationForEmployee_ValidId_ValidBody_Success() throws Exception {
        UUID randomUUID = UUID.randomUUID();
        Set<EmployeeCompensationRequest> request =
                Set.of(EmployeeFixtures.createEmployeeCompensationRequest());

        mockMvc.perform(
                        post("/employee/%s/compensation".formatted(randomUUID))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(compensationService).createCompensationForEmployee(randomUUID, request);
    }

    @Test
    void fetchAllCompensationsForEmployee_InvalidId_BadRequest() throws Exception {
        mockMvc.perform(get("/employee/123/compensation")).andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(compensationService);
    }

    @Test
    void fetchAllCompensationsForEmployee_ValidId_Success() throws Exception {
        UUID randomUUID = UUID.randomUUID();

        mockMvc.perform(get("/employee/%s/compensation".formatted(randomUUID)))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(compensationService).fetchAllCompensationsForEmployee(randomUUID);
    }
}
