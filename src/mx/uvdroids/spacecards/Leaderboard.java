package mx.uvdroids.spacecards;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Leaderboard extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		getActionBar().setTitle("Leaderboard");
		getSupportFragmentManager().beginTransaction().replace(R.id.content, new LeaderboardFragment()).commit();
	}
}
