package com.darryl.middleware.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: Darryl
 * @Description: synchronized对象锁；
 * 可以观察 synchronized 加了static前后的区别；也可以了解一下synchronized锁是如何升级的；
 * @Date: 2020/04/27
 */
public class SyncLock{
	// 静态统计计数
	private static int staticCount;
	// 统计数
	private static int count;
	// 多线程计数器
	private static CountDownLatch latch = new CountDownLatch(5);

	// 非静态对象锁
	public synchronized void syncFun() {
		count++;
	}
	// 静态对象锁
	public static synchronized void staticSyncFun() {
		staticCount++;
	}

	public static void main(String[] args) {
		for (int i=0; i<5; i++) {
			SyncLock syncLock = new SyncLock();
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						//1. 会同步
						staticSyncFun();
						//2. 不会同步
						syncLock.syncFun();
					}
					latch.countDown();
				}
			}).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("synchronized count is " + count);
		System.out.println("static synchronized count is " + staticCount);
	}
}
