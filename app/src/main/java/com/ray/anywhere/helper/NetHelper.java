package com.ray.anywhere.helper;



import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetHelper {
	
	/**
	 * �����Ƿ�����
	 * @param context
	 * @return
	 */
	public static boolean isNetConnected(Context context) {
		boolean isNetConnected;
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			isNetConnected = true;
		} else {
			isNetConnected = false;
		}
		return isNetConnected;
	}
	
	/**
	 * �ж�WIFI�����Ƿ���õķ���
	 * @param context
	 * @return
	 */
	public static boolean isOpenWifi(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}
	
	/**
	 * ��ת����������
	 * @param context
	 */
	public static void ToNetSetting(Context context){
		Intent intent = null;
		try {
			String sdkVersion = android.os.Build.VERSION.SDK;
			if (Integer.valueOf(sdkVersion) > 10) {
				intent = new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS);
			} else {
				intent = new Intent();
				ComponentName comp = new ComponentName(
						"com.android.settings",
						"com.android.settings.WirelessSettings");
				intent.setComponent(comp);
				intent.setAction("android.intent.action.VIEW");
			}
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
