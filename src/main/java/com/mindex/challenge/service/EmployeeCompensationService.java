package com.mindex.challenge.service;

import com.mindex.challenge.models.employee.compensation.EmployeeCompensationRequest;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationResponse;
import java.util.Set;
import java.util.UUID;

public interface EmployeeCompensationService {
    Set<EmployeeCompensationResponse> createCompensationForEmployee(
            UUID employeeId, Set<EmployeeCompensationRequest> employeeCompensationRequest);

    Set<EmployeeCompensationResponse> fetchAllCompensationsForEmployee(UUID employeeId);
}
