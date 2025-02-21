package com.mindex.challenge.fixtures;

import com.mindex.challenge.entities.Employee;
import com.mindex.challenge.models.employee.EmployeeCreationRequest;
import com.mindex.challenge.models.employee.EmployeeUpdateRequest;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationRequest;
import com.mindex.challenge.models.employee.reporting.EmployeeWithDirectReportResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class EmployeeFixtures {
    public static EmployeeCompensationRequest createEmployeeCompensationRequest() {
        return new EmployeeCompensationRequest(LocalDate.now(), BigDecimal.ONE);
    }

    public static EmployeeCreationRequest createEmployeeCreationRequest() {
        return new EmployeeCreationRequest("FIRST_NAME", "LAST_NAME", "POSITION", "DEPARTMENT");
    }

    public static EmployeeWithDirectReportResponse createAcyclicZeroDirectReportsEmployeeResponse(
            int level) {
        return createEmployeeResponseHelper(level, 0, UUID.randomUUID(), true);
    }

    private static EmployeeWithDirectReportResponse createEmployeeResponseHelper(
            int levelRequired, int currentLevel, UUID uuid, boolean isAcyclic) {
        if (currentLevel >= levelRequired) {
            return null;
        }
        String mode = isAcyclic ? "acyclic" : "notAcyclic";
        String firstName = "FirstName" + currentLevel + mode;
        String lastName = "LastName" + currentLevel + mode;
        String position = "Position" + currentLevel + mode;
        String department = "Department" + currentLevel + mode;

        UUID employeeUUID = isAcyclic ? uuid : UUID.randomUUID();

        EmployeeWithDirectReportResponse directReport =
                createEmployeeResponseHelper(
                        levelRequired, currentLevel + 1, employeeUUID, isAcyclic);

        Set<EmployeeWithDirectReportResponse> directReports = new HashSet<>();
        if (directReport != null && !isAcyclic) {
            directReports.add(directReport);
        }

        return new EmployeeWithDirectReportResponse(
                employeeUUID, firstName, lastName, position, department, directReports);
    }

    public static EmployeeWithDirectReportResponse createAcyclicDirectReportsEmployeeResponse(
            int level) {
        return new EmployeeWithDirectReportResponse(
                UUID.randomUUID(),
                "FIRST_NAME",
                "LAST_NAME",
                "POSITION",
                "DEPARTMENT",
                Set.of(
                        Objects.requireNonNull(
                                createEmployeeResponseHelper(level, 0, UUID.randomUUID(), false)),
                        createAcyclicZeroDirectReportsEmployeeResponse(level)));
    }

    public static Employee createBasicEmployeeNoReferences() {
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID());
        employee.setFirstName("FIRST_NAME");
        employee.setLastName("LAST_NAME");
        employee.setPosition("POSITION");
        employee.setDepartment("DEPARTMENT");
        return employee;
    }

    public static Employee createEmployeeWithDirectReports() {
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID());
        employee.setFirstName("FIRST_NAME");
        employee.setLastName("LAST_NAME");
        employee.setPosition("POSITION");
        employee.setDepartment("DEPARTMENT");
        employee.setDirectReports(Set.of(createBasicEmployeeNoReferences()));
        return employee;
    }

    public static EmployeeUpdateRequest createEmployeeUpdateRequest() {
        return new EmployeeUpdateRequest("FIRST_NAME", "LAST_NAME", "POSITION", "DEPARTMENT");
    }
}
