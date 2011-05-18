package com.cn.zxq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SetItemActivity extends Activity {

	LinearLayout ll;
	ListView lv_exchange;
	private static String PREFS_NAME = "exchange";
	private static final String URL_STRING = "http://www.gaitameonline.com/rateaj/getrate";
	private Button endButton;
	private SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.select_exchange);
		settings = getSharedPreferences(PREFS_NAME, 0);
		endButton = (Button) findViewById(R.id.select_end);
		endButton();
		ll = (LinearLayout) findViewById(R.id.select_title);
		ll.setBackgroundResource(R.drawable.channelgallery_bg);
		lv_exchange = (ListView) findViewById(R.id.select_exchange);
		loadListData();
	}

	private void loadListData() {

		DataProcess dataProcess = new DataProcess(URL_STRING,settings);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MyBean> list_data  = new ArrayList<MyBean>();
		try {
		    list_data = dataProcess.getData();
//			for (MyBean item : list_data) {
//				map = new HashMap<String, Object>();
//				String pairCode = item.getCurrencyPairCode();
//				String temp = pairCode.substring(3);
//				pairCode = pairCode.replace(temp, "/" + temp);
//				map.put("pair", pairCode);
//				if (item.isDisplay()) {
//					map.put("flag", true);
//				} else {
//					map.put("flag", false);
//				}
//				list.add(map);
//
//			}
		} catch (Exception e) {

		}
//		SimpleAdapter adapter = new SimpleAdapter(this, list,
//				R.layout.select_item, new String[] { "pair", "flag" },
//				new int[] { R.id.item_name, R.id.item_flag });
		ListAdapter adapter = new MyListAdapter(this,R.layout.select_item,list_data);
		lv_exchange.setAdapter(adapter);
	}

	public void endButton() {
		endButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SetItemActivity.this,
						MainExchangeActivity.class);
				Editor editor = settings.edit();
				ListAdapter adapter = lv_exchange.getAdapter();
				int count = adapter.getCount();
				for (int i = 0; i < count; i++) {
					MyBean bean = (MyBean) adapter.getItem(i);

					//boolean flag = bean.isDisplay;
					//String pair =  bean.getCurrencyPairCode();
					if (!bean.isDisplay())
						editor.putString(bean.getCurrencyPairCode(), String.valueOf(i));
					else
						editor.remove(bean.getCurrencyPairCode());
				}
				editor.commit();
				startActivity(intent);
			};

		});
	}
}

class MyListAdapter extends ArrayAdapter<MyBean> {
	protected LayoutInflater inflater;
	private List<MyBean> items;
	private int rowLayoutResourceId;
	
	public MyListAdapter(Context context, int rowLayoutResourceId, List<MyBean> items) {
		super(context, rowLayoutResourceId, items);
		this.items = items;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.rowLayoutResourceId = rowLayoutResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			Log.i("MultipleChoiceListActivity", "constructing a new view for " + String.valueOf(position) + "-th list row");
			view = inflater.inflate(R.layout.select_item, null);
		} else {
   			Log.i("MultipleChoiceListActivity", "reusing the view for " + String.valueOf(position) + "-th list row");
		}
		final MyBean myBean = items.get(position);
		TextView textView = (TextView)view.findViewById(R.id.item_name);
		textView.setTextColor(Color.RED);
		String pairCode = myBean.getCurrencyPairCode();
		textView.setText(pairCode);
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_flag);
		Log.i("MultipleChoiceListActivity", "CheckBox#setChecked(" + String.valueOf(items.get(position)) + ")");
		// 明示的にクリアするならこうだけど、とにかくsetChecked前にリスナ登録すればOK
		// checkBox.setOnCheckedChangeListener(null);
		final int p = position;
		// 必ずsetChecked前にリスナを登録(convertView != null の場合は既に別行用のリスナが登録されている！)
		//checkBox.setOnCheckedChangeListener(null);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.i("MultipleChoiceListActivity", "p=" + String.valueOf(p) + ", isChecked=" + String.valueOf(isChecked));
				myBean.setDisplay(isChecked);
				items.set(p, myBean);
			}
		});
		checkBox.setChecked(myBean.isDisplay());
		
		return view;
	}
}
