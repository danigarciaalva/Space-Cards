package mx.uvdroids.spacecards;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class PlayActivity extends Activity{

	ViewPager viewPager = null;
	CirclePageIndicator mIndicator;
	private int level;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		getActionBar().hide();
		level = getIntent().getIntExtra(ChooseCategory.LEVEL,-1);
		Toast.makeText(this, String.valueOf(level), Toast.LENGTH_SHORT).show();
	}
}
