package net.uyghurdev.avaroid.rssreader;

import java.util.Calendar;

import net.uyghurdev.avaroid.rssreader.data.FeedData;
import net.uyghurdev.avaroid.rssreader.operator.NetworkOperator;
import net.uyghurdev.avaroid.rssreader.tools.Helper;
import net.uyghurdev.avaroid.rssreader.tools.UIManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class ContentActivity extends Activity{
	private ScrollView content;
	private TextView appTitle;
	private TextView contentTitle;
	private TextView contentText;
	private TextView contentDate;
	private TextView contentLink;
	private ImageView contentImage;
	private ProgressBar prog;
	private SharedPreferences getPreferences;
	int xDown, yDown, xTouch = 0, yTouch = 0, xClickOffset = 0,
			yClickOffset = 0, xOffset = 0, yOffset = 0, xCurrent, yCurrent,
			xUp, yUp, yScroll;
	long pttime, lttime;
	boolean toRight = true;
	boolean moved = false;
	int moveX = 0, moveY = 0;
	Helper help;
	Item item;
	Display display;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);


		setContentView(R.layout.content);
		init();
		prepareContent();


//		contentImage.setOnClickListener(new ImageView.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent =new Intent();
//				intent.setClass(ContentActivity.this, ItemPicture.class);
//				startActivity(intent);
//
//			}
//			
//		});


		contentLink.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = item.getLink();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});

		content.setOnTouchListener(new ImageView.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				int tileSize = display.getWidth();
				// Log.d("Desplay", "screen width: " + tileSize);
				// TODO Auto-generated method stub

				// ---------------Action Down---------------------
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// Log.d("Action",
					// "down: " + event.getX() + "," + event.getY());
					pttime = Calendar.getInstance().getTimeInMillis();
					xTouch = (int) event.getX();
					yTouch = (int) event.getY();
					xDown = (int) event.getX();
					yDown = (int) event.getY();
					xClickOffset = xTouch; // *****************
					yClickOffset = yTouch; // ****************
					
					moveX = xTouch;
					moveY = yTouch;

					// -----------------Action Move------------------------
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					// Log.d("Action", "moved.");
					lttime = Calendar.getInstance().getTimeInMillis();
					
					xOffset += xTouch - (int) event.getX();
					// offsets for scrolling the game board and other stuff
					// here, isn't related to clicking
					yOffset += yTouch - (int) event.getY();
					xCurrent = (int) event.getX();
					yCurrent = (int) event.getY();
					
					if (xCurrent != moveX || yCurrent != moveY){
						moved = true;
					}
					
					toRight = xCurrent - xTouch >= 0 ? true : false;
					// Log.d("Action Move", "moved." + xTouch + ", " + yDown
					// + "  to  " + xCurrent + ", " + yCurrent
					// + ".   Distance:" + (xCurrent - xTouch));
					// Log.d("toRight", ""+toRight);
					// movePage();
					content.scrollBy(0, yTouch - yCurrent);
					yScroll = yTouch - yCurrent;
					xTouch = xCurrent;
					yTouch = yCurrent;
					
					pttime = lttime;

					// ------------------Action Up----------------------------
				} else if (event.getAction() == MotionEvent.ACTION_UP) {


					
					lttime = Calendar.getInstance().getTimeInMillis();
					
					xUp = (int) event.getX();
					yUp = (int) event.getY();
//					Log.d("Disteance", "" + yScroll);
//					Log.d("Time", "" + (lttime - pttime));
					int mult = 5;
					int time = (int) (lttime - pttime);
					if(time == 0){
						mult = 10;
						time = 1;
					}
						
					for (int i = 0; i<time; i++){
						content.scrollBy(0, (int) (mult*yScroll/time));
					}
					
	
					
					// ++++++++++++++Moved to Right, X scale > 1/4 Screen
					// Size+++++++++
					if (toRight) {
						if (xUp - xDown > tileSize / 2) {

							if (Configs.IdIndex < Configs.ItemIds.length - 1)// This
																					// is
							// not Last Page, Move to Next Page
							{
							
								Configs.IdIndex++;
								prog.setVisibility(View.VISIBLE);
								content.setVisibility(View.GONE);
								prepareContent();
								content.scrollTo(0, 0);

							} else// This is Last Page
							{
								// moveBack();
							}
						} else {
							// moveBack();
						}

					} else {

						// ++++++++++++++Moved to Left, X scale > 1/4 Screen
						// Size+++++++++

			
						if ((xUp - xDown) < -tileSize / 2) {
							if (Configs.IdIndex == 0) // This is First Page
							{
								// moveBack();
							}

							else // This is not First Page, Move to Previous
									// Page
							{
								Configs.IdIndex--;
								prog.setVisibility(View.VISIBLE);
								content.setVisibility(View.GONE);
								prepareContent();
								content.scrollTo(0, 0);
							}
						} else {
							// moveBack();
						}

					}

				}
				return true;
			}

		});

	}
	
	private void prepareContent(){
		

		
		final Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				prog.setVisibility(View.GONE);
				content.setVisibility(View.VISIBLE);

				setContents();
			}
		};

		Thread checkUpdate = new Thread() {
			public void run() {

				FeedData data = new FeedData(ContentActivity.this);
				item = data.getItem(Configs.ItemIds[Configs.IdIndex]); 

				handler.sendEmptyMessage(0);
			}
		};

		checkUpdate.start();
	}
	

	private void setContents() {
		// TODO Auto-generated method stub
		if(newItem() == 1){
			FeedData data = new FeedData(this);
			data.newItemRead(Configs.ItemIds[Configs.IdIndex]);
			FeedData data2 = new FeedData(this);
			data2.newFeedItemRead(Configs.FeedId);
		}
		


			appTitle.setGravity(Gravity.LEFT);
			contentText.setGravity(Gravity.LEFT);
			contentDate.setGravity(Gravity.LEFT);
			contentLink.setGravity(Gravity.LEFT);
			appTitle.setTypeface(Typeface.DEFAULT);
			contentTitle.setTypeface(Typeface.DEFAULT);
			contentText.setTypeface(Typeface.DEFAULT);
			contentDate.setTypeface(Typeface.DEFAULT);
			contentLink.setTypeface(Typeface.DEFAULT);
		

		appTitle.setText(Configs.FeedTitle);
		contentTitle.setText(item.getTitle());
		contentText.setTextSize(Configs.FontSize);
		contentText.setText(item.getDescription().replaceAll("\\<[^>]*>","").replaceAll("&amp;","").replaceAll("&nbsp;","").replace("     \n", "").replace("    ", " ").replace("  ", " "));
		contentDate.setText(item.getPubDate());
		contentLink.setText(getString(R.string.link));

//		if (Configs.ShowPicture()) {
//			ImageLoader imgldr = new ImageLoader(this);
//			imgldr.DisplayImage(item.getImageUrl(), this, contentImage);
//			Log.d("PictureUrl", item.getLink());
//		}else{
			contentImage.setVisibility(View.GONE);
//		}
		

	}



	private int newItem() {
		// TODO Auto-generated method stub
		FeedData data = new FeedData(this);
		return data.getItemNew(Configs.ItemIds[Configs.IdIndex]);
	}

	private void init() {
		// TODO Auto-generated method stub
		
		help = new Helper();
		prog = (ProgressBar)findViewById(R.id.progress);
		display = getWindowManager().getDefaultDisplay();
		content = (ScrollView) findViewById(R.id.scroll);
		appTitle = (TextView) findViewById(R.id.appTitle);
		contentTitle = (TextView) findViewById(R.id.conTitle);
		contentText = (TextView) findViewById(R.id.conText);
		contentDate = (TextView) findViewById(R.id.conDate);
		contentLink = (TextView) findViewById(R.id.conLink);
		contentImage = (ImageView) findViewById(R.id.conImage);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		prepareContent();
		
	}



	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


			Intent intent = new Intent(ContentActivity.this,
					ItemListActivity.class);
			startActivity(intent);
//			overridePendingTransition(R.anim.enter_back, R.anim.exit_back);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}




	// =============================================================================
	// Functions appear when the hard menu button clicked
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, 1, 0, "").setIcon(R.drawable.about_icon);
		menu.add(0, 2, 0, "").setIcon(R.drawable.share_icon);
		menu.add(0, 3, 0, "").setIcon(R.drawable.add_feed).setVisible(false);
		menu.add(0, 4, 0, "").setIcon(R.drawable.settings_icon);
		menu.add(0, 5, 0, "").setIcon(R.drawable.suggession).setVisible(false);
		menu.add(0, 6, 0, "").setIcon(R.drawable.reload_icon1).setVisible(false);

		return true;
	}

	// Go to add when item opted
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case 1:
			about();
			break;
		case 2:
			share();
			break;
		case 3:
			add();
			break;
		case 4:
			set();
			break;
		case 5:
			
			break;
		case 6:
			
			break;
		}
		return false;

	}



	private void about() {
		// TODO Auto-generated method stub
		UIManager ui = new UIManager();
		ui.About(this);
	}

	private void share() {
		// TODO Auto-generated method stub
//		JSONManager jman = new JSONManager();
		NetworkOperator net = new NetworkOperator();
		String share = item.getLink();
		net.shareItem(share);
	}

	private void add() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(ContentActivity.this, AddFeedActivity.class);
		startActivity(intent);
		finish();
	}

	private void set() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(ContentActivity.this, SettingsActivity.class);
		startActivity(intent);
	}

	public SharedPreferences getGetPreferences() {
		return getPreferences;
	}

	public void setGetPreferences(SharedPreferences getPreferences) {
		this.getPreferences = getPreferences;
	}

}