package com.allen.readnews.bean;

import android.graphics.Bitmap;

public class HotNewsBean {
	private int id;
	private String title;
	private String img;
	private String from;
	private String time;
	private int count;
	private String keywords;
	private Bitmap imgBitmap;

	public Bitmap getImgBitmap() {
		return imgBitmap;
	}

	public void setImgBitmap(Bitmap imgBitmap) {
		this.imgBitmap = imgBitmap;
	}

	public HotNewsBean(int id, String title, String img, String from,
			String time, String keywords, int count, Bitmap imgBitmap) {
		this.id = id;
		this.title = title;
		this.img = img;
		this.from = from;
		this.count = count;
		this.keywords = keywords;
		this.time = time;
		this.imgBitmap = imgBitmap;

	}

	public HotNewsBean(int id, String title, String img, String from,
			String time, String keywords, int count) {
		this.id = id;
		this.title = title;
		this.img = img;
		this.from = from;
		this.count = count;
		this.keywords = keywords;
		this.time = time;

	}

	public HotNewsBean(Bitmap imgBitmap) {
		this.imgBitmap = imgBitmap;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
