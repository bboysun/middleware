package com.darryl.middleware.util;

import com.google.common.base.Charsets;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @Auther: Darryl
 * @Description: zookeeper client util
 * @Date: 2020/05/07
 */
public class ZkClient {
	// 日志
	private static final Logger log = LoggerFactory.getLogger(ZkClient.class);
	// 客户端
	public static volatile CuratorFramework zkClient;

	/**
	 * 获取zookeeper客户端实例对象
	 * @param zkAddress zookeeper地址
	 * @return zookeeper客户端实例对象
	 */
	public static CuratorFramework getZkClient(String zkAddress) {
		if (zkClient == null) {
			synchronized (ZkClient.class) {
				if (zkClient == null) {
					zkClient = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(5000, 3));
					zkClient.start();
				}
			}
		}
		return zkClient;
	}


	/**
	 * 销毁zookeeper客户端实例对象
	 */
	public static void destoryClient() {
		if (zkClient != null) {
			zkClient.close();
		}
	}

	/**
	 * 创建一个zk节点
	 * @param zkClient zookeeper客户端实例对象
	 * @param nodeName 节点名称即路径
	 * @param value 节点存储的值
	 * @return 是否创建节点成功
	 */
	public static boolean createNode(CuratorFramework zkClient, String nodeName, String value) {
		boolean isSuccessFlag = false;
		try {
			Stat stat = zkClient.checkExists().forPath(nodeName);
			if (stat == null) {
				String opRes = null;
				if (StringUtils.isEmpty(value))
					opRes = zkClient.create().creatingParentsIfNeeded().forPath(nodeName);
				else
					opRes = zkClient.create().creatingParentsIfNeeded().forPath(nodeName, value.getBytes(Charsets.UTF_8));
				isSuccessFlag = nodeName.equals(opRes);
			}
		} catch (Exception e) {
			log.error("zookeeper create node Exception: ", e);
		}
		return isSuccessFlag;
	}

	/**
	 * 更新节点的内容
	 * @param zkClient zookeeper客户端实例对象
	 * @param path 节点名称即路径
	 * @param value 节点存储的值
	 * @return 是否更新节点值成功
	 */
	public static boolean updateNode(CuratorFramework zkClient, String path, String value) {
		boolean isSuccessFlag = false;
		try {
			Stat stat = zkClient.checkExists().forPath(path);
			if (stat != null) {
				Stat res = zkClient.setData().forPath(path, value.getBytes(Charsets.UTF_8));
				if (res != null)
					isSuccessFlag = true;
			}
		} catch (Exception e) {
			log.error("zookeeper update node Exception: ", e);
		}
		return isSuccessFlag;
	}

	/**
	 * 删除节点
	 * @param zkClient zookeeper客户端实例对象
	 * @param node 节点名称
	 */
	public static void delNode(CuratorFramework zkClient, String node) {
		try {
			zkClient.delete().deletingChildrenIfNeeded().forPath(node);
		} catch (Exception e) {
			log.error("zookeeper delete node Exception: ", e);
		}
	}

	/**
	 * 针对某一个节点添加默认监控
	 * @param zkClient 客户端实例对象
	 * @param node 待监听节点
	 */
	public static void addDefaultWatcher(CuratorFramework zkClient, String node) {
		NodeCache nodeCache = new NodeCache(zkClient, node, false);
		try {
			nodeCache.start(true);
			nodeCache.getListenable().addListener(new NodeCacheListener() {
				@Override
				public void nodeChanged() throws Exception {
					log.info("[" + node + "]，节点已经发生了变化，请知悉。如果有需要额外操作可以在我这里添加触发功能！");
				}
			});
		} catch (Exception e) {
			log.error("zookeeper add default nodecache watcher Exception: ", e);
		}
	}

	/**
	 * 针对某一个节点添加自定义监听器
	 * @param zkClient 客户端实例对象
	 * @param node 待监听节点
	 * @param listener 自定义监听器
	 */
	public static void addWatcher(CuratorFramework zkClient, String node, NodeCacheListener listener) {
		NodeCache nodeCache = new NodeCache(zkClient, node, false);
		try {
			nodeCache.start(true);
			nodeCache.getListenable().addListener(listener);
		} catch (Exception e) {
			log.error("zookeeper add nodecache watcher Exception: ", e);
		}
	}

}
