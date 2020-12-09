package com.tcs.employeespringsecurity.security.services;

import java.util.List;
import java.util.Optional;

import com.tcs.employeespringsecurity.models.Employee;

public interface EmployeeService {
	
	
	public Employee addEmployee(Employee emp);
	public String updateEmployee(Employee usr);
	public void deleteEmployee(long id);
	public Optional<Employee> findById(long id);
	public Optional<List<Employee>> getEmployees();
	public Optional<List<Employee>> findByOrganizationId(long id);
	

}
