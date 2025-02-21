package com.mindex.challenge.models.employee;

import jakarta.annotation.Nonnull;
import java.util.UUID;

public record EmployeeResponse(
        @Nonnull UUID employeeId,
        @Nonnull String firstName,
        @Nonnull String lastName,
        @Nonnull String position,
        @Nonnull String department) {}
