package com.ray.anywhere.utils;

import com.ray.anywhere.config.ServerConfig;

import android.util.Log;

/**
 * Logͳһ������
 * 
 * @author way
 * 
 */
public class L {
	
	// �Ƿ���Ҫ��ӡbug��������application��onCreate���������ʼ��
	public static boolean isDebug = ServerConfig.DEVELOP_MODE;   
	private static final String TAG = "schoolknow";

	// �����ĸ���Ĭ��tag�ĺ���
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}

	// �����Ǵ����Զ���tag�ĺ���
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}
}
