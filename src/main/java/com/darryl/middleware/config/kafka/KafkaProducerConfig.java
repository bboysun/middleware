package com.darryl.middleware.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Darryl
 * @Description: kafka producer config
 * @Date: 2020/05/10
 */
@Configuration
public class KafkaProducerConfig {

	@Autowired
	private KafkaConfig kafkaConfig;

	@Bean
	public ProducerFactory<Integer, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs(), integerKeySerializer(), stringValueSerializer());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfig.getAddress());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfig.getClientId());
		return props;
	}

	@Bean
	public KafkaTemplate<Integer, String> darrylKafkaTemplate() {
		KafkaTemplate<Integer, String> kafkaTemplate =  new KafkaTemplate<>(producerFactory());
		kafkaTemplate.setDefaultTopic(kafkaConfig.getTopic());
		return kafkaTemplate;
	}

	@Bean
	public Serializer integerKeySerializer() {
		return new IntegerSerializer();
	}

	@Bean
	public Serializer stringValueSerializer() {
		return new StringSerializer();
	}

}
