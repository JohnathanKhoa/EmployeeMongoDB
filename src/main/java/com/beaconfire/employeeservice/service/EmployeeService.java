package com.beaconfire.employeeservice.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beaconfire.employeeservice.dao.EmployeeRepository;
import com.beaconfire.employeeservice.entity.Employee;

@Service
public class EmployeeService {

	@Autowired 
	private EmployeeRepository employeeRepository;
	
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	public Employee getEmployeeById(String id) {
    	return employeeRepository.findById(id).get();
    }
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public void deleteEmployee(String id) {
    	employeeRepository.deleteById(id);
    }
	public Employee getEmployeeByUserID(Integer userID) {
		return employeeRepository.findByuserId(userID).get();
	}
}

