package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * Returns ReportingStructure based on employee object and count for numberOfReports
     *
     * @param id : employee id
     * @return : ReportingStructure
     */
    @Override
    public ReportingStructure create(String id) {
        LOG.debug("Creating employee direct report [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        // checks if employee exists
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // Call for countReport to fetch integer count of numberOfReports
        int numberOfReports =  countReports(employee);


        ReportingStructure reportingStructure = new ReportingStructure(employee,numberOfReports);



        return reportingStructure;
    }


    /**
     *
     * Calculates employeeReportCount by enqueueing subordinate employee  and dequeuing current or senior employee
     * in the hierarchy
     *
     * @param employee : Employee type evaluated from employee id
     * @return : fully filled employee reporting structure
     */
    public int countReports(Employee employee){
        LOG.debug("Evaluating employee direct report [{}]", employee.getEmployeeId());

        // Hold Employee in queue
        Queue<Employee> employeeQueue = new ArrayDeque<>();
        employeeQueue.add(employee);

        // HashSet to ensure every employee is visited once
        HashSet<Employee> visitedEmployee = new HashSet<>();
        visitedEmployee.add(employee);

        // Count for the number of employee's reporting beneath the specified employee
        int employeeReportCount = 0;


        while(!employeeQueue.isEmpty()){

            // reports : employee in the queue
            Employee reports = employeeQueue.remove();

            // Ensures employee that are reported are considered
            if(reports.getDirectReports() != null){

                //subordinate : subordinate employee that reports to current employee in the hirerachy
                for(Employee subordinate : reports.getDirectReports()){

                    // HashSet to check for visited employee
                    if( !visitedEmployee.contains(subordinate)){
                        visitedEmployee.add(subordinate);
                        employeeQueue.add(subordinate);
                        employeeReportCount++;

                        // Populate the reporting structure with employee details
                        populateEmployee(subordinate,subordinate.getEmployeeId());
                    }
                }
            }

        }

        return employeeReportCount;
    }


    /**
     *
     * @param employee : employee object with empty fields except employee id
     * @param id : employee
     */
    public void populateEmployee(Employee employee,String id){

            Employee employeeComplete = employeeRepository.findByEmployeeId(id);

            employee.setFirstName(employeeComplete.getFirstName());
            employee.setLastName(employeeComplete.getLastName());
            employee.setPosition(employeeComplete.getPosition());
            employee.setDepartment(employeeComplete.getDepartment());
            employee.setDirectReports(employeeComplete.getDirectReports());


    }


}
