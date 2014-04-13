package mx.uvdroids.spacecards.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Environment;

public class Files {
	public static final String APP_FOLDER ="/SpaceCards/";
	public static final String IMAGES = "Images/";
	public static final String FILES = "Files/";
	
	public static final File ruta_sd = Environment.getExternalStorageDirectory();
	public static void checkFolder(String folder){
		File carpeta = new File(ruta_sd.getAbsolutePath(),folder);	    
	    if(!carpeta.exists())
	    	carpeta.mkdir();
	}
	public static void createJson(String json, String name,String folder) {
	    checkFolder(APP_FOLDER);
	    checkFolder(APP_FOLDER+folder);
	    File f = new File(ruta_sd.getAbsolutePath(), APP_FOLDER+folder+name);
	    try {
	    OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));
	    fout.write(json);
	    fout.close();
	    System.out.println("se creo el archivo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String LoadJson(String name,String folder){
		String json = "";
		File f = new File(ruta_sd.getAbsolutePath(), APP_FOLDER+folder+name);
		System.out.println(APP_FOLDER+folder+name);
		try {
    	    BufferedReader fin =
    	        new BufferedReader(
    	            new InputStreamReader(
    	                new FileInputStream(f)));
    	    try{
    	    StringBuilder string = new StringBuilder();
    	    String cad = null;
    	    while((cad = fin.readLine())!= null)
    	    	string.append(cad);
    	    json = string.toString();
    	    }catch(Exception e){}
    	    fin.close();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		        return null;
		    }
		System.out.println(json);
		return json;
	}
	public static ArrayList<Question> LoadQuestions(String category){
		ArrayList<Question> ArrayQuestions= new ArrayList<Question>();
		
		try {
		JSONObject obj = new JSONObject(LoadJson(category+".json",FILES));
		JSONArray questions = obj.getJSONArray("questions");
		for(int i=0;i<questions.length();i++){
			JSONObject question = questions.getJSONObject(i);
			Question q = new Question();
			q.question = question.getString("question");
			q.image = question.getString("image");
			q.position_correct_answer = question.getInt("correct");
			JSONArray answers = question.getJSONArray("answers");
			q.posible_answers = new ArrayList<String>();
			for(int j=0;j<answers.length();j++){
				q.posible_answers.add(answers.getString(j));
				System.out.println(answers.getString(j));
			}
			ArrayQuestions.add(q);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ArrayQuestions;
	}
}
