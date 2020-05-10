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
//@Configuration
@PropertySource(value = {"classpath:config/kafka.yml"}, encoding = "UTF-8")
@ConfigurationProperties(prefix = "darryl.middleware.kafka")
public class KafkaConfig {

	@Value("${add}")
	private String address;
	@Value("${client_id}")
	private String clientId;
	@Value("${linger_ms}")
	private String lingerMs;

	@Value("${group_id}")
	private String groupId;
	@Value("${auto_commit}")
	private boolean autoCommit;
	@Value("${auto_commit_interval}")
	private String autoCommitInterval;
	@Value("${session_timeout}")
	private String sessionTimeout;
	@Value("${auto_offset_reset}")
	private String autoOffsetReset;

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public String getAutoCommitInterval() {
		return autoCommitInterval;
	}

	public void setAutoCommitInterval(String autoCommitInterval) {
		this.autoCommitInterval = autoCommitInterval;
	}

	public String getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(String sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}

	public void setAutoOffsetReset(String autoOffsetReset) {
		this.autoOffsetReset = autoOffsetReset;
	}
}
