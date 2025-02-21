package com.mindex.challenge.models.employee.compensation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCompensationResponse(
        UUID employeeId, LocalDate effectiveDate, BigDecimal salary) {}
