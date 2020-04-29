package com.darryl.middleware.util;

import redis.clients.jedis.Jedis;

/**
 * @Auther: Darryl
 * @Description: redis client
 * @Date: 2020/04/29
 */
public interface RedisClient {

	/**
	 * 获取客户端
	 * @return jedis获取客户端
	 */
	 Jedis getClient();


	/**
	 * 向redis存储值
	 * @param key string类型
	 * @param value String类型
	 * @return key值
	 */
	String set(String key, String value);
}
