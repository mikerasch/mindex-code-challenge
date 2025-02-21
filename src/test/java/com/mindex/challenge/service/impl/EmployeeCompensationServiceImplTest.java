package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.exceptions.ResourceNotFoundException;
import com.mindex.challenge.fixtures.CompensationFixtures;
import com.mindex.challenge.fixtures.EmployeeFixtures;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationRequest;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationResponse;
import com.mindex.challenge.service.EmployeeService;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeCompensationServiceImplTest {
    @InjectMocks EmployeeCompensationServiceImpl service;

    @Mock EmployeeService employeeService;

    @Mock CompensationRepository compensationRepository;

    @Test
    void createCompensationForEmployee_AddedToCompensationAndEmployeeEntities_Success() {
        EmployeeCompensationRequest employeeCompensationRequest =
                EmployeeFixtures.createEmployeeCompensationRequest();
        ArgumentCaptor<Set<Compensation>> compensationCaptor = ArgumentCaptor.forClass(Set.class);
        UUID employeeId = UUID.randomUUID();

        Mockito.when(employeeService.exists(Mockito.any())).thenReturn(true);

        Mockito.when(compensationRepository.saveAll(compensationCaptor.capture()))
                .thenReturn(new ArrayList<>());

        Set<EmployeeCompensationResponse> responses =
                service.createCompensationForEmployee(
                        employeeId, Set.of(employeeCompensationRequest));

        Set<Compensation> compensations = compensationCaptor.getValue();
        EmployeeCompensationResponse expectedCompensation =
                new EmployeeCompensationResponse(
                        employeeId,
                        employeeCompensationRequest.effectiveDate(),
                        employeeCompensationRequest.salary());
        Mockito.verify(compensationRepository).saveAll(compensations);
        Mockito.verify(employeeService).appendCompensations(employeeId, compensations);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, responses.size()),
                () -> Assertions.assertTrue(responses.contains(expectedCompensation)));
    }

    @Test
    void createCompensationForEmployee_EmployeeNotFound_NotFoundException() {
        Mockito.when(employeeService.exists(Mockito.any())).thenReturn(false);

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () ->
                        service.createCompensationForEmployee(
                                UUID.randomUUID(),
                                Set.of(EmployeeFixtures.createEmployeeCompensationRequest())));

        Mockito.verifyNoInteractions(compensationRepository);
    }

    @Test
    void fetchAllCompensationsForEmployee_Success() {
        Compensation compensation = CompensationFixtures.buildBaseCompensation();

        Mockito.when(compensationRepository.findAllByEmployeeId(Mockito.any()))
                .thenReturn(Set.of(compensation));

        Set<EmployeeCompensationResponse> actualResponse =
                service.fetchAllCompensationsForEmployee(
                        compensation.getEmployee().getEmployeeId());

        EmployeeCompensationResponse expectedResponse =
                new EmployeeCompensationResponse(
                        compensation.getEmployee().getEmployeeId(),
                        compensation.getEffectiveDate(),
                        compensation.getSalary());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, actualResponse.size()),
                () -> Assertions.assertTrue(actualResponse.contains(expectedResponse)));
    }
}
