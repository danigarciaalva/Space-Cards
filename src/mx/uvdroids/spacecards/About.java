package mx.uvdroids.spacecards;

import android.app.Activity;
import android.os.Bundle;

public class About extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getActionBar().setTitle("About");
	}
}
