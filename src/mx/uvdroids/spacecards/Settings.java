package mx.uvdroids.spacecards;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity{

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.activity_settings);
	}
}
