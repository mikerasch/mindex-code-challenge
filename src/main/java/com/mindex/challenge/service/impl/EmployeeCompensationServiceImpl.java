package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.entities.Employee;
import com.mindex.challenge.exceptions.ResourceNotFoundException;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationRequest;
import com.mindex.challenge.models.employee.compensation.EmployeeCompensationResponse;
import com.mindex.challenge.service.EmployeeCompensationService;
import com.mindex.challenge.service.EmployeeService;
import jakarta.annotation.Nonnull;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCompensationServiceImpl implements EmployeeCompensationService {
    private final EmployeeService employeeService;
    private final CompensationRepository compensationRepository;
    private static final Logger LOG =
            LoggerFactory.getLogger(EmployeeCompensationServiceImpl.class);

    public EmployeeCompensationServiceImpl(
            EmployeeService employeeService, CompensationRepository compensationRepository) {
        this.employeeService = employeeService;
        this.compensationRepository = compensationRepository;
    }

    /**
     * Creates compensation for employee. This will persist the changes in both
     * the employee and compensation collection.
     * @param employeeId Unique Identifier for employee collection
     * @param employeeCompensationRequest {@link EmployeeCompensationRequest} which will be used to create a record.
     * @return A set of {@link EmployeeCompensationResponse}
     */
    @Override
    public Set<EmployeeCompensationResponse> createCompensationForEmployee(
            @Nonnull UUID employeeId,
            @Nonnull Set<EmployeeCompensationRequest> employeeCompensationRequest) {
        Employee employee = retrieveEmployeeId(employeeId);
        Set<Compensation> compensations =
                buildCompensationsFromRequest(employeeCompensationRequest, employee);

        compensationRepository.saveAll(compensations);
        employeeService.appendCompensations(employeeId, compensations);

        LOG.info(
                "Saved #{} compensations for employee with ID: {}",
                compensations.size(),
                employeeId);

        return buildEmployeeCompensationResponse(employeeId, compensations);
    }

    /**
     * Fetches all compensations for an employee.
     * This will **NOT** fetch information related to employee, just the ID.
     * @param employeeId Unique Identifier for employee collection
     * @return A set of {@link EmployeeCompensationResponse}
     */
    @Override
    public Set<EmployeeCompensationResponse> fetchAllCompensationsForEmployee(
            @Nonnull UUID employeeId) {
        Set<Compensation> compensationList = compensationRepository.findAllByEmployeeId(employeeId);
        return buildEmployeeCompensationResponse(employeeId, compensationList);
    }

    /**
     * Retrieves a base level Employee entity with **ONLY** the id filled in.
     * If employee does not exist, this will throw a ResourceNotFoundException.
     */
    private Employee retrieveEmployeeId(UUID employeeId) {
        boolean employeeExists = employeeService.exists(employeeId);

        if (!employeeExists) {
            throw new ResourceNotFoundException(
                    "Employee with id %s does not exist".formatted(employeeId));
        }

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        return employee;
    }

    private Set<Compensation> buildCompensationsFromRequest(
            Set<EmployeeCompensationRequest> employeeCompensationRequest, Employee employee) {
        return employeeCompensationRequest.stream()
                .map(
                        compensationRequest -> {
                            Compensation compensation = new Compensation();
                            compensation.setCompensationId(UUID.randomUUID());
                            compensation.setEffectiveDate(compensationRequest.effectiveDate());
                            compensation.setSalary(compensationRequest.salary());
                            compensation.setEmployee(employee);
                            return compensation;
                        })
                .collect(Collectors.toSet());
    }

    private static Set<EmployeeCompensationResponse> buildEmployeeCompensationResponse(
            UUID employeeId, Set<Compensation> compensations) {
        return compensations.stream()
                .map(
                        compensation ->
                                new EmployeeCompensationResponse(
                                        employeeId,
                                        compensation.getEffectiveDate(),
                                        compensation.getSalary()))
                .collect(Collectors.toSet());
    }
}
