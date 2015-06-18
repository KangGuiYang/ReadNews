package com.allen.readnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SplashActivity extends Activity {
	private static final String TAG = "SPLASH_ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		FrameLayout container = (FrameLayout) this
				.findViewById(R.id.splashcontainer);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);

					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, MainActivity.class);
					startActivity(intent);
					SplashActivity.this.finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				super.run();
			}
		}.start();

		// new SplashAd(this, container, Constants.APPId, Constants.SplashPosId,
		// new SplashAdListener() {
		//
		// @Override
		// public void onAdPresent() {
		//
		// }
		//
		// @Override
		// public void onAdFailed(int arg0) {
		// Intent intent = new Intent();
		// intent.setClass(SplashActivity.this, MainActivity.class);
		// startActivity(intent);
		// SplashActivity.this.finish();
		// }
		//
		// @Override
		// public void onAdDismissed() {
		//
		// Intent intent = new Intent();
		// intent.setClass(SplashActivity.this, MainActivity.class);
		// startActivity(intent);
		// SplashActivity.this.finish();
		// }
		// });
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK
	// || keyCode == KeyEvent.KEYCODE_HOME) {
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }
}
