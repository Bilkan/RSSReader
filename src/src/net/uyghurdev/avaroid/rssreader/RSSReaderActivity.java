package net.uyghurdev.avaroid.rssreader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import net.uyghurdev.avaroid.rssreader.operator.Feed;
import net.uyghurdev.avaroid.rssreader.operator.FeedsAdapter;
import net.uyghurdev.avaroid.rssreader.operator.NetworkOperator;
import net.uyghurdev.avaroid.rssreader.service.UpdateService;
import net.uyghurdev.avaroid.rssreader.tools.Helper;
import net.uyghurdev.avaroid.rssreader.tools.UIManager;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RSSReaderActivity extends Activity {

	/**
	 * 
	 */

	private ArrayList<Feed> feeds;
	private TextView header;
	private ListView lv;
	private Helper helper;
	private ProgressBar prog;
	private SharedPreferences sharedPreferences, getPreferences;
	int err = 0;
	String[] ops = {"","",""};
	ProgressDialog dialog;

	// private SharedPreferences sharedPreferences;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getSharedFeatures();
//		stopUpdate();
		
		init();
		setContents();

		// registerForContextMenu(lv);

		// Go to show feed items when a feed clicked
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub


					Configs.FeedId = feeds.get(position).getId();
					Configs.FeedUrl = feeds.get(position).getUrl();
					Configs.FeedTitle = feeds.get(position).getTitle();
					Intent intent = new Intent(RSSReaderActivity.this,
							ItemListActivity.class);
					startActivity(intent);
					finish();
		


			}

		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Configs.FeedId = feeds.get(position).getId();
				Configs.FeedUrl = feeds.get(position).getUrl();
				Configs.FeedTitle = feeds.get(position).getTitle();
				feedLongClick();
				return false;
			}
			
		});
		
		lv.setOnScrollListener(new ListView.OnScrollListener(){

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				Configs.FeedListScroll = lv.getScrollY();
			}
			
		});

	}

	private void stopUpdate() {
		// TODO Auto-generated method stub
		if(Configs.ServiceStarted){
			stopService(new Intent(this, UpdateService.class));
		}
	}

	private void setContents() {
		// TODO Auto-generated method stub

			header.setTypeface(Typeface.DEFAULT);
			header.setGravity(Gravity.LEFT);
		
		header.setText(getString(R.string.app_name));
		FeedData data = new FeedData(this);
		feeds = data.getFeeds();
		FeedsAdapter adapter = new FeedsAdapter(this, feeds);
		lv.setAdapter(adapter);
	}

	private void getSharedFeatures() {
		// TODO Auto-generated method stub
		Context otherAppsContext = null;
		try {
			otherAppsContext = createPackageContext(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getPreferences = otherAppsContext.getSharedPreferences("RSSReader",
				Context.MODE_PRIVATE);
		Configs.NewsMobile2G = getPreferences.getBoolean("NewsMobile2G", true);
		Configs.NewsMobile3G = getPreferences.getBoolean("NewsMobile3G", true);
		Configs.NewsWIFI = getPreferences.getBoolean("NewsWIFI", true);
		Configs.PictureMobile2G = getPreferences.getBoolean("PictureMobile2G",
				false);
		Configs.PictureMobile3G = getPreferences.getBoolean("PictureMobile3G",
				false);
		Configs.PictureWIFI = getPreferences.getBoolean("PictureWIFI", true);
		Configs.StartService = getPreferences.getBoolean("StartService", true);
		Configs.ServiceStarted = getPreferences.getBoolean("StartService", true);

		Configs.UpdateRatio = getPreferences.getInt("UpdateRatio", 1);
		Configs.Hour = getPreferences.getInt("Hour", 8);
		Configs.Minute = getPreferences.getInt("Minute", 30);
		Configs.FontSize = getPreferences.getInt("FontSize", 17);
		Configs.ShowCount = getPreferences.getInt("ShowItemCount", 20);
		Configs.Model = android.os.Build.MODEL;
		Configs.Version = "Android" + android.os.Build.VERSION.RELEASE;
		Configs.Language = Locale.getDefault().getDisplayLanguage();
		Configs.AppName = getString(R.string.app_name);
	}


	private void reloadFeeds() {
		// TODO Auto-generated method stub
		lv.setAdapter(null);
		prog.setVisibility(View.VISIBLE);
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				prog.setVisibility(View.GONE);
				if(err == 0 ){
					
				}else if(err == 1){
//					wrongConfiguration();
				}else if(err == 2){
//					wrongParse();
				}else if(err == 3){
//					wrongAddress();
				}
				
				setContents();
			}

		};

		Thread checkUpdate = new Thread() {
			public void run() {
				for (Feed feed: feeds){
					try {
						
							helper.downloadNewItems(RSSReaderActivity.this, feed.getId(), feed.getUrl());
						
						
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						err = 1;
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						err = 2;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						err = 3;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						err = 1;
					}
				}
				handler.sendEmptyMessage(0);
				
			}
		};

		checkUpdate.start();
	}

	
	private void init() {
		// TODO Auto-generated method stub
		helper = new Helper();
		header = (TextView) findViewById(R.id.title);
		prog = (ProgressBar)findViewById(R.id.progress);
		prog.setVisibility(View.GONE);
		lv = (ListView) findViewById(R.id.lvFeedList);
		lv.setCacheColorHint(0);
		lv.scrollTo(0, Configs.FeedListScroll);
		Configs.ItemListScroll = 0;
//		feeds = data.getFeeds();
		
	}


	private void clearFeed() {
		// TODO Auto-generated method stub
		FeedData data = new FeedData(this);
		data.deleteFeedItems(Configs.FeedId);
		FeedData data2 = new FeedData(this);
		data2.itemsCleared(Configs.FeedId);
		init();
		setContents();
	}

	private void editFeed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, EditFeedActivity.class);
		startActivity(intent);
		finish();
	}

	private void deleteFeed() {
		// TODO Auto-generated method stub
		FeedData data = new FeedData(this);
		//data.deleteFeed(Configs.FeedTitle);
		FeedData data2 = new FeedData(this);
		data2.deleteFeedItems(Configs.FeedId);
		init();
		setContents();
	}
	
	
	private boolean available() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// boolean yes = false;
		NetworkOperator net = new NetworkOperator();
		// UIManager ui = new UIManager(this);
		int state = net.getNetworkState(this);
		Configs.Network = state;
		if (state == 0) {
			noNetwork();
		} else if (state == 1) {
			if (Configs.NewsWIFI) {
				return true;
			} else {
				WiFiNotSelected();
			}
		} else if (state == 2) {
			if (Configs.NewsMobile3G) {
				return true;
			} else {
				TGNotSelected();
			}
		} else if (state == 3) {
			if (Configs.NewsMobile2G) {
				return true;
			} else {
				GNotSelected();
			}
		}
		return false;
	}


	private void feedLongClick() {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View dialogView = inflater.inflate(R.layout.list, null);

		TextView delete = (TextView)dialogView.findViewById(R.id.delete);
		TextView edit = (TextView)dialogView.findViewById(R.id.edit);
		TextView clear = (TextView)dialogView.findViewById(R.id.clear);
		

		delete.setText(getString(R.string.delete));
		edit.setText(getString(R.string.edit));
		clear.setText(getString(R.string.clear));
		delete.setOnClickListener(new TextView.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteFeed();
				dialog.dismiss();
			}
			
		});
		edit.setOnClickListener(new TextView.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editFeed();
				dialog.dismiss();
			}
			
		});
		clear.setOnClickListener(new TextView.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clearFeed();
				dialog.dismiss();
			}
			
		});

		dialog.setView(dialogView);
		dialog.setCancelable(true);
		dialog.show();
		


	}

	
	private void GNotSelected() {
		// TODO Auto-generated method stub
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		String titleText = getString(R.string.G_not_selected_title);
		builder.setTitle(titleText);

		builder.setMessage(getString(R.string.G_not_selected_massage));

		builder.setPositiveButton(getString(R.string.setting),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						goToSettings();
					}
				});

		builder.setNegativeButton(getString(R.string.no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});

		builder.show();

	}

	private void TGNotSelected() {
		// TODO Auto-generated method stub
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		String titleText = getString(R.string.TG_not_selected_title);
		builder.setTitle(titleText);

		builder.setMessage(getString(R.string.TG_not_selected_massage));

		builder.setPositiveButton(getString(R.string.setting),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						goToSettings();
					}
				});

		builder.setNegativeButton(getString(R.string.no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});

		builder.show();
	}

	private void WiFiNotSelected() {
		// TODO Auto-generated method stub
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		String titleText = getString(R.string.WiFi_not_selected_title);
		builder.setTitle(titleText);

		builder.setMessage(getString(R.string.WiFi_not_selected_massage));

		builder.setPositiveButton(getString(R.string.setting),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						goToSettings();
					}
				});

		builder.setNegativeButton(getString(R.string.no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});

		builder.show();
	}

	private void noNetwork() {
		// TODO Auto-generated method stub
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this);
		String titleText = getString(R.string.no_network_title);
		builder.setTitle(titleText);

		builder.setMessage(getString(R.string.no_network_massage));

		builder.setNegativeButton(getString(R.string.close),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						finish();
					}
				});

		builder.show();
	}


	private void wrongConfiguration() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast, null);

		TextView text = (TextView) layout.findViewById(R.id.toast);

		text.setText(getString(R.string.wrong_configuration));
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	private void wrongParse() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast, null);

		TextView text = (TextView) layout.findViewById(R.id.toast);

		text.setText(getString(R.string.wrong_content));
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}

	private void wrongAddress() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast, null);

		TextView text = (TextView) layout.findViewById(R.id.toast);

		text.setText(getString(R.string.wrong_address));
		Toast toast = new Toast(this);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	private void goToSettings() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(RSSReaderActivity.this, SettingsActivity.class);
		startActivity(intent);
		finish();
	}
	
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setContents();
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			if(Configs.StartService){
				startService(new Intent(this, UpdateService.class));
//				Log.d("Service", "Service created");
				sharedPreferences = getSharedPreferences("RSSReader", Context.MODE_PRIVATE);
				Editor prefsPrivateEditor = sharedPreferences.edit();
				prefsPrivateEditor.putBoolean("ServiceStarted", true);
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// ##############################################################################
	// Functions appear when the hard menu button clicked
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		

		menu.add(0, 1, 0, "").setIcon(R.drawable.about_icon);
		menu.add(0, 2, 0, "").setIcon(R.drawable.share_icon).setVisible(false);
		menu.add(0, 3, 0, "").setIcon(R.drawable.add_feed);
		menu.add(0, 4, 0, "").setIcon(R.drawable.settings_icon);
		menu.add(0, 5, 0, "").setIcon(R.drawable.suggession);
		menu.add(0, 6, 0, "").setIcon(R.drawable.reload_icon1);
		return true;
	}

	// Go to add when item opted
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case 1:
			about();
			break;
		case 2:
			share();
			break;
		case 3:
			add();
			break;
		case 4:
			set();
			break;
		case 5:
			addSuggested();
			break;
		case 6:
			reload();
			break;
		}
		return false;

	}

	private void reload() {
		// TODO Auto-generated method stub
		if(available())
			reloadFeeds();
	}

	private void addSuggested() {
		// TODO Auto-generated method stub
		if (available()) {
			Intent intent = new Intent();
			intent.setClass(RSSReaderActivity.this,
					SuggestedFeedsActivity.class);
			startActivity(intent);
			finish();
		}
	}



	private void about() {
		// TODO Auto-generated method stub
		UIManager ui = new UIManager();
		ui.About(this);
	}

	private void share() {
		// TODO Auto-generated method stub

	}

	private void add() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(RSSReaderActivity.this, AddFeedActivity.class);
		startActivity(intent);
		finish();
	}

	private void set() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(RSSReaderActivity.this, SettingsActivity.class);
		startActivity(intent);
	}

}