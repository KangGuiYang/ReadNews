package com.allen.readnews.bean;

public class LeftNewsbean {

	String title;
	String tag;
	String img;
	int count;
	String md;
	String time;
	int id;

	public LeftNewsbean(String title, String tag, String img, int count,
			String md, String time, int id) {
		super();
		this.title = title;
		this.tag = tag;
		this.img = img;
		this.count = count;
		this.md = md;
		this.time = time;
		this.id = id;

	}
	
	public LeftNewsbean(String title, String tag, int count,
			String md, String time, int id) {
		super();
		this.title = title;
		this.tag = tag;
	
		this.count = count;
		this.md = md;
		this.time = time;
		this.id = id;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
