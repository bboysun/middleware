package com.darryl.middleware.util.impl;

import com.darryl.middleware.config.redis.RedisConfig;
import com.darryl.middleware.util.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * @Auther: Darryl
 * @Description: redis client
 * @Date: 2020/04/29
 */
@Service
public class RedisClientImpl implements RedisClient {

	private Logger log = LoggerFactory.getLogger(this.getClass());

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
	// 延迟队列的默认topic
	private final String DEFAULT_TOPIC = "DELAY_LIST";


	@PostConstruct
	public void init() {
		jedisPool = new JedisPool(redisConfig.getHost(), redisConfig.getPort());
		// 开启监听延迟队列的线程
//		new Thread(new DelayListConsumer()).start();
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

	@Override
	public boolean delayListProducer(int delayTime, String message) {
		boolean isSuccessFlag = false;
		Jedis client = getClient();
		try {
			Calendar calendar = Calendar.getInstance();
			log.info("消息进入延迟队列的时间：{}", calendar.getTimeInMillis());
			calendar.add(Calendar.SECOND, delayTime);
			double score = calendar.getTimeInMillis()/1000;
			Long res = client.zadd(DEFAULT_TOPIC, score, message);
			if (res > 0) {
				isSuccessFlag = true;
				log.info("【Redis延迟队列】主题：{}，message：{}，延迟时间：{}", DEFAULT_TOPIC, message, score);
			}
		} finally {
			client.close();
		}
		return isSuccessFlag;
	}

	/*class DelayListConsumer implements Runnable{
		Jedis client = getClient();

		@Override
		public void run() {
			while (true) {
				Set<Tuple> messages = client.zrangeWithScores(DEFAULT_TOPIC, 0, 0);
				if (!CollectionUtils.isEmpty(messages)) {
					Tuple msg = (Tuple) messages.toArray()[0];
					double score = msg.getScore();
					Calendar calendar = Calendar.getInstance();
					double nowTime = calendar.getTimeInMillis()/1000;
					// 表示已经超过了延迟的时间，可以消费了
					if (nowTime > score) {
						Long result = client.zrem(DEFAULT_TOPIC, msg.getElement());
						if (result > 0)
							log.info("【延迟队列】已成功消费的message: {}，时间为：{}", msg.getElement()
									,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}
				}
			}
		}
	}*/
}
