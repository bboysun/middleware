package com.darryl.middleware.business.impl;

import com.darryl.middleware.business.KafkaBusinessService;
import com.darryl.middleware.util.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Darryl
 * @Description: kafka business service implements instance
 * @Date: 2020/05/09
 */
@Service
public class KafkaBusinessServiceImpl implements KafkaBusinessService{
	@Autowired
	private KafkaProducerService producerService;

	@Override
	public void sendMessage(String topicName, String id) {
		String message = "darryl kafka message id is " + id;

		//producerService.sendMessage(topicName, msgOne, msgTwo, msgThree);
		new Thread(new Runnable() {
			@Override
			public void run() {
				producerService.sendMessage(topicName, message);
			}
		}).start();
	}


}
