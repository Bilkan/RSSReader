package net.uyghurdev.avaroid.rssreader.tools;

import net.uyghurdev.avaroid.rssreader.operator.FeedParser;
import net.uyghurdev.avaroid.rssreader.operator.NetworkOperator;

import android.content.Context;

public class Helper {

	NetworkOperator netop;
	FeedParser parser;
	
	public Helper (){}
	


	public void downloadNewItems(Context ctx, int feedId, String feedUrl) throws Exception {
		// TODO Auto-generated method stub
		
		netop = new NetworkOperator();
		parser = new FeedParser();
		parser.parseFeed(ctx, feedId, feedUrl);

		
	}
	
	
	public static String removeHTML(String input) {
	    int i = 0;
	    String[] str = input.split("");

	    String s = "";
	    boolean inTag = false;

	    for (i = input.indexOf("<"); i < input.indexOf(">"); i++) {
	        inTag = true;
	    }
	    if (!inTag) {
	        for (i = 0; i < str.length; i++) {
	            s = s + str[i];
	        }
	    }
	    return s;
	}
	
}
