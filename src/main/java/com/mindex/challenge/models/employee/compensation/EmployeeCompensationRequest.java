package com.mindex.challenge.models.employee.compensation;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeCompensationRequest(
        LocalDate effectiveDate, @Min(value = 1) BigDecimal salary) {}
