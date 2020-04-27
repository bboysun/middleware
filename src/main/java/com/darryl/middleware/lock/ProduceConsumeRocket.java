package com.darryl.middleware.lock;

/**
 * @Auther: Darryl
 * @Description: 火箭的生产和消费，为了测试多个线程并发过程中产生的假死现象；
 * 这和死锁也是有差别的；
 * @Date: 2020/04/26
 */
public class ProduceConsumeRocket {
	// 用于记录火箭的编号
	private int count;
	// 定一个锁
	private final Object LOCK = new Object();
	// 表示该火箭已经被生产
	private volatile boolean isProduce = false;

	// 生产火箭
	public void produce() throws InterruptedException {
		synchronized (LOCK) {
			if (isProduce) {
				// 生产完成，等待消费
				LOCK.wait();
			} else {
				System.out.println("生产者" + Thread.currentThread().getName() + "生产了一枚火箭，编号【" + ++count + "】");
				isProduce = true;
				// 通知消费
				LOCK.notify();
				//LOCK.notifyAll();
			}
		}
	}

	// 消费火箭
	public void consume() throws InterruptedException {
		synchronized (LOCK) {
			if (isProduce) {
				System.out.println("消费者" + Thread.currentThread().getName() + "购买了一枚火箭，编号【" + count + "】");
				isProduce = false;
				// 消费完成，通知生产
				LOCK.notify();
				//LOCK.notifyAll();
			} else {
				// 等待生产
				LOCK.wait();
			}
		}
	}

	public static void main(String[] args) {
		ProduceConsumeRocket rocket = new ProduceConsumeRocket();
		// 生产火箭
		for (int i=0; i<10; i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						while (true) {
							rocket.produce();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		// 消费火箭
		for (int i=0; i<10; i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						while (true) {
							rocket.consume();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
