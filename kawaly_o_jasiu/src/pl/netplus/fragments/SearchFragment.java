package pl.netplus.fragments;

import java.util.ArrayList;

import pl.netplus.activities.ObjectsActivity;
import pl.netplus.basepackage.activities.AppBaseActivity;
import pl.netplus.basepackage.asynctasks.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.basepackage.entities.ContentObject;
import pl.netplus.basepackage.enums.EDialogType;
import pl.netplus.basepackage.exceptions.RepositoryException;
import pl.netplus.basepackage.interfaces.IReadRepository;
import pl.netplus.basepackage.managers.ObjectManager;
import pl.netplus.basepackage.support.DialogHelper;
import pl.netplus.basepackage.support.NetPlusPackageGlobals;
import pl.netplus.kawaly_o_jasiu.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment implements IReadRepository {

	private EditText textToSearch;

	public static SearchFragment newInstance(int index) {
		SearchFragment f = new SearchFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_search_layout, container,
				false);

		textToSearch = (EditText) v.findViewById(R.id.edt_search_text);

		Button btn_search = (Button) v.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchText();
			}
		});

		return v;
	}

	protected void searchText() {
		NetPlusPackageGlobals.getInstance().setObjectsDictionary(
				NetPlusPackageGlobals.ITEMS_SEARCH, null);

		String text = textToSearch.getText().toString().trim();

		if (text.length() == 0)
			DialogHelper.createInfoDialog(getActivity(),
					"Nie podano kryterium wyszukiwania").show();
		else {
			ObjectManager manager = new ObjectManager();
			manager.searchContentObjects(this, 0, text);
		}
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ArrayList<?>) {
			if (((ArrayList<ContentObject>) response.bundle).size() > 0)
				startWishesIntent(NetPlusPackageGlobals.ITEMS_SEARCH);
			else
				DialogHelper.createDialog(getActivity(),
						EDialogType.No_SearchResult).show();
		}
	}

	private void startWishesIntent(int categoryId) {
		Intent intent = new Intent(getActivity(), ObjectsActivity.class);
		Bundle b = new Bundle();
		b.putInt(ObjectsActivity.BUNDLE_CATEGORY_ID, categoryId);
		intent.putExtras(b);
		startActivity(intent);
	}

	@Override
	public void onTaskEnd() {
		((AppBaseActivity) getActivity()).onTaskEnd();
	}

	@Override
	public void onTaskStart(String message, boolean showProgress) {
		((AppBaseActivity) getActivity()).onTaskStart(
				getString(R.string.progress_search), showProgress);
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		((AppBaseActivity) getActivity()).onTaskInvalidResponse(exception);
	}

	@Override
	public void onTaskProgressUpdate(int actualProgress) {
		((AppBaseActivity) getActivity()).onTaskProgressUpdate(actualProgress);
	}

}
