package com.mindex.challenge.converters;

import com.mindex.challenge.entities.Employee;
import com.mindex.challenge.models.employee.EmployeeResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeResponseConverter implements Converter<Employee, EmployeeResponse> {

    @Override
    public EmployeeResponse convert(Employee source) {
        return new EmployeeResponse(
                source.getEmployeeId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPosition(),
                source.getDepartment());
    }
}
