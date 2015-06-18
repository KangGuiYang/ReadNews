package com.allen.readnews;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.readews.utils.DialogTools;
import com.allen.readews.utils.JsonTools;
import com.allen.readews.utils.NetConnect;
import com.allen.readews.utils.ToHtmlString;
import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDBannerAd.BannerAdListener;

public class NewsDetailActivity extends Activity {

	private WebView webView;
	String path_show = "http://api.yi18.net/top/show?id=1 ";
	private ProgressBar progressBar;
	private getNewsShowTask getNewsShowTask;
	NetConnect netConnect = new NetConnect();
	private TextView titleView, timeView;
	String titleString, timeString;

	//private AdView bannerAD;
	private RelativeLayout bannerContainer;

	private static String TAG = "AppX_BannerAd";

	private RelativeLayout appxBannerContainer;
	private static BDBannerAd bannerAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String title = getIntent().getStringExtra("title");
		int id = getIntent().getIntExtra("id", 1);
		int type = getIntent().getIntExtra("type", 0);
		if (type == 0) {
			path_show = "http://api.yi18.net/top/show?id=" + id + "";
		} else {
			path_show = "http://api.yi18.net/news/show?id=" + id + "";
		}

		switch (type) {
		case 1:
			title = "企业要闻";
			break;
		case 2:
			title = "医药新闻";
			break;
		case 3:
			title = "生活贴士";
			break;
		case 4:
			title = "药品新闻";
			break;
		case 5:
			title = "食品新闻";
			break;
		case 6:
			title = "社会热点";
			break;

		default:
			break;
		}
		setContentView(R.layout.news_detail_activity);
		bannerContainer = (RelativeLayout) findViewById(R.id.bannercontainer);
		TextView titlebar = (TextView) findViewById(R.id.title_bar_TV);
		ImageView backView = (ImageView) findViewById(R.id.backIV);
		backView.setImageResource(R.drawable.back);
		backView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		titlebar.setText(title);
		titleView = (TextView) findViewById(R.id.title);
		timeView = (TextView) findViewById(R.id.time);

		webView = (WebView) findViewById(R.id.webView_news);
		progressBar = (ProgressBar) findViewById(R.id.progressBar01);

		getNewsShowTask = new getNewsShowTask();
		getNewsShowTask.execute(path_show);

	}

	private void webload(String data) {
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 设置滚动条样式
		webView.setHorizontalScrollBarEnabled(false);
		webView.setVerticalScrollBarEnabled(false);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webView.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
		webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);

	}

	private class getNewsShowTask extends
			AsyncTask<String, ProgressBar, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			DialogTools.showSelfProgressBar(NewsDetailActivity.this,
					progressBar, true);
		}

		@Override
		protected String doInBackground(String... path) {
			// TODO Auto-generated method stub
			// 请求服务器地址

			String jsonStr = NetConnect.sendGetMethod(path[0]);

			if (jsonStr.equals("Error")) {
				Toast.makeText(NewsDetailActivity.this, "网络请求失败！", 0).show();
			} else {
				try {
					JSONObject jsonObject = JsonTools.parseJsonObj(jsonStr);

					jsonStr = jsonObject.getString("message");
					titleString = jsonObject.getString("title");
					timeString = jsonObject.getString("time");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return jsonStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			titleView.setText(titleString);
			timeView.setText(timeString);
			String resultString = ToHtmlString.toHTMLString(result);
			webload(resultString);
			DialogTools.showSelfProgressBar(NewsDetailActivity.this,
					progressBar, false);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 showBannerAD();
	}

	private void showBannerAD() {
		// this.bannerAD = new AdView(this, AdSize.BANNER, Constants.APPId,
		// Constants.BannerPosId);
		// this.bannerAD.setAdListener(new AdListener() {
		//
		// @Override
		// public void onNoAd() {
		// Log.i("admsg:", "Banner AD LoadFail");
		// }
		//
		// @Override
		// public void onBannerClosed() {
		//
		// Log.i("admsg:", "Banner AD Closed");
		// }
		//
		// @Override
		// public void onAdReceiv() {
		// Log.i("admsg:", "Banner AD Ready to show");
		// }
		//
		// @Override
		// public void onAdExposure() {
		// Log.i("admsg:", "Banner AD Exposured");
		// }
		//
		// @Override
		// public void onAdClicked() {
		//
		// Log.i("admsg:", "Banner AD Clicked");
		// }
		//
		// @Override
		// public void onNoAd(int arg0) {
		// Log.i("admsg:", "Banner AD onNoAd:::" + arg0);
		//
		// }
		// });
		// this.bannerContainer.removeAllViews();
		// this.bannerContainer.addView(bannerAD);
		// bannerAD.fetchAd(new AdRequest());

		// 创建广告视图
		// 发布时请使用正确的ApiKey和广告位ID
		// 此处ApiKey和推广位ID均是测试用的
		// 您在正式提交应用的时候，请确认代码中已经更换为您应用对应的Key和ID
		// 具体获取方法请查阅《百度开发者中心交叉换量产品介绍.pdf》
		bannerAdView = new BDBannerAd(this, "CRqGC0MMbzpSLT2EYgDKk58d6ymsHylt",
				"TRwQxo62D74ULcY9TDRCjvno");

		// 设置横幅广告展示尺寸，如不设置，默认为SIZE_FLEXIBLE;
		bannerAdView.setAdSize(BDBannerAd.SIZE_FLEXIBLE);

		// 设置横幅广告行为监听器
		bannerAdView.setAdListener(new BannerAdListener() {

			@Override
			public void onAdvertisementDataDidLoadFailure() {
				Log.e(TAG, "load failure");
			}

			@Override
			public void onAdvertisementDataDidLoadSuccess() {
				Log.e(TAG, "load success");
			}

			@Override
			public void onAdvertisementViewDidClick() {
				Log.e(TAG, "on click");
			}

			@Override
			public void onAdvertisementViewDidShow() {
				Log.e(TAG, "on show");
			}

			@Override
			public void onAdvertisementViewWillStartNewIntent() {
				Log.e(TAG, "leave app");
			}
		});

		// 创建广告容器
		appxBannerContainer = (RelativeLayout) findViewById(R.id.bannercontainer);

		// 显示广告视图
		appxBannerContainer.addView(bannerAdView);
	}
}
