package com.beaconfire.employeeservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.beaconfire.employeeservice.entity.Address;
import com.beaconfire.employeeservice.entity.Contact;
import com.beaconfire.employeeservice.entity.Employee;
import com.beaconfire.employeeservice.entity.PersonalDocument;
import com.beaconfire.employeeservice.entity.StatusNotification;
import com.beaconfire.employeeservice.entity.VisaStatus;
import com.beaconfire.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		//TODO: We need to write validation method to validate all mandatory fields

		Employee createdEmployee = employeeService.saveEmployee(employee);
		return ResponseEntity.ok(createdEmployee);
	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable String employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		return ResponseEntity.ok(employee);
	}
	
	
	@PostMapping("/employee/personal-document")
	public ResponseEntity<Employee> createAddress( @RequestParam("employeeId") String employeeId, @RequestBody PersonalDocument personalDocument) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<PersonalDocument> tempList = fetchedEmployee.getPersonalDocument();
		tempList.add(personalDocument);
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@PutMapping("/employee/personal-document")
	public ResponseEntity<Employee> updatePersonalDocument( @RequestParam("employeeId") String employeeId, @RequestBody PersonalDocument personalDocument) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<PersonalDocument> personalDocuments = fetchedEmployee.getPersonalDocument();
		// fetchedEmployee will be updated in the for loop
		for (int i = 0; i < personalDocuments.size(); i++) {
			if (personalDocuments.get(i).getId().equals(personalDocument.getId())) {
				personalDocuments.set(i, personalDocument);
			}
		}
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}

	
	@PostMapping("/employee/contact")
	public ResponseEntity<Employee> createContact( @RequestParam("employeeId") String employeeId, @RequestBody Contact contact) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<Contact> tempList = fetchedEmployee.getContact();
		tempList.add(contact);
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@PutMapping("/employee/contact")
	public ResponseEntity<Employee> updateContact( @RequestParam("employeeId") String employeeId, @RequestBody Contact contact) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<Contact> tempList = fetchedEmployee.getContact();
		// fetchedEmployee will be updated in the for loop
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(contact.getId())) {
				tempList.set(i, contact);
			}
		}
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}

	
	@PostMapping("/employee/visa-status")
	public ResponseEntity<Employee> createVisaStatus( @RequestParam("employeeId") String employeeId, @RequestBody VisaStatus visaStatus) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<VisaStatus> tempList = fetchedEmployee.getVisaStatus();
		tempList.add(visaStatus);
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@PutMapping("/employee/visa-status")
	public ResponseEntity<Employee> updateVisaStatus( @RequestParam("employeeId") String employeeId, @RequestBody VisaStatus visaStatus) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<VisaStatus> tempList = fetchedEmployee.getVisaStatus();
		// fetchedEmployee will be updated in the for loop
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(visaStatus.getId())) {
				tempList.set(i, visaStatus);
			}
		}
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@GetMapping("/employee/visa-status-notification")
	public ResponseEntity<StatusNotification> checkVisaStatusNotification(@RequestParam("employeeId") String employeeId) {
		StatusNotification notification = new StatusNotification();
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<VisaStatus> tempList = fetchedEmployee.getVisaStatus();
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).isActiveFlag() == true) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
				long daysDiff = 0;
				try {
					Date fetchedEndDate = sdf.parse(tempList.get(i).getEndDate());
					Date currentDate = new Date(System.currentTimeMillis());
					System.out.println("endDate / current" + fetchedEndDate + " / " + currentDate);
					long timeDiff = Math.abs(currentDate.getTime() - fetchedEndDate.getTime());
					daysDiff = TimeUnit.DAYS.convert(timeDiff,TimeUnit.MILLISECONDS);
					System.out.println(daysDiff);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String tempStatus = tempList.get(i).getVisaType();
				switch (tempStatus) {
					case "OPT Receipt" : notification.setNotification("Please upload a copy of your OPT EAD");
						break;
					case "OPT STEM Receipt" : notification.setNotification("Please upload a copy of your OPT STEM EAD");
						break;	
					case "OPT EAD" : if (daysDiff < 100) {
						notification.setNotification("Please download and fill your I-983 form");
						break;
					} else {notification.setNotification("Visa Status OK."); break;}
					case "I-983 Unsigned" : notification.setNotification("Waiting for HR to approve and sign I-983");
						break;
					case "I-983 Signed" : notification.setNotification("Please send the I-983 with all necessary documents to your school and upload the new I-20");
						break;
					case "I-20" : notification.setNotification("Please upload your OPT STEM Receipt");
						break;
					case "OPT STEM EAD" : notification.setNotification("Visa Status OK.");
						break;
				}
				return ResponseEntity.ok(notification);
			}
		}
		notification.setNotification("Visa Status OK.");
		return ResponseEntity.ok(notification);
	}
	
	@PostMapping("/employee/address")
	public ResponseEntity<Employee> createAddress( @RequestParam("employeeId") String employeeId, @RequestBody Address address) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<Address> tempList = fetchedEmployee.getAddress();
		tempList.add(address);
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@PutMapping("/employee/address")
	public ResponseEntity<Employee> updateAddress( @RequestParam("employeeId") String employeeId, @RequestBody Address address) {
		Employee fetchedEmployee = employeeService.getEmployeeById(employeeId);
		List<Address> tempList = fetchedEmployee.getAddress();
		// fetchedEmployee will be updated in the for loop
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(address.getId())) {
				tempList.set(i, address);
			}
		}
		Employee updatedEmployee = employeeService.saveEmployee(fetchedEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@GetMapping("/employee/userID/{userID}")
	public Employee getEmployeeByUserID(@PathVariable Integer userID) {
		return employeeService.getEmployeeByUserID(userID);
	}
	
	@DeleteMapping("/api/employee/{id}")
	public void deleteEmployee(@PathVariable String id) {
		employeeService.deleteEmployee(id);
	}

	@PostMapping("/employee/update/{employeeId}")
	public Employee updateEmployeeByUserID(@PathVariable String employeeId, @RequestBody Employee employee) {
		employee.setId(employeeId);
		System.out.println(employee);
		return employeeService.saveEmployee(employee);
	}
}
