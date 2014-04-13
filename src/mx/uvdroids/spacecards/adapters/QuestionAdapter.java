package mx.uvdroids.spacecards.adapters;


import java.util.ArrayList;
import java.util.LinkedList;
import mx.uvdroids.spacecards.R;
import mx.uvdroids.spacecards.model.Question;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionAdapter extends PagerAdapter {
	
	private Context context;
	ViewPager view = null;

    LinkedList<View> views;
    ArrayList<Question> questions;
    public QuestionAdapter(Context context, ArrayList<Question> questions) {
    	this.context = context;
    	views = new LinkedList<View>();
    	this.questions = questions;
    	for(Question q  : questions){
    		View v = LayoutInflater.from(context).inflate(R.layout.fragment_question, null);
    		TextView title = (TextView)v.findViewById(R.id.question);
    		ListView answers = (ListView)v.findViewById(R.id.options_question);
    		title.setText(q.question);
    		answers.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, q.posible_answers));
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
