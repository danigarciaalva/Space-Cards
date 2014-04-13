package mx.uvdroids.spacecards;

import java.util.ArrayList;

import mx.uvdroids.spacecards.adapters.QuestionAdapter;
import mx.uvdroids.spacecards.model.Files;
import mx.uvdroids.spacecards.model.Question;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class PlayActivity extends Activity{

	String[] categories = {"water","manmade","landforms","cities","nighttime","weather"};
	ViewPager viewPager = null;
	CirclePageIndicator mIndicator;
	private int level;
	private int category;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		getActionBar().hide();
		level = getIntent().getIntExtra(ChooseCategory.LEVEL,-1);
	}
	
	class LoadQuestions extends AsyncTask<Void, Void, Boolean>{

		private ProgressDialog dialog;
		private ArrayList<Question> questions;
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getBaseContext());
			dialog.setTitle(getBaseContext().getResources().getString(R.string.loading_questions));
			dialog.show();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			try{
				questions = Files.LoadQuestions(categories[category]);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dialog.dismiss();
			if(result){
				viewPager = (ViewPager)findViewById(R.id.viewPagerQuestions);
				viewPager.setAdapter(new QuestionAdapter(getBaseContext(), questions));
		        mIndicator = (CirclePageIndicator)findViewById(R.id.indicatorQuestions);
		        mIndicator.setViewPager(viewPager);
			}
				
		}
		
	}
}
