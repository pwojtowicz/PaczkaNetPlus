package pl.netplus.activities;

import pl.netplus.kawaly_o_jasiu.R;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		TextView version = (TextView) findViewById(R.id.txtv_version);
		try {
			version.setText(String.format(
					"wersja: %s",
					this.getPackageManager().getPackageInfo(
							this.getPackageName(), 0).versionName));
		} catch (NameNotFoundException e) {

		}
	}

}
