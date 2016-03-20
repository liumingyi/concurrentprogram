package com.mingyi.concurrentprogramming.model;

import com.mingyi.concurrentprogramming.PhilosopherTestActivity;
import java.util.Random;

public class Philosopher extends Thread {

	private static final String TAG = "Philosopher";
	private Chopstick left, right;
	private Random random;
	private int id;
	private PhilosopherTestActivity.Callback callback;
	private boolean isRunning = true;

	public Philosopher(int id, Chopstick right, Chopstick left, PhilosopherTestActivity.Callback callback) {
		this.callback = callback;
		this.id = id;
		this.right = right;
		this.left = left;
		random = new Random();
	}

	@Override public void run() {
		try {
			while (isRunning) {
				//Log.d(TAG, " " + id + " 思考");
				callback.think();
				Thread.sleep(random.nextInt(2000));// 思考一段时间
				synchronized (left) { // 拿起筷子1
					//Log.d(TAG, " " + id + "左");
					callback.left();
					Thread.sleep(random.nextInt(1000));
					synchronized (right) { // 拿起筷子2
						//Log.d(TAG, " " + id + "右 -> 吃饭");
						callback.right();
						Thread.sleep(random.nextInt(2000));// 进餐一段时间
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void stopEat() {
		isRunning = false;
		interrupt();
	}
}
