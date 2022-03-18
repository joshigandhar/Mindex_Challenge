package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure; // added new package
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CompensationRepository compensationRepository;
    

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    
    
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
    private ReportingStructure employeeStructure(String id,Employee employee) {
    	
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
