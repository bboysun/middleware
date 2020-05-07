package com.darryl.middleware.controller;

import com.darryl.middleware.business.ZookeeperService;
import com.darryl.middleware.business.listener.DarrylWatcherListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Darryl
 * @Description: zookeeper controller
 * @Date: 2020/05/07
 */
@RestController
@RequestMapping(value = "/zk")
public class ZkController {

	@Autowired
	private ZookeeperService zookeeperService;

	@PostMapping(value = "/create")
	public ResponseEntity<String> createNode(String node, String value) {
		boolean res = zookeeperService.createNode(node, value);
		if (res) {
			// 添加默认监控
			// zookeeperService.addDefaultWatch(node);
			// 添加自定义监控
			DarrylWatcherListener listener = new DarrylWatcherListener();
			zookeeperService.addWatch(node, listener);

			return new ResponseEntity<>("[" + node + "]，节点成功创建！", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("系统抛锚了，客观稍等片刻！", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<String> updateNode(String path, String value) {
		boolean res = zookeeperService.updateNode(path, value);
		if (res)
			return new ResponseEntity<>("[" + path + "]，节点成功更新！", HttpStatus.OK);
		else
			return new ResponseEntity<>("系统抛锚了，客观稍等片刻！", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "/del")
	public ResponseEntity<String> delNode(String node) {
		zookeeperService.delNode(node);
		return new ResponseEntity<>("[" + node + "]，节点成功删除！", HttpStatus.OK);
	}

}
