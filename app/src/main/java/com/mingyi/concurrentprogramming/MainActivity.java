package com.mingyi.concurrentprogramming;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.philosopher_button).setOnClickListener(v -> philosopherButtonClick());
	}

	// // FIXME: 3/25/16 test github account
	private void philosopherButtonClick() {
		startActivity(new Intent(this, PhilosopherTestActivity.class));
	}
}
