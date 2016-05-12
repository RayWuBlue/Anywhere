package com.ray.anywhere.app;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;

import com.ray.anywhere.config.ServerConfig;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.SystemHelper;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.UploadUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Looper;




public class CrashHandler implements UncaughtExceptionHandler {
	
	private Thread.UncaughtExceptionHandler mDefaultHandler;// ϵͳĬ�ϵ�UncaughtException������
	private static CrashHandler INSTANCE;// CrashHandlerʵ��
	private Context mContext;// �����Context����
	private LoginHelper lh;

	
	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private CrashHandler() {
		
	}

	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public static CrashHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CrashHandler();
		return INSTANCE;
	}
	
	/**
	 * ��ʼ��
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		lh=new LoginHelper(context);
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// ��ȡϵͳĬ�ϵ�UncaughtException������
		Thread.setDefaultUncaughtExceptionHandler(this);// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
	}
	
	
	/**
	 * ��UncaughtException����ʱ��ת�����д�ķ���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// ����Զ����û�д�������ϵͳĬ�ϵ��쳣������������
			mDefaultHandler.uncaughtException(thread, ex);
		}else{
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			 //�˳�����  
	         android.os.Process.killProcess(android.os.Process.myPid());  
	         System.exit(1);  
		}
	}
	
	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����.
	 * 
	 * @param ex �쳣��Ϣ
	 * @return true ��������˸��쳣��Ϣ;���򷵻�false.
	 */
	public boolean handleException(Throwable ex) {
		if (ex == null || mContext == null)
			return false;
		final String crashReport = getCrashReport(mContext, ex);
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				File file = save2File(crashReport);
				UploadUtil.uploadFile(file, ServerConfig.HOST+"/schoolknow/log.php");
				T.showLong(mContext,"�ܱ�Ǹ,��������쳣,��������");
				Looper.loop();
			}

		}.start();
		return true;
	}
	
	@SuppressLint("SimpleDateFormat")
	private File save2File(String crashReport) {
		String fileName = "crash" +System.currentTimeMillis()+ ".log";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + "schoolknow"+File.separator+"data"+
						File.separator+"log");
				if (!dir.exists())
					dir.mkdir();
				File file = new File(dir, fileName);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(crashReport.toString().getBytes());
				fos.close();
				return file;
			} catch (Exception e) {
				
			}
		}
		return null;
	}

	/**
	 * ��ȡAPP�����쳣����
	 * 
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Context context, Throwable ex) {
		PackageInfo pinfo = getPackageInfo(context);
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: " + pinfo.versionName + "("
				+ pinfo.versionCode + ")\n");
		exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
				+ "(" + android.os.Build.MODEL + ")\n");
		exceptionStr.append("User: " + lh.getUid()
				+ "(ip=" + SystemHelper.getLocalIpAddress() + ",Num="+SystemHelper.getTelNum(context)+")\n");
		exceptionStr.append("Phone:" + SystemHelper.getPhoneInfo(context)+"\n");
		exceptionStr.append("Exception: " + ex.getMessage() + "\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString() + "\n");
		}
		return exceptionStr.toString();
	}

	/**
	 * ��ȡApp��װ����Ϣ
	 * 
	 * @return
	 */
	private PackageInfo getPackageInfo(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
	
	
}
