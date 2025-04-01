package com.beaconfire.employeeservice.entity;

import lombok.*;
import org.bson.types.ObjectId;

@Data
@Builder
@AllArgsConstructor
public class Contact {

	private String id;
	private String firstName;
	private String lastName;
	private String cellPhone;
	private String alternatePhone;
	private String email;
	private String relationship;
	private String type;

	// For auto generate sub document ID
	public Contact() {
		this.id = new ObjectId().toString();
	}
}
