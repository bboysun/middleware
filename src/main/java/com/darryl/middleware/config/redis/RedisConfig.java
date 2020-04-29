package com.darryl.middleware.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: Darryl
 * @Description: redis config
 * @Date: 2020/04/28
 */
@Component
@Configuration
@PropertySource(value = {"classpath:config/redis.yml"}, encoding = "UTF-8")
@ConfigurationProperties(prefix = "darryl.middleware.redis")
public class RedisConfig {

	@Value("${host}")
	private String host;
	@Value("${port}")
	private int port;
	@Value("${password}")
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
