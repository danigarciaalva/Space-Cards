package mx.uvdroids.spacecards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import mx.uvdroids.spacecards.model.Files;
import mx.uvdroids.spacecards.model.Question;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Download extends Activity{

	private ProgressBar progressBar;
	private int progressStatus = 0;
	private TextView textView;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		getActionBar().hide();
		progressBar = (ProgressBar) findViewById(R.id.downloading);
		textView = (TextView) findViewById(R.id.downloading_progress);
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
			        for(int j = 0; j < questions.size(); i ++){
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
				        
				        OutputStream fOut = null;
				        Files.checkFolder(Files.APP_FOLDER);
				        Files.checkFolder(Files.APP_FOLDER+Files.IMAGES);
			            File file = new File(Files.ruta_sd+Files.APP_FOLDER+Files.IMAGES,realname);
			            fOut = new FileOutputStream(file);
			            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			            fOut.flush();
			            fOut.close();
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
		}
	}

}
