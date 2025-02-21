package com.mindex.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mindex.challenge.service.EmployeeReportingService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class EmployeeReportingControllerTest {
    @InjectMocks private EmployeeReportingController controller;
    @Mock private EmployeeReportingService employeeReportingService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void fetchDirectReports_InvalidId_BadRequest() throws Exception {
        mockMvc.perform(get("/employee/%s/direct-reports")).andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(employeeReportingService);
    }

    @Test
    void fetchDirectReports_ValidId_Success() throws Exception {
        UUID randomUUID = UUID.randomUUID();

        mockMvc.perform(get("/employee/%s/direct-reports".formatted(randomUUID)))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(employeeReportingService).fetchDirectReports(randomUUID);
    }
}
