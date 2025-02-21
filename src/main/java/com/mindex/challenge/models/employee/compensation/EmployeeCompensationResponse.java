package com.mindex.challenge.models.employee.compensation;

import jakarta.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCompensationResponse(
        @Nonnull UUID employeeId, @Nonnull LocalDate effectiveDate, @Nonnull BigDecimal salary) {}
