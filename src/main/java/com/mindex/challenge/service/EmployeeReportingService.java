package com.mindex.challenge.service;

import com.mindex.challenge.models.employee.reporting.ReportingStructureResponse;
import java.util.UUID;

public interface EmployeeReportingService {
    ReportingStructureResponse fetchDirectReports(UUID employeeId);
}
