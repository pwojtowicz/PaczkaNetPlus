package pl.netplus.fragments;

import pl.netplus.activities.AboutActivity;
import pl.netplus.activities.MainActivity;
import pl.netplus.basepackage.enums.EButtonType;
import pl.netplus.basepackage.fragments.BaseFragment;
import pl.netplus.kawaly_o_jasiu.R;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RightFragment extends BaseFragment<Object> implements
		OnClickListener {

	public RightFragment() {
		super(R.layout.fragment_right_layout, null);
	}

	@Override
	public void onClick(View view) {
		Object o = view.getTag();
		if (o instanceof EButtonType) {
			switch ((EButtonType) o) {
			case Update:
				upddate();
				break;
			case About:
				showAboutPage();
				break;
			default:
				break;
			}
		} else if (o instanceof String) {
			showWebPage((String) o);

		}
	}

	@Override
	public void linkViews(View convertView) {
		Button btn_webpage = (Button) convertView.findViewById(R.id.btn_web);

		Button btn_add_wish = (Button) convertView
				.findViewById(R.id.btn_add_wish);
		Button btn_about = (Button) convertView.findViewById(R.id.btn_about);
		Button btn_update = (Button) convertView.findViewById(R.id.btn_update);
		Button btn_facebook = (Button) convertView
				.findViewById(R.id.btn_facebook);

		btn_update.setTag(EButtonType.Update);
		btn_update.setOnClickListener(this);

		btn_about.setTag(EButtonType.About);
		btn_about.setOnClickListener(this);

		btn_webpage.setOnClickListener(this);
		btn_add_wish.setOnClickListener(this);
		btn_facebook.setOnClickListener(this);

	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

	protected void upddate() {
		((MainActivity) getActivity()).update(true);
	}

	protected void showAboutPage() {
		Intent intent = new Intent(getActivity(), AboutActivity.class);
		startActivity(intent);
	}

	protected void showWebPage(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

}
