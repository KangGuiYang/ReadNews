package com.allen.readews.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

public class DialogTools {

	public static ProgressDialog connDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("���ݶ�ȡ�С�����");

		return progressDialog;
	}

	public static ProgressBar getProgressBar(Context context) {

		ProgressBar progressBar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleSmall);

		progressBar.setVisibility(View.VISIBLE);
		return progressBar;
	}

	public static void showSelfProgressBar(Context context, ProgressBar progressBar,
			boolean isShowOrColsed) {
		if (progressBar != null) {

			if (isShowOrColsed) {
				progressBar.setVisibility(View.VISIBLE);
			} else {

				progressBar.setVisibility(View.GONE);

			}
		}
	}

}
