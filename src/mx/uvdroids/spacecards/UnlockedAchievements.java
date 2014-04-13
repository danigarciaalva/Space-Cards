package mx.uvdroids.spacecards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class UnlockedAchievements extends Activity{

	LinearLayout layout;
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlocked);
		int ids[] = getIntent().getIntArrayExtra("ids_unlocked");
		TypedArray images = getResources().obtainTypedArray(R.array.achievements_icons);
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = (LinearLayout)inflater.inflate(R.layout.activity_unlocked, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		TextView text = (TextView)findViewById(R.id.unlocked_amount);
		text.setText("You have unlocked "+ids.length+" achievments");
		for(int i = 0; i < ids.length; i++){
			ImageView image = new ImageView(this);
			image.setLayoutParams(params);
			image.setBackgroundResource(images.getResourceId(ids[i], 0));
			layout.addView(image);
		}
	}
}
