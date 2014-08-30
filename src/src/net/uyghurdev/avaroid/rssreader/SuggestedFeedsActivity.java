package net.uyghurdev.avaroid.rssreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import net.uyghurdev.avaroid.rssreader.operator.Feed;
import net.uyghurdev.avaroid.rssreader.operator.JSONManager;
import net.uyghurdev.avaroid.rssreader.operator.NetworkOperator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SuggestedFeedsActivity extends Activity {

	private TextView title;
//	private ListView lvFeeds;
	private LinearLayout list;
	private ProgressBar prog;
	private Button addSelected;
	private Button addAll;
	private NetworkOperator netOp;
	private JSONManager jman;
	private ArrayList<Feed> sugFeeds;
	boolean err;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sugfeeds);
		init();
		prepareList();
		
		addSelected.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				for(int position = 0; position < sugFeeds.size(); position++){
					if(((CheckBox) list.getChildAt(position)).isChecked()){
						FeedData data = new FeedData(SuggestedFeedsActivity.this);
//					Object cb = lvFeeds.getAdapter().getItem(position);
//					if(((View) lvFeeds.getAdapter().getItem(position)).isSelected())
					data.addFeed(sugFeeds.get(position).getTitle(), sugFeeds.get(position).getUrl());
					}
				}
				
				Intent intent = new Intent(SuggestedFeedsActivity.this,
						RSSReaderActivity.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
				finish();
			}
			
		});
		
		addAll.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for(Feed feed : sugFeeds){
					FeedData data = new FeedData(SuggestedFeedsActivity.this);
					data.addFeed(feed.getTitle(), feed.getUrl());
				}
				
				Intent intent = new Intent(SuggestedFeedsActivity.this,
						RSSReaderActivity.class);
				startActivity(intent);
//				overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
				finish();
			}
			
		});
		
	}

	private void prepareList() {
		// TODO Auto-generated method stub
		err = false;
		
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				prog.setVisibility(View.GONE);
				if(err){
					errSiteMes();
				}else{
					setContent();
				}
			}

		};

		Thread checkUpdate = new Thread() {
			public void run() {

				try{
				String jStr = netOp.urlToString(Configs.SugFeedServer);
				if(jStr.length() == 0){
					err = true;
				}else{
					sugFeeds = jman.getSugFeeds(SuggestedFeedsActivity.this, jStr);
					if(sugFeeds == null){
						err = true;
					}
				}

				
				}catch(Exception e){
					err = true;
				}

				handler.sendEmptyMessage(0);
				
			}
		};

		checkUpdate.start();
	}
	
	public String ReadText() {
		AssetManager am = getAssets();
		String txt = "";
		try {

			
			InputStream fileIS = am.open("rssjson.txt");
			InputStreamReader reader = new InputStreamReader(fileIS, "UTF-8");
			StringBuffer sb = new StringBuffer();
			int start = 0;
			int count;
			char[] buf = new char[1024];
			while ((count = reader.read(buf)) != -1) {
				sb.append(buf, start, count);//sb.append(buf);//
			}
			txt = sb.toString();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
		return txt;
	}

	private void setContent() {
		// TODO Auto-generated method stub
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		
		for(int f = 0; f < sugFeeds.size(); f++){
			CheckBox cb = new CheckBox(this);
			cb.setId(f);

			cb.setTextColor(Color.BLACK);
			cb.setText(sugFeeds.get(f).title);
			list.addView(cb, lp);
		}
		
//		SuggestedFeedAdapter adapter = new SuggestedFeedAdapter(this, sugFeeds);
//		lvFeeds.setAdapter(adapter);
	}

	private void init() {
		// TODO Auto-generated method stub
		title = (TextView)findViewById(R.id.title);
		addSelected = (Button)findViewById(R.id.addSelected);
		addAll = (Button)findViewById(R.id.addAll);

		title.setText(getString(R.string.sug_feeds));
		addSelected.setText(getString(R.string.add_selected));
		addAll.setText(getString(R.string.add_all));
		list = (LinearLayout)findViewById(R.id.items);
//		lvFeeds = (ListView)findViewById(R.id.lvItems);
		prog = (ProgressBar)findViewById(R.id.progress);
		
		netOp = new NetworkOperator();
		jman = new JSONManager();
	}
	

	private void errSiteMes() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast, null);

		TextView text = (TextView) layout.findViewById(R.id.toast);

		text.setText(getString(R.string.can_not_conn));
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


			Intent intent = new Intent(SuggestedFeedsActivity.this,
					RSSReaderActivity.class);
			startActivity(intent);
//			overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
