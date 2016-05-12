package com.ray.anywhere.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.ray.anywhere.R;
import com.ray.anywhere.activity.Main;
import com.ray.anywhere.config.PathConfig;
import com.ray.anywhere.helper.VersionHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateService extends Service {
	
	private static final int NOTIFY_ID = 0;
	private int progress=0;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private String apkUrl = "";
	private static final String savePath = PathConfig.BASEPATH+"/updateApk/";
	private static final String saveFileName = savePath + "V.apk";
	private Context mContext;
	
	private int lastRate = 0;
	private boolean canceled=false;
	
	private VersionHelper version;

	@Override
	public IBinder onBind(Intent it) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		version=new VersionHelper(this);
		apkUrl=version.getLoadUrl();
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		setUpNotification();
		new AsyncUpdate().execute("");
		
	}

	/**
	 * ����֪ͨ
	 */
	private void setUpNotification() {
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText ="����У԰ͨ��ʼ����";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		// ������"��������"��Ŀ��
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		

		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.part_notify_update);
		contentView.setTextViewText(R.id.name, "����У԰ͨ��������...");
		// ָ�����Ի���ͼ
		mNotification.contentView = contentView;

		Intent intent = new Intent(this, Main.class);
		// ���������� �ڰ�home�󣬵��֪ͨ��������֮ǰactivity ״̬;
		// ����������Ļ�������service���ں�̨���أ� �ڵ������ͼƬ���½������ʱ��ֱ�ӵ����ؽ��棬�൱�ڰѳ���MAIN ��ڸ��� - -
		// ����ô���ô������
		// intent.setAction(Intent.ACTION_MAIN);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// ָ��������ͼ
		mNotification.contentIntent = contentIntent;	  
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}
	
	/**
	 * ��װAPK
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		//version.clear();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
	
	class AsyncUpdate extends AsyncTask<String,Integer,String>{
		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];
				do{
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// ���½���
					if (progress >= lastRate + 1) {
						publishProgress(progress);
						lastRate = progress;
					}
					if (numread <= 0) {
						// �������֪ͨ��װ
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				}while(!canceled);
				
				fos.close();
				is.close();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mNotificationManager.cancel(NOTIFY_ID);
			installApk();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int rate = values[0];
			if (rate < 100) {
				RemoteViews contentview = mNotification.contentView;
				contentview.setTextViewText(R.id.tv_progress, rate + "%");
				contentview.setProgressBar(R.id.progressbar, 100, rate,false);
			} else {
				// ������Ϻ�任֪ͨ��ʽ
				mNotification.flags = Notification.FLAG_AUTO_CANCEL;
				mNotification.contentView = null;
				Intent intent = new Intent(mContext,
						Main.class);
				// ��֪�����
				intent.putExtra("completed", "yes");
				// ���²���,ע��flagsҪʹ��FLAG_UPDATE_CURRENT
				PendingIntent contentIntent = PendingIntent.getActivity(
						mContext, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);

				stopSelf();// ͣ����������
			}
			mNotificationManager.notify(NOTIFY_ID, mNotification);
			super.onProgressUpdate(values);
		}
		
		
	}

}
