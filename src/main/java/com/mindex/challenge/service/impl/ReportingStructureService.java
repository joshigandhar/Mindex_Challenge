package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

public interface ReportingStructureService {
    ReportingStructure formStructure(String id);
    ReportingStructure employeeStructure(String id, Employee employee);
}
