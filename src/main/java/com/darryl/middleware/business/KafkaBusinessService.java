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

	/**
	 * 接收kafka消息
	 * @param message 消息体，我们这个栗子的消息体的类型是String的，这个可以自定义类型的
	 * @param offset 偏移量
	 * @param partition 分区
	 * @param topic 主题名
	 */
	void receiveMessage(String message, Integer offset, int partition, String topic);
}
