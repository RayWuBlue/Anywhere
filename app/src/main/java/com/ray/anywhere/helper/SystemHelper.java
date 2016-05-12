package com.ray.anywhere.helper;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class SystemHelper {
	/**
	 * ��ȡ��Ļ�Ŀ�
	 * @return
	 */
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}
	
	/**
	 * ��ȡ��Ļ�ĸ�
	 * @return
	 */
	
	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getHeight();
	}
	
	/**
	 *  ����ֻ����ں��루IMEI��
	 * @param context
	 * @return
	 */
	public static String getTelephoneIMEI(Context context) {
        TelephonyManager telMg = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telMg.getDeviceId();
    }
	
	/**
	 * ��ȡ�ֻ�����
	 * @param context
	 * @return
	 */
	public static String getTelNum(Context context){
		TelephonyManager telMg = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telMg.getLine1Number();
		
	}
	
	/**
	 * ��ȡ�ֻ�IP��ַ
	 * @return
	 */
	public static  String getLocalIpAddress() {
        try{
            for (Enumeration<NetworkInterface> en = NetworkInterface
                            .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                                .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                                return inetAddress.getHostAddress().toString();
                        }
                }
            }
        } catch (SocketException ex) {
           
        }
        return "error";
	}
	
	/**
	 * ��ȡ�ֻ�Ʒ�ƺ��ͺ�
	 * @return
	 */
	public static String getPhoneInfo(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String mtyb = android.os.Build.BRAND;// �ֻ�Ʒ��
		String mtype = android.os.Build.MODEL; // �ֻ��ͺ�
		String imsi = tm.getSubscriberId();   //�ֻ����ں��루IMEI��
		return mtyb+"(type="+mtype+",imsi="+imsi+")";
		
	}
	
	/** 
	 * �����ֻ��ķֱ��ʴ� PX(����) �ĵ�λ ת��Ϊ DP
	 */
	 public static int dip2px(Context context, float dpValue) {        
	    	final float scale = context.getResources().getDisplayMetrics().density;        
	    	return (int) (dpValue * scale + 0.5f);} 
	 public static int px2dip(Context context, float pxValue) {        
	    	final float scale = context.getResources().getDisplayMetrics().density;        
	    	return (int) (pxValue / scale + 0.5f);}

}
