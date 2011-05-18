package com.cn.zxq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class android extends Activity {
	/** Called when the activity is first created. */
	private static final String URL_STRING = "http://www.gaitameonline.com/rateaj/getrate";
	private TextView tv;
	private Handler mHandler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv =(TextView) findViewById(R.id.textView1);
		Timer mTimer = new Timer();
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				List<MyBean> beans = new ArrayList<MyBean>();
				String result = "";
				switch (msg.what) {
				case 0:
					try {
						beans = getData();
					} catch (Exception e) {
						e.printStackTrace();
						result = "dddddddd";

					}

					for (MyBean bean : beans) {
						result = result + bean.getCurrencyPairCode() + ":"
								+ bean.getAsk() + "\n";
					}
					String a = String.valueOf((int)(Math.random() * 10)) ; 
					result= result +  "\n" +a;
					tv.setText(result);
					break;
				}
			}
		};
		TimerTaskEx mTimerTask = new TimerTaskEx();

		//str = mTimerTask.getResult();

		//tv.setText(str);
		//setContentView(tv);
		mTimer.schedule(mTimerTask, 1000, 1000);
		
	}

	public List<MyBean> getData() throws ClientProtocolException, IOException,
			JSONException {
		List<MyBean> beans = new ArrayList<MyBean>();

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL_STRING);
		HttpResponse response = httpclient.execute(httppost);
		String data = new BasicResponseHandler().handleResponse(response);

		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("quotes");
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
			MyBean bean = new MyBean();
			bean.setHigh(jsonObject2.getString("high"));
			bean.setOpen(jsonObject2.getString("open"));
			bean.setBid(jsonObject2.getString("bid"));
			bean.setCurrencyPairCode(jsonObject2.getString("currencyPairCode"));
			bean.setAsk(jsonObject2.getString("ask"));
			bean.setLow(jsonObject2.getString("low"));
			beans.add(bean);
		}

		return beans;
	}

	class TimerTaskEx extends TimerTask {

		

		@Override
		public void run() {
			 mHandler.sendEmptyMessage(0);
			
		}

	}
}