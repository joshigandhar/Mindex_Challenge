package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


@Service
public class CompensationServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    //@Override
    public Compensation create(String id, double salary, Date effectiveDate) {
        LOG.debug("Creating compensation [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(effectiveDate);

        compensationRepository.insert(compensation);

        return compensation;
    }


    //@Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);

        Compensation compensation = compensationRepository.findCompensationById(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compensation;
    }

}
