package net.uyghurdev.avaroid.rssreader.operator;

import java.util.ArrayList;

import net.uyghurdev.avaroid.rssreader.Configs;
import net.uyghurdev.avaroid.rssreader.R;
import net.uyghurdev.avaroid.rssreader.R.id;
import net.uyghurdev.avaroid.rssreader.R.layout;
import net.uyghurdev.avaroid.rssreader.tools.Helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedsAdapter extends BaseAdapter {
	
	private ArrayList<Feed> feeds;
	private Context ctx;
	private static LayoutInflater inflater=null;
	private Helper helper;

	public FeedsAdapter(Context context, ArrayList<Feed> fds){
		ctx = context;
		feeds = fds;
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		helper = new Helper();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feeds.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return feeds.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.feed, null);

        TextView title=(TextView)vi.findViewById(R.id.feedTitle);;
        TextView count=(TextView)vi.findViewById(R.id.newItemCount);;


        	if(feeds.get(position).getNewItemCount() > 0){
        		title.setTextColor(Color.BLUE);
        		count.setTextColor(Color.BLUE);
        		title.setTextSize(28);
        		count.setTextSize(28);  //.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
	        	title.setText(feeds.get(position).getTitle());
	        	count.setText("(" + feeds.get(position).getNewItemCount() + ")");
	        	
	        }else{
	        	title.setTextColor(Color.BLACK);
        		count.setTextColor(Color.BLACK);
	        	title.setTextSize(28);
        		count.setTextSize(28);
	        	title.setText(feeds.get(position).getTitle());
	        	count.setText("");
	        }
        
        
        

        return vi;
    
	}

}
