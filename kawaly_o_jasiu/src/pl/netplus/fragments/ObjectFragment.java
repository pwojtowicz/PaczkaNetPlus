package pl.netplus.fragments;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import pl.netplus.basepackage.activities.AppBaseActivity;
import pl.netplus.basepackage.asynctasks.ObjectsAsycnTask.AsyncTaskResult;
import pl.netplus.basepackage.entities.ContentObject;
import pl.netplus.basepackage.entities.Favorite;
import pl.netplus.basepackage.enums.ERepositoryTypes;
import pl.netplus.basepackage.exceptions.RepositoryException;
import pl.netplus.basepackage.fragments.BaseFragment;
import pl.netplus.basepackage.interfaces.IReadRepository;
import pl.netplus.basepackage.managers.ObjectManager;
import pl.netplus.basepackage.support.StringHelper;
import pl.netplus.kawaly_o_jasiu.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ObjectFragment extends BaseFragment<ContentObject> implements
		IReadRepository {

	public static final String BUNDLE_CURRENT_PAGE = "BUNDLE_CURRENT_PAGE";

	private ContentObject contentObject;

	private TextView tv_wish;
	private TextView tv_rating;
	private Button btn_favorite;
	private TextView tv_lenght;
	// private TextView tv_addDate;

	private TextView txtv_additional;

	private ImageView iv_new;

	private TextView tv_addDate;

	public ObjectFragment() {
		super(R.layout.fragment_single_contentobject_layout,
				ERepositoryTypes.SingleWish);

	}

	@Override
	public void onResume() {
		super.onResume();

		reloadContent();
	}

	@Override
	public void linkViews(View convertView) {

		tv_wish = (TextView) convertView.findViewById(R.id.txtv_wish);
		tv_lenght = (TextView) convertView.findViewById(R.id.txtv_lenght);

		txtv_additional = (TextView) convertView
				.findViewById(R.id.txtv_additional);

		iv_new = (ImageView) convertView.findViewById(R.id.iv_news);

		tv_addDate = (TextView) convertView.findViewById(R.id.txtv_addTime);

		tv_rating = (TextView) convertView.findViewById(R.id.txtv_rating);

		btn_favorite = (Button) convertView.findViewById(R.id.btn_favorite);

		btn_favorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeFavoriteState();
			}
		});
	}

	protected void changeFavoriteState() {
		if (contentObject != null) {
			ObjectManager manager = new ObjectManager();
			Favorite f = new Favorite();
			f.setFavorite(!contentObject.isFavorites());
			f.setObjectId(contentObject.getId());
			manager.insertOrUpdate(this, ERepositoryTypes.Favorite, f);
		}
	}

	public void reloadContent() {
		if (contentObject != null) {

			Bundle b = getArguments();
			String currentPage = "";
			if (b != null) {
				currentPage = b.getString(BUNDLE_CURRENT_PAGE);
			}

			tv_lenght.setText(currentPage);

			Date updateDate = new Date(contentObject.getUploadDate() * 1000);

			long daysBetween = TimeUnit.MILLISECONDS.toDays(new Date()
					.getTime() - updateDate.getTime());

			if (daysBetween < 5) {
				txtv_additional.setVisibility(TextView.GONE);
				iv_new.setVisibility(ImageView.VISIBLE);
			} else {
				txtv_additional.setVisibility(TextView.VISIBLE);
				iv_new.setVisibility(ImageView.GONE);
			}

			tv_addDate.setText(StringHelper.daysCountToString(daysBetween));

			tv_wish.setText(contentObject.getText());

			tv_rating.setText(String.format("%.2f", contentObject.getRating()));

			Drawable drawable = null;
			String text = "";
			if (contentObject.isFavorites()) {
				drawable = getResources().getDrawable(
						R.drawable.cbx_star_enabled);
				text = getString(R.string.removed_from_favorites);
			} else {
				drawable = getResources().getDrawable(
						R.drawable.cbx_star_disabled);
				text = getString(R.string.add_to_favorites);
			}
			btn_favorite.setText(text);
			btn_favorite.setCompoundDrawablesWithIntrinsicBounds(drawable,
					null, null, null);
		}
	}

	@Override
	public void reload() {

	}

	@Override
	public void onTaskEnd() {
		((AppBaseActivity) getActivity()).onTaskEnd();
	}

	@Override
	public void onTaskStart(String message, boolean showProgress) {
		((AppBaseActivity) getActivity()).onTaskStart(
				getString(R.string.progress_save_changes), showProgress);
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof Boolean) {
			contentObject.setFavorites(!contentObject.isFavorites());
			reloadContent();
		}

	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		((AppBaseActivity) getActivity()).onTaskInvalidResponse(exception);
	}

	@Override
	public void onTaskProgressUpdate(int actualProgress) {
		((AppBaseActivity) getActivity()).onTaskProgressUpdate(actualProgress);
	}

	public ContentObject getContentObject() {
		return contentObject;
	}

	public void setContentObject(ContentObject contentObject) {
		this.contentObject = contentObject;
	}

}
