package net.uyghurdev.avaroid.rssreader.service;

import java.util.Timer;
import java.util.TimerTask;

import net.uyghurdev.avaroid.rssreader.RSSReaderActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	
	private RSSReaderActivity rss;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		
		//this is starting the service from a BroadcastReceiver
		Intent myIntent = new Intent(context, UpdateService.class);
		myIntent.putExtra("extraData", "somedata");
		context.startService(myIntent);
		
		//Update when the given time is up
		Log.d("My Log","Receiver Received!");
		Toast.makeText(context, "RSSReader Item Updating Started!", Toast.LENGTH_LONG).show();
	//	rss.upDate();
	//	rss.ReloadAll(allCursor);
	//	Toast.makeText(context, "RSSReader Item Updating Done!", Toast.LENGTH_LONG).show();
	}
}
