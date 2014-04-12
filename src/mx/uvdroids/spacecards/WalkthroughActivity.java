package mx.uvdroids.spacecards;

import mx.uvdroids.spacecards.adapters.ViewAdapter;

import com.viewpagerindicator.UnderlinePageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;


public class WalkthroughActivity extends Activity {

	
	ViewPager viewPager = null;
	Button start;
	UnderlinePageIndicator mIndicator;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walktrought);
		getActionBar().hide();
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		viewPager.setAdapter(new ViewAdapter(this));
        mIndicator = (UnderlinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
	}
}