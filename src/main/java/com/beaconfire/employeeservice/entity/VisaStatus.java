package com.beaconfire.employeeservice.entity;

import lombok.*;
import org.bson.types.ObjectId;

@Data
@Builder
@AllArgsConstructor
public class VisaStatus {

	private String id;
	private String visaType;
	private boolean activeFlag;
	private String startDate;
	private String endDate;
	private String lastModificationDate;

	// For auto generate sub document ID
	public VisaStatus() {
		this.id = new ObjectId().toString();
	}
}
