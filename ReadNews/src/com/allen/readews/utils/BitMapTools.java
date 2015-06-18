package com.allen.readews.utils;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitMapTools {

	public  static Bitmap getBitmap(String path) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(path);
			bitmap = BitmapFactory.decodeStream(url.openStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bitmap;
	}

}
