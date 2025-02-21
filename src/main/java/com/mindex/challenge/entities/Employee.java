package com.mindex.challenge.entities;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collation = "employees")
public class Employee {
    // Spring will handle as String internally
    @MongoId(value = FieldType.STRING)
    private UUID employeeId;

    private String firstName;
    private String lastName;
    private String position;
    private String department;

    @DBRef(lazy = true)
    private Set<Employee> directReports;

    @DBRef(lazy = true)
    private Set<Compensation> compensations;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(Set<Employee> directReports) {
        this.directReports = directReports;
    }

    public Set<Compensation> getCompensations() {
        return compensations;
    }

    public void setCompensations(Set<Compensation> compensations) {
        this.compensations = compensations;
    }
}
