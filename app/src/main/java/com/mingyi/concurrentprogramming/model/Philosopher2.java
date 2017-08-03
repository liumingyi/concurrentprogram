package com.mingyi.concurrentprogramming.model;

import com.mingyi.concurrentprogramming.PhilosopherTestActivity;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据条件变量
 */
public class Philosopher2 extends Thread {

  private static final String TAG = "Philosopher2";
  private final PhilosopherTestActivity.Callback2 callback;
  private boolean eating;
  private Philosopher2 left;
  private Philosopher2 right;
  private ReentrantLock table;
  private Condition condition;
  private int id;
  private boolean isRunning = true;

  public Philosopher2(int id, ReentrantLock table, PhilosopherTestActivity.Callback2 callback) {
    eating = false;
    this.id = id;
    this.table = table;
    condition = table.newCondition();
    this.callback = callback;
  }

  public void setLeft(Philosopher2 left) {
    this.left = left;
  }

  public void setRight(Philosopher2 right) {
    this.right = right;
  }

  @Override public void run() {
    try {
      while (isRunning) {
        think();
        eat();
      }
    } catch (InterruptedException e) {
    }
  }

  private void think() throws InterruptedException {
    table.lock();
    try {
      eating = false;
      left.condition.signal();
      right.condition.signal();
    } finally {
      table.unlock();
    }
    callback.think();
    Thread.sleep(1000);
  }

  private void eat() throws InterruptedException {
    table.lock();
    try {
      while (left.eating || right.eating) {
        condition.await();
      }
      eating = true;
      callback.eat();
    } finally {
      table.unlock();
    }
    Thread.sleep(1000);
  }

  public void stopEat() {
    isRunning = false;
    interrupt();
  }
}
