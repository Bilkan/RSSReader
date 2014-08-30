package net.uyghurdev.avaroid.rssreader.operator;

import java.util.ArrayList;

import net.uyghurdev.avaroid.rssreader.Item;
import net.uyghurdev.avaroid.rssreader.data.FeedData;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

public class JSONManager {

	public ArrayList<Feed> getSugFeeds(Context ctx, String jStr) {
		// TODO Auto-generated method stub
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		

		try {
			// this will break the JSON messages into an array
			JSONArray jary = new JSONArray(jStr.replace((char) 65279, ' '));
			// loop through the array
			for (int i = 0; i < jary.length(); i++) {
				FeedData data = new FeedData(ctx);
				if(data.getExistingFeed(jary.getJSONObject(i).getString("url")) == 0){
					Feed feed = new Feed();
					feed.setTitle(jary.getJSONObject(i).getString("title"));
					feed.setUrl(jary.getJSONObject(i).getString("url"));
	
					feeds.add(feed);
				}
			}
			
		} catch (JSONException e) {
			
			return null;
		}
		 return feeds;
	}

	public String makeItemJson(Item item) {
		// TODO Auto-generated method stub
		String json = "{\"title\":\"" + item.getTitle() + "\",\"content\":\"" + item.getDescription() + "\",\"url\":\"" + item.getLink() + "\",\"imgurl\":" + item.getImageUrl() + "}";
		return json;
	}

}
