package com.darryl.middleware.business.impl;

import com.darryl.middleware.business.KafkaBusinessService;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
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
	private KafkaTemplate<Integer, String> producerService;

	@Override
	public void sendMessage(String topicName, String id) {
		String message = "darryl kafka message id is " + id;
		Integer defaultKey = 7;
		String defaultMessage = "default message";
		//producerService.sendMessage(topicName, msgOne, msgTwo, msgThree);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				producerService.sendMessage(topicName, message);
//			}
//		}).start();
		try {
			log.info("发送【指定主题】的消息：{}", message);
			SendResult<Integer, String> res = producerService.send(topicName, message).get();
			RecordMetadata recordMetadata = res.getRecordMetadata();
			log.info("发送【指定主题】结果：topic = {}, partition = {}, offset = {}, message = {}",
					recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), message);
			log.info("发送到【默认主题】的消息，{}", defaultMessage);
			SendResult<Integer, String> defRes = producerService.sendDefault(defaultKey, defaultMessage).get();
			RecordMetadata defRecordMetadata = defRes.getRecordMetadata();
			log.info("发送【默认主题】结果：topic = {}, partition = {}, offset = {}, message = {}",
					defRecordMetadata.topic(), defRecordMetadata.partition(), defRecordMetadata.offset(), message);
		} catch (Exception e) {
			log.error("send messge Exception: ", e);
		}

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
