package com.darryl.middleware.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Darryl
 * @Description: 原子类型的基本类型，保证多个线程安全执行
 * @Date: 2020/04/27
 */
public class AtomicLock implements Runnable{
	// 计数
	private int count;
	// 原子计数
	private AtomicInteger atomicInteger;
	// 并发线程计数器
	private CountDownLatch countDownLatch;

	public AtomicLock(int count, AtomicInteger atomicInteger, CountDownLatch countDownLatch) {
		this.count = count;
		this.atomicInteger = atomicInteger;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		for (int i=0; i<10000; i++) {
			count++;
			atomicInteger.incrementAndGet();
		}
		countDownLatch.countDown();
	}

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(5);
		AtomicLock lockThread = new AtomicLock(0, new AtomicInteger(0), countDownLatch);
		for (int i=0; i<5; i++) {
			new Thread(lockThread).start();
		}
		try {
			countDownLatch.await();
			System.out.println("count is " + lockThread.count);
			System.out.println("atomicInteger is " + lockThread.atomicInteger);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
