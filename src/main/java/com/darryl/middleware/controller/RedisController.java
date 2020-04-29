package com.darryl.middleware.controller;

import com.darryl.middleware.common.res.Response;
import com.darryl.middleware.common.res.ResponseBuilder;
import com.darryl.middleware.util.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Darryl
 * @Description: redis controller
 * @Date: 2020/04/28
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

	@Autowired
	private RedisClient redisClient;

	@PostMapping(value = "/setRedis")
	public Response setRedis(String key, String value) {
		redisClient.set(key, value);
		return ResponseBuilder.buildSeccuess();
	}

}
