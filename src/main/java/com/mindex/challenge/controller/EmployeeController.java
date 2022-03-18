package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure; // added new package
import com.mindex.challenge.data.Compensation; // added new package
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.impl.CompensationService;
import com.mindex.challenge.service.impl.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private CompensationService compensationService;
    

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
    
    //ReportinStructure
    // accept employeeid and return ReportingStructure
    @GetMapping("/employee/{id}/ReportingStructure")
    public ReportingStructure formStructure(@PathVariable("id") String id) {
        LOG.debug("Received employee create request for id [{}]", id); //change log statement

        return reportingStructureService.formStructure(id);
    }


    // Compensation
    @PostMapping("/employee")
    public Compensation create(@RequestBody String id, double salary, Date effectiveDate) {
        LOG.debug("Received employee create request for [{}]", id);

        return compensationService.create(id,salary,effectiveDate);
    }

    @GetMapping("/employee/{id}")
    public Compensation readCompensation(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return compensationService.read(id);
    }
    
}
