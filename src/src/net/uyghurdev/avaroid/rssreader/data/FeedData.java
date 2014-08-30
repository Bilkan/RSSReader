package net.uyghurdev.avaroid.rssreader.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.laplanete.mobile.example.Page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.uyghurdev.avaroid.rssreader.Configs;
import net.uyghurdev.avaroid.rssreader.Item;
import net.uyghurdev.avaroid.rssreader.LItem;
import net.uyghurdev.avaroid.rssreader.R;
import net.uyghurdev.avaroid.rssreader.operator.Feed;

/*
 * Datebase Operation class for Feed
 * Create:2012-6-30
 * Creator:Sarwan
 * 
 * 
 */
public class FeedData {

	private OpenHelper oh;
	private int[] itemIds;

	public FeedData(Context ctx) {

		oh = new OpenHelper(ctx);
	};

	// delete feed by feed id
	public void deleteFeed(int page, int item) {
		String[] args = { String.valueOf(page), String.valueOf(item) };

		oh.delete("feed", "pagenumber = " + page + " and " + " itemnumber = "
				+ item, null);

	}

	// get feeds
	public ArrayList<Feed> getFeeds() {

		ArrayList<Feed> list = new ArrayList<Feed>();
		String[] strClmns = new String[] { "_id", "title", "url",
				"newItemCount", "pagenumber", "itemnumber", "item_id" };
		String Orders = "item_id DESC";
		Cursor cursor = oh.getCursor("feed", strClmns, null, Orders, null);
		cursor.moveToLast();
		for (int m = 0; m < cursor.getCount(); m++) {
			Feed feed = new Feed();
			feed.setPage(cursor.getInt(4));
			feed.setItemnumber(cursor.getInt(5));
			feed.setId(cursor.getInt(0));
			feed.setTitle(cursor.getString(1));
			feed.setUrl(cursor.getString(2));
			feed.setNewItemCout(cursor.getInt(3));
			list.add(feed);
			cursor.moveToPrevious();
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		oh.close();
		return list;
	}

	// get feed Items
	public ArrayList<LItem> getFeedItems(int feedId) {
		ArrayList<LItem> list = new ArrayList<LItem>();

		String[] Clmns = new String[] { "_id", "title", "isNew" };
		String Orders = "pubDate desc";

		String selection = "feedId=" + feedId;
		Cursor cursor = oh.getCursor("item", Clmns, selection, Orders, ""
				+ Configs.ShowCount);
		itemIds = new int[cursor.getCount()];
		cursor.moveToFirst();
		for (int m = 0; m < cursor.getCount(); m++) {
			LItem item = new LItem();
			item.setId(cursor.getInt(0));
			itemIds[m] = cursor.getInt(0);
			item.setTitle(cursor.getString(1));
			item.setNewItem(cursor.getInt(2));
			list.add(item);
			cursor.moveToNext();
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		oh.close();
		return list;
	}

	// get item ids from getFeedItems(feedId)
	public int[] getItemIds() {
		return itemIds;
	}

	// get one Item
	public Item getItem(int id) {

		String[] clmns = new String[] { "title", "link", "description",
				"author", "imgurl", "pubDateString" };
		String selection = "_id=" + id;
		String Orders = "pubDate desc";
		Cursor cursor = oh.getCursor("item", clmns, selection, Orders, null);
		Item item = new Item();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			item.setTitle(cursor.getString(0));
			item.setLink(cursor.getString(1));
			item.setDescription(cursor.getString(2));
			item.setAuthor(cursor.getString(3));
			item.setImageUrl(cursor.getString(4));
			item.setPubDate(cursor.getString(5));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		oh.close();
		return item;

	}

	// get all items in one feed
	public void deleteFeedItems(int feedid) {
		// TODO Auto-generated method stub
		oh.delete("item", "feedId=" + feedid, null);

	}

	//
	public void itemsCleared(int feedid) {

		ContentValues cv = new ContentValues();
		cv.put("readCount", 0);
		cv.put("newItemCount", 0);
		cv.put("itemCount", 0);
		String whereClause = "_id=" + feedid;
		oh.update("feed", cv, whereClause, null);

	}

	// insert a feed to db
	public void addFeed(String title, String url) {
		String selection = "title='" + title + "'";
		oh.getCursor("item", null, selection, null, null);
		ContentValues cv = new ContentValues();
		cv.put("title", title);
		cv.put("url", url);
		cv.put("readCount", 0);
		cv.put("newItemCount", 0);
		cv.put("itemCount", 0);
		cv.put("enabled", 1);
		oh.insert("feed", "title", cv);

	}

	// edit feed
	public void editFeed(int feedId, String feedTitle, String feedUrl) {
		ContentValues cv = new ContentValues();
		cv.put("title", feedTitle);
		cv.put("url", feedUrl);

		String whereClause = "_id=" + feedId;
		oh.update("feed", cv, whereClause, null);
	}

	// delete all item in [item] table
	public void clearItemTable() {
		oh.delete("item", null, null);
	}

	// dele all recorn in feed table
	public void clearfeedTable() {
		oh.delete("feed", null, null);
	}

	// get item count by title
	public int getItemCount(String value) {

		int count = 0;
		String selection = "title='" + value + "'";
		try {
			Cursor cursor = oh.getCursor("item", null, selection, null, null);
			if (cursor != null && !cursor.isClosed()) {
				count = cursor.getCount();
				cursor.close();
			}
		} catch (Exception e) {
			// Log.d("Database", e.toString());
		}
		oh.close();
		return count;
	}

	// insert item
	public void addItem(int feedId, Item item) {
		ContentValues cv = new ContentValues();
		cv.put("feedId", feedId);
		cv.put("title", item.getTitle());
		cv.put("link", item.getLink());
		cv.put("description", item.getDescription());
		cv.put("author", item.getAuthor());
		cv.put("imgurl", item.getImageUrl());
		cv.put("pubDate", Date.parse(item.getPubDate()));
		cv.put("pubDateString", item.getPubDate());
		cv.put("isNew", 1);
		oh.insert("item", null, cv);
	}

	// change new state to Readed
	public void newItemRead(int id) {
		// TODO Auto-generated method stub

		ContentValues cv = new ContentValues();
		cv.put("isNew", 0);
		String whereClause = "_id=" + id;
		oh.update("item", cv, whereClause, null);
	}

	// feed items count -1
	public void newFeedItemRead(int feedId) {
		String[] clmns = new String[] { "newItemCount" };
		String selection = "_id=" + feedId;
		Cursor cursor = oh.getCursor("feed", clmns, selection, null, null);
		cursor.moveToFirst();
		int newItem = cursor.getInt(0);
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		ContentValues cv = new ContentValues();
		cv.put("newItemCount", newItem - 1);
		oh.update("feed", cv, "_id=" + feedId, null);
	}

	// feed items count +1
	public void newItemAdded(int feedId) {
		String[] clmns = new String[] { "newItemCount" };
		String selection = "_id=" + feedId;
		Cursor cursor = oh.getCursor("feed", clmns, selection, null, null);
		cursor.moveToFirst();
		int newItem = cursor.getInt(0);
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		ContentValues cv = new ContentValues();
		cv.put("newItemCount", newItem + 1);
		oh.update("feed", cv, "_id=" + feedId, null);

	}

	// /////////////////////////////?
	public int getItemNew(int id) {
		String[] clmns = new String[] { "isNew" };
		String selection = "_id=" + id;
		Cursor cursor = oh.getCursor("item", clmns, selection, null, null);
		cursor.moveToFirst();
		int newItem = cursor.getInt(0);
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		oh.close();
		return newItem;
	}

	//
	public int getExistingFeed(String string) {
		int count = 0;
		try {
			Cursor cursor = oh.getCursor("feed", null, "url='" + string + "'",
					null, null);
			if (cursor != null && !cursor.isClosed()) {
				count = cursor.getCount();
				cursor.close();
			}
		} catch (Exception e) {
			// Log.d("Database", e.toString());
		}
		oh.close();
		return count;
	}

	public int getItemCount() {
		int count = 0;
		try {
			Cursor cursor = oh.getCursor("item", new String[] { "_id" }, null,
					"_id desc", null);
			if (cursor != null && !cursor.isClosed()) {
				count = cursor.getCount();
				cursor.close();
				oh.close();
			}
		} catch (Exception e) {
			// Log.d("Database", e.toString());
		}

		return count;
	}

	// all items isNes state is Readed and All new Item Count is 0
	public void markAllRead() {
		ContentValues cvi = new ContentValues();
		cvi.put("isNew", 0);
		oh.update("item", cvi, null, null);
		ContentValues cvf = new ContentValues();
		cvf.put("newItemCount", 0);
		oh.update("feed", cvf, null, null);
	}

	// change isNew state-all is Readed in one feed
	public void itemsRead(int feedId) {
		ContentValues cvi = new ContentValues();
		cvi.put("isNew", 0);
		oh.update("item", cvi, "feedId=" + feedId, null);
		ContentValues cvf = new ContentValues();
		cvf.put("newItemCount", 0);
		oh.update("feed", cvf, "_id=" + feedId, null);

	}

	public void updateMoveadItems(int page, int itemA, int itemB) {
		String[] parameter = new String[] { "pagenumber", "itemnumber",
				"item_id", "_id" };
		int mypage = page;
		int myitemA = itemA;
		int myitemB = itemB;
		//
		Cursor cursorA = oh.getCursorT("feed", parameter, " pagenumber = "
				+ page + " and " + "itemnumber = " + myitemA, null, null, null);
		cursorA.moveToFirst();
		int item_idA = cursorA.getInt(2);
		int idA = cursorA.getInt(3);
		//
		//
		Cursor cursorB = oh.getCursorT("feed", parameter, " pagenumber = "
				+ page + " and " + "itemnumber = " + myitemB, null, null, null);
		cursorB.moveToFirst();
		int item_idB = cursorB.getInt(2);
		int idB = cursorB.getInt(3);
		//
		updateMOvedItem(mypage, myitemB, item_idB, idA);
		updateMOvedItem(mypage, myitemA, item_idA, idB);

	}

	public void updateMOvedItem(int page, int itemA, int item_id, int id) {
		ContentValues cv = new ContentValues();
		cv.put("pagenumber", page);
		cv.put("itemnumber", itemA);
		cv.put("item_id", item_id);
		oh.update("feed", cv, "pagenumber = " + page + " and " + "_id = " + id,
				null);

	}

	public void updateTable() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { "pagenumber", "itemnumber", "item_id" };
		String order = "item_id DESC";
		Cursor cursor = oh.getCursor("feed", columns, null, order, null);
		cursor.moveToLast();
		int a = cursor.getCount();
		int b = 8;
		int c;
		if (a % b == 0) {
			c = a / b;
		} else {
			c = a / b + 1;
		}
		for (int i = 0; i < c; i++) {

			int e;
			if (b + i * b > a - 1) {
				e = a;
			} else {
				e = b + i * b;
			}
			for (int j = i + i * (b - 1); j < e; j++) {
				ContentValues cv = new ContentValues();
				cv.put("pagenumber", i);
				cv.put("itemnumber", j);
				cv.put("item_id", i + j);
				oh.update("feed", cv, "pagenumber = ?", null);
				
			}
		}
	}
}
