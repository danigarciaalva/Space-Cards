package mx.uvdroids.spacecards;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private Button play, settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		play = (Button)findViewById(R.id.play);
		play.setOnClickListener(this);
		settings = (Button)findViewById(R.id.settings);
		settings.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.play:
			i = new Intent(this, ChooseCategory.class);
			break;
		case R.id.leaderboard:
			i = new Intent(this, Leaderboard.class);
			break;
		case R.id.achievements:
			i = new Intent(this, Achievements.class);
			break;
		case R.id.settings:
			i = new Intent(this, Settings.class);
			break;
		default:
			break;
		}
		startActivity(i);
	}

}
