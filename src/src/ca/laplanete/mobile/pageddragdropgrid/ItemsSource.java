package ca.laplanete.mobile.pageddragdropgrid;

import android.content.Context;

import com.example.draggridview.R;

public class ItemsSource {
	Context cxt;

	public ItemsSource(Context _cxt) {
		this.cxt = _cxt;
	}

	String[] str = { "item1", "item2", "item3", "item4", "item5", "item6",
			"item7", "item8", "item9", "item10", "item11", "item12", "item13",
			"item14", "item15", "item16", "item17", "item18", "item19" };
	int[] drw = { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher };

	public String[] getString() {
		return str;
	}

	public int[] getDrawable() {
		return drw;
	}
}
