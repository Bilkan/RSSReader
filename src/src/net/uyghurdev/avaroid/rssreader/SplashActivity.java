package net.uyghurdev.avaroid.rssreader;

import ca.laplanete.mobile.example.ExampleActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	ImageView imgsplash;
	Handler hand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		hand = new Handler();
		hand.postDelayed(new Runnable() {
			public void run() {
				Intent tunush = new Intent();
				tunush.setClass(SplashActivity.this, ExampleActivity.class);
				SplashActivity.this.startActivity(tunush);
				SplashActivity.this.finish();
			}
		}, 1000);

	}

}
