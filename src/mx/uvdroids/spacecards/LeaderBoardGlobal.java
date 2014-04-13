package mx.uvdroids.spacecards;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.uvdroids.spacecards.adapters.LeaderBoardAdapter;
import mx.uvdroids.spacecards.model.ScorePOJO;
import mx.uvdroids.spacecards.utils.Connection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class LeaderBoardGlobal extends Fragment{

	ListView lista;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.leaderboard_local,container,false);
		lista = (ListView)v.findViewById(R.id.leaderboard_local_list);
		if(Connection.checkInternetConnection(getActivity())){
			LoadHighScores task = new LoadHighScores();
			task.execute();
		}else{
			Toast.makeText(getActivity(), "Network unavailable", Toast.LENGTH_LONG).show();
		}
		return v;
	}
	
	class LoadHighScores extends AsyncTask<Void, Void, Boolean>{
		
		private ArrayList<ScorePOJO> scores;
		@Override
		protected Boolean doInBackground(Void... params) {
			try{
				scores = new ArrayList<ScorePOJO>();
				URLConnection conn = new URL("http://mobileapps.dragonflylabs.com.mx/spaceapps/highscores/").openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String json = br.readLine();
				JSONArray highscores = new JSONObject(json).getJSONArray("highscores");
				for(int i = 0; i < highscores.length(); i++){
					JSONObject o = highscores.getJSONObject(i);
					ScorePOJO p = new ScorePOJO();
					p.name = o.getString("user");
					p.score = o.getInt("score");
					p.date = o.getString("date");
					scores.add(p);
				}
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				lista.setAdapter(new LeaderBoardAdapter(getActivity(), R.layout.item_leaderboard, scores));
			}
		}
	}
	
}
