package mx.uvdroids.spacecards.adapters;

import java.util.ArrayList;

import mx.uvdroids.spacecards.R;
import mx.uvdroids.spacecards.model.ScorePOJO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LeaderBoardAdapter extends ArrayAdapter<ScorePOJO>{

	private ArrayList<ScorePOJO> scores;
	public LeaderBoardAdapter(Context context, int resource, ArrayList<ScorePOJO> objects) {
		super(context, resource, objects);
		this.scores = objects;
	}
	
	@Override
	public int getCount() {
		return this.scores.size();
	}
	
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		if(v == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_leaderboard, null);
		}
		TextView leader_name = (TextView)v.findViewById(R.id.leaderboard_name);
		TextView leader_score = (TextView)v.findViewById(R.id.leaderboard_score);
		ScorePOJO s = getItem(position);
		leader_name.setText(s.name);
		leader_score.setText(String.valueOf(s.score)+" pts");
		return v;
	}
}