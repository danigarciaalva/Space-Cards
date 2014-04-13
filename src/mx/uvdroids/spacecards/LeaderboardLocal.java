package mx.uvdroids.spacecards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LeaderboardLocal extends Fragment{

	ListView lista;
	String[] ITEMS = {"Dummy","Dummy","Dummy"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.leaderboard_local,container,false);
		lista = (ListView)v.findViewById(R.id.leaderboard_local_list);
		lista.setAdapter(new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, ITEMS));
		return v;
	}
}
