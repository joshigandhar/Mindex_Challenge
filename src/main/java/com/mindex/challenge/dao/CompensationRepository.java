package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
//import org.springframework.stereotype.Repository; // Why?
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CompensationRepository extends MongoRepository<Compensation, String> {
	Compensation findCompensationById(String employeeId);
}
