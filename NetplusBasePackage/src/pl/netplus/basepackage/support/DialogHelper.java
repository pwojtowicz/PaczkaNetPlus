package pl.netplus.basepackage.support;

import pl.netplus.basepackage.enums.EDialogType;
import pl.netplusbasepackage.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogHelper {

	public static Dialog createDialog(Context context, EDialogType type) {
		Builder dialog = new AlertDialog.Builder(context);
		switch (type) {
		case No_Favorites:
			dialog.setMessage(R.string.dialog_message_no_favorites);
			dialog.setTitle(R.string.dialog_title_info);
			dialog.setPositiveButton(R.string.dialog_OK, null);
			break;
		case No_SearchResult:
			dialog.setMessage(R.string.dialog_message_no_searchresult);
			dialog.setTitle(R.string.dialog_title_info);
			dialog.setPositiveButton(R.string.dialog_OK, null);
			break;
		default:
			break;
		}

		return dialog.create();
	}

	public static Dialog createErrorDialog(Context context, EDialogType type,
			OnClickListener positiveListener) {
		Builder dialog = new AlertDialog.Builder(context);
		switch (type) {
		case Connection_Error:
			dialog.setMessage(R.string.dialog_message_connection_error);
			dialog.setTitle(R.string.dialog_title_error);
			dialog.setNegativeButton(R.string.dialog_Cancel, null);
			dialog.setPositiveButton(R.string.dialog_Retry, positiveListener);
			break;
		}
		return dialog.create();
	}

	public static Dialog createInfoDialog(Context context, String message) {
		Builder dialog = new AlertDialog.Builder(context);

		dialog.setMessage(message);
		dialog.setTitle(R.string.dialog_title_info);
		dialog.setPositiveButton(R.string.dialog_OK, null);

		return dialog.create();
	}

	public static Dialog createQuestionDialog(Context context, String message,
			OnClickListener positiveListener) {
		Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(message);
		dialog.setTitle(R.string.dialog_title_info);
		dialog.setNegativeButton(R.string.dialog_Cancel, null);
		dialog.setPositiveButton(R.string.dialog_OK, positiveListener);
		return dialog.create();
	}

	public static Dialog createSortQuestionDialog(Context context,
			String title, String[] options, int checkedItemId,
			OnClickListener positiveListener, OnClickListener itemSelect) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setSingleChoiceItems(options, checkedItemId, itemSelect);

		builder.setNegativeButton(R.string.dialog_Cancel, null);
		builder.setPositiveButton(R.string.dialog_OK, positiveListener);

		return builder.create();

	}
}
