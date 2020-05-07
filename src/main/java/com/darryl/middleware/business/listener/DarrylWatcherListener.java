package com.darryl.middleware.business.listener;

import com.darryl.middleware.util.ZkClient;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: Darryl
 * @Description:
 * @Date: 2020/05/07
 */
public class DarrylWatcherListener implements NodeCacheListener {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void nodeChanged() throws Exception {
		log.info("===start darryl watcher===");
		// 中间可以做自己想要的业务逻辑
		log.info("===end darryl watcher===");
	}
}
