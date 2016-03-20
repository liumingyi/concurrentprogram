package com.mingyi.concurrentprogramming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import com.meican.android.inject.ViewInject;
import com.mingyi.concurrentprogramming.model.Chopstick;
import com.mingyi.concurrentprogramming.model.Philosopher;
import com.mingyi.concurrentprogramming.model.Philosopher2;
import com.mingyi.concurrentprogramming.views.PhilosopherView;
import com.mingyi.concurrentprogramming.views.TableView;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家吃饭演示页面
 * Created by liumingyi on 2/14/16.
 */
public class PhilosopherTestActivity extends BaseActivity {

	private static final String TAG = "PhilosopherTestActivity";

	private static final int HAND_LEFT = 1;
	private static final int HAND_RIGHT = 2;
	private static final int THINKING = 3;
	private static final int EAT = 4;

	@ViewInject(id = R.id.start_eat_button) Button startEatButton;

	@ViewInject(id = R.id.crash_button) Button crashButton;

	@ViewInject(id = R.id.stop_eat_button) Button stopEatButton;

	@ViewInject(id = R.id.lock_eat_button) Button startEatButton2;

	@ViewInject(id = R.id.p1) PhilosopherView pv1;

	@ViewInject(id = R.id.p2) PhilosopherView pv2;

	@ViewInject(id = R.id.p3) PhilosopherView pv3;

	@ViewInject(id = R.id.p4) PhilosopherView pv4;

	@ViewInject(id = R.id.p5) PhilosopherView pv5;

	@ViewInject(id = R.id.table) TableView tableView;

	@ViewInject(id = R.id.lock1) TextView lock1;

	@ViewInject(id = R.id.lock2) TextView lock2;

	@ViewInject(id = R.id.lock3) TextView lock3;

	@ViewInject(id = R.id.lock4) TextView lock4;

	@ViewInject(id = R.id.lock5) TextView lock5;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.philosopher_test_layout);

		startEatButton.setOnClickListener(v -> startEat());

		startEatButton2.setOnClickListener(v -> startEat2());

		crashButton.setOnClickListener(v -> showCrashStatus());

		stopEatButton.setOnClickListener(v -> stop());
	}

	private int mode;

	private void stop() {
		if (mode == 1) {
			stopAll();
		} else if (mode == 2) {
			stopAll2();
		}
	}

	private void showCrashStatus() {
		pv1.left();
		pv2.left();
		pv3.left();
		pv4.left();
		pv5.left();
	}

	private void stopAll() {
		p1.stopEat();
		p2.stopEat();
		p3.stopEat();
		p4.stopEat();
		p5.stopEat();
		pv1.thinking();
		pv2.thinking();
		pv3.thinking();
		pv4.thinking();
		pv5.thinking();
	}

	private void stopAll2() {
		ph1.stopEat();
		ph2.stopEat();
		ph3.stopEat();
		ph4.stopEat();
		ph5.stopEat();
		pv1.thinking();
		pv2.thinking();
		pv3.thinking();
		pv4.thinking();
		pv5.thinking();
		//lock1.setVisibility(View.INVISIBLE);
		//lock2.setVisibility(View.INVISIBLE);
		//lock3.setVisibility(View.INVISIBLE);
		//lock4.setVisibility(View.INVISIBLE);
		//lock5.setVisibility(View.INVISIBLE);
	}

	Philosopher p1;
	Philosopher p2;
	Philosopher p3;
	Philosopher p4;
	Philosopher p5;

	private void startEat() {
		mode = 1;
		Chopstick chopstick1 = new Chopstick();
		Chopstick chopstick2 = new Chopstick();
		Chopstick chopstick3 = new Chopstick();
		Chopstick chopstick4 = new Chopstick();
		Chopstick chopstick5 = new Chopstick();
		p1 = new Philosopher(1, chopstick1, chopstick2, new Callback() {
			@Override public void left() {
				// (1,1,status) -> (id,left | right | think,状态0放下1拿起)
				handler.obtainMessage(1, HAND_LEFT, 0).sendToTarget();
			}

			@Override public void right() {
				handler.obtainMessage(1, HAND_RIGHT, 0).sendToTarget();
			}

			@Override public void think() {
				handler.obtainMessage(1, THINKING, 0).sendToTarget();
			}
		});
		p2 = new Philosopher(2, chopstick2, chopstick3, new Callback() {
			@Override public void left() {
				handler.obtainMessage(2, HAND_LEFT, 0).sendToTarget();
			}

			@Override public void right() {
				handler.obtainMessage(2, HAND_RIGHT, 0).sendToTarget();
			}

			@Override public void think() {
				handler.obtainMessage(2, THINKING, 0).sendToTarget();
			}
		});
		p3 = new Philosopher(3, chopstick3, chopstick4, new Callback() {
			@Override public void left() {
				handler.obtainMessage(3, HAND_LEFT, 0).sendToTarget();
			}

			@Override public void right() {
				handler.obtainMessage(3, HAND_RIGHT, 0).sendToTarget();
			}

			@Override public void think() {

				handler.obtainMessage(3, THINKING, 0).sendToTarget();
			}
		});
		p4 = new Philosopher(4, chopstick4, chopstick5, new Callback() {
			@Override public void left() {
				handler.obtainMessage(4, HAND_LEFT, 0).sendToTarget();
			}

			@Override public void right() {

				handler.obtainMessage(4, HAND_RIGHT, 0).sendToTarget();
			}

			@Override public void think() {
				handler.obtainMessage(4, THINKING, 0).sendToTarget();
			}
		});
		p5 = new Philosopher(5, chopstick5, chopstick1, new Callback() {
			@Override public void left() {
				handler.obtainMessage(5, HAND_LEFT, 0).sendToTarget();
			}

			@Override public void right() {
				handler.obtainMessage(5, HAND_RIGHT, 0).sendToTarget();
			}

			@Override public void think() {
				handler.obtainMessage(5, THINKING, 0).sendToTarget();
			}
		});
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
	}

	public interface Callback {
		void left();

		void right();

		void think();
	}

	// FIXME: 2/22/16 handler可能内存泄露
	@SuppressLint("HandlerLeak") private Handler handler = new Handler() {
		@Override public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int type = msg.arg1;
			switch (msg.what) {
				case 1:
					setPhilosopherStatus(pv1, type);
					break;
				case 2:
					setPhilosopherStatus(pv2, type);
					break;
				case 3:
					setPhilosopherStatus(pv3, type);
					break;
				case 4:
					setPhilosopherStatus(pv4, type);
					break;
				case 5:
					setPhilosopherStatus(pv5, type);
					break;
			}
		}
	};

	private void setPhilosopherStatus(PhilosopherView p, int type) {
		if (type == HAND_LEFT) {
			p.left();
		} else if (type == HAND_RIGHT) {
			p.right();
		} else if (type == THINKING) {
			p.thinking();
		} else if (type == EAT) {
			p.eat();
		}
	}

	//////////////////////////条件变量吃法////////////////////////////////

	public interface Callback2 {
		void eat();

		void think();
	}

	Philosopher2 ph1;
	Philosopher2 ph2;
	Philosopher2 ph3;
	Philosopher2 ph4;
	Philosopher2 ph5;

	private void startEat2() {
		mode = 2;
		ReentrantLock table = new ReentrantLock();
		ph1 = new Philosopher2(1, table, new Callback2() {
			@Override public void eat() {
				handler2.obtainMessage(1, EAT, 0).sendToTarget();
			}

			@Override public void think() {
				handler2.obtainMessage(1, THINKING, 0).sendToTarget();
			}
		});
		ph2 = new Philosopher2(2, table, new Callback2() {
			@Override public void eat() {
				handler2.obtainMessage(2, EAT, 0).sendToTarget();
			}

			@Override public void think() {
				handler2.obtainMessage(2, THINKING, 0).sendToTarget();
			}
		});
		ph3 = new Philosopher2(3, table, new Callback2() {
			@Override public void eat() {
				handler2.obtainMessage(3, EAT, 0).sendToTarget();
			}

			@Override public void think() {
				handler2.obtainMessage(3, THINKING, 0).sendToTarget();
			}
		});
		ph4 = new Philosopher2(4, table, new Callback2() {
			@Override public void eat() {
				handler2.obtainMessage(4, EAT, 0).sendToTarget();
			}

			@Override public void think() {
				handler2.obtainMessage(4, THINKING, 0).sendToTarget();
			}
		});
		ph5 = new Philosopher2(5, table, new Callback2() {
			@Override public void eat() {
				handler2.obtainMessage(5, EAT, 0).sendToTarget();
			}

			@Override public void think() {
				handler2.obtainMessage(5, THINKING, 0).sendToTarget();
			}
		});

		ph1.setRight(ph5);
		ph1.setLeft(ph2);

		ph2.setRight(ph1);
		ph2.setLeft(ph3);

		ph3.setRight(ph2);
		ph3.setLeft(ph4);

		ph4.setRight(ph3);
		ph4.setLeft(ph5);

		ph5.setRight(ph4);
		ph5.setLeft(ph1);

		ph1.start();
		ph2.start();
		ph3.start();
		ph4.start();
		ph5.start();
	}

	@SuppressLint("HandlerLeak") private Handler handler2 = new Handler() {
		@Override public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int type = msg.arg1;
			switch (msg.what) {
				case 1:
					setPhilosopherStatus(pv1, type);
					break;
				case 2:
					setPhilosopherStatus(pv2, type);
					break;
				case 3:
					setPhilosopherStatus(pv3, type);
					break;
				case 4:
					setPhilosopherStatus(pv4, type);
					break;
				case 5:
					setPhilosopherStatus(pv5, type);
					break;
			}
		}
	};

	//private void setLockTextViewStatus(TextView lock, int type) {
	//	if (type == LOCK) {
	//		lock.setVisibility(View.VISIBLE);
	//		lock.setText("lock");
	//	} else if (type == UNLOCK) {
	//		lock.setVisibility(View.VISIBLE);
	//		lock.setText("unlock");
	//	}
	//}
}
