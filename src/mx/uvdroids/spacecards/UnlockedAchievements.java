package mx.uvdroids.spacecards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UnlockedAchievements extends Activity{

	LinearLayout layout;
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlocked);
		int ids[] = getIntent().getIntArrayExtra("ids_unlocked");
		setTitle("congratulations!");
		TextView text = (TextView)findViewById(R.id.unlocked_amount);
		text.setText("You have unlocked "+ids.length+" achievments");
	}
}
