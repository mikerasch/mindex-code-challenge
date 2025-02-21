package com.mindex.challenge.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collation = "compensations")
public class Compensation {
    // Spring will handle as String internally
    @MongoId(value = FieldType.STRING)
    private UUID compensationId;

    private BigDecimal salary;
    private LocalDate effectiveDate;

    @DBRef(lazy = true)
    private Employee employee;

    public UUID getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(UUID compensationId) {
        this.compensationId = compensationId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
