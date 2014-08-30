package net.uyghurdev.avaroid.rssreader;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditFeedActivity extends Activity {

	private TextView appTitle, feedTitle, feedUrl;
	private EditText title, url;
	private Button add, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
				FeedData data = new FeedData(EditFeedActivity.this);
				data.editFeed(Configs.FeedId, feedTitle, feedUrl);
				Intent intent = new Intent(EditFeedActivity.this, RSSReaderActivity.class);
				startActivity(intent);
				finish();

			}
		});

		// Back doing nothing
		cancel.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EditFeedActivity.this, RSSReaderActivity.class);
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
		url.setText(Configs.FeedUrl);
		title.setText(Configs.FeedTitle);

		add.setText(getString(R.string.yes));
		cancel.setText(getString(R.string.no));

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


			Intent intent = new Intent(EditFeedActivity.this,
					RSSReaderActivity.class);
			startActivity(intent);
//			overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
