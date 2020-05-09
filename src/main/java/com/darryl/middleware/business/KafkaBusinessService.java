package com.darryl.middleware.business;

/**
 * @Auther: Darryl
 * @Description: kafka business service
 * @Date: 2020/05/09
 */
public interface KafkaBusinessService {
	/**
	 * 向一个topic主题发送消息
	 * @param topicName 主题名
	 * @param id 请求id
	 */
	void sendMessage(String topicName, String id);
}
