package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;


@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Forms a Compensation Object for a specified employee id
     *
     * @param id : employee id
     * @param salary : Salary of the employee
     * @param effectiveDate : Effective Date of an Employee
     * @return : Compensation object
     */
    public Compensation create(String id, float salary, LocalDate effectiveDate){
        LOG.debug("Creating compensation [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        if (salary <= -1) {
            throw new RuntimeException("Invalid salary: " + salary);
        }
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(effectiveDate);

        compensationRepository.insert(compensation);

        return compensation;
    }


    /**
     * Fetches compensation object for specified employee id
     *
     * @param id : employee id
     * @return : Compensation object
     */
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);
        if(employee == null){
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        Compensation compensation = compensationRepository.findCompensationByEmployee(employee);

        if (compensation == null) {
            throw new RuntimeException("Compensation not found for employee: " + id);
        }

        return compensation;
    }

}
