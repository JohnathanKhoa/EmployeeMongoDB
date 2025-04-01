package com.beaconfire.employeeservice.entity;

import lombok.*;
import org.bson.types.ObjectId;

@Data
@Builder
@AllArgsConstructor
public class Address {
	private String id;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private int zipCode;

	// For auto generate sub document ID
	// Remove if we don't need to generate the sub document ID at save Employee method
	public Address() {
		this.id = new ObjectId().toString();
	}
}
