package mx.uvdroids.spacecards.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class DownloadImage extends AsyncTask<Void, Void, Void>{
	public String src;
	DownloadImage(String src){
		this.src=src;
	}
	@Override
	protected Void doInBackground(Void... arg0) {
		try {
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
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return null;
		
	}
	
}
