package mx.uvdroids.spacecards.adapters;

import java.util.LinkedList;
import mx.uvdroids.spacecards.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ViewAdapter extends PagerAdapter {
	
	private Context context;
	ViewPager view = null;

    LinkedList<View> views;

    public ViewAdapter(Context context) {
    	final Activity a = (Activity)context;
    	this.context = context;
    	views = new LinkedList<View>();
    	String [] strings = a.getResources().getStringArray(R.array.tutorial_texts);
    	for(String s : strings){
    		View v = LayoutInflater.from(context).inflate(R.layout.fragment_tutorial, null);
    		TextView t = (TextView)v.findViewById(R.id.tutorial_desc);
    		t.setText(s);
    		views.add(v);
    	}
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public Object instantiateItem(View container, int position){
		View view = new  View(context);
		view = views.get(position);
		
		((ViewPager) container).addView(view, 0);
		return view;
	}
	
	@Override
    public void destroyItem(View container, int position, Object obj) {
           ((ViewPager) container).removeView((View) obj);
    }

    @Override
    public boolean isViewFromObject(View container, Object obj) {
           return container == obj;
    }

}
