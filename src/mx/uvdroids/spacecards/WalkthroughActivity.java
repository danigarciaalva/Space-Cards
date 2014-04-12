package mx.uvdroids.spacecards;

import mx.uvdroids.spacecards.adapters.ViewAdapter;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Button;


public class WalkthroughActivity extends Activity {

	ViewPager viewPager = null;
	Button start;
	CirclePageIndicator mIndicator;
	Handler handler = null;
	private Runnable increment = new Runnable() {
		@Override
		public void run() {
			int currentItem = viewPager.getCurrentItem();
		      int maxItems = viewPager.getAdapter().getCount();
		      if (maxItems != 0)
		        viewPager.setCurrentItem((currentItem + 1) % maxItems, true);
		      else
		        viewPager.setCurrentItem(0, true);
		      handler.postDelayed(increment, 2000);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walktrought);
		getActionBar().hide();
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		viewPager.setAdapter(new ViewAdapter(this));
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
        handler = new Handler();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		handler.removeCallbacks(increment);
        handler.postDelayed(increment, 2000);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		handler.removeCallbacks(increment);
	}
}