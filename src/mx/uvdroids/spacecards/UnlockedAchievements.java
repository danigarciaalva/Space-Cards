package mx.uvdroids.spacecards;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class UnlockedAchievements extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int length = getIntent().getIntArrayExtra("ids_unlocked").length;
		Toast.makeText(this, String.valueOf(length), Toast.LENGTH_SHORT).show();
	}
}
