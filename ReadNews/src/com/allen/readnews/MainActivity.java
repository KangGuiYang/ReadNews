package com.allen.readnews;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import com.allen.readnews.applaction.AppData;
import com.allen.readnews.fragment.HomeFragment;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends BaseActivity {

	private DrawerLayout drawerLayout;

	private FragmentManager fmManager;
	private FragmentTransaction fTransaction;

	private HomeFragment homeFragment;
	private AppData appData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appData = (AppData) getApplicationContext();
		homeFragment = new HomeFragment();
		fmManager = getFragmentManager();
		fTransaction = fmManager.beginTransaction();
		fTransaction.replace(R.id.main, homeFragment);
		fTransaction.commit();

		init_DrawerLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 初始化DrawerLayout
	 */
	public void init_DrawerLayout() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
				Gravity.RIGHT);// 关闭右侧菜单的滑动出现效果
		drawerLayout
				.setBackgroundResource(R.drawable.img_drawlayout_background);
		init_DrawerLayout_Events();
		appData.setDrawerLayout(drawerLayout);
	}

	/**
	 * 初始化DrawerLayout事件监听
	 */
	private void init_DrawerLayout_Events() {
		// 设置监听
		drawerLayout.setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int newState) {
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				View mContent = drawerLayout.getChildAt(0);
				View mMenu = drawerView;
				float scale = 1 - slideOffset;
				float rightScale = 0.8f + scale * 0.2f;

				if (drawerView.getTag().equals(
						getResources().getString(R.string.left_tag))) {// 展开左侧菜单
					float leftScale = 1 - 0.3f * scale;

					// 设置左侧菜单缩放效果
					ViewHelper.setScaleX(mMenu, leftScale);
					ViewHelper.setScaleY(mMenu, leftScale);
					ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));

					// 设置中间View缩放效果
					ViewHelper.setTranslationX(mContent,
							mMenu.getMeasuredWidth() * (1 - scale));
					ViewHelper.setPivotX(mContent, 0);
					ViewHelper.setPivotY(mContent,
							mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				} else {// 展开右侧菜单
					// 设置中间View缩放效果
					ViewHelper.setTranslationX(mContent,
							-mMenu.getMeasuredWidth() * slideOffset);
					ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
					ViewHelper.setPivotY(mContent,
							mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				}

			}
 
			// 菜单打开
			@Override
			public void onDrawerOpened(View drawerView) {
			}

			// 菜单关闭
			@Override
			public void onDrawerClosed(View drawerView) {
				drawerLayout.setDrawerLockMode(
						DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
			}
		});
	}
}
