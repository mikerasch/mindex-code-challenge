package com.mindex.challenge.models.employee.reporting;

import jakarta.annotation.Nonnull;

public record ReportingStructureResponse(
        @Nonnull EmployeeWithDirectReportResponse employeeResponse, int numberOfReports) {}
