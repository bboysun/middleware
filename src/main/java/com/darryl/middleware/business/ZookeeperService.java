package com.darryl.middleware.business;

import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * @Auther: Darryl
 * @Description: zookeeper service
 * @Date: 2020/05/07
 */
public interface ZookeeperService {

	/**
	 * 创建zookeeper节点
	 * @param node 节点名称
	 * @param value 节点存储的值
	 * @return 是否成功创建一个节点
	 */
	boolean createNode(String node, String value);

	/**
	 * 更新节点值
	 * @param path 节点名称即路径
	 * @param value 节点更新的值
	 * @return 是否成功更新完成
	 */
	boolean updateNode(String path, String value);

	/**
	 * 删除节点值
	 * @param node 节点名称即路径
	 */
	void delNode(String node);

	/**
	 * 对node节点添加默认监控
	 * @param node 待监听节点
	 */
	void addDefaultWatch(String node);

	/**
	 * 对node节点添加自定义的监控
	 * @param node 待监听节点
	 * @param listener 监听器
	 */
	void addWatch(String node, NodeCacheListener listener);
}
