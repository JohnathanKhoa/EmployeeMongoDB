package com.beaconfire.employeeservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.beaconfire.employeeservice.dao.HRPaginationRepository;
import com.beaconfire.employeeservice.entity.Employee;
import com.beaconfire.employeeservice.entity.VisaStatus;

@Service
public class HRPaginationService {


	private HRPaginationRepository hrRepository;
	private EmployeeService employeeService;

	
	@Autowired
	public HRPaginationService(HRPaginationRepository hrRepository, EmployeeService employeeService) {
		this.hrRepository = hrRepository;
		this.employeeService = employeeService;
	}
	
	
	public List<Employee> findEmployeesByActiveVisa(){
		List<Employee> preFilteredEmployees = employeeService.getAllEmployees();
		return filterEmployeeByVisaStatus(preFilteredEmployees);

	}
	
	public List<Employee> findByFirstNameOrEmail(int page, String firstName, String email) {
		return hrRepository.findByFirstNameOrEmail(firstName, email, PageRequest.of(page, 3, Sort.unsorted()));
	}
	
	public List<Employee> filterEmployeeByVisaStatus(List<Employee> preFilteredEmployees) {
		List<Employee> activeVisaEmployees = new ArrayList<Employee>();
		for (Employee e : preFilteredEmployees) {
			List<VisaStatus> visaStatus = e.getVisaStatus();
			boolean hasFlag = false;
			for (VisaStatus v : visaStatus) {
				if (v.isActiveFlag() == true) {
					hasFlag = true;
				}
			}
			if (hasFlag == true) {
				activeVisaEmployees.add(e);
			}
		}
		return activeVisaEmployees;
	}

}
