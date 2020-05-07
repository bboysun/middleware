package com.darryl.middleware.business.impl;

import com.darryl.middleware.business.ZookeeperService;
import com.darryl.middleware.util.ZkClient;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Auther: Darryl
 * @Description: zookeeper service implements
 * @Date: 2020/05/07
 */
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

	private static final String ZK_ADDRESS = "localhost:2181";

	@PostConstruct
	public void init() {
		ZkClient.getZkClient(ZK_ADDRESS);
	}

	@PreDestroy
	public void destory() {
		ZkClient.destoryClient();
	}

	@Override
	public boolean createNode(String node, String value) {
		return ZkClient.createNode(ZkClient.zkClient, node, value);
	}

	@Override
	public boolean updateNode(String path, String value) {
		return ZkClient.updateNode(ZkClient.zkClient, path, value);
	}

	@Override
	public void delNode(String node) {
		ZkClient.delNode(ZkClient.zkClient, node);
	}

	@Override
	public void addDefaultWatch(String node) {
		ZkClient.addDefaultWatcher(ZkClient.zkClient, node);
	}

	@Override
	public void addWatch(String node, NodeCacheListener listener) {
		ZkClient.addWatcher(ZkClient.zkClient, node, listener);
	}
}
