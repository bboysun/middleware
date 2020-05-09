package com.darryl.middleware.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: Darryl
 * @Description: kafka config
 * @Date: 2020/05/09
 */
@Component
@Configuration
@PropertySource(value = {"classpath:config/kafka.yml"}, encoding = "UTF-8")
@ConfigurationProperties(prefix = "darryl.middleware.kafka.producer")
public class KafkaConfig {

	@Value("${add}")
	private String address;
	@Value("${client_id}")
	private String clientId;
	@Value("${linger_ms}")
	private String lingerMs;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLingerMs() {
		return lingerMs;
	}

	public void setLingerMs(String lingerMs) {
		this.lingerMs = lingerMs;
	}
}
