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

	/**
	 * 获取分布式锁
	 * @param lockKey  分布式锁的key
	 * @param requestId  分布式锁客户端的owner
	 * @param expireTime 超时时间
	 * @return 是否获取分布式锁
	 */
	boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime);

	/**
	 * 释放分布式锁
	 * @param lockKey 分布式锁的key
	 * @param requestId 分布式锁客户端的owner
	 * @return 是否释放分布式锁
	 */
	boolean releaseDistributedLock(String lockKey, String requestId);

	/**
	 * redis 构建一个延迟队列的生产者，将消息发送到默认的延迟队列"DELAY_LIST" 可以类比kafka专业的消息中间件
	 * @param delayTime 延迟队列的延迟时间，映射到redis中zset的score
	 * @param message 延迟队列的中的消息体，映射到redis中zset的value
	 * @return 是否已经成功的将消息放入到redis的延迟队列中
	 */
	boolean delayListProducer(int delayTime, String message);
}
