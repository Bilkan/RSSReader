package net.uyghurdev.avaroid.rssreader.data;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

	private static final String DB_PATH = "/data/data/net.uyghurdev.avaroid.rssreader/databases/"; // "/data/data/net.uyghurdev.app/databases/";
	private static final String DATABASE_NAME = "rss";
	private static final int DATABASE_VERSION = 3;

	private SQLiteDatabase db;

	public OpenHelper(Context ctx) {

		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE feed(_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,url TEXT,readCount INTEGER,newItemCount INTEGER,itemCount INTEGER, enabled INTEGER, pagenumber INTEGER, itemnumber INTEGER, item_id INTEGER);");

		db.execSQL("CREATE TABLE item(_id INTEGER PRIMARY KEY AUTOINCREMENT,feedId INTEGER,title TEXT,link TEXT,description TEXT,author TEXT,imgurl TEXT,pubDate DateTime,pubDateString TEXT, isNew INTEGER);");

		ContentValues cvj = new ContentValues();
		cvj.put("title", "جېك بلوگى");
		cvj.put("url", "http://jeckblog.com/feed/");
		cvj.put("readCount", 0);
		cvj.put("newItemCount", 0);
		cvj.put("itemCount", 0);
		cvj.put("enabled", 1);
		cvj.put("pagenumber", 0);
		cvj.put("itemnumber", 6);
		cvj.put("item_id", 6);
		db.insert("feed", "title", cvj);

		ContentValues cvq = new ContentValues();
		cvq.put("title", "قىسمەت");
		cvq.put("url", "http://www.qismet.me/feed");
		cvq.put("readCount", 0);
		cvq.put("newItemCount", 0);
		cvq.put("itemCount", 0);
		cvq.put("enabled", 1);
		cvq.put("pagenumber", 0);
		cvq.put("itemnumber", 7);
		cvq.put("item_id", 7);
		db.insert("feed", "title", cvq);

		ContentValues cva = new ContentValues();
		cva.put("title", "ئالىم بىز بلوگى");
		cva.put("url", "http://www.alimbiz.net/?feed=rss2");
		cva.put("readCount", 0);
		cva.put("newItemCount", 0);
		cva.put("itemCount", 0);
		cva.put("enabled", 1);
		cva.put("pagenumber", 0);
		cva.put("itemnumber", 1);
		cva.put("item_id", 1);
		db.insert("feed", "title", cva);

		ContentValues cvi = new ContentValues();
		cvi.put("title", "ئىزتىل تور خاتىرىسى");
		cvi.put("url", "http://www.iztil.com/?feed=rss2");
		cvi.put("readCount", 0);
		cvi.put("newItemCount", 0);
		cvi.put("itemCount", 0);
		cvi.put("enabled", 1);
		cvi.put("pagenumber", 0);
		cvi.put("itemnumber", 2);
		cvi.put("item_id", 2);
		db.insert("feed", "title", cvi);

		ContentValues cvu = new ContentValues();
		cvu.put("title", "ئۇيغۇربەگ تور تۇراسى");
		cvu.put("url", "http://www.uyghurbeg.net/feed");
		cvu.put("readCount", 0);
		cvu.put("newItemCount", 0);
		cvu.put("itemCount", 0);
		cvu.put("enabled", 1);
		cvu.put("pagenumber", 0);
		cvu.put("itemnumber", 3);
		cvu.put("item_id", 3);
		db.insert("feed", "title", cvu);

		ContentValues cvum = new ContentValues();
		cvum.put("title", "ئۈمىدلەن");
		cvum.put("url", "http://www.umidlen.com/feed");
		cvum.put("readCount", 0);
		cvum.put("newItemCount", 0);
		cvum.put("itemCount", 0);
		cvum.put("enabled", 1);
		cvum.put("pagenumber", 0);
		cvum.put("itemnumber", 4);
		cvum.put("item_id", 4);
		db.insert("feed", "title", cvum);

		ContentValues cvul = new ContentValues();
		cvul.put("title", "ئالىم ئەھەت تور خاتىرىسى");
		cvul.put("url", "http://www.alimahat.com/?feed=rss2");
		cvul.put("readCount", 0);
		cvul.put("newItemCount", 0);
		cvul.put("itemCount", 0);
		cvul.put("enabled", 1);
		cvul.put("pagenumber", 0);
		cvul.put("itemnumber", 0);
		cvul.put("item_id", 0);
		db.insert("feed", "title", cvul);

		ContentValues cvju = new ContentValues();
		cvju.put("title", "جۇلالىق بلوگى");
		cvju.put("url", "http://www.julaliq.com/?feed=rss2");
		cvju.put("readCount", 0);
		cvju.put("newItemCount", 0);
		cvju.put("itemCount", 0);
		cvju.put("enabled", 1);
		cvju.put("pagenumber", 0);
		cvju.put("itemnumber", 5);
		cvju.put("item_id", 5);
		db.insert("feed", "title", cvju);

		ContentValues cvn = new ContentValues();
		cvn.put("title", "نازۇگۇم");
		cvn.put("url", "http://www.nazugum.com/?feed=rss2");
		cvn.put("readCount", 0);
		cvn.put("newItemCount", 0);
		cvn.put("itemCount", 0);
		cvn.put("enabled", 1);
		cvn.put("pagenumber", 1);
		cvn.put("itemnumber", 0);
		cvn.put("item_id", 8);
		db.insert("feed", "title", cvn);

		ContentValues cvw = new ContentValues();
		cvw.put("title", "ۋەتەن بلوگى");
		cvw.put("url", "http://weten.me/?feed=rss2");
		cvw.put("readCount", 0);
		cvw.put("newItemCount", 0);
		cvw.put("itemCount", 0);
		cvw.put("enabled", 1);
		cvw.put("pagenumber", 1);
		cvw.put("itemnumber", 1);
		cvw.put("item_id", 9);
		db.insert("feed", "title", cvw);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECT);
		// onCreate(db);
	}

	private boolean checkdatabase() {
		// SQLiteDatabase checkdb = null;
		boolean checkdb = false;
		try {
			String myPath = DB_PATH + DATABASE_NAME;
			File dbfile = new File(myPath);
			// checkdb =
			// SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
			checkdb = dbfile.exists();

		} catch (SQLiteException e) {
			System.out.println("Database doesn't exist");
		}

		return checkdb;
	}

	public void dbOpen() {
		if (this.db == null || !this.db.isOpen()) {
			this.db = this.getWritableDatabase();
		}
	}

	/*
	 * Delete Fee by id
	 */
	// public void deleteFeed(int id) {
	// // TODO Auto-generated method stub
	// String[] args={String.valueOf(id)};
	// this.db = this.getWritableDatabase();
	// this.db.delete("feed", "_id=?", args);
	// }

	public void delete(String strTable, String strWhereCause, String[] args) {
		dbOpen();
		this.db.delete(strTable, strWhereCause, args);
		this.db.close();
	}

	// /*
	// * Get feeds to an array list of feeds
	// */
	// public ArrayList<Feed> getFeeds() {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ArrayList<Feed> list = new ArrayList<Feed>();
	// Cursor cursor = this.db.query("feed", new String[] {
	// "_id", "title", "url", "newItemCount" }, null, null, null, null,
	// "title asc");
	// cursor.moveToFirst();
	// for (int m = 0; m < cursor.getCount(); m++) {
	// Feed feed = new Feed();
	// feed.setId(cursor.getInt(0));
	// feed.setTitle(cursor.getString(1));
	// feed.setUrl(cursor.getString(2));
	// feed.setNewItemCout(cursor.getInt(3));
	// list.add(feed);
	// cursor.moveToNext();
	// }
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// this.db.close();
	// return list;
	// }

	public Cursor getCursor(String strTable, String[] Columns,
			String selection, String Orders, String Limt) {
		dbOpen();
		Cursor crsr = this.db.query(strTable, Columns, selection, null, null,
				null, Orders, Limt);
		// this.db.close();
		return crsr;
	}

	public Cursor getCursorT(String strTable, String[] Columns,
			String selection, String[] strArgs, String Orders, String Limt) {
		dbOpen();
		Cursor crsr = this.db.query(strTable, Columns, selection, strArgs,
				null, null, Orders);
		// this.db.close();
		return crsr;
	}

	// public ArrayList<LItem> getFeedItems(int feedId) {
	// // TODO Auto-generated method stub
	// ArrayList<LItem> list = new ArrayList<LItem>();
	// this.db = this.getWritableDatabase();
	// Cursor cursor = this.db.query("item", new String[] {
	// "_id", "title", "isNew" }, "feedId=" + feedId, null, null, null,
	// "pubDate desc", "" + Configs.ShowCount);
	//
	// cursor.moveToFirst();
	// for (int m = 0; m < cursor.getCount(); m++) {
	// LItem item = new LItem();
	// item.setId(cursor.getInt(0));
	// itemIds[m] = cursor.getInt(0);
	// item.setTitle(cursor.getString(1));
	// item.setNewItem(cursor.getInt(2));
	// list.add(item);
	// cursor.moveToNext();
	// }
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// this.db.close();
	// return list;
	// }

	// public Item getItem(int i) {
	// this.db = this.getWritableDatabase();
	// Cursor cursor = this.db.query("item", new String[] {
	// "title", "link", "description", "author", "imgurl", "pubDateString" },
	// "_id=" + i,
	// null, null, null, "pubDate desc");
	// cursor.moveToFirst();
	// Item item = new Item();
	// item.setTitle(cursor.getString(0));
	// item.setLink(cursor.getString(1));
	// item.setDescription(cursor.getString(2));
	// item.setAuthor(cursor.getString(3));
	// item.setImageUrl(cursor.getString(4));
	// item.setPubDate(cursor.getString(5));
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// this.db.close();
	// return item;
	// }

	// public void deleteFeedItems(int feedid) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// this.db.delete("item", "feedId=" + feedid, null);
	// this.db.close();
	// }

	public void update(String table, ContentValues cv, String whereClause,
			String[] whereArgs) {
		dbOpen();
		this.db.update(table, cv, whereClause, null);
		this.db.close();
	}

	// public void itemsCleared(int feedid) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cv = new ContentValues();
	// cv.put("readCount", 0);
	// cv.put("newItemCount", 0);
	// cv.put("itemCount", 0);
	// this.db.update("feed", cv, "_id=" + feedid, null);
	// this.db.close();
	// }

	public void insert(String table, String nullColumnHack, ContentValues cv) {
		dbOpen();
		this.db.insert(table, nullColumnHack, cv);
		this.db.close();
	}

	// public void addFeed(String title, String url){
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cv = new ContentValues();
	// cv.put("title", title);
	// cv.put("url", url);
	// cv.put("readCount", 0);
	// cv.put("newItemCount", 0);
	// cv.put("itemCount", 0);
	// cv.put("enabled", 1);
	// this.db.insert("feed", "title", cv);
	// this.db.close();
	// }

	// public void editFeed(int feedId, String feedTitle, String feedUrl) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cv = new ContentValues();
	// cv.put("title", feedTitle);
	// cv.put("url", feedUrl);
	// this.db.update("feed", cv, "_id=" + feedId, null);
	// this.db.close();
	// }

	// public void clearItemTable() {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// this.db.delete("item", null, null);
	// }

	// public void clearfeedTable() {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// this.db.delete("feed", null, null);
	// this.db.close();
	// }

	// public int getItemCount(String value) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// int count = 0;
	// try{
	// Cursor cursor = this.db.query("item", null, "title='" + value + "'",
	// null, //new String[] {value},
	// null, null, null);
	//
	// if (cursor != null && !cursor.isClosed()) {
	// count = cursor.getCount();
	// cursor.close();
	// }
	// }catch(Exception e){
	// // Log.d("Database", e.toString());
	// }
	// this.db.close();
	// return count;
	// }

	// public void addItem(int feedId, Item item) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cv = new ContentValues();
	// cv.put("feedId", feedId);
	// cv.put("title", item.getTitle());
	// cv.put("link", item.getLink());
	// cv.put("description", item.getDescription());
	// cv.put("author", item.getAuthor());
	// cv.put("imgurl", item.getImageUrl());
	// cv.put("pubDate", Date.parse(item.getPubDate()));
	// cv.put("pubDateString", item.getPubDate());
	// cv.put("isNew", 1);
	// this.db.insert("item", null, cv);
	// this.db.close();
	// }

	// public void newItemRead(int id) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cv = new ContentValues();
	// cv.put("isNew", 0);
	// this.db.update("item", cv, "_id=" + id, null);
	// this.db.close();
	//
	// }

	// public void newFeedItemRead(int feedId) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// Cursor cursor = this.db.query("feed", new String[] {"newItemCount"},
	// "_id=" + feedId, null, null, null, null);
	// cursor.moveToFirst();
	// int newItem = cursor.getInt(0);
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// ContentValues cv = new ContentValues();
	// cv.put("newItemCount", newItem - 1);
	// this.db.update("feed", cv, "_id=" + feedId, null);
	// this.db.close();
	// }

	// public void newItemAdded(int feedId) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// Cursor cursor = this.db.query("feed", new String[] {"newItemCount"},
	// "_id=" + feedId, null, null, null, null);
	// cursor.moveToFirst();
	// int newItem = cursor.getInt(0);
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// ContentValues cv = new ContentValues();
	// cv.put("newItemCount", newItem + 1);
	// this.db.update("feed", cv, "_id=" + feedId, null);
	// this.db.close();
	// }

	// public int getItemNew(int i) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// Cursor cursor = this.db.query("item", new String[] {"isNew"}, "_id=" + i,
	// null, null, null, null);
	// cursor.moveToFirst();
	// int newItem = cursor.getInt(0);
	// if (cursor != null && !cursor.isClosed()) {
	// cursor.close();
	// }
	// this.db.close();
	// return newItem;
	// }

	// public int getExistingFeed(String string) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// int count = 0;
	// try{
	// Cursor cursor = this.db.query("feed", null, "url='" + string + "'", null,
	// //new String[] {value},
	// null, null, null);
	//
	// if (cursor != null && !cursor.isClosed()) {
	// count = cursor.getCount();
	// cursor.close();
	// }
	// }catch(Exception e){
	// // Log.d("Database", e.toString());
	// }
	// this.db.close();
	// return count;
	// }

	// public int getItemCount() {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// int count = 0;
	// try{
	// Cursor cursor = this.db.query("item", new String[] {"_id"}, null, null,
	// //new String[] {value},
	// null, null, "_id desc");
	// if (cursor != null && !cursor.isClosed()) {
	// count = cursor.getCount();
	// cursor.close();
	// }
	// }catch(Exception e){
	// // Log.d("Database", e.toString());
	// }
	// this.db.close();
	// return count;
	// }

	// public void markAllRead() {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cvi = new ContentValues();
	// cvi.put("isNew", 0);
	// this.db.update("item", cvi, null, null);
	// ContentValues cvf = new ContentValues();
	// cvf.put("newItemCount", 0);
	// this.db.update("feed", cvf, null, null);
	// this.db.close();
	// }

	// public void itemsRead(int feedId) {
	// // TODO Auto-generated method stub
	// this.db = this.getWritableDatabase();
	// ContentValues cvi = new ContentValues();
	// cvi.put("isNew", 0);
	// this.db.update("item", cvi, "feedId=" + feedId, null);
	// ContentValues cvf = new ContentValues();
	// cvf.put("newItemCount", 0);
	// this.db.update("feed", cvf, "_id=" + feedId, null);
	// this.db.close();
	// }
}
