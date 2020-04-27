package com.darryl.middleware.lock;

import org.springframework.util.StopWatch;

/**
 * @Auther: Darryl
 * @Description: yield() 线程方法作用是放弃当前的CPU资源，将它让给其他的任务去占用
 *               CPU执行时间，但放弃的时间不确定，有可能刚放弃就马上又获得CPU时间片。
 * @Date: 2020/04/27
 */
public class YieldThread implements Runnable {
	private int count;
	private StopWatch stopWatch;

	public YieldThread(int count, StopWatch stopWatch) {
		this.count = count;
		this.stopWatch = stopWatch;
	}

	@Override
	public void run() {
		stopWatch.start();
		for (int i=0; i<50000; i++) {
			Thread.yield();
			count++;
		}
		stopWatch.stop();
		long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
		System.out.println(Thread.currentThread().getName() + "run time is " + lastTaskTimeMillis);
	}

	public static void main(String[] args) {
		for (int i=0; i<5; i++) {
			new Thread(new YieldThread(0, new StopWatch())).start();
		}
	}
}
