package com.allen.readnews.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.readnews.R;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDInterstitialAd.InterstitialAdListener;

public class LeftMenuFragment extends Fragment {

	ListView ListView;

	// 展示的文字
	private String[] texts = new String[] { "秒扫二维码", "天气纯净版", "证券资格考试", "精品推荐",
			"更多内容敬请期待" };
	// 展示的图片
	private int[] images = new int[] { R.drawable.icon_type48,
			R.drawable.icon_type48, R.drawable.icon_type48,
			R.drawable.icon_type48, R.drawable.icon_type48 };

	ImageView imageView;
	TextView title;

	LeftMenuAdapter leftMenuAdapter;
	LeftMenuOnClick leftMenuOnClick;

	FragmentManager fmManager;
	FragmentTransaction fTransaction;

	private static String TAG = "AppX_Interstitial";

	private BDInterstitialAd appxInterstitialAdView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.leftmenu_fragment, container,
				false);

		fmManager = getFragmentManager();

		leftMenuOnClick = new LeftMenuOnClick();
		ListView = (ListView) view.findViewById(R.id.listview);
		leftMenuAdapter = new LeftMenuAdapter();
		ListView.setAdapter(leftMenuAdapter);
		ListView.setOnItemClickListener(leftMenuOnClick);

		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init();
	}

	public class LeftMenuOnClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			switch (arg2) {
			case 0:
				Uri qruri = Uri
						.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.allen.myqrcode");
				Intent intent0 = new Intent(Intent.ACTION_VIEW, qruri);
				startActivity(intent0);
				break;
			case 1:
				Uri weatheruri = Uri
						.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.allen.weather");
				Intent intent1 = new Intent(Intent.ACTION_VIEW, weatheruri);
				startActivity(intent1);
				break;
			case 2:
				Uri securi = Uri
						.parse("http://www.mumayi.com/android-877576.html");
				Intent intent2 = new Intent(Intent.ACTION_VIEW, securi);
				startActivity(intent2);
				break;

			case 3:
				/*
				 * 创建应用墙广告 “appid” 指在 http://e.qq.com/dev/ 能看到的app唯一字符串 “posid”
				 * 指在 http://e.qq.com/dev/ 生成的数字串， 并非 appid 或者 appkey testad
				 * 如果设置为true，则进入测试广告模式。该广告模式下不扣费。 建议在调式时设置为true，发布前设置为false。
				 */
				// GdtAppwall wall = new GdtAppwall(getActivity(),
				// Constants.APPId, Constants.APPWallPosId, false);
				// wall.doShowAppWall();
				// 展示插屏广告前先请先检查下广告是否加载完毕
				Toast.makeText(getActivity(), "本软件所有费用有百度广告赞助！", 1).show();
				if (appxInterstitialAdView.isLoaded()) {
					appxInterstitialAdView.showAd();
				} else {
					Log.i(TAG, "AppX Interstitial Ad is not ready");
					appxInterstitialAdView.loadAd();
				}
				break;
			case 4:
				Toast.makeText(getActivity(), "更多内容正在加班开发中。。。", 0).show();
				break;

			}

		}

	}

	public class LeftMenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return texts.length;
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
		public View getView(int position, View view, ViewGroup arg2) {
			if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_leftmenu, null);

				imageView = (ImageView) view
						.findViewById(R.id.imageView_left_menu);
				title = (TextView) view.findViewById(R.id.left_menu_title);

			}

			imageView.setImageResource(images[position]);
			title.setText(texts[position]);
			return view;
		}

	}

	private void init() {
		// 创建广告视图
		// 发布时请使用正确的ApiKey和广告位ID
		// 此处ApiKey和推广位ID均是测试用的
		// 您在正式提交应用的时候，请确认代码中已经更换为您应用对应的Key和ID
		// 具体获取方法请查阅《百度开发者中心交叉换量产品介绍.pdf》
		appxInterstitialAdView = new BDInterstitialAd(getActivity(),
				"CRqGC0MMbzpSLT2EYgDKk58d6ymsHylt", "TRwQxo62D74ULcY9TDRCjvno");

		// 设置插屏广告行为监听器
		appxInterstitialAdView.setAdListener(new InterstitialAdListener() {

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
			public void onAdvertisementViewDidHide() {
				Log.e(TAG, "on hide");
			}

			@Override
			public void onAdvertisementViewDidShow() {
				Log.e(TAG, "on show");
			}

			@Override
			public void onAdvertisementViewWillStartNewIntent() {
				Log.e(TAG, "leave");
			}

		});

		// 加载广告
		appxInterstitialAdView.loadAd();

	}

}
