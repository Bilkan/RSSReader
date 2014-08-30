package net.uyghurdev.avaroid.rssreader.tools;

/*This object contains the methods related to UI (such as, dialog, toast, ect.).
 * Created in Jan 18 2012
 * Created by Dighar
 * 
 * 
 * 
 * 
 * 
 */

import android.content.Context;
import android.content.Intent;

public class UIManager {

	Context ctx;
	boolean dialogOK;

	public UIManager(){}
	
	public UIManager(Context context) {
		ctx = context;
	}



	public void About(Context ctx) {
		// TODO Auto-generated method stub
		
		
		Intent intent = new Intent();
    	intent.setClass(ctx, net.uyghurdev.avaroid.rssreader.About.class);
		ctx.startActivity(intent);

	}



}
