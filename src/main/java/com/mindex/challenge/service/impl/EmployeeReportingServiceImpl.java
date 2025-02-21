package com.mindex.challenge.service.impl;

import com.mindex.challenge.models.employee.reporting.EmployeeWithDirectReportResponse;
import com.mindex.challenge.models.employee.reporting.ReportingStructureResponse;
import com.mindex.challenge.service.EmployeeReportingService;
import com.mindex.challenge.service.EmployeeService;
import jakarta.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeReportingServiceImpl implements EmployeeReportingService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeReportingServiceImpl.class);

    private final EmployeeService employeeService;

    public EmployeeReportingServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Fetches the direct reports of an employee, including the total count of direct reports in the hierarchy.
     * This method starts from a given employee and traverses through their direct reports,
     * ensuring no employee is counted twice.
     *
     * @param employeeId Unique Identifier for employee collection
     * @return a {@link ReportingStructureResponse}
     */
    @Override
    public ReportingStructureResponse fetchDirectReports(@Nonnull UUID employeeId) {
        EmployeeWithDirectReportResponse employee =
                employeeService.fetchEmployeeWithDirectReports(employeeId);

        Queue<EmployeeWithDirectReportResponse> employeeQueue = new ArrayDeque<>();
        employeeQueue.add(employee);
        Set<UUID> seenEmployees = new HashSet<>();
        // Start at -1 because employee can't be direct employee of him/herself.
        int countOfDirectReports = -1;

        while (!employeeQueue.isEmpty()) {
            EmployeeWithDirectReportResponse underlingEmployee = employeeQueue.poll();
            if (seenEmployees.contains(underlingEmployee.employeeId())) {
                LOG.info("While fetching direct reports for employee {}, an acyclic relationship was spotted.", employeeId);
                continue;
            }
            seenEmployees.add(underlingEmployee.employeeId());
            if (underlingEmployee.directReports() != null) {
                employeeQueue.addAll(underlingEmployee.directReports());
            }
            countOfDirectReports++;
        }
        return new ReportingStructureResponse(employee, countOfDirectReports);
    }
}
