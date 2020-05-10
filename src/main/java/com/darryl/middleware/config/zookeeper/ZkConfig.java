package com.darryl.middleware.config.zookeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: Darryl
 * @Description: zookeeper config
 * @Date: 2020/05/07
 */
@Component
@Configuration
@PropertySource(value = {"classpath:zookeeper.yml"}, encoding = "UTF-8")
@ConfigurationProperties(prefix = "darryl.middleware.zookeeper")
public class ZkConfig {

	@Value("${address}")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
