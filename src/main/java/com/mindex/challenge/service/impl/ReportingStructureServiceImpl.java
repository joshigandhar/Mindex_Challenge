package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;
    /// Invoke's employeeStructure method
    @Override
    public ReportingStructure formStructure(String id) {
        LOG.debug("Evaulating employee direct report [{}]", id); //change

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employeeStructure(id,employee);
    }

    //Builds Employee Structure for numberOfReports
    public ReportingStructure employeeStructure(String id,Employee employee) {

        ///create employee structure

        //ReportingStructure Object
        ReportingStructure reportingStructure = null;

        //To visit each subordinate employee once.
        HashSet<Employee> hashSet = new HashSet<>();

        List<Employee> list = employee.getDirectReports();

        // new child list to make sure original list reference is not altered, add new distinct employees
        List<Employee> subordinate = new ArrayList<>();

        //Initialize the subordinate list with first employee's direct subordinate
        for(Employee temp : list) {
            subordinate.add(temp);
        }


        // Given employee added
        hashSet.add(employee);

        // Iterative BFS approach to traverse the structure
        for(Employee temp : subordinate) {

            if(!(hashSet.contains(temp))) {
                hashSet.add(temp);
                // add each employee's direct subordinates into the current list
                for(Employee successor : temp.getDirectReports() ) {
                    subordinate.add(successor);
                }

            }
        }

        reportingStructure = new ReportingStructure(employee,hashSet.size()-1);

        return reportingStructure;
    }
}
