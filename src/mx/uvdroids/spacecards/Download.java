package mx.uvdroids.spacecards;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Download extends Activity{

	private ProgressBar progressBar;
	private int progressStatus = 0;
	private TextView textView;
	private Handler handler = new Handler();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		getActionBar().hide();
		progressBar = (ProgressBar) findViewById(R.id.downloading);
		textView = (TextView) findViewById(R.id.downloading_progress);
		 new Thread(new Runnable() {
		     public void run() {
		        while (progressStatus < 100) {
		           progressStatus += 1;
		    handler.post(new Runnable() {
		    public void run() {
		       progressBar.setProgress(progressStatus);
		       textView.setText(progressStatus+"/"+progressBar.getMax());
		    }
		        });
		        try {
		           Thread.sleep(200);
		        } catch (InterruptedException e) {
		           e.printStackTrace();
		        }
		     }
		  }
		  }).start();
	}
}
