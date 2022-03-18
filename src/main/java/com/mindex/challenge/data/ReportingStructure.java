package com.mindex.challenge.data;


public class ReportingStructure {
	
	
	// fields
	private Employee employee;
	private int numberOfReports; // long or int?
	
	public ReportingStructure(Employee employee, int numberOfReports) {
		
		this.employee = employee;
		this.numberOfReports = numberOfReports;
	}
		


	public int getNumberOfReports() {
		return numberOfReports;
	}


	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	
	
}
