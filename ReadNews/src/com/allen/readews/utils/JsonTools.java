package com.allen.readews.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTools {

	/**
	 * ����json����
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */

	public static JSONObject parseJsonObj(String jsonStr) throws Exception {

		JSONObject newsJson = new JSONObject(jsonStr);
		String success = newsJson.getString("success");

		System.out.println(success);

		System.out.println("newsJson.getString==="+newsJson.getString("yi18"));

		JSONObject jsonObject = new JSONObject(newsJson.getString("yi18"));
		
		return jsonObject;
	}
	
	public  static JSONArray parseJsonArr(String jsonStr) throws Exception {

		JSONObject newsJson = new JSONObject(jsonStr);
		String success = newsJson.getString("success");
		JSONArray arr = newsJson.getJSONArray("yi18");

		for (int i = 0; i < arr.length(); i++) {

			JSONObject news = arr.getJSONObject(i);

			int id = news.getInt("id");
			String title = news.getString("title");


		}
		return arr;
	}

}
