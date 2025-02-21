package com.mindex.challenge.converters;

import com.mindex.challenge.entities.Employee;
import com.mindex.challenge.models.employee.reporting.EmployeeWithDirectReportResponse;
import jakarta.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeWithDirectReportResponseConverter
        implements Converter<Employee, EmployeeWithDirectReportResponse> {

    @Override
    public EmployeeWithDirectReportResponse convert(@Nonnull Employee source) {
        return convertHelper(source, new HashSet<>());
    }

    private EmployeeWithDirectReportResponse convertHelper(
            Employee source, Set<UUID> processedEmployeeIds) {
        if (processedEmployeeIds.contains(source.getEmployeeId())) {
            return null;
        }

        processedEmployeeIds.add(source.getEmployeeId());

        Set<EmployeeWithDirectReportResponse> employeeResponses =
                (source.getDirectReports() == null
                        ? new HashSet<>()
                        : source.getDirectReports().stream()
                                .map(
                                        directReport ->
                                                convertHelper(directReport, processedEmployeeIds))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet()));

        return new EmployeeWithDirectReportResponse(
                source.getEmployeeId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPosition(),
                source.getDepartment(),
                employeeResponses);
    }
}
