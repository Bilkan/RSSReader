package net.uyghurdev.avaroid.rssreader;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
	
	private ArrayList<LItem> items;
	private Context ctx;
	private static LayoutInflater inflater=null;
	
	public ItemAdapter(Context context, ArrayList<LItem> itms){
		ctx = context;
		items = itms;
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
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
            vi = inflater.inflate(R.layout.item, null);

        TextView title=(TextView)vi.findViewById(R.id.itemTitle);


        	if(items.get(position).getNewItem() == 1){
        		title.setTextColor(Color.BLUE);
        		title.setTextSize(21);
	        	title.setText(items.get(position).getTitle());
	        	
	        }else{
	        	title.setTextColor(Color.BLACK);
	        	title.setTextSize(21);
	        	title.setText(items.get(position).getTitle());
	        }
        
       
        
        return vi;
	}

}
