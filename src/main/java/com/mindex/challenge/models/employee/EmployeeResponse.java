package com.mindex.challenge.models.employee;

import java.util.UUID;

public record EmployeeResponse(
        UUID employeeId, String firstName, String lastName, String position, String department) {}
