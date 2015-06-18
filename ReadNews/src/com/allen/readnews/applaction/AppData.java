package com.allen.readnews.applaction;



import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;

import com.allen.readnews.bean.HotNewsBean;
import com.allen.readnews.bean.Newsbean;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class AppData extends Application{

	ArrayList<Newsbean> socialNewsbeans;
	ArrayList<Newsbean> leftNewsbeans;
	ArrayList<Newsbean> companylNewsbeans;
	ArrayList<Newsbean> FoodNewsbeans;
	ArrayList<Newsbean> yaopinNewsbeans;
	ArrayList<Newsbean> yiyaoNewsbeans;
	
	ArrayList<Newsbean> newsbeans;
	
	
	ArrayList<HotNewsBean> hotNewsBeans;
	
	DrawerLayout drawerLayout;

	public AppData() {
		// TODO Auto-generated constructor stub
		socialNewsbeans = new ArrayList<Newsbean>();
		leftNewsbeans = new ArrayList<Newsbean>();
		companylNewsbeans = new ArrayList<Newsbean>();
		FoodNewsbeans = new ArrayList<Newsbean>();
		yaopinNewsbeans = new ArrayList<Newsbean>();
		yiyaoNewsbeans = new ArrayList<Newsbean>();
		
		hotNewsBeans = new ArrayList<HotNewsBean>();
		
		
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
	}

	public ArrayList<Newsbean> getNewsbeans() {
		return newsbeans;
	}

	public void setNewsbeans(ArrayList<Newsbean> newsbeans) {
		this.newsbeans = newsbeans;
	}

	public ArrayList<Newsbean> getYaopinNewsbeans() {
		return yaopinNewsbeans;
	}

	public void setYaopinNewsbeans(ArrayList<Newsbean> yaopinNewsbeans) {
		this.yaopinNewsbeans = yaopinNewsbeans;
	}

	public ArrayList<Newsbean> getYiyaoNewsbeans() {
		return yiyaoNewsbeans;
	}

	public void setYiyaoNewsbeans(ArrayList<Newsbean> yiyaoNewsbeans) {
		this.yiyaoNewsbeans = yiyaoNewsbeans;
	}

	public ArrayList<HotNewsBean> getHotNewsBeans() {
		return hotNewsBeans;
	}

	public void setHotNewsBeans(ArrayList<HotNewsBean> hotNewsBeans) {
		this.hotNewsBeans = hotNewsBeans;
	}

	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}

	public void setDrawerLayout(DrawerLayout drawerLayout) {
		this.drawerLayout = drawerLayout;
	}

	public ArrayList<Newsbean> getFoodNewsbeans() {
		return FoodNewsbeans;
	}

	public void setFoodNewsbeans(ArrayList<Newsbean> foodNewsbeans) {
		FoodNewsbeans = foodNewsbeans;
	}

	public ArrayList<Newsbean> getSocialNewsbeans() {
		return socialNewsbeans;
	}

	public void setSocialNewsbeans(ArrayList<Newsbean> socialNewsbeans) {
		this.socialNewsbeans = socialNewsbeans;
	}

	public ArrayList<Newsbean> getLeftNewsbeans() {
		return leftNewsbeans;
	}

	public void setLeftNewsbeans(ArrayList<Newsbean> leftNewsbeans) {
		this.leftNewsbeans = leftNewsbeans;
	}

	public ArrayList<Newsbean> getCompanylNewsbeans() {
		return companylNewsbeans;
	}

	public void setCompanylNewsbeans(ArrayList<Newsbean> companylNewsbeans) {
		this.companylNewsbeans = companylNewsbeans;
	}
	
	
	public static void initImageLoader(Context context) {
		// 缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"AllenNews/imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用MD5加密
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass  your own memory cache implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 由原先的discCache -> diskCache
				.diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout(5s),readTimeout(30)超时时间
				.writeDebugLogs() // Remove for release app
				
				.build();
		// 全局初始化此配置
		ImageLoader.getInstance().init(config);
	}

}
