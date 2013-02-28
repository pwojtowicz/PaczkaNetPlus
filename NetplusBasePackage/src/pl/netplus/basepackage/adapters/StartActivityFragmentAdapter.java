package pl.netplus.basepackage.adapters;

import java.util.ArrayList;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StartActivityFragmentAdapter extends FragmentPagerAdapter {

	ArrayList<Fragment> fragments = new ArrayList<Fragment>();

	private String titleLeft;
	private String titleStart;
	private String titleRight;

	public StartActivityFragmentAdapter(FragmentManager fm, String titleLeft,
			String titleStart, String titleRight, ArrayList<Fragment> fragments) {
		super(fm);
		this.titleLeft = titleLeft;
		this.titleStart = titleStart;
		this.titleRight = titleRight;
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Resources res = Resources.getSystem();
		switch (position) {
		case 0:
			return titleLeft;
		case 1:
			return titleStart;
		case 2:
			return titleRight;
		default:
			return "";
		}
	}

}
