package com.allen.readnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.readnews.R;

public class Home_GridViewAdapter extends BaseAdapter {

	private String mTitleArr[] = null;
	private int mImgIdArr[] = null;
	private LayoutInflater inflater = null;

	public Home_GridViewAdapter(String[] mTitleArr, int[] mImgIdArr,
			LayoutInflater inflater) {
		super();
		this.mTitleArr = mTitleArr;
		this.mImgIdArr = mImgIdArr;
		this.inflater = inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImgIdArr.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gridview_item, null);

			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.imageView_item);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textView_item);

			imageView.setImageResource(mImgIdArr[position]);
			textView.setText(mTitleArr[position]);

		}
		return convertView;
	}

}
