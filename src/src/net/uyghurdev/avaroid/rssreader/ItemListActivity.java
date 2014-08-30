package net.uyghurdev.avaroid.rssreader;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import net.uyghurdev.avaroid.rssreader.operator.NetworkOperator;
import net.uyghurdev.avaroid.rssreader.tools.Helper;
import net.uyghurdev.avaroid.rssreader.tools.UIManager;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ItemListActivity extends Activity {


	private TextView feedTitle;
	private ListView itemList;
	private Helper helper;
	private ArrayList<LItem> items;
	private ProgressBar prog;
	int itmCount;
	int err = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemlist);
		
		init();
		
		prepareContent();
		


//		registerForContextMenu(lv);



		itemList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				if(items.get(position).getNewItem() == 1){
					FeedData data = new FeedData(ItemListActivity.this);
					data.newItemRead(items.get(position).getId());
					FeedData data2 = new FeedData(ItemListActivity.this);
					data2.newFeedItemRead(Configs.FeedId);
				}
				Configs.IdIndex = position;
				Intent intent = new Intent(ItemListActivity.this, ContentActivity.class);
				startActivity(intent);
				finish();
			}
		});
		

		itemList.setOnScrollListener(new ListView.OnScrollListener(){

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				Configs.ItemListScroll = itemList.getScrollY();
			}
			
		});
		
	}
	
	private void prepareContent(){
		

		itemList.setAdapter(null);
		
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				prog.setVisibility(View.GONE);
//				if (itmCount == 0){
//					Intent intent = new Intent(ItemListActivity.this,
//							RSSReaderActivity.class);
//					startActivity(intent);
//					finish();
//				}
				
				setContent();
			}
		};

		Thread checkUpdate = new Thread() {
			public void run() {
				FeedData data = new FeedData(ItemListActivity.this);
				items = data.getFeedItems(Configs.FeedId);
				Configs.ItemIds = data.getItemIds();
				itmCount = items.size();

				handler.sendEmptyMessage(0);
				
			}
		};

		checkUpdate.start();
	}
	

	private void setContent() {
		// TODO Auto-generated method stub
		

		
		feedTitle.setText(Configs.FeedTitle);
		ItemAdapter adapter = new ItemAdapter(this, items);
		itemList.setAdapter(adapter);
	}

	private void init() {
		// TODO Auto-generated method stub
		helper = new Helper(); 
		feedTitle = (TextView) findViewById(R.id.title);
		itemList = (ListView) findViewById(R.id.lvItems);
		itemList.setCacheColorHint(0);
		itemList.scrollTo(0, Configs.ItemListScroll);
		prog = (ProgressBar)findViewById(R.id.progress);
		items = new ArrayList<LItem>();
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
	
	private void goToSettings() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, SettingsActivity.class);
		startActivity(intent);
		finish();
	}
	
	private void reloadItems() {
		// TODO Auto-generated method stub
		itemList.setAdapter(null);
		prog.setVisibility(View.VISIBLE);
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				prog.setVisibility(View.GONE);
				if(err == 0 ){
					
				}else if(err == 1){
					wrongConfiguration();
				}else if(err == 2){
					wrongParse();
				}else if(err == 3){
					wrongAddress();
				}
				
				setContent();
			}

		};

		Thread checkUpdate = new Thread() {
			public void run() {

				try {
					
					helper.downloadNewItems(ItemListActivity.this, Configs.FeedId, Configs.FeedUrl);
					FeedData data = new FeedData(ItemListActivity.this);
					items = data.getFeedItems(Configs.FeedId);
					Configs.ItemIds = data.getItemIds();
					itmCount = items.size();
					
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
		
				handler.sendEmptyMessage(0);
				
			}
		};

		checkUpdate.start();
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		prepareContent();
		
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


			Intent intent = new Intent(ItemListActivity.this,
					RSSReaderActivity.class);
			startActivity(intent);
//			overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	//##############################################################################
		// Functions appear when the hard menu button clicked
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);

			menu.add(0, 1, 0, "").setIcon(R.drawable.about_icon);
			menu.add(0, 2, 0, "").setIcon(R.drawable.share_icon).setVisible(false);
			menu.add(0, 3, 0, "").setIcon(R.drawable.read_icon);
			menu.add(0, 4, 0, "").setIcon(R.drawable.settings_icon);
			menu.add(0, 5, 0, "").setIcon(R.drawable.suggession).setVisible(false);
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
				read();
				break;
			case 4:
				set();
				break;
			case 5:
				
				break;
			case 6:
				reload();
				break;
			}
			return false;

		}

		private void reload() {
			// TODO Auto-generated method stub
			reloadItems();
		}



		private void about() {
			// TODO Auto-generated method stub
			UIManager ui = new UIManager();
			ui.About(this);
		}

		private void share() {
			// TODO Auto-generated method stub
			
		}

		private void read() {
			// TODO Auto-generated method stub
			FeedData data = new FeedData(ItemListActivity.this);
			data.itemsRead(Configs.FeedId);
			prepareContent();
		}

		private void set() {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(ItemListActivity.this, SettingsActivity.class);
			startActivity(intent);
		}

}
