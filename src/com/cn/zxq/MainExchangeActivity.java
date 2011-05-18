package com.cn.zxq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.cn.axq.adapter.ExchangePairAdapter;
import com.cn.axq.db.ExchangeDBHelper;
import com.cn.axq.dlg.AboutDialog;

public class MainExchangeActivity extends Activity {

	private static final String URL_STRING = "http://www.gaitameonline.com/rateaj/getrate";
	private static String PREFS_NAME = "exchange";

	GridView gv_button_menu;
	ListView lv_exchange;
	LinearLayout ll;
	private Handler mHandler;
	private Thread thread;
	private Button editButton;
	private DataProcess dataProcess;
	private ScheduledExecutorService srv;
	private SharedPreferences settings;
	
	private TextView tvDateTime ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_exchange);
		ll = (LinearLayout) findViewById(R.id.ll_title);
		ll.setBackgroundResource(R.drawable.channelgallery_bg);
		lv_exchange = (ListView) findViewById(R.id.lv_exchange);
		editButton = (Button) findViewById(R.id.title_edit);
		settings = getSharedPreferences(PREFS_NAME, 0);
		editButton();
		dataProcess = new DataProcess(URL_STRING,settings);

		mHandler = new Handler();// {
		// @Override
		// public void handleMessage(Message msg) {
		// loadListData();
		// }
		// };

		srv = Executors.newSingleThreadScheduledExecutor();
		srv.scheduleAtFixedRate(new Runnable() {
			public void run() {
				mHandler.post(new Runnable() {
					public void run() {
						// text.setText("Got Data:" + new Date().toString());
						loadListData();
					}
				});
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);

		loadButtonMenu();
		
		ExchangeDBHelper.init(MainExchangeActivity.this);
		
		// Runnable runnable = new Runnable(){
		// public void run() {
		// try {
		// while(true){
		// mHandler.sendEmptyMessage(RESULT_OK);
		// Thread.sleep(1000);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// };

		// thread = new Thread(runnable);
		// thread.start();
//		loadListData();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_MENU) {
//			if (gv_button_menu == null) {
//				loadButtonMenu();
//			}
//
//			if (gv_button_menu.getVisibility() == GridView.GONE) {
//				gv_button_menu.setVisibility(GridView.VISIBLE);
//			} else if (gv_button_menu.getVisibility() == GridView.VISIBLE) {
//				gv_button_menu.setVisibility(GridView.GONE);
//			}
//		}
		
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getData() {
		List<HashMap<String, String>> beans = new ArrayList<HashMap<String, String>>();
		Map<String, ?> setMap = (Map<String, ?>) settings.getAll();
		HashMap map = new HashMap();
		try {

			List<MyBean> list_data = dataProcess.getData();
			for (MyBean item : list_data) {
				map = new HashMap();
				String pairCode = item.getCurrencyPairCode();
				
				int code = 9;
//				try {
//					code = ExchangeType.valueOf(pairCode).aliasName();
//				} catch (Exception e) {
//
//				}
				
				// switch (code) {
				// case 1:
				// map.put("pair_image", R.drawable.audjpy);
				// break;
				// case 2:
				// map.put("pair_image", R.drawable.cadjpy);
				// break;
				// case 3:
				// map.put("pair_image", R.drawable.chfjpy);
				// break;
				// case 4:
				// map.put("pair_image", R.drawable.gbpjpy);
				// break;
				// case 5:
				// map.put("pair_image", R.drawable.gbpusd);
				// break;
				// case 6:
				// map.put("pair_image", R.drawable.hkdjpy);
				// break;
				// case 7:
				// map.put("pair_image", R.drawable.usdjpy);
				// break;
				// case 8:
				// map.put("pair_image", R.drawable.zarjpy);
				// break;
				//
				// default:
				//
				// map.put("pair_image", R.drawable.suisse_m);
				// item.setDisplay(false);
				//
				// }
				map.put("pairCode", pairCode);
				map.put("bid", item.getBid());
				map.put("ask", item.getAsk());
				map.put("color",item.getColor());
				if (item.isDisplay()) {
					beans.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*
			map.put("pair_image", R.drawable.suisse_m);
			map.put("pairCode", "error");
			map.put("bid", "eeee");
			map.put("ask", "ffff");
			map.put("color",Color.WHITE);
			beans.add(map);
			*/
		}
		return beans;
	}

	@SuppressWarnings("unchecked")
	private void loadListData() {
		List<HashMap<String, String>> list = getData();
		String strUpdateTime = "Update Time: ";
		
		if(list != null && list.size() > 0) {
			ExchangeDBHelper.deleteAllPairRecord();
			
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			// insert into db...
			if(list!= null && list.size() > 0) {
				for(int i = 0; i < list.size(); i++) {
					HashMap<String, String> pairMap = list.get(i);
					String pairCode =  pairMap.get("pairCode");
					String bid =  pairMap.get("bid");
					String ask =  pairMap.get("ask");
					
					ExchangeDBHelper.insertPairRecord(pairCode, bid, ask, df.format(date));
				}		
			} 
			///			
		}
		
		Cursor cursor = ExchangeDBHelper.fetchAllPairRecord();
		
		if(cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			int dateTimeIndex = cursor.getColumnIndexOrThrow(ExchangeDBHelper.EXCHANGE_DATETIME);
			strUpdateTime += cursor.getString(dateTimeIndex);	
		}

		/*
		SimpleAdapter adapter = new SpecialAdapter(this, list,
				R.layout.list_item, new String[] { "pairCode", "bid", "ask" },
				new int[] { R.id.list_name, R.id.list_bid, R.id.list_ask });
		*/
		
		lv_exchange.setAdapter(new ExchangePairAdapter(this, cursor));
		
		// 
		tvDateTime = (TextView)findViewById(R.id.update_datetime);
		tvDateTime.setVisibility(View.VISIBLE);
		tvDateTime.setText(strUpdateTime);
	}

	@SuppressWarnings("unchecked")
	private void loadButtonMenu() {

		gv_button_menu = (GridView) findViewById(R.id.gv_main);
		gv_button_menu.setBackgroundResource(R.drawable.channelgallery_bg);
		gv_button_menu.setNumColumns(5);
		gv_button_menu.setGravity(Gravity.CENTER);
		gv_button_menu.setVerticalSpacing(10);

		ArrayList list = new ArrayList();
		HashMap map = new HashMap();
		map.put("itemImage", R.drawable.menu_new_user);
		map.put("itemText", getText(R.string.menu_currencies));
		list.add(map);

		map = new HashMap<String, Integer>();
		map.put("itemImage", R.drawable.menu_search);
		map.put("itemText", getText(R.string.menu_chart));
		list.add(map);

		map = new HashMap<String, Integer>();
		map.put("itemImage", R.drawable.menu_delete);
		map.put("itemText", getText(R.string.menu_news));
		list.add(map);

		map = new HashMap<String, Integer>();
		map.put("itemImage", R.drawable.menu_quit);
		map.put("itemText", getText(R.string.menu_setting));
		list.add(map);

		map = new HashMap<String, Integer>();
		map.put("itemImage", R.drawable.menu_fresh);
		map.put("itemText", getText(R.string.menu_about));
		list.add(map);

		final SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });

		gv_button_menu.setAdapter(adapter);
		gv_button_menu.setOnItemClickListener(new ItemClickListener());
	}
	
	class ItemClickListener implements OnItemClickListener {
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			txtInputPostCode = editInputBox.getText().toString().trim();
//			nIndex = position + 1;
//
//			if ("".equals(txtInputPostCode)) {
//				showToast(getText(R.string.slip_description).toString());
//				return;
//			}
//
//			httpExec();
//		}

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			int nIndex = position + 1;
			if(nIndex == 0){
				// currencies
				
			} else if(nIndex == 1) {
				
			} else if(nIndex == 2) {
				
			} else if(nIndex == 3) {
				Intent i = new Intent(MainExchangeActivity.this, NewsListActivity.class);
				startActivity(i);
			} else if(nIndex == 4) {
				// AboutDialog.create(MainExchangeActivity.this).show();
			} else if(nIndex == 5) {
				AboutDialog.create(MainExchangeActivity.this).show();
			}
		}
	}

	public void editButton() {
		editButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// mainMenuDialog.dismiss();
				// AlertDialog.Builder builder = new
				// AlertDialog.Builder(MainExchangeActivity.this);
				// AlertDialog confirmDialog = builder.create();
				// builder.setTitle("�Ƿ�ɾ�����У�?");
				// builder.create().show();
				Intent intent = new Intent(MainExchangeActivity.this,
						SetItemActivity.class);
				// intent.putExtra("user", dataProcess.getData());
				// intent.putStringArrayListExtra("user", dataProcess.getData())
				startActivity(intent);
			};

		});
	}
}

/*
class SpecialAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
	private List<HashMap<String, String>> items1;
	public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
	    super(context, items, resource, from, to);
	    this.items1 = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		int colorPos = position % colors.length;
		view.setBackgroundColor(colors[colorPos]);
		HashMap<String, ?> map = (HashMap<String, ?>)items1.get(position);
		TextView bidTextView = (TextView) view.findViewById(R.id.list_bid);
		Integer color = (Integer)map.get("color");
		//int color= Integer.parseInt(aa);
		bidTextView.setTextColor(color);
		TextView askTextView = (TextView) view.findViewById(R.id.list_ask);
		askTextView.setTextColor(color);
		return view;
	}
}
*/