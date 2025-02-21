package com.mindex.challenge.dao;

import com.mindex.challenge.entities.Compensation;
import java.util.Set;
import java.util.UUID;

public interface EmployeeRepositoryCustom {
    void addCompensationsToEmployee(UUID employeeId, Set<Compensation> compensations);
}
