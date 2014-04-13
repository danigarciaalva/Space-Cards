package mx.uvdroids.spacecards;

import mx.uvdroids.spacecards.utils.Connection;
import mx.uvdroids.spacecards.utils.Server;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity{

	protected boolean _active = true;
    protected int _splashTime = 3000;
    private Activity a = this;
    private SharedPreferences mPrefs;
    public static final String PREF_WALKTROUGHT = "walktrought_pref";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread splashTread = new Thread() {
            @Override 
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                	
                } finally {
                	if(Connection.checkInternetConnection(a)){
                		if(Server.isThereUpdates()){
                			Intent i = new Intent(getBaseContext(), Download.class);
                            startActivity(i);
                		}else{ 
                			mPrefs = getSharedPreferences("spacecardsPrefs", MODE_PRIVATE);
                			if (!mPrefs.contains(PREF_WALKTROUGHT)){
                				Intent i = new Intent(getBaseContext(), WalkthroughActivity.class);
                                startActivity(i);
                			}else if(mPrefs.getBoolean(PREF_WALKTROUGHT, false)){
                				Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                			}else{
                				Intent i = new Intent(getBaseContext(), WalkthroughActivity.class);
                                startActivity(i);
                			}
                		}
                	}else{
                		Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                	}
                    this.interrupt();
                    finish();
                }
            }
        };
        splashTread.start();
    } 
     
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}
