package mx.uvdroids.spacecards.model;

import java.util.ArrayList;

import mx.uvdroids.spacecards.R;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAchievementHelper extends SQLiteOpenHelper {
	Context context;
	public SQLiteAchievementHelper(Context context, String nombre, CursorFactory factory, int version) {
		super(context, nombre, factory, version);
		this.context = context;
	}
	
	@SuppressLint("Recycle")
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table achievements(id integer primary key, name text, description text, status integer, res integer,dis_res integer)");
		String[] names = context.getResources().getStringArray(R.array.achievements_names);
		String[] descriptions = context.getResources().getStringArray(R.array.achievements_descriptions);
		TypedArray icons = context.getResources().obtainTypedArray(R.array.achievements_icons);
		TypedArray icons_disabled = context.getResources().obtainTypedArray(R.array.achievements_icons_disabled);
		ArrayList<Achievement> arrayAchievements = new ArrayList<Achievement>();
		Achievement a = null;
		for(int i = 0; i < names.length; i++){
			a = new Achievement(names[i], descriptions[i], 0, icons.getResourceId(i, -1), icons_disabled.getResourceId(i, -1));
			arrayAchievements.add(a);
		}
		ContentValues registro = new ContentValues();
		for(Achievement achieve : arrayAchievements){
			registro.put("name",achieve.name);
			registro.put("description",achieve.description);
			registro.put("status",achieve.status);
			registro.put("res",achieve.res);
			registro.put("dis_res",achieve.dis_res);
			db.insert("achievements", null, registro);
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
		db.execSQL("drop table if exists achievements");
		db.execSQL("create table achievements(id integer primary key, name text, description text, status integer, res integer,dis_res integer)");		
	}
	public static void insert(Achievement achievement,Context c){
		SQLiteAchievementHelper admin = new SQLiteAchievementHelper(c, "achievements",null,1);
		SQLiteDatabase db = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("name",achievement.name);
		registro.put("description",achievement.description);
		registro.put("status",achievement.status);
		registro.put("res",achievement.res);
		registro.put("dis_res",achievement.dis_res);
		db.insert("achievements", null, registro);
		db.close();
	}
	public static void unlock(int id,Context c){
		SQLiteAchievementHelper admin = new SQLiteAchievementHelper(c, "achievements",null,1);
		SQLiteDatabase db = admin.getWritableDatabase();
		db.execSQL("UPDATE achievements SET status=1 WHERE id="+id);
		db.close();
	}
	public static ArrayList<Achievement> getAll(Context con) {
		ArrayList<Achievement> achievements;
		Achievement a;
		SQLiteAchievementHelper admin = new SQLiteAchievementHelper(con, "achievements",null,1);
		SQLiteDatabase db = admin.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * from achievements", null);
		achievements = new ArrayList<Achievement>();
		if (c.moveToFirst()) {
		     do {
		    	  a = new Achievement();
		    	  a.id = c.getInt(0);
		    	  a.name = c.getString(1);
		    	  a.description = c.getString(2);
		    	  a.status = c.getInt(3);
		    	  a.res = c.getInt(4);
		    	  a.dis_res = c.getInt(5);
		    	  achievements.add(a);
		     } while(c.moveToNext());
		}
		db.close();
		return achievements;
	}
}
