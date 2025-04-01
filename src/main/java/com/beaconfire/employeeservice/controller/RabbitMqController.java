package com.beaconfire.employeeservice.controller;

import com.beaconfire.employeeservice.entity.EmailDetails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/sendreject")
    public String sendReject(@RequestBody EmailDetails mail) {
        rabbitTemplate.convertAndSend("beaconfire.hremail.message", "beaconfire.message.reject", mail);
        return "Reject mail sent";
    }

    @PostMapping("/sendtoken")
    public String sendToken(@RequestBody EmailDetails mail) {
        rabbitTemplate.convertAndSend("beaconfire.hremail.message", "beaconfire.message.token", mail);
        return "Token mail sent";
    }
}
