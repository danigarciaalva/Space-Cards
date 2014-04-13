package mx.uvdroids.spacecards;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getActionBar().setTitle("About");
	}
	
	public void aboutSpaceapps(View v){
		View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        textView.setText(R.string.about_spaceapps);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_space);
        builder.setTitle("Space Apps");
        builder.setView(messageView);
        builder.create();
        builder.show();
	}
	public void aboutUvdroids(View v){
		View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        textView.setText(R.string.about_uvdroids);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_uvdroids);
        builder.setTitle("UvDroids");
        builder.setView(messageView);
        builder.create();
        builder.show();
	}
	public void aboutSpacecards(View v){
		View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        textView.setText(R.string.about_spacecards);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_logo);
        builder.setTitle("Space Cards");
        builder.setView(messageView);
        builder.create();
        builder.show();
	}
}
