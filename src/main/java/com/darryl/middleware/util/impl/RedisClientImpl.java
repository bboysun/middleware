package com.darryl.middleware.util.impl;

import com.darryl.middleware.config.redis.RedisConfig;
import com.darryl.middleware.util.RedisClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Auther: Darryl
 * @Description: redis client
 * @Date: 2020/04/29
 */
@Service
public class RedisClientImpl implements RedisClient {

	@Resource
	private RedisConfig redisConfig;
	private JedisPool jedisPool;

	@PostConstruct
	public void init() {
		jedisPool = new JedisPool(redisConfig.getHost(), redisConfig.getPort());
	}

	@Override
	public Jedis getClient() {
		Jedis client = jedisPool.getResource();
		client.auth(redisConfig.getPassword());
		return client;
	}

	@Override
	public String set(String key, String value) {
		Jedis client = getClient();
		if (StringUtils.isEmpty(key))
			return null;
		client.set(key, value);
		return key;
	}
}
