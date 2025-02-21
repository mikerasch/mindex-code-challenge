package com.mindex.challenge.models.employee;

import jakarta.annotation.Nullable;

/**
 * All fields are nullable because the update only applies to the fields that are non-null.
 * Fields with null values will not modify the corresponding employee data.
 */
public record EmployeeUpdateRequest(
        @Nullable String firstName,
        @Nullable String lastName,
        @Nullable String position,
        @Nullable String department) {}
