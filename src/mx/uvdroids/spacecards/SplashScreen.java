package mx.uvdroids.spacecards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity{

	protected boolean _active = true;
    protected int _splashTime = 3000;
     
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
                    finish();
                    Intent i = new Intent(getBaseContext(), Download.class);
                    startActivity(i);
                    this.interrupt();
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
