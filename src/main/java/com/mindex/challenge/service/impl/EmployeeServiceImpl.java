package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.EmployeeRepositoryCustom;
import com.mindex.challenge.entities.Compensation;
import com.mindex.challenge.entities.Employee;
import com.mindex.challenge.exceptions.ResourceNotFoundException;
import com.mindex.challenge.models.employee.EmployeeCreationRequest;
import com.mindex.challenge.models.employee.EmployeeResponse;
import com.mindex.challenge.models.employee.EmployeeUpdateRequest;
import com.mindex.challenge.models.employee.reporting.EmployeeWithDirectReportResponse;
import com.mindex.challenge.service.EmployeeService;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final ConversionService conversionService;

    private final EmployeeRepository employeeRepository;

    private final EmployeeRepositoryCustom employeeRepositoryCustom;

    public EmployeeServiceImpl(
            ConversionService conversionService,
            EmployeeRepository employeeRepository,
            EmployeeRepositoryCustom employeeRepositoryCustom) {
        this.conversionService = conversionService;
        this.employeeRepository = employeeRepository;
        this.employeeRepositoryCustom = employeeRepositoryCustom;
    }

    /**
     * Creates a new employee based on the provided creation request and saves it to the repository.
     *
     * @param creationRequest {@link EmployeeCreationRequest} the details of the employee to be created
     * @return {@link EmployeeResponse}
     */
    @Override
    public EmployeeResponse create(@Nonnull EmployeeCreationRequest creationRequest) {
        LOG.debug("Creating employee [{}]", creationRequest);
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID());
        employee.setFirstName(creationRequest.firstName());
        employee.setLastName(creationRequest.lastName());
        employee.setPosition(creationRequest.position());
        employee.setDepartment(creationRequest.department());

        employeeRepository.insert(employee);

        return conversionService.convert(employee, EmployeeResponse.class);
    }

    /**
     * Fetches the employee details based on the provided unique identifier.
     *
     * @param id the unique identifier for the employee
     * @return {@link EmployeeResponse}
     */
    @Override
    public EmployeeResponse fetch(@Nonnull UUID id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = findByIdOrElseThrow(id);

        return conversionService.convert(employee, EmployeeResponse.class);
    }

    /**
     * Fetches the employee details along with their direct reports.
     *
     * @param id the unique identifier for the employee
     * @return {@link EmployeeWithDirectReportResponse}
     */
    @Override
    public EmployeeWithDirectReportResponse fetchEmployeeWithDirectReports(@Nonnull UUID id) {
        LOG.debug("Reading employee with direct reports with id [{}]", id);

        Employee employee = findByIdOrElseThrow(id);

        return conversionService.convert(employee, EmployeeWithDirectReportResponse.class);
    }

    /**
     * Checks if an employee exists based on the provided unique identifier.
     *
     * @param id the unique identifier for the employee
     */
    @Override
    public boolean exists(@Nonnull UUID id) {
        return employeeRepository.existsById(id);
    }

    /**
     * Updates an existing employee's details based on the provided employee ID and update request.
     * Only the fields that are present in the update request will be modified.
     *
     * @param employeeId the unique identifier for the employee to be updated
     * @param employeeUpdateRequest the details to be updated for the employee
     * @return {@link EmployeeResponse}
     */
    @Override
    public EmployeeResponse update(
            @Nonnull UUID employeeId, @Nonnull EmployeeUpdateRequest employeeUpdateRequest) {
        LOG.debug("Updating employee [{}]", employeeUpdateRequest);

        Employee employee = findByIdOrElseThrow(employeeId);

        Optional.ofNullable(employeeUpdateRequest.firstName()).ifPresent(employee::setFirstName);
        Optional.ofNullable(employeeUpdateRequest.lastName()).ifPresent(employee::setLastName);
        Optional.ofNullable(employeeUpdateRequest.position()).ifPresent(employee::setPosition);
        Optional.ofNullable(employeeUpdateRequest.department()).ifPresent(employee::setDepartment);

        return conversionService.convert(employeeRepository.save(employee), EmployeeResponse.class);
    }

    /**
     * Appends a set of compensations to the employee.
     *
     * @param employeeId the unique identifier for the employee
     * @param compensations the compensations to be appended to the employee
     */
    @Override
    public void appendCompensations(
            @Nonnull UUID employeeId, @Nonnull Set<Compensation> compensations) {
        LOG.debug("Appending compensations [{}] to employee {}", compensations, employeeId);
        employeeRepositoryCustom.addCompensationsToEmployee(employeeId, compensations);
    }

    private Employee findByIdOrElseThrow(UUID id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Employee with id %s not found.".formatted(id)));
    }
}
