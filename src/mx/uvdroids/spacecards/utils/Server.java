package mx.uvdroids.spacecards.utils;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Server {

	public static boolean isThereUpdates(){
		return false;
	}
	
	public static void sendScore(final String score, final String name){
		new  Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					URI uri=new URI("http://mobileapps.dragonflylabs.com.mx/spaceapps/uploadscore/");
		            HttpResponse response=null;
			        ArrayList<BasicNameValuePair> parametros= new ArrayList<BasicNameValuePair>();
			         
			        
			        BasicNameValuePair parametro1=new BasicNameValuePair("score",score);
			        BasicNameValuePair parametro2=new BasicNameValuePair("user",name);
			        
			        parametros.add(parametro1);
			        parametros.add(parametro2);
			         
			        HttpPost post=new HttpPost(uri);
			        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(parametros,HTTP.UTF_8);
			        post.setEntity(entity);
			         
			        HttpClient client= new DefaultHttpClient();
			        response=client.execute(post);
			        HttpEntity e = response.getEntity();
			        String responseString = EntityUtils.toString(e, "UTF-8");
			        System.out.println(responseString);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();

	}
}
