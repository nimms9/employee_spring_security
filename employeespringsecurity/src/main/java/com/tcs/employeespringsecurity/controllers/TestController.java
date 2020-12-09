package com.tcs.employeespringsecurity.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcs.employeespringsecurity.exception.ResourceNotFoundException;
import com.tcs.employeespringsecurity.models.Employee;
import com.tcs.employeespringsecurity.security.services.EmployeeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	
	@Autowired
	EmployeeService employeeService;
	
	
	
	/*
	 * @GetMapping("/all") public String allAccess() { return "Public Content."; }
	 */
	
	
	  @GetMapping("/user")
	  
	  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	  public String userAccess() { return "User Content."; }
	 


	/*
	 * @GetMapping("/admin")
	 * 
	 * @PreAuthorize("hasRole('ADMIN')") public String adminAccess() { return
	 * "Admin Board."; }
	 */
	
	
	
	@GetMapping("/all")
	public List<Employee> getEmployees() {
		return employeeService.getEmployees().get();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int employeeId)  throws ResourceNotFoundException{
		
		Employee employee= employeeService.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
		return ResponseEntity.ok().body(employee);
	
	
	}
	
	@GetMapping("/emp/{id}")
	public ResponseEntity<List<Employee>> getEmployeeOrganizationById(@PathVariable("id") int organizationId)  throws ResourceNotFoundException{
		
		List<Employee> employee= employeeService.findByOrganizationId(organizationId).orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
		return ResponseEntity.ok().body(employee);
	
	
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee, UriComponentsBuilder uriComponentsBuilder,HttpServletRequest request) {
		
		Employee employee2=employeeService.addEmployee(employee);
		UriComponents uriComponents = uriComponentsBuilder.path(request.getRequestURI()+"/{id}").buildAndExpand(employee2.getId());
		return ResponseEntity.created(uriComponents.toUri()).body(employee2);
		
	}
	
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteEmployeeById(@PathVariable int id) throws ResourceNotFoundException {
		Employee employee= employeeService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
		
		
		employeeService.deleteEmployee(id);
		
		HashMap<String, Boolean> hashMap=new HashMap<>();
		hashMap.put("deleted", true);
		return hashMap;
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id,
		@Valid @RequestBody	Employee employee) throws ResourceNotFoundException{
		
		Employee employee2= employeeService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found"));
		employee.setId((long) id);
		Employee employee3 = employeeService.addEmployee(employee);
		return ResponseEntity.ok(employee3);
	}
	
}
