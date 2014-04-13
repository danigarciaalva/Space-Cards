package mx.uvdroids.spacecards;

import java.util.ArrayList;

import mx.uvdroids.spacecards.model.SQLiteScoreHelper;
import mx.uvdroids.spacecards.model.ScorePOJO;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeaderboardLocal extends Fragment{

	ListView lista;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.leaderboard_local,container,false);
		lista = (ListView)v.findViewById(R.id.leaderboard_local_list);
		ArrayList<ScorePOJO> pojos = SQLiteScoreHelper.getAll(getActivity());
		lista.setAdapter(new LeaderBoardAdapter(getActivity(), R.layout.item_leaderboard, pojos));
		return v;
	}
	
	class LeaderBoardAdapter extends ArrayAdapter<ScorePOJO>{

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
			leader_score.setText(String.valueOf(s.score));
			return v;
		}
	}
}
