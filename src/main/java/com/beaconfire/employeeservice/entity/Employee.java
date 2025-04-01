package com.beaconfire.employeeservice.entity;

import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	// ** = not mandatory = can be null
    @Id
    private String id;
    private int userId;
    private String firstName;
    private String lastName;
    private String middleName; //**
    private String preferredName; //**
    private String cellPhone;
    private String alternatePhone; //**
    private String email;
    private String ssn;
    private String dob;
    private String gender; //**
    private String startDate;
    private String endDate;
    private String driversLicense;
    private String driversLicenseExpiration;
    private String houseID;
    private List<VisaStatus> visaStatus;
    private List<Contact> contact;
    private List<Address> Address;
    private List<PersonalDocument> personalDocument;

}