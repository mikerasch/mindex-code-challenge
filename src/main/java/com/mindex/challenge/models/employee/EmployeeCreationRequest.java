package com.mindex.challenge.models.employee;

import jakarta.validation.constraints.NotNull;

public record EmployeeCreationRequest(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String position,
        @NotNull String department) {}
