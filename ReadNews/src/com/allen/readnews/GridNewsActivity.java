package com.allen.readnews;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
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
import com.allen.readnews.adapter.NewsAdapter;
import com.allen.readnews.applaction.AppData;
import com.allen.readnews.bean.Newsbean;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class GridNewsActivity extends Activity {

	private PullToRefreshListView pullToRefreshListView;

	private AsyncHttpClient asyncHttpClient;

	public LayoutInflater mInflater;

	private int pageIndex = 1;
	NewsAdapter newsAdapter;
	ArrayList<Newsbean> newsbeans;

	TextView toptitleTV;
	ProgressBar progressBar;

	ImageView backImageView;
	AppData appData;
	int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		type = getIntent().getIntExtra("type", 1);
		String title = getIntent().getStringExtra("title");

		setContentView(R.layout.news_listview);
		appData = (AppData) getApplicationContext();

		mInflater = LayoutInflater.from(this);

		progressBar = (ProgressBar) findViewById(R.id.progressBar01);
		toptitleTV = (TextView) findViewById(R.id.title_bar_TV);
		backImageView = (ImageView) findViewById(R.id.backIV);
		backImageView.setImageResource(R.drawable.back);
		initListView();// 初始化listview
		toptitleTV.setText(title);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GridNewsActivity.this.finish();
			}
		});

		initPullListview();
	}

	private void initListView() {
		switch (type) {
		case 1:
			newsbeans = appData.getCompanylNewsbeans();
			break;
		case 2:
			newsbeans = appData.getYiyaoNewsbeans();
			break;
		case 3:
			newsbeans = appData.getLeftNewsbeans();
			break;
		case 4:
			newsbeans = appData.getYaopinNewsbeans();
			break;
		case 5:
			newsbeans = appData.getFoodNewsbeans();
			break;
		case 6:
			newsbeans = appData.getSocialNewsbeans();
			break;

		default:
			break;
		}
		if (newsAdapter == null) {
			newsAdapter = new NewsAdapter(GridNewsActivity.this, newsbeans);
		}
		if (newsbeans.size() > 0) {
			newsAdapter.notifyDataSetChanged();
		} else {
			sendRefushRequest();
		}

		newsAdapter.notifyDataSetChanged();

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
						sendRefushRequest();

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

						sendMoreRequest();
					}

				});

		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				intent.setClass(GridNewsActivity.this, NewsDetailActivity.class);
				intent.putExtra("id", newsbeans.get(position - 1).getId());
				intent.putExtra("type", type);
				startActivity(intent);

			}
		});
		// 这两个绑定方法用其一
		// 方法一
		// mPullRefreshListView.setAdapter(mAdapter);
		// 方法二
		ListView actualListView = pullToRefreshListView.getRefreshableView();
		actualListView.setAdapter(newsAdapter);
		ImageLoader imageLoader = ImageLoader.getInstance();
        pullToRefreshListView.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));

	}

	private void sendRefushRequest() {
		String RefreshPath = "http://api.yi18.net/news/list?id=" + type
				+ "&page=1&limit=10";
		newsbeans.clear();
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(RefreshPath, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				DialogTools.showSelfProgressBar(GridNewsActivity.this,
						progressBar, true);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("arg2===" + new String(arg2));
				String jsonStr = new String(arg2);
				String imgurl = "img/top/default.jpg";
				Newsbean socialNewsbean;
				try {
					JSONArray arr = JsonTools.parseJsonArr(jsonStr);
					System.out.println("arr.length()===" + arr.length());
					for (int i = 0; i < arr.length(); i++) {
						JSONObject news = arr.getJSONObject(i);

						if (news.has("img")) {
							imgurl = news.getString("img");
						} else {
							imgurl = "";
						}
						socialNewsbean = new Newsbean(news.getString("title"),
								news.getString("tag"), imgurl, news
										.getInt("count"), news.getString("md"),
								news.getString("time"), news.getInt("id"));
						newsbeans.add(socialNewsbean);

					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(GridNewsActivity.this, "网络请求失败，请检查网络是否连接！", 1)
						.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				newsAdapter.notifyDataSetChanged();
				DialogTools.showSelfProgressBar(GridNewsActivity.this,
						progressBar, false);
				pullToRefreshListView.onRefreshComplete();
			}

		});
	}

	private void sendMoreRequest() {
		pageIndex = pageIndex + 1;

		String path_more = "http://api.yi18.net/news/list?id=" + type
				+ "&page=" + pageIndex + "&limit=10";

		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(path_more, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				System.out.println("arg2===" + new String(arg2));
				String jsonStr = new String(arg2);
				String imgurl = "img/top/default.jpg";
				Newsbean socialNewsbean;
				try {
					JSONArray arr = JsonTools.parseJsonArr(jsonStr);
					System.out.println("arr.length()===" + arr.length());
					for (int i = 0; i < arr.length(); i++) {
						JSONObject news = arr.getJSONObject(i);
						if (news.has("img")) {
							imgurl = news.getString("img");

						} else {
							imgurl = "";
						}
						socialNewsbean = new Newsbean(news.getString("title"),
								news.getString("tag"), imgurl, news
										.getInt("count"), news.getString("md"),
								news.getString("time"), news.getInt("id"));
						newsbeans.add(socialNewsbean);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(GridNewsActivity.this, "网络请求失败，请检查网络是否连接！", 1)
						.show();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				newsAdapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
			}
		});

	}
}
