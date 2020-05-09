package com.darryl.middleware.controller;

import com.darryl.middleware.business.KafkaBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Darryl
 * @Description: kafka controller
 * @Date: 2020/05/09
 */
@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	@Autowired
	private KafkaBusinessService kafkaBusinessService;

	@PostMapping(value = "/{requestId}")
	public ResponseEntity<String> sendMessage(String topicName, @PathVariable String requestId) {
		kafkaBusinessService.sendMessage(topicName, requestId);
		return new ResponseEntity<>("request id is " + requestId + ", send to [" + topicName + "]" , HttpStatus.OK);
	}

}
