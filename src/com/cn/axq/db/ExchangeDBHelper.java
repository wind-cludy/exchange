package com.cn.axq.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExchangeDBHelper {
	private static SQLiteDatabase _db;

	private static final String DATABASE_NAME = "ExchangeDB";
	public static final String TABLE_0 = "Table_0";
	public static final String EXCHANGE_ROWID = "_id";
	public static final String EXCHANGE_PAIRCODE = "pairCode";
	public static final String EXCHANGE_BID = "bid";
	public static final String EXCHANGE_ASK = "ask";
	public static final String EXCHANGE_VISIBLE = "visible";
	public static final String EXCHANGE_DATETIME = "datetime";

	private static final int DATABASE_VERSION = 1;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_TASKMANAGER);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (oldVersion == 1) {
				
			}
		}
	}

	public static final void init(Context ctx) {
		if (_db == null) {
			_db = new DatabaseHelper(ctx).getWritableDatabase();
		}
	}

	private static final String CREATE_TABLE_TASKMANAGER = "create table "
												+ TABLE_0 + " ( "
												+ EXCHANGE_ROWID + " integer primary key autoincrement, " 
												+ EXCHANGE_PAIRCODE + " text not null, "
												+ EXCHANGE_BID + " text not null, "
												+ EXCHANGE_ASK + " text not null, "
												+ EXCHANGE_VISIBLE + " text,  "
												+ EXCHANGE_DATETIME + " text not null"
												+ ");";

	public static final long insertPairRecord(String pairCode,
			String bidPrice, String askPrice /* , String visible */, String datetime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(EXCHANGE_PAIRCODE, pairCode);
		initialValues.put(EXCHANGE_BID, bidPrice);
		initialValues.put(EXCHANGE_ASK, askPrice);
//		initialValues.put(EXCHANGE_VISIBLE, visible);
		initialValues.put(EXCHANGE_DATETIME, datetime);
		return _db.insert(TABLE_0, null, initialValues);
	}

	public static final boolean deleteAllPairRecord() {
		return 1 == _db.delete(TABLE_0, null, null);
	}
	
	public static final Cursor fetchAllPairRecord() {
		/*
		if (orderBy.length() > 0) {
			if (isAsc) {
				orderBy = orderBy + " asc";
			} else {
				orderBy = orderBy + " desc";
			}
		}
		*/

		return _db.query(TABLE_0, new String[] {
				EXCHANGE_ROWID,
				EXCHANGE_PAIRCODE,
				EXCHANGE_BID, 
				EXCHANGE_ASK,
				EXCHANGE_DATETIME
				}, null, null, null, null, null);
	}
	
/*	
	public static final Cursor fetchPairById(long id) {
		Cursor cursor = _db.query(TABLE_TASKMANAGER, new String[] {
				TASKMANAGER_ROWID, TASKMANAGER_PROCESSNAME,
				TASKMANAGER_PACKAGENAME, TASKMANAGER_ICON }, TASKMANAGER_ROWID
				+ " = " + id, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

	public static final Cursor fetchAllApps(String orderBy, boolean isAsc) {
		if (orderBy.length() > 0) {
			if (isAsc) {
				orderBy = orderBy + " asc";
			} else {
				orderBy = orderBy + " desc";
			}
		}

		return _db.query(TABLE_TASKMANAGER, new String[] { TASKMANAGER_ROWID,
				TASKMANAGER_PROCESSNAME, TASKMANAGER_PACKAGENAME,
				TASKMANAGER_ICON, }, null, null, null, null, orderBy);
	}

	public static final boolean deleteAppItem(long id) {
		return 1 == _db.delete(TABLE_TASKMANAGER, TASKMANAGER_ROWID + "=?",
				new String[] { id + "" });
	}

	public static final boolean deleteAllApps() {
		return 1 == _db.delete(TABLE_TASKMANAGER, null, null);
	}

	public static final boolean checkExistInExcludeList(String packageName){
		Cursor cursor = _db.query(TABLE_TASKMANAGER, null,
				TASKMANAGER_PACKAGENAME + " = '" + packageName + "'", null,
				null, null, null, null);

		try {
			if (null == cursor || cursor.getCount() == 0) {
				return false;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return true;
	}
*/
}
