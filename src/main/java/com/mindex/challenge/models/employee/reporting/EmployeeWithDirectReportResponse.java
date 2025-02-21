package com.mindex.challenge.models.employee.reporting;

import java.util.Set;
import java.util.UUID;

public record EmployeeWithDirectReportResponse(
        UUID employeeId,
        String firstName,
        String lastName,
        String position,
        String department,
        Set<EmployeeWithDirectReportResponse> directReports) {}
