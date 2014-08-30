package net.uyghurdev.avaroid.rssreader;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddFeedActivity extends Activity {

	private TextView appTitle, feedTitle, feedUrl;
	private EditText title, url;
	private Button add, cancel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addfeed);
		init();

		
		// Save edited feed
		add.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				String feedTitle = (title.getText().toString());
				String feedUrl = url.getText().toString();
				// TODO Auto-generated method stub
				if (title.length() == 0 || url.length() == 0) {
					
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.toast, null);

					TextView text = (TextView) layout.findViewById(R.id.toast);


						text.setText(getString(R.string.fill_all));


					Toast toast = new Toast(AddFeedActivity.this);
					toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.setView(layout);
					toast.show();
				
				} else {
					FeedData data = new FeedData(AddFeedActivity.this);
					data.addFeed(feedTitle, feedUrl);
					Intent intent = new Intent(AddFeedActivity.this, RSSReaderActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});

		// Back doing nothing
		cancel.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddFeedActivity.this, RSSReaderActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		
		appTitle = (TextView)findViewById(R.id.appTitle);
		feedTitle = (TextView) findViewById(R.id.tvTitle);
		feedUrl = (TextView) findViewById(R.id.tvUrl);
		url = (EditText) findViewById(R.id.url);
		title = (EditText) findViewById(R.id.title);
		add = (Button) findViewById(R.id.add);
		cancel = (Button) findViewById(R.id.bcancel);

		add.setGravity(Gravity.CENTER);
		cancel.setGravity(Gravity.CENTER);
		
		appTitle.setText(getString(R.string.app_name));
		feedTitle.setText(getString(R.string.feedtitle));
		feedUrl.setText(getString(R.string.address));
		

		add.setText(getString(R.string.yes));
		cancel.setText(getString(R.string.no));


	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


			Intent intent = new Intent(AddFeedActivity.this,
					RSSReaderActivity.class);
			startActivity(intent);
//			overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
