package net.uyghurdev.avaroid.rssreader;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import net.uyghurdev.avaroid.rssreader.service.UpdateService;
import net.uyghurdev.avaroid.rssreader.tools.UIManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

public class SettingsActivity extends Activity {

	

	private SharedPreferences sharedPreferences;

	LinearLayout newsNet;
	LinearLayout picNet;
	LinearLayout nickname;
	
	RelativeLayout updateRatio;
	RelativeLayout updateTime;
	
	TextView appTitle;
	TextView newsNetTitle;
	TextView newsNetState;
	TextView picNetTitle;
	TextView picNetState;
	TextView itemCount;
	TextView clearItems;
	TextView clearAll;
	TextView fontSizeMes;
	TextView tvUpdate;
	TextView ratioDaily;
	TextView ratioTimes;
	TextView time;
	TextView read;
	
	EditText count;
	EditText size;
	
	Spinner spRatio;
	
	TimePicker startTime;
	
	CheckBox clrItms;
	CheckBox clrAll;
	CheckBox cbUpdate;
	CheckBox cbRead;
	
	Button btn;
	UIManager ui;
	String[] times = {"1","2","3","4","6"};
	// private RadioButton day;
	// private RadioButton week;
	// private Spinner weekdays;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.settings);
		init();
		newsNet.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ui.setAlert(Dialogs.SelectNewsNet);
				SelectNewsNet();
			}

		});

		picNet.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ui.setAlert(Dialogs.SelectPictureNet);
				SelectPictureNet();
			}

		});
		
		cbUpdate.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked){
					updateRatio.setVisibility(View.VISIBLE);
					updateTime.setVisibility(View.VISIBLE);
					Configs.StartService = true;
				}else{
					updateRatio.setVisibility(View.GONE);
					updateTime.setVisibility(View.GONE);
					Configs.StartService = false;
				}
			}
			
		});

		btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Configs.ShowCount = Integer.parseInt(count.getText().toString());

				saveSettings();
				
				if(cbRead.isChecked()){
					FeedData data = new FeedData(SettingsActivity.this);
					data.markAllRead();
				}

				if (clrAll.isChecked()) {
					clearFeedTableMessage();
				} else if (clrItms.isChecked()) {
					FeedData data = new FeedData(SettingsActivity.this);
					data.clearItemTable();
					
					finish();
				} else {
					
					finish();
				}

			}
		});
	}

	

	private void init() {
		ui = new UIManager(this);
		newsNet = (LinearLayout) findViewById(R.id.viewNews_layout);
		picNet = (LinearLayout) findViewById(R.id.viewImage_layout);
		updateRatio = (RelativeLayout)findViewById(R.id.updateRatio);
		updateTime = (RelativeLayout)findViewById(R.id.updateTime);
		spRatio = (Spinner)findViewById(R.id.spRatio);
		fontSizeMes = (TextView) findViewById(R.id.fontSizeMes);
		tvUpdate = (TextView) findViewById(R.id.tvUpdate);
		ratioDaily = (TextView) findViewById(R.id.ratioDaily);
		ratioTimes = (TextView) findViewById(R.id.ratioTimes);
		time = (TextView) findViewById(R.id.time);
		appTitle = (TextView) findViewById(R.id.appTitle);
		newsNetTitle = (TextView) findViewById(R.id.viewNews_textTitle);
		newsNetState = (TextView) findViewById(R.id.viewNews_textState);
		picNetTitle = (TextView) findViewById(R.id.viewImage_textTitle);
		picNetState = (TextView) findViewById(R.id.viewImage_textState);
		count = (EditText)findViewById(R.id.etItemCount);
		size = (EditText)findViewById(R.id.size);
		itemCount = (TextView) findViewById(R.id.tvItemCount);
		clearItems = (TextView) findViewById(R.id.tvClearLabel);
		clearAll = (TextView) findViewById(R.id.tvClearAllLabel);
		read = (TextView)findViewById(R.id.tvAllRead);
		cbRead = (CheckBox)findViewById(R.id.cbAllRead);
		btn = (Button) findViewById(R.id.btnOK);
		cbUpdate = (CheckBox)findViewById(R.id.cbUpdate);
		
		clrItms = (CheckBox)findViewById(R.id.cbClearItems);
		clrAll = (CheckBox)findViewById(R.id.cbClearFeeds);
		
		startTime = (TimePicker)findViewById(R.id.startTime);
		startTime.setIs24HourView(true);

		appTitle.setText(getString(R.string.app_name));
		newsNetTitle.setText(getString(R.string.news_net_title));
		picNetTitle.setText(getString(R.string.picture_net_title));
		itemCount.setText(getString(R.string.item_count));
		clearItems.setText(getString(R.string.clear_items));
		clearAll.setText(getString(R.string.clear_all));
		fontSizeMes.setText(getString(R.string.font_size_mes));
		tvUpdate.setText(getString(R.string.update_check_label));
		
		ratioDaily.setText(getString(R.string.daily));
		
		ratioTimes.setText(getString(R.string.times));
		time.setText(getString(R.string.update_time));
		read.setText(getString(R.string.all_read));
		btn.setText(getString(R.string.yes));
		
		cbUpdate.setChecked(Configs.StartService);
		if(!cbUpdate.isChecked()){
			updateRatio.setVisibility(View.GONE);
			updateTime.setVisibility(View.GONE);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, times);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRatio.setAdapter(adapter);
		spRatio.setSelection(getPositionFromAdapter(spRatio.getAdapter(),String.valueOf("" + Configs.UpdateRatio)));
		
		startTime.setCurrentHour(Configs.Hour);
		startTime.setCurrentMinute(Configs.Minute);

		setStates();
		
	}
	

	private void setStates() {
		// TODO Auto-generated method stub
		count.setText("" + Configs.ShowCount);
		size.setText("" + Configs.FontSize);
		String newsState = "";
		String picState = "";
		if (Configs.NewsWIFI) {
			newsState += "Wi-Fi";
		}
		if (Configs.NewsMobile3G) {
			if (newsState.length() > 0) {
				newsState += ", 3G";
			} else {
				newsState += "3G";
			}
		}
		if (Configs.NewsMobile2G) {
			if (newsState.length() > 0) {
				newsState += ", GPRS";
			} else {
				newsState += "GPRS";
			}
		}
		newsNetState.setText(newsState);

		if (Configs.PictureWIFI) {
			picState += "Wi-Fi";
		}
		if (Configs.PictureMobile3G) {
			if (picState.length() > 0) {
				picState += ", 3G";
			} else {
				picState += "3G";
			}
		}
		if (Configs.PictureMobile2G) {
			if (picState.length() > 0) {
				picState += ", GPRS";
			} else {
				picState += "GPRS";
			}
		}
		picNetState.setText(picState);
	}
	
	private int getPositionFromAdapter(SpinnerAdapter adapter, String strValue) {
		int nPosition=0;
		for(int nIndex=0;nIndex<adapter.getCount();nIndex++)
		{
			if(adapter.getItem(nIndex).toString().trim().toLowerCase().equalsIgnoreCase(strValue.trim().toLowerCase()))
			{
				nPosition= nIndex;
				
			}
		}
		return nPosition;
	}

	private void saveSettings() {
		// TODO Auto-generated method stub
		Configs.ShowCount = Integer.parseInt(count.getText().toString());
		Configs.FontSize = Integer.parseInt(size.getText().toString());
		Configs.UpdateRatio = Integer.parseInt(spRatio.getSelectedItem().toString());
		if(Configs.Hour != startTime.getCurrentHour() || Configs.Minute != startTime.getCurrentMinute()){
//			Configs.StartService = true;
			stopService(new Intent(this, UpdateService.class));
		}
		if(!Configs.StartService){
			stopService(new Intent(this, UpdateService.class));
		}
		Configs.Hour = startTime.getCurrentHour();
		Configs.Minute = startTime.getCurrentMinute();
		
		sharedPreferences = getSharedPreferences("RSSReader", Context.MODE_PRIVATE);
		Editor prefsPrivateEditor = sharedPreferences.edit();
		prefsPrivateEditor.putBoolean("NewsMobile2G", Configs.NewsMobile2G);
		prefsPrivateEditor.putBoolean("NewsMobile3G", Configs.NewsMobile3G);
		prefsPrivateEditor.putBoolean("NewsWIFI", Configs.NewsWIFI);
		prefsPrivateEditor.putBoolean("PictureMobile2G",
				Configs.PictureMobile2G);
		prefsPrivateEditor.putBoolean("PictureMobile3G",
				Configs.PictureMobile3G);
		prefsPrivateEditor.putInt("ShowItemCount", Configs.ShowCount);
		prefsPrivateEditor.putInt("FontSize", Configs.FontSize);
		prefsPrivateEditor.putInt("UpdateRatio", Configs.UpdateRatio);
		prefsPrivateEditor.putInt("Hour", Configs.Hour);
		prefsPrivateEditor.putInt("Minute", Configs.Minute);
		prefsPrivateEditor.putBoolean("PictureWIFI", Configs.PictureWIFI);
		prefsPrivateEditor.putBoolean("StartService", Configs.StartService);
		prefsPrivateEditor.commit();
	}

	private void SelectNewsNet() {
		// TODO Auto-generated method stub
		
		String[] nets = { "WiFi", "3G", "2G" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMultiChoiceItems(nets, new boolean[] { Configs.NewsWIFI,
				Configs.NewsMobile3G, Configs.NewsMobile2G },
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							Configs.NewsWIFI = isChecked;
							break;
						case 1:
							Configs.NewsMobile3G = isChecked;
							break;
						case 2:
							Configs.NewsMobile2G = isChecked;
							break;
						}
					}

				});
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View dialogView = inflater.inflate(R.layout.netstate, null);


		Button close = (Button)dialogView.findViewById(R.id.close);
		

		close.setText(getString(R.string.close));
		
		close.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setStates();
				dialog.dismiss();
			}
			
		});
		dialog.setView(dialogView);

		dialog.show();
		


	}

	private void SelectPictureNet() {
		// TODO Auto-generated method stub
		String[] nets = { "WiFi", "3G", "2G" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMultiChoiceItems(nets, new boolean[] { Configs.PictureWIFI,
				Configs.PictureMobile3G, Configs.PictureMobile2G },
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							Configs.PictureWIFI = isChecked;
							break;
						case 1:
							Configs.PictureMobile3G = isChecked;
							break;
						case 2:
							Configs.PictureMobile2G = isChecked;
							break;
						}
					}
				});
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View dialogView = inflater.inflate(R.layout.netstate, null);


		Button close = (Button)dialogView.findViewById(R.id.close);
		

		close.setText(getString(R.string.close));
		
		close.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setStates();
				dialog.dismiss();
			}
			
		});
		dialog.setView(dialogView);

		dialog.show();
	}


	

	// Show a message if you select to delete feed, if yes then delete all feeds
	// and items
	private void clearFeedTableMessage() {
		// TODO Auto-generated method stub
		CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(this, true);
		String titleText=getString(R.string.clear_all);
		builder.setTitle(titleText);

		builder.setMessage(getString(R.string.clear_all_message));

		builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
				FeedData data = new FeedData(SettingsActivity.this);
				data.clearItemTable();
				FeedData data2 = new FeedData(SettingsActivity.this);
				data2.clearfeedTable();

				Intent backintent = new Intent(SettingsActivity.this,
						RSSReaderActivity.class);
				startActivity(backintent);
				finish();
			}
		});
		builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
			}
		});
		builder.show();

	}
	
	
	@Override
	protected void onResume()
	{
	    super.onResume();

	    //do not give the editbox focus automatically when activity starts
	    count.clearFocus();
	    newsNet.requestFocus();
	}

}
