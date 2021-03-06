package mx.uvdroids.spacecards;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import mx.uvdroids.spacecards.model.Files;
import mx.uvdroids.spacecards.model.Question;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Download extends Activity{

	private ProgressBar progressBar;
	private TextView textView;
	private SharedPreferences mPrefs;
	private ImageButton button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		getActionBar().hide();
		progressBar = (ProgressBar) findViewById(R.id.downloading);
		progressBar.setIndeterminate(true);
		textView = (TextView) findViewById(R.id.downloading_progress);
		button = (ImageButton)findViewById(R.id.part1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckAchievements.unlock(getApplication());
			}
		});
		Downloading task = new Downloading();
		task.execute();
	}
	
	class Downloading extends AsyncTask<Void, String, Boolean>{
		public String[] srcs = {"water","manmade","landforms","cities","nighttime","weather"};
		@Override
		protected Boolean doInBackground(Void... arg0) {
			try {
				for(int i = 0; i < srcs.length; i++){
					publishProgress(srcs[i],"one");
					URL mi_url = new URL("http://mobileapps.dragonflylabs.com.mx/spaceapps/"+srcs[i]+"/");
					URLConnection con = mi_url.openConnection();
			        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			        StringBuilder json = new StringBuilder();
			        String cad = "";
			        while((cad = br.readLine()) != null){
			        	json.append(cad);
			        }
			        Files.createJson(json.toString(),srcs[i]+".json",Files.FILES);
			        final ArrayList<Question> questions = Files.LoadQuestions(srcs[i]);
			        for(int j = 0; j < questions.size(); j ++){
			        	final int k = j;
			        	publishProgress(questions.get(k).image,"two");
			        	String src = "http://mobileapps.dragonflylabs.com.mx"+questions.get(k).image;
			        	java.net.URL url = new java.net.URL(src);
				        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				        connection.setDoInput(true);
				        connection.connect();
				        InputStream input = connection.getInputStream();
				        Bitmap myBitmap = BitmapFactory.decodeStream(input);
				        
				        String[] name = src.split("/");
				        String realname = name[name.length-1];
				        
				        Files.checkFolder(Files.APP_FOLDER);
				        Files.checkFolder(Files.APP_FOLDER+Files.IMAGES);
			            File file = new File(Files.ruta_sd+Files.APP_FOLDER+Files.IMAGES,realname);
			            
			            ByteArrayOutputStream fOut = new ByteArrayOutputStream();
			            FileOutputStream stream = new FileOutputStream(file);
			            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			            byte[] array = fOut.toByteArray();
			            stream.write(array);
			            stream.close();
			            myBitmap.recycle();
			            myBitmap = null;
			            publishProgress(questions.get(k).image,"three");
			        }
			        publishProgress(srcs[i],"four");
				}
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			return false;
		}
		
		@Override
		protected void onProgressUpdate(final String... values) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(values[1].equals("one"))
						textView.setText("Downloading category: "+values[0]);
					else if(values[1].equals("two"))
						textView.setText("Downloading image: "+values[0]);
					else if(values[1].equals("three"))
						textView.setText(values[0]+" downloaded");
					else if(values[1].equals("four"))
						textView.setText(values[0]+" downloaded");
				}
			});
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(true){
				progressBar.setIndeterminate(false);
				mPrefs = getSharedPreferences("spacecardsPrefs", MODE_PRIVATE);
    			if (!mPrefs.contains(SplashScreen.PREF_WALKTROUGHT) && !mPrefs.getBoolean(SplashScreen.PREF_WALKTROUGHT, false)){
    				Intent i = new Intent(getBaseContext(), WalkthroughActivity.class);
                    startActivity(i);
    			}else{
    				Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
    			}
				finish();
			}
			
		}
	}

}
