package com.mindex.challenge.controller;

import com.mindex.challenge.models.employee.compensation.EmployeeCompensationRequest;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationResponse;
import com.mindex.challenge.service.EmployeeCompensationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Tag(
        name = "Employee Compensation",
        description = "Operations related to managing employee compensation details.")
@Validated
public class EmployeeCompensationController {
    private final EmployeeCompensationService employeeCompensationService;

    public EmployeeCompensationController(EmployeeCompensationService employeeCompensationService) {
        this.employeeCompensationService = employeeCompensationService;
    }

    @PostMapping("/{employeeId}/compensation")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(
            summary = "Create compensation for an employee",
            description = "This endpoint creates compensation details for an employee.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Compensation successfully created for the employee",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        array =
                                                @ArraySchema(
                                                        schema =
                                                                @Schema(
                                                                        implementation =
                                                                                EmployeeCompensationResponse
                                                                                        .class)))),
                @ApiResponse(responseCode = "404", description = "Employee not found"),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad request, invalid compensation data")
            })
    public Set<EmployeeCompensationResponse> createCompensationForEmployee(
            @PathVariable UUID employeeId,
            @RequestBody @Valid Set<EmployeeCompensationRequest> employeeCompensationRequest) {
        return employeeCompensationService.createCompensationForEmployee(
                employeeId, employeeCompensationRequest);
    }

    @Operation(
            summary = "Retrieve all compensations for an employee",
            description =
                    "This endpoint retrieves all compensation details for an employee by their"
                            + " employee ID.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved all compensations for the employee",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        array =
                                                @ArraySchema(
                                                        schema =
                                                                @Schema(
                                                                        implementation =
                                                                                EmployeeCompensationResponse
                                                                                        .class)))),
                @ApiResponse(responseCode = "404", description = "Employee not found")
            })
    @GetMapping("/{employeeId}/compensation")
    public Set<EmployeeCompensationResponse> fetchAllCompensationsForEmployee(
            @PathVariable UUID employeeId) {
        return employeeCompensationService.fetchAllCompensationsForEmployee(employeeId);
    }
}
