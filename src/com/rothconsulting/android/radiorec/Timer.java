package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends Activity {

	TextView mTextField;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// mTextField = (TextView) findViewById(R.id.timer1);

		CountDownTimer aCounter = new CountDownTimer(100000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				mTextField.setText("Seconds remaining: " + millisUntilFinished
						/ 1000);
			}

			@Override
			public void onFinish() {
				mTextField.setText("Finished");
			}
		};
		aCounter.start();

	}
}
