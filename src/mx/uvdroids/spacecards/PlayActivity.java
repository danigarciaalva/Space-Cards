package mx.uvdroids.spacecards;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import mx.uvdroids.spacecards.model.Files;
import mx.uvdroids.spacecards.model.Question;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayActivity extends Activity  implements OnItemClickListener{

	String[] categories = {"water","manmade","landforms","cities","nighttime","weather"};
	ViewPager viewPager = null;
	CirclePageIndicator mIndicator;
	private int level;
	private int category;
	private ArrayList<Question> questions;
	private int correct = 0;
	private int total_questions = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		getActionBar().hide();
		level = getIntent().getIntExtra(ChooseCategory.LEVEL,-1);
		category = getIntent().getIntExtra(ChooseCategory.CATEGORY, -1);
		questions = Files.LoadQuestions(categories[category]);
		total_questions = questions.size();
		viewPager = (ViewPager)findViewById(R.id.viewPagerQuestions);
		viewPager.setAdapter(new QuestionAdapter(getBaseContext(), questions));
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicatorQuestions);
        mIndicator.setViewPager(viewPager);
	}
	
	LinkedList<View> views;
	class QuestionAdapter extends PagerAdapter{
		private Context context;
		ViewPager view = null;
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
	    		answers.setOnItemClickListener(PlayActivity.this);
	    		String[] name = q.image.split("/");
		        String realname = name[name.length-1];
	    		File file = new File(Files.ruta_sd+Files.APP_FOLDER+Files.IMAGES,realname);
	    		ImageView image = (ImageView)v.findViewById(R.id.image_question);
	    		image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		RelativeLayout v = (RelativeLayout)views.get(viewPager.getCurrentItem());
		Question q = questions.get(viewPager.getCurrentItem());
		LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout layout = null;
		TextView score = null;
		if(position == q.position_correct_answer){
			layout = (LinearLayout)inflater.inflate(R.layout.screen_correct, null);
			score = (TextView)layout.findViewById(R.id.score_counter);
			correct++;
		}else{
			layout = (LinearLayout)inflater.inflate(R.layout.screen_wrong, null);
			score = (TextView)layout.findViewById(R.id.score_counter);
		}
		layout.setLayoutParams(params);
		score.setText(String.valueOf(correct)+"/"+String.valueOf(total_questions));
		Button next = (Button)layout.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			}
		});
		layout.setVisibility(View.VISIBLE);
		v.addView(layout);
	}

}
