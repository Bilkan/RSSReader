package net.uyghurdev.avaroid.rssreader.operator;

/*This object contains the methods related to network managing.
 * Created in Jan 17 2012
 * Created by Dighar
 * 
 * Jan 17 2012 15:20, Dighar moved in String urlToString(String url) method to Helper.java
 * Jan 17 2012 15:24, Dighar moved in sendReplyComment(String jsonString) method from JSONManager.java
 * Jan 18 2012 12:10, Dighar added getNetworkState(Context ctx) method
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.uyghurdev.avaroid.rssreader.Configs;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkOperator {

	//Constructor
	public NetworkOperator(){}
	
	//
	public String urlToString(String url) {
		// TODO Auto-generated method stub
		String str = "";
		URL jurl;
		try {

			jurl = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) jurl
					.openConnection();
			urlConn.setRequestProperty("User-Agent",Configs.Model + "; " + Configs.Version + "; " + Configs.Language + "; " + Configs.AppName);
			InputStream inputStream = urlConn.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line);
			}

			str = total.toString();
			// jsonString =
			// "[{\"appname\": \"Hero Of Sparta\",\"packagename\": \"com.gameloft.android.GAND.GloftSPAW.HeroOfSparta\",\"appicon\": \"heroofsparta.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Ø¦Û�Ù„Ù‰Ù¾Ø¨Û•\",\"packagename\": \"net.uyghurdev.avaroid.elipba\",\"appicon\": \"elipbe.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Ø±Û•Ø³Ù‰Ù…Ù„Ù‰Ùƒ ÙƒÙ‰ØªØ§Ø¨\",\"packagename\": \"net.uyghurdev.avaroid.picturebookreader\",\"appicon\": \"picturebook.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Ø¦Û�Ù„ÙƒÙ‰ØªØ§Ø¨\",\"packagename\": \"net.uyghurdev.app.avaroid.epubreader\",\"appicon\": \"epubreader.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"ÙŠÙ‰Ù„Ù†Ø§Ù…Û•\",\"packagename\": \"com.android.calendar\",\"appicon\": \"chisla.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Wallpapers\",\"packagename\": \"com.appsilicious.wallpapers\",\"appicon\": \"wallpapers.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Angry Birds\",\"packagename\": \"com.rovio.angrybirdsrio\",\"appicon\": \"angrybirds.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"3D Cat\",\"packagename\": \"com.junefsh.game.talking3dcat\",\"appicon\": \"3dcat.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Chat Free\",\"packagename\": \"com.j.jtalkfree\",\"appicon\": \"chatfree.png\",\"shared\": 0,\"downloaded\": 0},{\"appname\": \"Ú¾Ø§ÙŠÛ‹Ø§Ù†Ø§ØªÙ„Ø§Ø±\",\"packagename\": \"net.uyghurdev.avaroid.haywanatlar\",\"appicon\": \"haywanatlar.png\",\"shared\": 0,\"downloaded\": 0}]";

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	
	
	public int getNetworkState(Context ctx){
		
		ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		int state = 0;
		if(info != null && info.isConnected()){
			if(info.getType() == ConnectivityManager.TYPE_WIFI){
				//WiFi Network
				state = 1;
			}else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
				//Mobile Network
				TelephonyManager tm =  (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);        
				if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_1xRTT) {
					// Network type is 2G
					//Current network is 1xRTT
					//Constant Value: 7 (0x00000007)
					//Log.d("Mobile Network", "1xRTT");
					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_CDMA) {
					// Network type is 2G
					//Current network is CDMA: Either IS95A or IS95B
					//Constant Value: 4 (0x00000004)
					//Log.d("Mobile Network", "CDMA");
					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE) {
					// Network type is 2G
					//Current network is EDGE
					//Constant Value: 2 (0x00000002)
					//Log.d("Mobile Network", "EDGE");
					state = 3;
//				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EHRPD) {
//					// Network type is 2G
//					//Current network is eHRPD
//					//Constant Value: 14 (0x0000000e)
//					Log.d("Mobile Network", "eHRPD");
//					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
					// Network type is 3G
					//Current network is EVDO revision 0
					//Constant Value: 5 (0x00000005)
					//Log.d("Mobile Network", "EVDO_0");
					state = 2;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
					// Network type is 3G
					//Current network is EVDO revision A
					//Constant Value: 6 (0x00000006)
					//Log.d("Mobile Network", "EVDO_A");
					state = 2;
//				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_B) {
//					// Network type is 2G
//					//Current network is EVDO revision B
//					//Constant Value: 2 (0x00000002)
//					Log.d("Mobile Network", "EVDO_B");
//					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS) {
					// Network type is 2G
					//Current network is GPRS
					//Constant Value: 1 (0x00000001)
					//Log.d("Mobile Network", "GPRS");
					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA) {
					// Network type is 3G
					//Current network is HSDPA
					//Constant Value: 8 (0x00000008)
					//Log.d("Mobile Network", "HSDPA");
					state = 2;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA) {
					// Network type is 3G
					//Current network is HSPA
					//Constant Value: 10 (0x0000000a)
					//Log.d("Mobile Network", "HSPA");
					state = 2;
//				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPAP) {
//					// Network type is 2G
//					//Current network is HSPAP
//					//Constant Value: 15 (0x0000000f)
//					Log.d("Mobile Network", "HSPAP");
//					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSUPA) {
					// Network type is 3G
					//Current network is HSUPA
					//Constant Value: 9 (0x00000009)
					//Log.d("Mobile Network", "HSUPA");
					state = 2;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_IDEN) {
					// Network type is 2G
					//Current network is IDEN
					//Constant Value: 11 (0x0000000b)
					//Log.d("Mobile Network", "iDEN");
					state = 3;
//				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
//					// Network type is 4G
//					//Current network is LTE
//					//Constant Value: 13 (0x0000000d)
//					Log.d("Mobile Network", "LTE");
//					state = 2;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {
					// Network type is 2G
					//Current network is UMTS
					//Constant Value: UMTS
					//Log.d("Mobile Network", "UMTS");
					state = 3;
				} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
					// Network type is 2G
					//Current network is UNKNOWN
					//Constant Value: 0 (0x00000000)
					//Log.d("Mobile Network", "UNKNOWN");
					state = 3;
				}else{
					state = 3;
				}
				
			}
		}else{
			//Network is off
			state = 0;
		}
		return state;
	}
	

	public void shareItem(String shItem) {
		// TODO Auto-generated method stub
		try {
			HttpPost httpost = new HttpPost(Configs.ShareServer);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Shared", shItem));
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//			httpost.setHeader("User-Agent", Restore.Model + "; " + Restore.Version + "; " + Restore.Language + "; " + Restore.AppName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
