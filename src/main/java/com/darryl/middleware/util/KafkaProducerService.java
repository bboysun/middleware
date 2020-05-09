package com.darryl.middleware.util;

import java.util.Map;

/**
 * @Auther: Darryl
 * @Description: kafka 工具类
 * @Date: 2020/05/09
 */
public interface KafkaProducerService {

	/**
	 * 发送msg到kafka集群中
	 * @param topicName topic名字
	 * @param messages 消息体，String类型的
	 */
	void sendMessage(String topicName, String messages);

	/**
	 * 发送msg到kafka集群中
	 * @param topicName topic名字
	 * @param msg map类型的消息体
	 */
    void sendMessage(String topicName, Map<Object, Object> msg);

}
