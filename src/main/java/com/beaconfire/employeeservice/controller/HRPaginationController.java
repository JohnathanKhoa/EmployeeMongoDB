package com.beaconfire.employeeservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beaconfire.employeeservice.entity.Employee;
import com.beaconfire.employeeservice.service.HRPaginationService;



@RestController
@RequestMapping("/api")
public class HRPaginationController {

	@Autowired
	private HRPaginationService hrService;
	
	@GetMapping("/hr/status-tracking-home/{page}") //HR Home Page
	public ResponseEntity<PageImpl<Employee>> getActiveVisas(@PathVariable int page) {
		List<Employee> temp = hrService.findEmployeesByActiveVisa();
		return ResponseEntity.ok(new PageImpl<Employee>(temp, PageRequest.of(page, 3), temp.size()));
	}

	@GetMapping("/hr/employee-profiles/{page}") //HR Employee Profile
	public ResponseEntity<List<Employee>> getEmployee(@PathVariable int page, @RequestParam(required = false) String firstName, @RequestParam(required = false) String email) {
		return ResponseEntity.ok(hrService.findByFirstNameOrEmail(page, firstName, email));
	}
	
}
