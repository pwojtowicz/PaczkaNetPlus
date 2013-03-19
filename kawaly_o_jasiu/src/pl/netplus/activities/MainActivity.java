package pl.netplus.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.netplus.basepackage.adapters.StartActivityFragmentAdapter;
import pl.netplus.basepackage.asynctasks.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.basepackage.interfaces.IReadRepository;
import pl.netplus.basepackage.managers.ObjectManager;
import pl.netplus.basepackage.support.DialogHelper;
import pl.netplus.fragments.RightFragment;
import pl.netplus.fragments.SearchFragment;
import pl.netplus.fragments.StartFragment;
import pl.netplus.kawaly_o_jasiu.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends ActivityBase implements IReadRepository {

	private int currentPage = 1;
	private final static String categoryId = "&kat=10";
	private static String contentAddress = "http://kawaly.tja.pl/api/android_bramka.php?co=lista_obekty&data=";
	private static String contentToDeleteAddress = "http://kawaly.tja.pl/api/android_bramka.php?co=lista_obekty_id";

	public String getContentAddress(boolean updateOldVersion) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		return String.format("%s%s%s", contentAddress, (updateOldVersion ? 0
				: prefs.getString("prop_last_update_return_date", "0")),
				categoryId);
	}

	public String getContentToDeleteAddress() {
		return String.format("%s%s", contentToDeleteAddress, categoryId);
	}

	ViewPager mViewPager;
	private StartActivityFragmentAdapter mPageAdapter;
	private ArrayList<Fragment> pages;

	@Override
	public void onStart() {
		super.onStart();

		super.onMainActivityStart();

	}

	@Override
	public void getUpdate() {
		if (super.checkIsOnline()) {
			Bundle b = new Bundle();

			b.putString("ObjectsLink", getContentAddress(updateOldVersion));
			b.putString("ObjectsToDeleteLink", getContentToDeleteAddress());

			ObjectManager manager = new ObjectManager();
			manager.updateData(b, this);
		} else {
			DialogHelper.createInfoDialog(this,
					getString(R.string.dialog_no_internet_connection)).show();
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		isFirstTime = false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		updateFragmentPager();

	}

	private void updateFragmentPager() {
		pages = new ArrayList<Fragment>();

		FragmentManager fm = getSupportFragmentManager();

		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new SearchFragment());
		fragments.add(new StartFragment());
		fragments.add(new RightFragment());

		mPageAdapter = new StartActivityFragmentAdapter(fm,
				getString(R.string.title_fragment_search),
				getString(R.string.title_fragment_start),
				getString(R.string.title_fragment_right), fragments);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(3);

		mViewPager.setAdapter(mPageAdapter);
		mViewPager.setCurrentItem(currentPage);

		// mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
		//
		// @Override
		// public void onPageSelected(int state) {
		// if (state == ViewPager.SCROLL_STATE_IDLE)
		// currentPage = mViewPager.getCurrentItem();
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof Long) {
			long returnFromServerTime = (Long) response.bundle;
			if (returnFromServerTime > 0) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH, 7);
				setUpdateDates(c.getTimeInMillis(), returnFromServerTime);
			}
		}
		if (updateOldVersion) {
			try {
				String curr_app_ver = this.getPackageManager().getPackageInfo(
						this.getPackageName(), 0).versionName;
				setLastAppVersion(curr_app_ver);
			} catch (Exception e) {

			}
			updateOldVersion = false;

		}

		updateFragmentPager();
	}

	@Override
	public void retryLastAction() {
		update(false);
	}

}
