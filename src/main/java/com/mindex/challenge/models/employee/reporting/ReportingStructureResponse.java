package com.mindex.challenge.models.employee.reporting;

public record ReportingStructureResponse(
        EmployeeWithDirectReportResponse employeeResponse, int numberOfReports) {}
