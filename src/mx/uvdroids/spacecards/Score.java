package mx.uvdroids.spacecards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Score extends FragmentActivity implements OnClickListener{

	String score;
	String n_preguntas;
	String category;
	Button play_again, back_to_menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		getActionBar().setTitle("My score");
		score = getIntent().getStringExtra("score");
		n_preguntas = getIntent().getStringExtra("n_preguntas");
		category = getIntent().getStringExtra("category");
		
		TextView score_text = (TextView)findViewById(R.id.score_score_counter);
		score_text.setText(String.valueOf(score)+"/"+String.valueOf(n_preguntas));
		TextView best_score = (TextView)findViewById(R.id.score_best_score);
		best_score.setText("Best score: ");
		play_again = (Button)findViewById(R.id.replay);
		back_to_menu = (Button)findViewById(R.id.back_to_main_menu);
		play_again.setOnClickListener(this);
		back_to_menu.setOnClickListener(this);
		getSupportFragmentManager().beginTransaction().replace(R.id.leaderboard_score, new LeaderboardFragment()).commit();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.replay:
			Intent i = new Intent(this, ChooseCategory.class);
			startActivity(i);
			finish();
			break;
		case R.id.back_to_main_menu:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_item_share){
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		    sharingIntent.setType("text/plain");
		    String shareBody = "Just answer "+score+" correct questions in the "+category+" category on Space Cards for Android";
		    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Space Cards score");
		    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		    startActivity(Intent.createChooser(sharingIntent, "Share via"));
		    return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
