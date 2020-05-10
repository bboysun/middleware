package com.darryl.middleware.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Darryl
 * @Description: kafka consumer listener config class
 * @Date: 2020/05/10
 */
@Configuration
@EnableKafka
public class KafkaListenerConfig {

	@Autowired
	private  KafkaConfig kafkaConfig;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConcurrency(1);
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps(), integerKeyDeserializer(),
				stringValueDeserializer());
	}

	@Bean
	public Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getAddress());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getGroupId());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfig.isAutoCommit());
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConfig.getAutoCommitInterval());
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaConfig.getSessionTimeout());
		// 对于当前group id来说，从最早的offset开始消费
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfig.getAutoOffsetReset());
		return props;
	}

	@Bean
	public Deserializer integerKeyDeserializer() {
		return new IntegerDeserializer();
	}

	@Bean
	public Deserializer stringValueDeserializer() {
		return new StringDeserializer();
	}

}
