package mx.uvdroids.spacecards;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import mx.uvdroids.spacecards.model.Achievement;
import mx.uvdroids.spacecards.model.SQLiteAchievementHelper;
import mx.uvdroids.spacecards.model.SQLiteScoreHelper;

public class CheckAchievements {

	public static void check(Context context){
		ArrayList<Achievement> achievements = SQLiteAchievementHelper.getAll(context);
		ArrayList<Achievement> unlocked = new ArrayList<Achievement>();
		for(int i = 0; i < achievements.size(); i++){
			Achievement a = achievements.get(i);
			if(a.status == 0){
				switch (i) {
				case 0:{
					int played = SQLiteScoreHelper.getAll(context).size();
					if(played >= 5)
						unlocked.add(a);
					break;
				}
				case 1:{
					int played = SQLiteScoreHelper.getAll(context).size();
					if(played >= 10)
						unlocked.add(a);
					break;
				}
				case 2:{
					int hard = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 15 and level = 'hard'");
					if(hard >= 1)
						unlocked.add(a);
					break;
				}
				case 3:{
					int expert = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 10 and level = 'expert'");
					if(expert >= 1)
						unlocked.add(a);
					break;
				}
				case 4:{
					int easy = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 10 and level = 'easy'");
					int medium = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 10 and level = 'medium'");
					int hard = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 10 and level = 'hard'");
					int expert = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 10 and level = 'expert'");
					if(easy >=1 && medium >= 1 && hard >= 1 && expert >= 1)
						unlocked.add(a);
					break;
				}
				case 5:{
					int hard = SQLiteScoreHelper.getCount(context, "SELECT * FROM scores WHERE score > 10 and level = 'hard'");
					if(hard >= 1)
						unlocked.add(a);
					break;
				}
				case 7:{
					int played = SQLiteScoreHelper.getAll(context).size();
					if(played >= 20)
						unlocked.add(a);
					break;
				}
				default:
					break;
				}
			}
		}
		int ids[] = new int[unlocked.size()];
		for(int i = 0; i < unlocked.size(); i++){
			Achievement a = unlocked.get(i);
			SQLiteAchievementHelper.unlock(a.id, context);
			ids[i] = a.id;
		}
		if(ids.length > 0){
			Toast.makeText(context, "You have unlocked: "+ids.length+" achievements", Toast.LENGTH_LONG).show();
		}
	}
	
	public static void unlock(Context con){
		SQLiteAchievementHelper.unlock(7, con);
		Toast.makeText(con, "You have unlocked 1 achievements", Toast.LENGTH_LONG).show();
	}
}
