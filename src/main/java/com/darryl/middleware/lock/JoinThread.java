package com.darryl.middleware.lock;

/**
 * @Auther: Darryl
 * @Description: 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程往往将
 *               早于子线程结束之前结束。这时，如果主线程想要等待子线程执行完成以后再结束，那就要用到join的方法。
 *               join方法会使所属线程正常执行run方法，而使当前线程进行无限期阻塞，直到join所属线程销毁后，
 *               当前线程才会继续执行。
 * @Date: 2020/04/27
 */
public class JoinThread implements Runnable{
	private int count;

	@Override
	public void run() {
		for (int i=0; i<90000; i++)
			count++;
	}

	public static void main(String[] args) {
		JoinThread joinThread = new JoinThread();
		Thread thread = new Thread(joinThread);
		thread.start();
		// 加上join方法前后，可以观察count值
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("count is " + joinThread.count);
	}
}
