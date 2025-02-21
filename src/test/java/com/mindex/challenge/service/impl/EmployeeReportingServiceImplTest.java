package com.mindex.challenge.service.impl;

import com.mindex.challenge.fixtures.EmployeeFixtures;
import com.mindex.challenge.models.employee.reporting.ReportingStructureResponse;
import com.mindex.challenge.service.EmployeeService;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeReportingServiceImplTest {
    @InjectMocks EmployeeReportingServiceImpl service;

    @Mock EmployeeService employeeService;

    @Test
    void fetchDirectReports_SkipAcyclicRelationships_NoUnderlings_Success() {
        Mockito.when(employeeService.fetchEmployeeWithDirectReports(Mockito.any()))
                .thenReturn(EmployeeFixtures.createAcyclicZeroDirectReportsEmployeeResponse(5));

        ReportingStructureResponse response = service.fetchDirectReports(UUID.randomUUID());

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.employee().directReports().isEmpty()),
                () -> Assertions.assertEquals(0, response.numberOfReports()));
    }

    @Test
    void fetchDirectReports_SkipAcyclicRelationships_UnderlingsPresent_Success() {
        Mockito.when(employeeService.fetchEmployeeWithDirectReports(Mockito.any()))
                .thenReturn(EmployeeFixtures.createAcyclicDirectReportsEmployeeResponse(2));

        ReportingStructureResponse response = service.fetchDirectReports(UUID.randomUUID());

        Assertions.assertAll(
                // 3 because we created one acyclic relationship
                () -> Assertions.assertEquals(3, response.numberOfReports()));
    }
}
