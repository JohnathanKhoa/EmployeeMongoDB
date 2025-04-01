package com.beaconfire.employeeservice.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.beaconfire.employeeservice.entity.Employee;

@Repository
public interface HRPaginationRepository extends PagingAndSortingRepository<Employee, String>{

	List<Employee> findByFirstNameOrEmail(String firstName, String Email, Pageable pageable);
	
}
