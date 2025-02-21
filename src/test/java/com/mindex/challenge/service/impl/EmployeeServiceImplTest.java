package com.mindex.challenge.service.impl;

import com.mindex.challenge.converters.EmployeeToEmployeeResponseConverter;
import com.mindex.challenge.converters.EmployeeToEmployeeWithDirectReportResponseConverter;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.EmployeeRepositoryCustom;
import com.mindex.challenge.entities.Employee;
import com.mindex.challenge.exceptions.ResourceNotFoundException;
import com.mindex.challenge.fixtures.EmployeeFixtures;
import com.mindex.challenge.models.employee.EmployeeCreationRequest;
import com.mindex.challenge.models.employee.EmployeeResponse;
import com.mindex.challenge.models.employee.EmployeeUpdateRequest;
import com.mindex.challenge.models.employee.reporting.EmployeeWithDirectReportResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.support.DefaultConversionService;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock EmployeeRepository employeeRepository;
    @Mock EmployeeRepositoryCustom employeeRepositoryCustom;
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setup() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new EmployeeToEmployeeResponseConverter());
        conversionService.addConverter(new EmployeeToEmployeeWithDirectReportResponseConverter());
        employeeService =
                new EmployeeServiceImpl(
                        conversionService, employeeRepository, employeeRepositoryCustom);
    }

    @Test
    void create_Success() {
        EmployeeCreationRequest employeeCreationRequest =
                EmployeeFixtures.createEmployeeCreationRequest();

        EmployeeResponse employeeResponse = employeeService.create(employeeCreationRequest);

        Mockito.verify(employeeRepository).insert(Mockito.any(Employee.class));
        Assertions.assertAll(
                () -> Assertions.assertNotNull(employeeResponse),
                () -> Assertions.assertNotNull(employeeResponse.employeeId()),
                () -> Assertions.assertEquals("FIRST_NAME", employeeResponse.firstName()),
                () -> Assertions.assertEquals("LAST_NAME", employeeResponse.lastName()),
                () -> Assertions.assertEquals("POSITION", employeeResponse.position()),
                () -> Assertions.assertEquals("DEPARTMENT", employeeResponse.department()));
    }

    @Test
    void fetch_EmployeeNotFound_ResourceNotFoundException() {
        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResourceNotFoundException.class, () -> employeeService.fetch(UUID.randomUUID()));
    }

    @Test
    void fetch_EmployeeFound_Success() {
        Employee employee = EmployeeFixtures.createBasicEmployeeNoReferences();

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));

        EmployeeResponse employeeResponse = employeeService.fetch(UUID.randomUUID());

        Mockito.verify(employeeRepository).findById(Mockito.any());
        Assertions.assertAll(
                () -> Assertions.assertEquals("FIRST_NAME", employeeResponse.firstName()),
                () -> Assertions.assertEquals("LAST_NAME", employeeResponse.lastName()),
                () -> Assertions.assertEquals("POSITION", employeeResponse.position()),
                () -> Assertions.assertEquals("DEPARTMENT", employeeResponse.department()));
    }

    @Test
    void fetchEmployeeWithDirectReports_Success() {
        Employee employee = EmployeeFixtures.createEmployeeWithDirectReports();

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));

        EmployeeWithDirectReportResponse employeeWithDirectReportResponse =
                employeeService.fetchEmployeeWithDirectReports(UUID.randomUUID());

        Mockito.verify(employeeRepository).findById(Mockito.any());
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                "FIRST_NAME", employeeWithDirectReportResponse.firstName()),
                () ->
                        Assertions.assertEquals(
                                "LAST_NAME", employeeWithDirectReportResponse.lastName()),
                () ->
                        Assertions.assertEquals(
                                "POSITION", employeeWithDirectReportResponse.position()),
                () ->
                        Assertions.assertEquals(
                                "DEPARTMENT", employeeWithDirectReportResponse.department()),
                () ->
                        Assertions.assertEquals(
                                1, employeeWithDirectReportResponse.directReports().size()));
    }

    @Test
    void update_OnlyUpdateFirstName_Success() {
        EmployeeUpdateRequest employeeUpdateRequest =
                new EmployeeUpdateRequest("UPDATED_FIRST_NAME", null, null, null);

        Employee employee = EmployeeFixtures.createEmployeeWithDirectReports();

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));

        employeeService.update(employee.getEmployeeId(), employeeUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(employee),
                () -> Assertions.assertEquals("UPDATED_FIRST_NAME", employee.getFirstName()),
                () -> Assertions.assertNotNull(employee.getLastName()),
                () -> Assertions.assertNotNull(employee.getDepartment()),
                () -> Assertions.assertNotNull(employee.getPosition()));
    }

    @Test
    void update_UpdateAllFields_Success() {
        EmployeeUpdateRequest employeeUpdateRequest =
                new EmployeeUpdateRequest(
                        "UPDATED_FIRST_NAME",
                        "UPDATED_LAST_NAME",
                        "UPDATED_POSITION",
                        "UPDATED_DEPARTMENT");

        Employee employee = EmployeeFixtures.createEmployeeWithDirectReports();

        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(employee));

        employeeService.update(employee.getEmployeeId(), employeeUpdateRequest);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(employee),
                () -> Assertions.assertEquals("UPDATED_FIRST_NAME", employee.getFirstName()),
                () -> Assertions.assertEquals("UPDATED_LAST_NAME", employee.getLastName()),
                () -> Assertions.assertEquals("UPDATED_DEPARTMENT", employee.getDepartment()),
                () -> Assertions.assertEquals("UPDATED_POSITION", employee.getPosition()));
    }

    @Test
    void appendCompensations_CallsCustomRepository_Success() {
        Assertions.assertDoesNotThrow(
                () -> employeeService.appendCompensations(UUID.randomUUID(), new HashSet<>()));

        Mockito.verify(employeeRepositoryCustom)
                .addCompensationsToEmployee(Mockito.any(), Mockito.any());
    }
}
