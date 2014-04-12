package mx.uvdroids.spacecards;

import mx.uvdroids.spacecards.adapters.ViewAdapter;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;


public class WalkthroughActivity extends Activity {

	ViewPager viewPager = null;
	Button start;
	CirclePageIndicator mIndicator;
	Handler handler;
	VideoView video;
	Context context = this;
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
        start = (Button)findViewById(R.id.start_button);
        start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MainActivity.class);
				startActivity(i);
				finish();
			}
		});
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