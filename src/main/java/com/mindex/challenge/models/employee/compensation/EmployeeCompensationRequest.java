package com.mindex.challenge.models.employee.compensation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeCompensationRequest(
        @NotNull LocalDate effectiveDate, @NotNull @Min(value = 1) BigDecimal salary) {}
