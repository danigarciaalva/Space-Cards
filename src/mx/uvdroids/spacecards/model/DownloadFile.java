package mx.uvdroids.spacecards.model;

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

import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

public class DownloadFile extends AsyncTask<Void, Void, Bundle>{
	public String src;
	DownloadFile(String src){
		this.src=src;
	}
	@Override
	protected Bundle doInBackground(Void... arg0) {
		try {
			String url2 = "http://mobileapps.dragonflylabs.com.mx/spaceapps/"+src+"/";
        	URL mi_url = new URL(url2);
	        URLConnection con = mi_url.openConnection();
	        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
	        StringBuilder json = new StringBuilder();
	        String cad = "";
	        while((cad = br.readLine()) != null){
	        	json.append(cad);
	        }
	        System.out.println(json.toString());
	        Bundle b = new Bundle();
	        b.putSerializable("json",json.toString());
	        return b;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	@Override
	protected void onPostExecute(Bundle result) {
		@SuppressWarnings("unchecked")
		String contenido = (String)result.getSerializable("json");
		System.out.print(contenido);
		Files.createJson(contenido,src+".json",Files.FILES);
	}
}
