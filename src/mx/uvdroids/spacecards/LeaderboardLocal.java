package mx.uvdroids.spacecards;

import java.util.ArrayList;

import mx.uvdroids.spacecards.adapters.LeaderBoardAdapter;
import mx.uvdroids.spacecards.model.SQLiteScoreHelper;
import mx.uvdroids.spacecards.model.ScorePOJO;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
	
}
