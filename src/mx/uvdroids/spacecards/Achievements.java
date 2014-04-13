package mx.uvdroids.spacecards;

import java.util.ArrayList;

import mx.uvdroids.spacecards.model.Achievement;
import mx.uvdroids.spacecards.model.SQLiteAchievementHelper;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Achievements extends Activity{

	ListView list_achievements;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievements);
		getActionBar().setTitle("Achievements");
		list_achievements = (ListView)findViewById(R.id.achievements_list);
		ArrayList<Achievement> achievements = SQLiteAchievementHelper.getAll(this);
		list_achievements.setAdapter(new AchievementsAdapter(this, R.layout.item_achievement, achievements));
	}
	
	class AchievementsAdapter extends ArrayAdapter<Achievement>{

		private ArrayList<Achievement> achievements;
		public AchievementsAdapter(Context context, int resource, ArrayList<Achievement> achievements) {
			super(context, resource, achievements);	
			this.achievements = achievements;
		}
		
		@Override
		public int getCount() {
			return this.achievements.size();
		}
		
		@Override
		public View getView(int position, View v, ViewGroup parent) {
			if(v == null){
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.item_achievement, null);
			}
			Achievement a = getItem(position);
			TextView name = (TextView)v.findViewById(R.id.name_achievement);
			name.setText(a.name);
			TextView desc = (TextView)v.findViewById(R.id.description_achievement);
			desc.setText(a.description);
			ImageView image = (ImageView)v.findViewById(R.id.image_achievement);
			image.setImageDrawable(getContext().getResources().getDrawable(a.status == 1 ? a.res : a.dis_res));
			return v;
		}
		
		
	}
}
