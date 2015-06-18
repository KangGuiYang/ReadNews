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
import com.allen.readnews.bean.HotNewsBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotNewsAdapter extends BaseAdapter {

	ArrayList<HotNewsBean> hotNewsBeans;

	private Context mContext;

	public HotNewsAdapter(ArrayList<HotNewsBean> hotNewsBeans, Context mContext) {
		super();
		this.hotNewsBeans = hotNewsBeans;
		this.mContext = mContext;
	}

	public ArrayList<HotNewsBean> getHotNewsBeans() {
		return hotNewsBeans;
	}

	public void setHotNewsBeans(ArrayList<HotNewsBean> hotNewsBeans) {
		this.hotNewsBeans = hotNewsBeans;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getHotNewsBeans().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_news,
					null);
			viewHolder.img = (ImageView) view.findViewById(R.id.imageView_news);
			viewHolder.keywords = (TextView) view.findViewById(R.id.news_title);
			viewHolder.title = (TextView) view.findViewById(R.id.news_tag);
			viewHolder.time = (TextView) view.findViewById(R.id.news_time);

			viewHolder.count = (TextView) view.findViewById(R.id.news_count);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		// TextView img = (TextView) view.findViewById(R.id.news_img);

		viewHolder.keywords.setText(hotNewsBeans.get(position).getKeywords());
		viewHolder.title.setText(hotNewsBeans.get(position).getTitle());
		viewHolder.time.setText(hotNewsBeans.get(position).getTime());
		viewHolder.count.setText(hotNewsBeans.get(position).getCount() + "");
		// img.setImageBitmap(newsListBeans.get(arg0).getImgBitmap());
		// img.setText(newsListBeans.get(arg0).getImg());

		// 显示图片的配置
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.loadpicture)
				.showImageOnFail(R.drawable.nopicture).cacheInMemory(true)
				.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		if (hotNewsBeans.get(position).getImg().equals("img/top/default.jpg")) {
			viewHolder.img.setImageResource(R.drawable.nopicture);
		} else {
			ImageLoader.getInstance().displayImage(
					"http://www.yi18.net/"
							+ hotNewsBeans.get(position).getImg(),
					viewHolder.img, options);
		}

		return view;
	}

	private static class ViewHolder {
		ImageView img;
		TextView keywords;
		TextView title;
		TextView time;
		TextView count;
	}

}
