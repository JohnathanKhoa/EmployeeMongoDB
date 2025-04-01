package com.beaconfire.employeeservice.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.beaconfire.employeeservice.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByuserId(Integer userID);
}
