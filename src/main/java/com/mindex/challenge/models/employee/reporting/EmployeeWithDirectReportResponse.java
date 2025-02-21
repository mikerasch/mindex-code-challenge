package com.mindex.challenge.models.employee.reporting;

import jakarta.annotation.Nonnull;
import java.util.Set;
import java.util.UUID;

public record EmployeeWithDirectReportResponse(
        @Nonnull UUID employeeId,
        @Nonnull String firstName,
        @Nonnull String lastName,
        @Nonnull String position,
        @Nonnull String department,
        Set<EmployeeWithDirectReportResponse> directReports) {}
