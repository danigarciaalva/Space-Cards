package mx.uvdroids.spacecards;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import mx.uvdroids.spacecards.model.Files;
import mx.uvdroids.spacecards.model.Question;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayActivity extends Activity  implements OnItemClickListener, OnClickListener{

	String[] categories = {"water","manmade","landforms","cities","nighttime","weather"};
	ViewPager viewPager = null;
	CirclePageIndicator mIndicator;
	private int category;
	private ArrayList<Question> questions;
	private int correct = 0;
	private int total_questions = 0;
	private LinearLayout screen_pause, screen_correct, screen_wrong;
	private Button resume, exit, settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		getActionBar().hide();
		category = getIntent().getIntExtra(ChooseCategory.CATEGORY, -1);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				questions = Files.LoadQuestions(categories[category]);
				long seed = System.nanoTime();
				Collections.shuffle(questions, new Random(seed));
				total_questions = questions.size();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						viewPager = (ViewPager)findViewById(R.id.viewPagerQuestions);
						viewPager.setAdapter(new QuestionAdapter(getBaseContext(), questions));
				        mIndicator = (CirclePageIndicator)findViewById(R.id.indicatorQuestions);
				        mIndicator.setViewPager(viewPager);
					}
				});
			}
		}).start();
        screen_pause = (LinearLayout)findViewById(R.id.screen_pause);
        screen_correct = (LinearLayout)findViewById(R.id.screen_correct);
        screen_wrong = (LinearLayout)findViewById(R.id.screen_wrong);
        resume = (Button)screen_pause.findViewById(R.id.resume);
        settings = (Button)screen_pause.findViewById(R.id.settings_pause);
        settings.setOnClickListener(this);
        exit = (Button)screen_pause.findViewById(R.id.exit_game);
        resume.setOnClickListener(this);
        exit.setOnClickListener(this);
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
	    		answers.setAdapter(new AnswersAdapter(context,R.layout.item_question, q.posible_answers));
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
	
	boolean isFinish = false;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Question q = questions.get(viewPager.getCurrentItem());
		TextView score = null;
		Button next = null;
		if(position == q.position_correct_answer-1){
			score = (TextView)screen_correct.findViewById(R.id.score_counter);
			next = (Button)screen_correct.findViewById(R.id.next);
			correct++;
			showViews(true);
		}else{
			score = (TextView)screen_wrong.findViewById(R.id.score_counter);
			next = (Button)screen_wrong.findViewById(R.id.next);
			TextView correct_answer = (TextView)screen_wrong.findViewById(R.id.screen_wrong_correct);
			correct_answer.setText(q.posible_answers.get(q.position_correct_answer-1));
			showViews(false);
		}
		score.setText(String.valueOf(correct)+"/"+String.valueOf(total_questions));
		if(viewPager.getCurrentItem() >= questions.size()-1){
			next.setText("Finish");
			isFinish = true;
		}
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!isFinish){
					viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
					isShowingInfo = false;
					screen_correct.setVisibility(View.GONE);
					screen_wrong.setVisibility(View.GONE);
				}
				else
					saveScore();
			}
		});
	}

	public void saveScore(){
		System.out.println("Score: "+correct+" No. Preguntas: "+questions.size()+" Category: "+categories[category]);
		Intent i = new Intent(this, Score.class);
		i.putExtra("score", String.valueOf(correct));
		i.putExtra("n_preguntas", String.valueOf(questions.size()));
		i.putExtra("category", categories[category]);
		startActivity(i);
		finish();
	}
	
	public void showViews(boolean state){
		isShowingInfo = true;
		screen_correct.setVisibility(state ? View.VISIBLE : View.GONE);
		screen_wrong.setVisibility(state ? View.GONE : View.VISIBLE);
		views.get(viewPager.getCurrentItem()).setVisibility(View.GONE);
	}
	
	boolean isPaused = false;
	boolean isShowingInfo = false;
	@Override
	public void onBackPressed() {
		if(!isShowingInfo){
			if(!isPaused){
				views.get(viewPager.getCurrentItem()).setVisibility(View.GONE);
				screen_pause.setVisibility(View.VISIBLE);
				viewPager.setEnabled(false);
				views.get(viewPager.getCurrentItem()).setEnabled(false);
				isPaused = true;
			}else{
				views.get(viewPager.getCurrentItem()).setVisibility(View.VISIBLE);
				screen_pause.setVisibility(View.GONE);
				viewPager.setEnabled(true);
				views.get(viewPager.getCurrentItem()).setEnabled(true);
				isPaused = false;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resume:
			onBackPressed();
			break;
		case R.id.exit_game:
			finish();
			break;
		case R.id.settings_pause:
			Intent i = new Intent(this, Settings.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}
	
	class AnswersAdapter extends ArrayAdapter<String>{

		private ArrayList<String> answers;
		public AnswersAdapter(Context context, int resource, ArrayList<String> objects) {
			super(context, resource, objects);
			this.answers = objects;
		}
		
		@Override
		public int getCount() {
			return this.answers.size();
		}
		
		@Override
		public View getView(int position, View v, ViewGroup parent) {
			if(v == null){
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.item_question, null);
			}
			TextView answer = (TextView)v.findViewById(R.id.item_answer);
			answer.setText(getItem(position));
			return v;
		}
		
	}
}
