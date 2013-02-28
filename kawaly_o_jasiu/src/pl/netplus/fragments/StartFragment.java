package pl.netplus.fragments;

import pl.netplus.activities.ObjectsActivity;
import pl.netplus.basepackage.enums.EButtonType;
import pl.netplus.basepackage.enums.EDialogType;
import pl.netplus.basepackage.fragments.BaseFragment;
import pl.netplus.basepackage.support.DialogHelper;
import pl.netplus.basepackage.support.NetPlusPackageGlobals;
import pl.netplus.kawaly_o_jasiu.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartFragment extends BaseFragment<Object> implements
		OnClickListener {

	private Button btn_favorite;

	public static StartFragment newInstance(int index) {
		StartFragment f = new StartFragment();
		return f;
	}

	public StartFragment() {
		super(R.layout.fragment_start_layout, null);

	}

	@Override
	public void linkViews(View convertView) {

		btn_favorite = (Button) convertView.findViewById(R.id.button_favorite);

		Button btn_random = (Button) convertView.findViewById(R.id.btn_random);
		Button btn_thebest = (Button) convertView
				.findViewById(R.id.btn_thebest);

		Button btn_last = (Button) convertView.findViewById(R.id.btn_last);

		btn_favorite.setTag(EButtonType.Favorite);
		btn_favorite.setOnClickListener(this);

		btn_random.setTag(EButtonType.Random);
		btn_random.setOnClickListener(this);

		btn_last.setTag(EButtonType.News);
		btn_last.setOnClickListener(this);

		btn_thebest.setTag(EButtonType.TheBest);
		btn_thebest.setOnClickListener(this);

	}

	@Override
	public void reload() {
		setFavoritesCount(NetPlusPackageGlobals.getInstance()
				.getFavoritesCount());

	}

	public void setFavoritesCount(int totalCount) {
		// if (totalCount < 0)
		// totalCount = 0;
		// if (btn_favorite != null)
		// btn_favorite.setText(String.format("%s (%d)",
		// getText(R.string.favorites), totalCount));

	}

	@Override
	public void onClick(View view) {
		Object o = view.getTag();
		if (o instanceof EButtonType) {
			switch ((EButtonType) o) {
			case TheBest:
				showTheBestJokes();
				break;
			case Favorite:
				showFavoritesJokes();
				break;
			case News:
				showNewsJokes();
				break;
			case Random:
				showRandomJokes();
				break;
			default:
				break;
			}
		}
	}

	private void showTheBestJokes() {
		startWishesIntent(NetPlusPackageGlobals.ITEMS_THE_BEST);
	}

	protected void showFavoritesJokes() {
		if (NetPlusPackageGlobals.getInstance().getFavoritesCount() > 0)
			startWishesIntent(NetPlusPackageGlobals.ITEMS_FAVORITE);
		else {
			DialogHelper.createDialog(getActivity(), EDialogType.No_Favorites)
					.show();
		}
	}

	protected void showRandomJokes() {
		startWishesIntent(NetPlusPackageGlobals.ITEMS_RANDOM);
	}

	protected void showNewsJokes() {
		startWishesIntent(NetPlusPackageGlobals.ITEMS_LATEST);
	}

	private void startWishesIntent(int categoryId) {
		Intent intent = new Intent(getActivity(), ObjectsActivity.class);
		Bundle b = new Bundle();
		b.putInt(ObjectsActivity.BUNDLE_CATEGORY_ID, categoryId);
		intent.putExtras(b);
		startActivity(intent);
	}

}
