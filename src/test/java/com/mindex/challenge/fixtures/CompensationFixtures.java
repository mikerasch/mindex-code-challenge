package com.mindex.challenge.fixtures;

import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.entities.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CompensationFixtures {
    public static Compensation buildBaseCompensation() {
        Compensation compensation = new Compensation();
        compensation.setEffectiveDate(LocalDate.now());
        compensation.setSalary(BigDecimal.ONE);
        compensation.setCompensationId(UUID.randomUUID());
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID());
        compensation.setEmployee(employee);
        return compensation;
    }
}
