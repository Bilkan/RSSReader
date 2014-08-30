package net.uyghurdev.avaroid.rssreader.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.uyghurdev.avaroid.rssreader.Item;
import net.uyghurdev.avaroid.rssreader.data.FeedData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class FeedParser {



	public FeedParser() {
		
	}



	public void parseFeed(Context ctx, int feedId, String feedUrl) throws Exception {
		
		FeedData data = new FeedData(ctx);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try{
		
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(feedUrl);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("item");
			for (int i=0;i<items.getLength();i++){
				boolean add = true;
				Item itm = new Item();
				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j=0;j<properties.getLength();j++){
					Node property = properties.item(j);
					String name = property.getNodeName();
					if (name.equalsIgnoreCase("title")){
						
						int count = data.getItemCount(property.getFirstChild().getNodeValue());
						if(count > 0){
							add = false;
							return;
						}
						
						itm.setTitle(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase( "author")){
						itm.setAuthor(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase("description")){
						itm.setDescription(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase("content:encoded")){
						itm.setDescription(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase( "link")){
						itm.setLink(property.getFirstChild().getNodeValue());
					} else if (name.equalsIgnoreCase( "img")){
						Element imgElement =  (Element) property;
						itm.setImageUrl(imgElement.getAttribute("src"));
					} else if (name.equalsIgnoreCase("pubDate")){
						itm.setPubDate(property.getFirstChild().getNodeValue());
					} 
				}

				if(add){
					data.addItem(feedId, itm);
					data.newItemAdded(feedId);
				}
				
			}
			
		}catch(Exception e){
			throw e;
		}

		
	}
	
}
