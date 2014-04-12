package mx.uvdroids.spacecards;

import mx.uvdroids.spacecards.adapters.QuestionAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class PlayActivity extends Activity{

	ViewPager viewPager = null;
	CirclePageIndicator mIndicator;
	//private int level;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		getActionBar().hide();
		//level = getIntent().getIntExtra(ChooseCategory.LEVEL,-1);
		viewPager = (ViewPager)findViewById(R.id.viewPagerQuestions);
		viewPager.setAdapter(new QuestionAdapter(this));
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicatorQuestions);
        mIndicator.setViewPager(viewPager);
	}
	
	class LoadQuestions extends AsyncTask<Integer, Void, Boolean>{

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(getBaseContext());
			dialog.setTitle(getBaseContext().getResources().getString(R.string.loading_questions));
			dialog.show();
		}
		@Override
		protected Boolean doInBackground(Integer... params) {
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dialog.dismiss();
			if(result)
				System.out.println("Ok");
		}
		
	}
}
