package com.cn.axq.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.cn.axq.db.ExchangeDBHelper;
import com.cn.zxq.R;

public class ExchangePairAdapter extends ResourceCursorAdapter {
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
	private List<HashMap<String, String>> items1;
	
	public ExchangePairAdapter(Context context,  Cursor c) {
		super(context, R.layout.list_item, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		int rowIdIndex = cursor
				.getColumnIndexOrThrow(ExchangeDBHelper.EXCHANGE_ROWID);
		int pairCodeIndex = cursor
				.getColumnIndexOrThrow(ExchangeDBHelper.EXCHANGE_PAIRCODE);
		int bidPriceIndex = cursor
				.getColumnIndexOrThrow(ExchangeDBHelper.EXCHANGE_BID);
		int askPriceIndex = cursor
				.getColumnIndexOrThrow(ExchangeDBHelper.EXCHANGE_ASK);
		
		int rowId = cursor.getInt(rowIdIndex);
		String pairCode = cursor.getString(pairCodeIndex);
		String bidPrice = cursor.getString(bidPriceIndex);
		String askPrice = cursor.getString(askPriceIndex);
		
		int colorPos = rowId % colors.length;
		view.setBackgroundColor(colors[colorPos]);
		
		ViewHolder holder = (ViewHolder)view.getTag();
		holder.pairCode.setText(pairCode);
		holder.bidPrice.setText(bidPrice);
		holder.askPrice.setText(askPrice);
		
		/*
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
		*/
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = super.newView(context, cursor, parent);
		ViewHolder holder = new ViewHolder();
		holder.pairCode = (TextView) view.findViewById(R.id.list_name);
		holder.bidPrice = (TextView) view.findViewById(R.id.list_bid);
		holder.askPrice = (TextView) view
				.findViewById(R.id.list_ask);

		view.setTag(holder);
		return view;
	}
	
	class ViewHolder {
		TextView pairCode;
		TextView bidPrice;
		TextView askPrice;
	}
}