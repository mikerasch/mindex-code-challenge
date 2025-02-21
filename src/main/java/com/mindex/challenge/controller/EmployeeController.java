package com.mindex.challenge.controller;

import com.mindex.challenge.models.employee.EmployeeCreationRequest;
import com.mindex.challenge.models.employee.EmployeeResponse;
import com.mindex.challenge.models.employee.EmployeeUpdateRequest;
import com.mindex.challenge.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Tag(
        name = "Employee Management",
        description =
                "Operations related to only base level employee information. For further access"
                        + " into employee information, see other endpoints.")
@Validated
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Create a new employee", description = "Creates a new employee.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Employee successfully created",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = EmployeeResponse.class))),
                @ApiResponse(responseCode = "400", description = "Bad request, invalid input data")
            })
    public EmployeeResponse create(
            @RequestBody @Valid EmployeeCreationRequest employeeCreationRequest) {
        LOG.debug("Received employee create request for [{}]", employeeCreationRequest);

        return employeeService.create(employeeCreationRequest);
    }

    @GetMapping("/{employeeId}")
    @Operation(
            summary = "Retrieve an employee by ID",
            description =
                    "This endpoint retrieves the details of an employee based on their unique"
                            + " employee ID.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Employee successfully retrieved",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = EmployeeResponse.class))),
                @ApiResponse(responseCode = "404", description = "Employee not found"),
                @ApiResponse(responseCode = "400", description = "Bad request")
            })
    public EmployeeResponse read(@PathVariable UUID employeeId) {
        LOG.debug("Received employee create request for employeeId [{}]", employeeId);

        return employeeService.fetch(employeeId);
    }

    @PatchMapping("/{employeeId}")
    @Operation(
            summary = "Partial update of an employee",
            description =
                    "This endpoint allows partial updates to an employee."
                            + " If a field is passed as null, that field will not be updated. "
                            + "Only the non-null fields will be updated.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Employee successfully updated",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = EmployeeResponse.class))),
                @ApiResponse(responseCode = "404", description = "Employee not found"),
                @ApiResponse(responseCode = "400", description = "Bad request")
            })
    public EmployeeResponse update(
            @PathVariable UUID employeeId,
            @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        LOG.debug(
                "Received employee update request for employeeId [{}] and employee [{}]",
                employeeId,
                employeeUpdateRequest);

        return employeeService.update(employeeId, employeeUpdateRequest);
    }
}
