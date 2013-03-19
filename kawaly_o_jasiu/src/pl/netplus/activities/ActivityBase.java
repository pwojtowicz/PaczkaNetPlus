package pl.netplus.activities;

import pl.netplus.basepackage.activities.AppBaseActivity;
import pl.netplus.basepackage.activities.PreferencesNewActivity;
import pl.netplus.basepackage.activities.PreferencesOldActivity;
import pl.netplus.kawaly_o_jasiu.R;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

public abstract class ActivityBase extends AppBaseActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			break;
		case R.id.menu_setting:
			if (Build.VERSION.SDK_INT < 11) {
				startActivity(new Intent(this, PreferencesOldActivity.class));
			} else {
				startActivity(new Intent(this, PreferencesNewActivity.class));
			}
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void getUpdate() {
		// TODO Auto-generated method stub

	}
}
