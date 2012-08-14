package com.rothconsulting.android.radiorec;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class SleepTimer extends Activity {

	TextView mTextField;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// mTextField = (TextView) findViewById(R.id.timer1);

		CountDownTimer aCounter = new CountDownTimer(10000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				mTextField.setText("Seconds remaining: " + millisUntilFinished
						/ 1000);
			}

			@Override
			public void onFinish() {
				Toast.makeText(getApplicationContext(), "E N D E !!!!",
						Toast.LENGTH_LONG);
				mTextField.setText("Finished");
			}
		};
		aCounter.start();

	}
}
