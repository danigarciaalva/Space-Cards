package mx.uvdroids.spacecards.model;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteScoreHelper extends SQLiteOpenHelper {
	public SQLiteScoreHelper(Context context, String nombre, CursorFactory factory, int version) {
		super(context, nombre, factory, version);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
		db.execSQL("drop table if exists scores");
		db.execSQL("create table scores(id integer primary key, score integer, date text, name text, level text,category text)");		
	}
	public static void insert(Score score,Context c){
		SQLiteAchievementHelper admin = new SQLiteAchievementHelper(c, "scores",null,1);
		SQLiteDatabase db = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("score",score.score);
		registro.put("date",score.date);
		registro.put("name",score.name);
		registro.put("level",score.level);
		registro.put("category",score.category);
		db.insert("scores", null, registro);
		db.close();
	}
	
	public static ArrayList<Score> getAll(Context con) {
		ArrayList<Score> puntuaciones;
		Score p;
		SQLiteAchievementHelper admin = new SQLiteAchievementHelper(con, "scores",null,1);
		SQLiteDatabase db = admin.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * from scores order by score DESC", null);
		puntuaciones = new ArrayList<Score>();
		if (c.moveToFirst()) {
		     do {
		    	  p = new Score();
		    	  p.id = c.getInt(0);
		    	  p.score = c.getInt(1);
		    	  p.date = c.getString(2);
		    	  p.name = c.getString(3);
		    	  p.level = c.getString(4);
		    	  p.category = c.getString(5);
		    	  puntuaciones.add(p);
		     } while(c.moveToNext());
		}
		db.close();
		return puntuaciones;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
}
