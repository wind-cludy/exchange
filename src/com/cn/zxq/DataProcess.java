package com.cn.zxq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Color;

public class DataProcess {

	private SharedPreferences settings;
	DataProcess dataProcess = null;
	private static String PREFS_NAME = "exchange";
	private HttpClient httpclient;
	private HttpPost httppost;
	private List<MyBean> list_temp;

	public DataProcess(String url, SharedPreferences setting) {
		this.httpclient = new DefaultHttpClient();
		this.httppost = new HttpPost(url);
		this.settings = setting;
		list_temp =  new ArrayList<MyBean>();
		
	}

	public List<MyBean> getData() throws ClientProtocolException, IOException,
			JSONException {
		HttpResponse response = httpclient.execute(httppost);
		String data = new BasicResponseHandler().handleResponse(response);
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("quotes");
		List<MyBean> beans = new ArrayList<MyBean>();
		Map<String, ?> setMap = (Map<String, ?>) settings.getAll();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
			MyBean bean = new MyBean();
			bean.setHigh(jsonObject2.getString("high"));
			bean.setOpen(jsonObject2.getString("open"));
			String bid = jsonObject2.getString("bid");
			bean.setBid(bid);
			String pairCode = jsonObject2.getString("currencyPairCode");
			String temp = pairCode.substring(3);
			pairCode = pairCode.replace(temp, "/" + temp);
			bean.setCurrencyPairCode(pairCode);
			bean.setAsk(jsonObject2.getString("ask"));
			bean.setLow(jsonObject2.getString("low"));
			
			if(list_temp==null || list_temp.size()==0) {
				bean.setColor(Color.WHITE);
			} else {
				MyBean myBean = list_temp.get(i);
				if (myBean == null || myBean.getBid() == null
						|| myBean.getBid().equals("")) {
					bean.setColor(Color.WHITE);
				} else if (myBean.getBid().compareTo(bid) == 0) {
					bean.setColor(Color.WHITE);
				} else if (myBean.getBid().compareTo(bid) > 0) {
					bean.setColor(Color.RED);
				} else {
					bean.setColor(Color.GREEN);
				}
			}
//			if(i%2==0)
//				bean.setColor(Color.RED);
//			else
//				bean.setColor(Color.GREEN);
			if (setMap.get(pairCode) == null)
				bean.setDisplay(true);
			else
				bean.setDisplay(false);
			beans.add(bean);
		}
		list_temp =beans;
		return beans;

	}
	
}
