package pl.netplus.basepackage.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {

	protected ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments,
			int startIndex) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragmentObject = fragments.get(i);

		return fragmentObject;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return FragmentPagerAdapter.POSITION_NONE;
	}

}
