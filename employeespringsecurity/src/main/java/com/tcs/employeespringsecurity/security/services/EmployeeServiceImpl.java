package com.tcs.employeespringsecurity.security.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tcs.employeespringsecurity.models.Employee;
import com.tcs.employeespringsecurity.repository.EmployeeRepository;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	

	
	@Override
	public Employee addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Employee emp2=null;
		try {
		emp2=employeeRepository.save(employee);
		return emp2;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public String updateEmployee(Employee emp) {
		// TODO Auto-generated method stub
		Optional<Employee> emp3=null;
		try {
		emp3=employeeRepository.findById(emp.getId());
		if(emp3.isPresent()) {
			emp3.get().setAge(emp.getAge());
			emp3.get().setPosition(emp.getPosition());
			employeeRepository.save(emp3.get());
			return "success";
		}
		
		return "fail";
		} catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	public void deleteEmployee(long id) {
		// TODO Auto-generated method stub
		 employeeRepository.deleteById(id);
	}

	@Override
	public Optional<Employee> findById(long id) {
		// TODO Auto-generated method stub
		return employeeRepository.findById(id);
	}

	@Override
	public Optional<List<Employee>> getEmployees() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(employeeRepository.findAll());
	}

	@Override
	public Optional<List<Employee>> findByOrganizationId(long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(employeeRepository.findByOrganizationId(id));
	}

}
