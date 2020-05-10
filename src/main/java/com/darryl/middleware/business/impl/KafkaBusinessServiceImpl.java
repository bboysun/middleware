package com.darryl.middleware.business.impl;

import com.darryl.middleware.business.KafkaBusinessService;
import com.darryl.middleware.util.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * @Auther: Darryl
 * @Description: kafka business service implements instance
 * @Date: 2020/05/09
 */
@Service
public class KafkaBusinessServiceImpl implements KafkaBusinessService{
	private Logger log = LoggerFactory.getLogger(this.getClass());

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

	@Override
	@KafkaListener(topics = "darryl")
	public void receiveMessage(String message, @Header(KafkaHeaders.OFFSET) Integer offset,
	                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		// 目前我们接收到kafka消息后，就直接打印出即可
		log.info("Processing topic = {}, partition = {}, offset = {}, workUnit = {}",
				topic, partition, offset, message);
	}
}
