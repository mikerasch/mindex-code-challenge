package com.mindex.challenge.service;

import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.models.employee.EmployeeCreationRequest;
import com.mindex.challenge.models.employee.EmployeeResponse;
import com.mindex.challenge.models.employee.EmployeeUpdateRequest;
import com.mindex.challenge.models.employee.reporting.EmployeeWithDirectReportResponse;
import java.util.Set;
import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse create(EmployeeCreationRequest employeeCreationRequest);

    EmployeeResponse fetch(UUID id);

    boolean exists(UUID id);

    EmployeeResponse update(UUID employeeId, EmployeeUpdateRequest employeeUpdateRequest);

    void appendCompensations(UUID employeeId, Set<Compensation> compensations);

    EmployeeWithDirectReportResponse fetchEmployeeWithDirectReports(UUID id);
}
