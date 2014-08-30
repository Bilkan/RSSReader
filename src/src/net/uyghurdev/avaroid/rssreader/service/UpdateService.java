package net.uyghurdev.avaroid.rssreader.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.parsers.ParserConfigurationException;

import net.uyghurdev.avaroid.rssreader.Configs;
import net.uyghurdev.avaroid.rssreader.R;
import net.uyghurdev.avaroid.rssreader.RSSReaderActivity;
import net.uyghurdev.avaroid.rssreader.data.*;
import net.uyghurdev.avaroid.rssreader.operator.Feed;
import net.uyghurdev.avaroid.rssreader.operator.FeedParser;
import net.uyghurdev.avaroid.rssreader.operator.NetworkOperator;

import org.xml.sax.SAXException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Binder;
import android.os.IBinder;

public class UpdateService extends Service {

	private Timer mainTimer;
	private Timer findWiFi;
	private int HOUR;
	private int MINUTE;
	private int RATIO;
	private long delay;
	private long REPEAT;
	private int times = 0;
	// private final long HOUR = 3600000;
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		UpdateService getService() {
			return UpdateService.this;
		}
	}

	private TimerTask repeatTask = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			NetworkOperator net = new NetworkOperator();
			if (net.getNetworkState(UpdateService.this) == 1
					|| times >= 24 * 6 / RATIO) {
//				Log.d("Repeat", "Finally I got WiFi Connection!");
				findWiFi.cancel();
				FeedData data = new FeedData(UpdateService.this);
				showUpdatingNotif();
				int before = data.getItemCount();
//				Log.d("Repeat", "Update Started");
				updateFeeds();
				FeedData data2 = new FeedData(UpdateService.this);
				int after = data2.getItemCount();
				showUpdatedNotif(after - before);
			}
//			Log.d("Repeat", "There is no WiFi Connection!");
			times++;
		}

	};

	private TimerTask updateTask = new TimerTask() {
		@Override
		public void run() {
			// Log.i("TimerTask", "Timer task doing work");
	
			NetworkOperator net = new NetworkOperator();
			if (net.getNetworkState(UpdateService.this) == 1) {
				FeedData data = new FeedData(UpdateService.this);
				showUpdatingNotif();
				int before = data.getItemCount();
//				Log.d("Service", "Update Started");
				updateFeeds();
				FeedData data2 = new FeedData(UpdateService.this);
				int after = data2.getItemCount();
				showUpdatedNotif(after - before);
			} else {
//				Log.d("Service", "No WiFi");
				findWiFi = new Timer();
				findWiFi.schedule(repeatTask, 0, 600000);
			}

		}

	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Log.i("LocalService", "Received start id " + startId + ": " +
		// intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		// code to execute when the service is first created
		getPreferences();
//		Log.d("Service", "Service created");
		Calendar cal = Calendar.getInstance();
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int m = cal.get(Calendar.MINUTE);
		long now = h * 36000000 + m * 60000;
		long set = HOUR * 36000000 + MINUTE * 60000;
		if (set > now) {
			delay = (set - now) % REPEAT;
		} else {
			delay = REPEAT - (now - set) % REPEAT;
		}

		mainTimer = new Timer("UpdateTimer");
		mainTimer.schedule(updateTask, delay, REPEAT);
//		Log.d("Service", "Update triggered");
//		Log.d("Set to", "" + set / 1000);
//		Log.d("Now", "" + now / 1000);
//		Log.d("Delay", "" + delay / 1000);

	}

	private void getPreferences() {
		// TODO Auto-generated method stub
		Context otherAppsContext = null;
		try {
			otherAppsContext = createPackageContext(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SharedPreferences getPreferences = otherAppsContext
				.getSharedPreferences("RSSReader", Context.MODE_PRIVATE);


		RATIO = getPreferences.getInt("UpdateRatio", 1);
		HOUR = getPreferences.getInt("Hour", 8);
		MINUTE = getPreferences.getInt("Minute", 30);
		if (RATIO == 0) {
			RATIO = 1;
		}
		REPEAT = 36000000 * 24 / RATIO;

	}

	@Override
	public void onDestroy() {
		// code to execute when the service is shutting down
		// Log.i("Service", "Destroyed");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		// code to execute when the service is starting up
		// Log.i("Service", "Started");
	}

	private void updateFeeds() {
		// TODO Auto-generated method stub
		// Log.i("Service", "Updating feeds");
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		FeedData data = new FeedData(this);
		FeedParser prsr = new FeedParser();
		feeds = data.getFeeds();
		for (Feed feed : feeds) {
			try {
				prsr.parseFeed(this, feed.getId(), feed.getUrl());
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// Log.i("Service", "Update ended");
	}

	private void showUpdatingNotif() {
		// TODO Auto-generated method stub
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = getString(R.string.updating_started);
		long when = System.currentTimeMillis();
		
		Notification notification = new Notification(icon, tickerText, when);
		
		
		Context context = getApplicationContext();
		CharSequence contentTitle = getString(R.string.updating_now);
		// CharSequence contentText = getString(R.string.updating_started);
		Intent notificationIntent = new Intent(this, RSSReaderActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, "",
				contentIntent);
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
	}

	private void showUpdatedNotif(int i) {
		// TODO Auto-generated method stub
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		mNotificationManager.cancel(1);
		int icon = R.drawable.icon;
		CharSequence tickerText = getString(R.string.update_finish);
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		Context context = getApplicationContext();
		CharSequence contentTitle = "";
		if (i > 0) {
			contentTitle = getString(R.string.new_items).replace("%n", "" + i);
		} else {
			contentTitle = getString(R.string.no_new_item);
		}

		// CharSequence contentText = getString(R.string.updating_started);
		Intent notificationIntent = new Intent(this, RSSReaderActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, "",
				contentIntent);
		final int UPDATED_ID = 2;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(UPDATED_ID, notification);
	}

}
