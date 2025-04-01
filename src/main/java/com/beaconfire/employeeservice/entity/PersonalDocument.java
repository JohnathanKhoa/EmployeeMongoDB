package com.beaconfire.employeeservice.entity;

import lombok.*;
import org.bson.types.ObjectId;

@Data
@Builder
@AllArgsConstructor
public class PersonalDocument {

	private String id;
	private String path;
	private String title;
	private String comment;
	private String createDate;

	// For auto generate sub document ID
	public PersonalDocument() {
		this.id = new ObjectId().toString();
	}
}
