package com.darryl.middleware.util.impl;

import com.alibaba.fastjson.JSONObject;
import com.darryl.middleware.config.kafka.KafkaConfig;
import com.darryl.middleware.util.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @Auther: Darryl
 * @Description: kafka 实现类
 * @Date: 2020/05/09
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private KafkaProducer<Integer, String> producer;
	@Resource
	private KafkaConfig kafkaConfig;

	@PostConstruct
	public void init() {
		Properties properties = new Properties();
		// kafka服务端的IP地址和端口号信息
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfig.getAddress());
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfig.getClientId());
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// 延迟发送消息，单位毫秒，便于提高kafka吞吐量
		//properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaConfig.getLingerMs());
		producer =new KafkaProducer<>(properties);
	}

	@PreDestroy
	public void destroy() {
		producer.close();
	}

	@Override
	public void sendMessage(String topicName, String messages) {
		log.info("msg is {}", messages);
		producer.send(new ProducerRecord<>(topicName, messages));
	}

	@Override
	public void sendMessage(String topicName, Map<Object, Object> msg) {
		String jsonMsg = JSONObject.toJSONString(msg);
		log.info("send message is {}", jsonMsg);
		try {
			RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(topicName, jsonMsg)).get();
			log.info("result is {}, after send message", recordMetadata.toString());
		} catch (InterruptedException e) {
			log.error("send message Exception: ", e);
		} catch (ExecutionException e) {
			log.error("send message Exception: ", e);
		}
	}
}
