package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Employee;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Get all Employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//Add Employee To Table
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//Get By ID
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		 Employee employee = employeeRepository.findById(id).orElseThrow(() -> 
		 new ResourceNotFoundException("Employee not exist with id:" +id));
		return ResponseEntity.ok(employee);
	}
	
	//Update Employee List
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		 Employee employee = employeeRepository.findById(id).orElseThrow(() -> 
		 new ResourceNotFoundException("Employee not exist with id:" +id));
		 
		 employee.setFirstName(employeeDetails.getFirstName());
		 employee.setLastName(employeeDetails.getLastName());
		 employee.setEmailId(employeeDetails.getEmailId());
		 
		 Employee updateEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee);
	}
	
	//delete Employee
	@DeleteMapping("employees/{id}")
	public ResponseEntity <Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
		
		employeeRepository.delete(employee);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	

}
