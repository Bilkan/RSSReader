/**
 * Copyright 2012 
 * 
 * Nicolas Desjardins  
 * https://github.com/mrKlar
 * 
 * Facilite solutions
 * http://www.facilitesolutions.com/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ca.laplanete.mobile.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.uyghurdev.avaroid.rssreader.Configs;
import net.uyghurdev.avaroid.rssreader.R;
import net.uyghurdev.avaroid.rssreader.data.FeedData;
import net.uyghurdev.avaroid.rssreader.operator.Feed;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.laplanete.mobile.pageddragdropgrid.DragDropGrid;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGridAdapter;

public class ExamplePagedDragDropGridAdapter implements
		PagedDragDropGridAdapter  {

	private Context context;
	private PagedDragDropGrid gridview;
	DragDropGrid grid;
	ArrayList<Feed> feeds=new ArrayList<Feed>();
	List<Page> pages = new ArrayList<Page>();
	
	public ExamplePagedDragDropGridAdapter(Context context,
			PagedDragDropGrid gridview) {
		super();
		FeedData data = new FeedData(context);
		feeds = data.getFeeds();
		this.context = context;
		this.gridview = gridview;
		int a = feeds.size();
		int b = 8;
		int c;
		if (a % b == 0) {
			c = a / b;
		} else {
			c = a / b + 1;
		}
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < c; i++) {

			Page page = new Page();
			items = new ArrayList<Item>();
			int e;
			if (b + i * b > a - 1) {
				e = a;
			} else {
				e = b + i * b;
			}
			for (int j = i + i * (b-1); j < e; j++) {
				items.add(new Item(j, feeds.get(j).getTitle(), R.drawable.icon));
				page.setItems(items);
				
			}
			pages.add(page);
		}
		
	}

	@Override
	public int pageCount() {
		return pages.size();
	}

	private List<Item> itemsInPage(int page) {
		if (pages.size() > page) {
			return pages.get(page).getItems();
		}
		return Collections.emptyList();
	}

	@Override
	public View view(int page, int index) {
		
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		ImageView icon = new ImageView(context);
		Item item = getItem(page, index);
		icon.setImageResource(item.getDrawable());
		icon.setPadding(15, 15, 15, 15);

		layout.addView(icon);

		TextView label = new TextView(context);
		label.setTag("text");
		label.setText(item.getName());
		label.setTextColor(Color.BLACK);
		label.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

		label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		// only set selector on every other page for demo purposes
		// if you do not wish to use the selector functionality, simply
		// disregard this code
		if (page % 2 == 0) {
			setViewBackground(layout);
			layout.setClickable(true);
			layout.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					return gridview.onLongClick(v);
				}
			});
		}
		layout.addView(label);
		return layout;
	}

	//@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setViewBackground(LinearLayout layout) {
		if (android.os.Build.VERSION.SDK_INT >= 16) {
			layout.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.ic_launcher));
		}
	}

	private Item getItem(int page, int index) {
		List<Item> items = itemsInPage(page);
		return items.get(index);
	}

	@Override
	public int rowCount() {
		return AUTOMATIC;
	}

	@Override
	public int columnCount() {
		return AUTOMATIC;
	}

	@Override
	public int itemCountInPage(int page) {
		return itemsInPage(page).size();
	}

	public void printLayout() {
		int i = 0;
		for (Page page : pages) {
			Log.d("Page", Integer.toString(i++));

			for (Item item : page.getItems()) {
				Log.d("Item", Long.toString(item.getId()));
			}
		}
	}

	private Page getPage(int pageIndex) {
		
		return pages.get(pageIndex);
	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		Log.e("Swap Item", " A: "+itemIndexA+" B: "+ itemIndexB);
		getPage(pageIndex).swapItems(itemIndexA, itemIndexB);
	}

	@Override
	public void moveItemToPreviousPage(int pageIndex, int itemIndex) {
		int leftPageIndex = pageIndex - 1;
		if (leftPageIndex >= 0) {
			Page startpage = getPage(pageIndex);
			Page landingPage = getPage(leftPageIndex);

			Item item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);
		}
	}

	@Override
	public void moveItemToNextPage(int pageIndex, int itemIndex) {
		int rightPageIndex = pageIndex + 1;
		if (rightPageIndex < pageCount()) {
			Page startpage = getPage(pageIndex);
			Page landingPage = getPage(rightPageIndex);

			Item item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);
		}
	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex)  {
		
		Configs.FeedId = feeds.get(itemIndex).getId();
		Configs.FeedUrl = feeds.get(itemIndex).getUrl();
		Configs.FeedTitle = feeds.get(itemIndex).getTitle();
		FeedData data = new FeedData(context);
		//data.deleteFeed(Configs.FeedTitle);
		FeedData data2 = new FeedData(context);
		data2.deleteFeed(pageIndex, itemIndex);
		//gridview.refreshView(context,gridview);
		data2.updateTable();
		getPage(pageIndex).deleteItem(itemIndex);
		
		
	}


	

	@Override
	public int deleteDropZoneLocation() {
		return BOTTOM;
	}

	@Override
	public boolean showRemoveDropZone() {
		return true;
	}


	@Override
	public void refreshCurrentView(PagedDragDropGridAdapter adapter,
			PagedDragDropGrid gridview) {
		// TODO Auto-generated method stub
		gridview.setAdapter(adapter);
	}


	



	

}
