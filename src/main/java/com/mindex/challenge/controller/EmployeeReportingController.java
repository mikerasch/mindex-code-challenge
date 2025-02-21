package com.mindex.challenge.controller;

import com.mindex.challenge.models.employee.reporting.ReportingStructureResponse;
import com.mindex.challenge.service.EmployeeReportingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Tag(
        name = "Employee Reporting",
        description = "Operations related to managing employee reporting.")
public class EmployeeReportingController {
    private final EmployeeReportingService employeeReportingService;

    public EmployeeReportingController(EmployeeReportingService employeeReportingService) {
        this.employeeReportingService = employeeReportingService;
    }

    @GetMapping("/{employeeId}/direct-reports")
    @Operation(
            summary = "Fetch direct reports of an employee",
            description =
                    "Fetch the direct reports for a given employee by their employee ID. Handles"
                            + " acyclic relationships gracefully.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully fetched direct reports"),
                @ApiResponse(responseCode = "404", description = "Employee not found"),
            })
    public ReportingStructureResponse fetchDirectReports(@PathVariable UUID employeeId) {
        return employeeReportingService.fetchDirectReports(employeeId);
    }
}
