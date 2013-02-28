package pl.netplus.basepackage.activities;

import pl.netplus.basepackage.fragments.SettingsFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

public class PreferencesNewActivity extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();

	}
}
