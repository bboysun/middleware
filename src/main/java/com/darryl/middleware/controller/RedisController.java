package com.darryl.middleware.controller;

import com.darryl.middleware.common.res.ResponseBuilder;
import com.darryl.middleware.util.RedisClient;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: Darryl
 * @Description: redis controller
 * @Date: 2020/04/28
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisClient redisClient;

	@PostMapping(value = "/setRedis")
	public ResponseEntity<String> setRedis(String key, String value) {
		redisClient.set(key, value);
		//return ResponseBuilder.buildSeccuess();
		// for http restful api
		return new ResponseEntity<>(key, HttpStatus.OK);
	}

	@PostMapping(value = "/redisDistributedLock/{nameId}")
	public ResponseEntity<Map<String, String>> redisDistributedLock(@PathVariable String nameId) {
		logger.info("start get redis distributed lock");
		Map<String, String> map = Maps.newHashMap();
		String uuid = UUID.randomUUID().toString();
		boolean isLock = redisClient.tryGetDistributedLock(nameId, uuid, 1000*10);
		if (isLock) {
			logger.info("received lock");
			//return ResponseBuilder.buildSeccuess(nameId);
			map.put(nameId, uuid);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		logger.info("dont received lock");
		//return ResponseBuilder.buildForbidden(nameId);
		map.put(nameId, null);
		return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
	}

}
