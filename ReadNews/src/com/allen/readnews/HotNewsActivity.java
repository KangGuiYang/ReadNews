package com.allen.readnews;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.readews.utils.DialogTools;
import com.allen.readews.utils.JsonTools;
import com.allen.readnews.adapter.HotNewsAdapter;
import com.allen.readnews.applaction.AppData;
import com.allen.readnews.bean.HotNewsBean;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class HotNewsActivity extends Activity {
	private PullToRefreshListView pullToRefreshListView;
	private AsyncHttpClient asyncHttpClient;
	private int pageIndex = 1;
	HotNewsAdapter hotNewsAdapter;
	ArrayList<HotNewsBean> hotNewsBeans;
	String path_hotnews = "http://api.yi18.net/top/list?page=1&limit=10";

	TextView toptitleTV;
	ProgressBar progressBar;

	ImageView backImageView;
	AppData appData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_listview);

		appData = (AppData) getApplicationContext();

		progressBar = (ProgressBar) findViewById(R.id.progressBar01);
		toptitleTV = (TextView) findViewById(R.id.title_bar_TV);
		backImageView = (ImageView) findViewById(R.id.backIV);
		backImageView.setImageResource(R.drawable.back);
		initListView();// 初始化listview
		toptitleTV.setText("热点新闻");
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HotNewsActivity.this.finish();
			}
		});

		initPullListview();
	}

	private void initListView() {

		hotNewsBeans = appData.getHotNewsBeans();
		if (hotNewsAdapter == null) {
			hotNewsAdapter = new HotNewsAdapter(hotNewsBeans,
					HotNewsActivity.this);
		}
		if (hotNewsBeans.size() > 0) {
			hotNewsAdapter.notifyDataSetChanged();
		} else {
			sendHttpResponse_HotNews(path_hotnews);// 发送网络请求

		}

	}

	private void initPullListview() {
		String label = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		pullToRefreshListView.setMode(Mode.BOTH);
		ILoadingLayout startLayout = pullToRefreshListView
				.getLoadingLayoutProxy(true, false);
		startLayout.setLastUpdatedLabel(label);
		startLayout.setLastUpdatedLabel("上次更新时间" + label);
		startLayout.setPullLabel("下拉刷新");
		startLayout.setRefreshingLabel("正在刷新");
		startLayout.setReleaseLabel("松开刷新");
		ILoadingLayout endLayout = pullToRefreshListView.getLoadingLayoutProxy(
				false, true);
		endLayout.setPullLabel("上滑加载更多");
		endLayout.setRefreshingLabel("正在加载");
		endLayout.setReleaseLabel("松开加载更多");

		pullToRefreshListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						sendHttpResponse_HotNews(path_hotnews);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

						sendHttpResponse_HotNews_more();
					}

				});

		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(HotNewsActivity.this, NewsDetailActivity.class);
				intent.putExtra("id", hotNewsBeans.get(position-1).getId());
				intent.putExtra("type", 0);
				intent.putExtra("title", "热点新闻");
				startActivity(intent);

			}
		});
		// 这两个绑定方法用其一
		// 方法一
		// mPullRefreshListView.setAdapter(mAdapter);
		// 方法二
		ListView actualListView = pullToRefreshListView.getRefreshableView();
		actualListView.setAdapter(hotNewsAdapter);
		ImageLoader imageLoader = ImageLoader.getInstance();
        pullToRefreshListView.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));
	}

	public void sendHttpResponse_HotNews(String path) {
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(path, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				DialogTools.showSelfProgressBar(HotNewsActivity.this,
						progressBar, true);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println(new String(arg2));

				hotNewsBeans.clear();
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

				Toast.makeText(HotNewsActivity.this, "网络请求失败，请检查网络是否连接！", 1)
						.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				hotNewsAdapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
				DialogTools.showSelfProgressBar(HotNewsActivity.this,
						progressBar, false);

			}
		});

	}

	public void sendHttpResponse_HotNews_more() {

		pageIndex = pageIndex + 1;
		String path_more = "http://api.yi18.net/top/list?page=" + pageIndex
				+ "&limit=10";

		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(path_more, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				DialogTools.showSelfProgressBar(HotNewsActivity.this,
						progressBar, true);
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

				Toast.makeText(HotNewsActivity.this, "网络请求失败，请检查网络是否连接！", 1)
						.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				pullToRefreshListView.onRefreshComplete();
				hotNewsAdapter.notifyDataSetChanged();
				DialogTools.showSelfProgressBar(HotNewsActivity.this,
						progressBar, false);

			}
		});

	}
}
