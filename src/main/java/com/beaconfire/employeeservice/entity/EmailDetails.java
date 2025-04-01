package com.beaconfire.employeeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmailDetails implements Serializable {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
