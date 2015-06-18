package com.allen.readews.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetConnect {

	private static final String TAG = "NetUtil";

	/**
	 * ���������Ƿ����
	 */
	public static boolean isConnnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivityManager) {
			NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

			if (null != networkInfo) {
				for (NetworkInfo info : networkInfo) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						Log.e(TAG, "the net is ok");
						return true;
					}
				}
			}
		}
		Toast.makeText(context, "��������ʧ��", Toast.LENGTH_SHORT).show();
		return false;
	}

	/**
	 * �������״̬�£�ͨ��get��ʽ��server�˷������󣬲�������Ӧ����
	 * 
	 * @param strUrl
	 *            ������ַ
	 * @param context
	 *            ������
	 * @return ��Ӧ����
	 */
	public static JSONObject getResponseForGet(String strUrl, Context context) {
		if (isConnnected(context)) {
			return getResponseForGet(strUrl);
		}
		return null;
	}

	/**
	 * ͨ��Get��ʽ�������󣬲�������Ӧ����
	 * 
	 * @param strUrl
	 *            ������ַ
	 * @return ��Ӧ��JSON����
	 */
	public static JSONObject getResponseForGet(String strUrl) {
		HttpGet httpRequest = new HttpGet(strUrl);
		System.out.println("getRespose(httpRequest)===="
				+ getRespose(httpRequest));
		return getRespose(httpRequest);
	}

	/**
	 * �������״̬�£�ͨ��post��ʽ��server�˷������󣬲�������Ӧ����
	 * 
	 * @param market_uri
	 *            ������ַ
	 * @param nameValuePairs
	 *            ������Ϣ
	 * @param context
	 *            ������
	 * @return ��Ӧ����
	 */
	public static JSONObject getResponseForPost(String market_uri,
			List<NameValuePair> nameValuePairs, Context context) {
		if (isConnnected(context)) {
			return getResponseForPost(market_uri, nameValuePairs);
		}
		return null;
	}

	/**
	 * ͨ��post��ʽ��������������󣬲�������Ӧ����
	 * 
	 * @param strUrl
	 *            ������ַ
	 * @param nameValuePairs
	 *            ������Ϣ
	 * @return ��Ӧ����
	 */
	public static JSONObject getResponseForPost(String market_uri,
			List<NameValuePair> nameValuePairs) {
		if (null == market_uri || "" == market_uri) {
			return null;
		}
		HttpPost request = new HttpPost(market_uri);
		try {
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			return getRespose(request);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * ��Ӧ�ͻ�������
	 * 
	 * @param request
	 *            �ͻ�������get/post
	 * @return ��Ӧ����
	 */
	public static JSONObject getRespose(HttpUriRequest request) {
		try {
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(request);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				Log.i(TAG, "results=" + result);
				return new JSONObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����JSON ����
	 * 
	 * @throws Exception
	 */

	public  JSONArray parseJson(String jsonStr) throws Exception {

		JSONObject newsJson = new JSONObject(jsonStr);
		String success = newsJson.getString("success");
		//String total = newsJson.getString("total");
		//System.out.println(success + "/n" + total);
		// JSONObject yi18 = root.getJSONObject("yi18");
		JSONArray arr = newsJson.getJSONArray("yi18");

		for (int i = 0; i < arr.length(); i++) {

			JSONObject news = arr.getJSONObject(i);

			int id = news.getInt("id");
			String title = news.getString("title");
//
//			String img = news.getString("img");
//			String keywords = news.getString("keywords");
//			String from = news.getString("from");
//			String time = news.getString("time");
//
//			System.out.println(id + "/n" + img + "/n" + title + "/n" + keywords
//					+ "/n" + from + "/n" + time);

		}
		return arr;
	}

	public static String sendGetMethod(String path) {
		String result = "";

		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpGet httpGet = new HttpGet(path);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "gbk");
			} else {
				result = "Error";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;

	}

	public String sendRequest(String path) {

		// �����������ַ
		//String path = "http://api.yi18.net/top/list";

		try {
			// Ĭ�Ͽͻ���
			HttpClient httpClient = new DefaultHttpClient();
			// Get ��ʽ��������
			HttpGet httpGet = new HttpGet(path);

			// ������Ӧ
			HttpResponse httpResponse;
			httpResponse = httpClient.execute(httpGet);
			// �ж���Ӧ���
			if (httpResponse.getStatusLine().getStatusCode() == 200) { // ��Ӧ�ɹ�

				HttpEntity httpEntity = httpResponse.getEntity();

				String result = EntityUtils.toString(httpEntity, "gbk");

				Log.i("msg", result);

				return result;

			} else {
				Log.i("msgsss", "����");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
