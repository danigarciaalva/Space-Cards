package mx.uvdroids.spacecards;

import mx.uvdroids.spacecards.adapters.ViewAdapter;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.VideoView;


public class WalkthroughActivity extends Activity {

	ViewPager viewPager = null;
	Button start;
	CirclePageIndicator mIndicator;
	Handler handler;
	VideoView video;
	private Runnable increment = new Runnable() {
		@Override
		public void run() {
			int currentItem = viewPager.getCurrentItem();
		      int maxItems = viewPager.getAdapter().getCount();
		      if (maxItems != 0)
		        viewPager.setCurrentItem((currentItem + 1) % maxItems, true);
		      else
		        viewPager.setCurrentItem(0, true);
		      handler.postDelayed(increment, 4000);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walktrought);
		getActionBar().hide();
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		video = (VideoView)findViewById(R.id.tutorial_video);
		Uri videoUrl = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.space2);
		video.setVideoURI(videoUrl);
		video.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer arg0) {
				video.start();
			}
		});
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
        handler.postDelayed(increment, 4000);
        //video.start();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		handler.removeCallbacks(increment);
		//video.stopPlayback();
	}
}