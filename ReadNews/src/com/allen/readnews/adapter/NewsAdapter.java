package com.allen.readnews.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.readnews.R;
import com.allen.readnews.bean.Newsbean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsAdapter extends BaseAdapter {
	ArrayList<Newsbean> socialNewsbeans;

	private Context mContext;

	public ArrayList<Newsbean> getSocialNewsbeans() {
		return socialNewsbeans;
	}

	public void setSocialNewsbeans(ArrayList<Newsbean> socialNewsbeans) {
		this.socialNewsbeans = socialNewsbeans;
	}

	public NewsAdapter(Context context, ArrayList<Newsbean> socialNewsbeans) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.setSocialNewsbeans(socialNewsbeans);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getSocialNewsbeans().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {

			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_news,
					null);
			viewHolder.img = (ImageView) view.findViewById(R.id.imageView_news);
			viewHolder.title = (TextView) view.findViewById(R.id.news_title);
			viewHolder.tag = (TextView) view.findViewById(R.id.news_tag);
			viewHolder.time = (TextView) view.findViewById(R.id.news_time);

			viewHolder.count = (TextView) view.findViewById(R.id.news_count);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// TextView img = (TextView) view.findViewById(R.id.news_img);

		viewHolder.title.setText(socialNewsbeans.get(arg0).getTitle());
		viewHolder.tag.setText(socialNewsbeans.get(arg0).getTag());
		viewHolder.time.setText(socialNewsbeans.get(arg0).getTime());
		viewHolder.count.setText(socialNewsbeans.get(arg0).getCount() + "");

		// 显示图片的配置
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.loadpicture)
				.showImageOnFail(R.drawable.nopicture).cacheInMemory(true)
				.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		if (socialNewsbeans.get(arg0).getImg().equals("")) {
			viewHolder.img.setImageResource(R.drawable.nopicture);
		} else {
			ImageLoader.getInstance()
					.displayImage(
							"http://www.yi18.net/"
									+ socialNewsbeans.get(arg0).getImg(),
							viewHolder.img, options);
		}

		return view;
	}

	private static class ViewHolder {
		ImageView img;
		TextView title;
		TextView tag;
		TextView time;

		TextView count;
	}

}
