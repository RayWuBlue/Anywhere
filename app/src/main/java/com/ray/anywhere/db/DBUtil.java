package com.ray.anywhere.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil {
	private static DBUtil mInstance;
	private Context mContext;
	private SQLHelper mSQLHelp;
	private SQLiteDatabase mSQLiteDatabase;

	private DBUtil(Context context) {
		mContext = context;
		mSQLHelp = new SQLHelper(context);
		mSQLiteDatabase = mSQLHelp.getWritableDatabase();
	}
	/**
	 * ��ʼ�����ݿ����DBUtil��
	 */
	public static DBUtil getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBUtil(context);
		}
		return mInstance;
	}
	/**
	 * �ر����ݿ�
	 */
	public void close() {
		mSQLHelp.close();
		mSQLHelp = null;
		mSQLiteDatabase.close();
		mSQLiteDatabase = null;
		mInstance = null;
	}

	/**
	 * �������
	 */
	public long insertData(String tableName,ContentValues values) {
		return mSQLiteDatabase.insert(tableName, null, values);
	}

	/**
	 * ��������
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 */
	public void updateData(String tableName,ContentValues values, String whereClause,
			String[] whereArgs) {mSQLiteDatabase.update(tableName, values, whereClause,whereArgs);
	}

	/**
	 * ɾ������
	 * 
	 * @param whereClause
	 * @param whereArgs
	 */
	public void deleteData(String tableName,String whereClause, String[] whereArgs) {
		mSQLiteDatabase
				.delete(tableName, whereClause, whereArgs);
	}

	/**
	 * ��ѯ����
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor selectData(String tableName,String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		Cursor cursor = mSQLiteDatabase.query(tableName,columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor;
	}
	
	public Cursor rawQuery(String sql) {
		Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
		return cursor;
	}
	public void execSQL(String sql) {
		mSQLiteDatabase.execSQL(sql);
	}
}