package pl.netplus.basepackage.fragments;

import pl.netplus.basepackage.enums.ERepositoryTypes;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<T> extends Fragment {

	private int resources;
	protected ERepositoryTypes repositoryType;
	protected View convertView;

	public BaseFragment(int resources, ERepositoryTypes repositoryType) {

		this.resources = resources;
		this.repositoryType = repositoryType;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		convertView = inflater.inflate(this.resources, null);
		linkViews(convertView);
		return convertView;
	}

	public abstract void linkViews(View convertView);

	public abstract void reload();
}
