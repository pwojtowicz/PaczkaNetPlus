package pl.netplus.basepackage.activities;

import pl.netplus.basepackage.database.DataBaseManager;
import pl.netplus.basepackage.enums.EDialogType;
import pl.netplus.basepackage.exceptions.RepositoryException;
import pl.netplus.basepackage.interfaces.IReadRepository;
import pl.netplus.basepackage.support.DialogHelper;
import pl.netplus.basepackage.support.NetPlusPackageGlobals;
import pl.netplus.basepackage.support.StringHelper;
import pl.netplusbasepackage.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

public abstract class AppBaseActivity extends FragmentActivity implements
		IReadRepository {

	private ProgressDialog dialog;

	private boolean pref_replaceChars;
	private boolean pref_addSignature;
	private String pref_signature;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		DataBaseManager.inicjalizeInstance(this);
	}

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		pref_replaceChars = (prefs.getBoolean("prop_replace", false));
		pref_addSignature = prefs.getBoolean("prop_add_signature", false);
		if (pref_addSignature)
			pref_signature = "\n" + (prefs.getString("prop_signature", ""));
		else
			pref_signature = ("");

		NetPlusPackageGlobals.getInstance().setHideEmptyCategories(
				prefs.getBoolean("prop_hide_empty", true));
	}

	@Override
	public void onTaskStart(String message, boolean showProgress) {
		dialog = new ProgressDialog(this);

		if (message.length() == 0)
			message = getString(R.string.progress_download_data);

		if (showProgress) {
			message = getString(R.string.progress_update_data);
			dialog.setIndeterminate(false);
			dialog.setMax(100);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMessage(message);
		} else {
			dialog.setIndeterminate(true);
			dialog.setMessage(message);
		}
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onTaskEnd() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		OnClickListener dialogPositiveListener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				retryLastAction();
			}
		};

		DialogHelper.createErrorDialog(this, EDialogType.Connection_Error,
				dialogPositiveListener).show();

	}

	public abstract void retryLastAction();

	@Override
	public void onTaskProgressUpdate(int actualProgress) {
		if (dialog != null)
			dialog.setProgress(actualProgress);

	}

	public long getNextUpdateDate() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String pref_next_update_date = prefs.getString("prop_next_update_date",
				"0");

		return Long.parseLong(pref_next_update_date);
	}

	public String getLastAppVersion() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String lastAppVersion = prefs.getString("last_app_version", "0");
		return lastAppVersion;
	}

	public void setLastAppVersion(String value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("last_app_version", value);
		editor.commit();
	}

	public void setUpdateDates(long nextUpdateLongDate, long returnDate) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("prop_last_update_return_date",
				String.valueOf(returnDate));
		editor.putString("prop_next_update_date",
				String.valueOf(nextUpdateLongDate));
		editor.commit();
	}

	public String charsReplace(String text) {
		return pref_replaceChars ? StringHelper.removePolishChars(text) : text;
	}

	public String getSignature() {
		return pref_signature;
	}

	public boolean checkIsOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
