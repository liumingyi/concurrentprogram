package com.mingyi.concurrentprogramming;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.philosopher_button).setOnClickListener(v -> philosopherButtonClick());
	}

	//TODO use rxAndroid  instead
	private void philosopherButtonClick() {
		startActivity(new Intent(this, PhilosopherTestActivity.class));
	}
}
