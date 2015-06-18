package com.allen.readnews.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.readews.utils.DialogTools;
import com.allen.readews.utils.JsonTools;
import com.allen.readnews.GridNewsActivity;
import com.allen.readnews.HotNewsActivity;
import com.allen.readnews.MainActivity;
import com.allen.readnews.NewsDetailActivity;
import com.allen.readnews.R;
import com.allen.readnews.adapter.Home_GridViewAdapter;
import com.allen.readnews.adapter.HotNewsAdapter;
import com.allen.readnews.applaction.AppData;
import com.allen.readnews.bean.HotNewsBean;
import com.allen.readnews.bean.Newsbean;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class HomeFragment extends Fragment {

	private GridView gridView;
	private Home_GridViewAdapter home_GridViewAdapter;
	private GV_ClickListener gvlickListener;
	private AsyncHttpClient asyncHttpClient;
	private LayoutInflater inflater = null;
	private ListView listView;

	private AppData appData;
	// NewsAdapter socialAdapter;
	HotNewsAdapter hotNewsAdapter;
	// ArrayList<Newsbean> socialNewsbeans;
	ArrayList<HotNewsBean> hotNewsBeans;

	// String path = "http://api.yi18.net/news/list?id=6&page=1&limit=5";
	String path_hotnews = "http://api.yi18.net/top/list?page=1&limit=10";

	// 展示的文字
	private String[] mTitleArr = new String[] { "企业要闻", "医疗新闻", "生活贴士", "药品新闻",
			"食品新闻", "社会热点" };
	// 展示的图片
	private int[] mImgIdArr = new int[] { R.drawable.corporatenews,
			R.drawable.medicalnews, R.drawable.livingtips, R.drawable.drugnews,
			R.drawable.foodnews, R.drawable.socialhotspot };

	private ProgressBar progressBar;
	private hotnewsClickListener hotClickListener;

	private final int HOTNEWS = 0;

	ImageView backImageView;
	TextView toptitleTV;
	TextView moreHotNews;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		appData = (AppData) getActivity().getApplicationContext();

		View view = inflater.inflate(R.layout.home_fragment, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar01);
		initGridView(view);
		initListView(view);

		moreHotNews = (TextView) view.findViewById(R.id.more_hotnews);
		moreHotNews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), HotNewsActivity.class);
				startActivity(intent);
			}
		});
		toptitleTV = (TextView) view.findViewById(R.id.title_bar_TV);
		backImageView = (ImageView) view.findViewById(R.id.backIV);

		toptitleTV.setText("爱资讯    爱生活");
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				appData.getDrawerLayout().openDrawer(Gravity.LEFT);
			}
		});

		return view;
	}

	/**
	 * 初始化GridView
	 * 
	 * @param view
	 */
	private void initGridView(View view) {
		inflater = LayoutInflater.from(getActivity());
		gridView = (GridView) view.findViewById(R.id.gridView_home);
		home_GridViewAdapter = new Home_GridViewAdapter(mTitleArr, mImgIdArr,
				inflater);

		gvlickListener = new GV_ClickListener();
		gridView.setAdapter(home_GridViewAdapter);
		gridView.setOnItemClickListener(gvlickListener);
	}

	private class GV_ClickListener implements OnItemClickListener {
		Intent intent = new Intent();

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			intent.setClass(getActivity(), GridNewsActivity.class);
			switch (mImgIdArr[position]) {
			case R.drawable.corporatenews:
				intent.putExtra("type", 1);
				intent.putExtra("title", "企业要闻");
				// Toast.makeText(getActivity(), mTitleArr[position], 0).show();
				break;
			case R.drawable.medicalnews:
				// Toast.makeText(getActivity(), mTitleArr[position], 0).show();
				intent.putExtra("type", 2);
				intent.putExtra("title", "医疗新闻");
				break;
			case R.drawable.livingtips:
				// Toast.makeText(getActivity(), mTitleArr[position], 0).show();
				intent.putExtra("type", 3);
				intent.putExtra("title", "生活贴士");
				break;
			case R.drawable.drugnews:
				intent.putExtra("type", 4);
				intent.putExtra("title", "药品新闻");
				// Toast.makeText(getActivity(), mTitleArr[position], 0).show();
				break;
			case R.drawable.foodnews:
				intent.putExtra("type", 5);
				intent.putExtra("title", "食品新闻");
				// Toast.makeText(getActivity(), mTitleArr[position], 0).show();
				break;
			case R.drawable.socialhotspot:
				intent.putExtra("type", 6);
				intent.putExtra("title", "社会热点");
				// Toast.makeText(getActivity(), mTitleArr[position], 0).show();
				break;

			default:
				break;
			}

			startActivity(intent);

		}

	}

	private void initListView(View view) {
		hotNewsBeans = appData.getHotNewsBeans();
		if (hotNewsAdapter == null) {
			hotNewsAdapter = new HotNewsAdapter(hotNewsBeans, getActivity());
		}
		listView = (ListView) view.findViewById(R.id.listView_home);
		hotClickListener = new hotnewsClickListener();
		listView.setOnItemClickListener(hotClickListener);

		if (hotNewsBeans.size() > 0) {
			hotNewsAdapter.notifyDataSetChanged();
		} else {

			sendHttpResponse_HotNews(path_hotnews);// 发送网络请求

		}

		listView.setAdapter(hotNewsAdapter);
		ImageLoader imageLoader = ImageLoader.getInstance();
		listView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));

	}

	private class hotnewsClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), NewsDetailActivity.class);
			intent.putExtra("id", hotNewsBeans.get(position).getId());
			intent.putExtra("type", HOTNEWS);
			intent.putExtra("title", "热点新闻");
			startActivity(intent);

		}

	}

	public void sendHttpResponse_HotNews(String path) {
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(path, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				DialogTools.showSelfProgressBar(getActivity(), progressBar,
						true);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println(new String(arg2));

				System.out.println("arg0===" + arg0);
				System.out.println("arg1===" + arg1);
				System.out.println("arg2===" + new String(arg2));
				String jsonStr = new String(arg2);
				try {
					JSONArray arr = JsonTools.parseJsonArr(jsonStr);
					System.out.println("arr.length()===" + arr.length());
					for (int i = 0; i < arr.length(); i++) {
						JSONObject news = arr.getJSONObject(i);

						HotNewsBean hotNewsBean = new HotNewsBean(news
								.getInt("id"), news.getString("title"), news
								.getString("img"), news.getString("from"), news
								.getString("time"), news.getString("keywords"),
								news.getInt("count"));
						hotNewsBeans.add(hotNewsBean);

					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

				Toast.makeText(getActivity(), "网络请求失败，请检查网络是否连接！", 1).show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				hotNewsAdapter.notifyDataSetChanged();
				DialogTools.showSelfProgressBar(getActivity(), progressBar,
						false);

			}
		});

	}
}
