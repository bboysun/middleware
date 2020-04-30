package com.darryl.middleware.util.impl;

import com.darryl.middleware.config.redis.RedisConfig;
import com.darryl.middleware.util.RedisClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;

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
	// 即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作
	private final String SET_IF_NOT_EXIST = "NX";
	// 分布式锁成功添加返回值
	private final String SUCCESS_LOCK = "OK";
	// 要给这个key加一个过期的设置
	private final String SET_WITH_EXPIRE_TIME = "PX";
	// 分布式锁成功释放返回值
	private final Long RELEASE_SUCCESS = 1L;


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
		try {
			if (StringUtils.isEmpty(key))
				return null;
			client.set(key, value);
			return key;
		} finally {
			client.close();
		}
	}

	@Override
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
		Jedis client = getClient();
		try {
			String result = client.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (SUCCESS_LOCK.equals(result))
				return true;
			return false;
		} finally {
			client.close();
		}
	}

	@Override
	public boolean releaseDistributedLock(String lockKey, String requestId) {
		Jedis client = getClient();
		try {
			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
			Object result = client.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
			if (RELEASE_SUCCESS.equals(result))
				return true;
			return false;
		} finally {
			client.close();
		}
	}
}
