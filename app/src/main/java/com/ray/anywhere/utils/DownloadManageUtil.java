package com.ray.anywhere.utils;

import java.io.File;
import java.net.URLDecoder;

import com.ray.anywhere.helper.TipHelper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
/**
 * ������
 * @author shiquanL
 *
 */
@SuppressLint("DefaultLocale")
public class DownloadManageUtil {
	static String FilePath;
	static BroadcastReceiver receiver;
	/**
	 * 
	 * @param context �����ĳ���
	 * @param url �����ļ��ĵ�ַ
	 * @param path SD�������·�� �磺"/MyDownload",�Զ���SD�´�����Ŀ¼��
	 */
	public static void DownloadFile(Context context,String url,String path){

		/*
		 * ע��㲥�����������
		 */
		receiver = new DownloadCompleteReceiver();
		context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		/**
		 * �ȼ��SD���Ƿ����
		 */
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return;
		}
		/*
		 * �����ļ���
		 */
		String file = Environment.getExternalStorageDirectory().getPath() +path;
		File files = new File(file);
		if (files == null || !files.exists()) {
			files.mkdir();
		}
		/*
		 * ��ȡ�ļ���
		 */
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		fileName = URLDecoder.decode(fileName);
		/*
		 *ϵͳ���ط�����
		 */
		DownloadManager downManager = (DownloadManager)context.getSystemService(Activity.DOWNLOAD_SERVICE);
		DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
		down.setShowRunningNotification(true);
		//��֪ͨ����ʾ
		down.setVisibleInDownloadsUi(true);
		//���Ŀ¼
		down.setDestinationInExternalPublicDir(path+"/",fileName);
		//�ļ�·��
		FilePath = file + "/" + fileName;
		//�������ض���ִ��
		downManager.enqueue(down);
	}
	public static void unregisterReceiver(Context context){
		context.unregisterReceiver(receiver);
	};
	/**
	 * �����������
	 * @author Administrator
	 *
	 */
	public static class DownloadCompleteReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
				//��ȡ�ļ�·��
				//File files = new File(FilePath);
				//������ļ�
				//Intent openFile = getFileIntent(files);
				//context.startActivity(openFile);
				TipHelper.PlaySound(context);
				T.showLong(context, "�������,�ļ�������SD��schoolknow�ļ�����");
			}
		}
	}
	public static Intent getFileIntent(File file) {
		Uri uri = Uri.fromFile(file);
		String type = getMIMEType(file);
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, type);
		return intent;
	}
	@SuppressLint("DefaultLocale")
	private static String getMIMEType(File f){   
	      String type="";  
	      String fName=f.getName();  
	      /* ȡ����չ�� */  
	      String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();
	      
	      /* ����չ�������;���MimeType */
	      if(end.equals("pdf")){
	              type = "application/pdf";//
	      }
	      else if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||  
	      end.equals("xmf")||end.equals("ogg")||end.equals("wav")){  
	        type = "audio/*";   
	      }  
	      else if(end.equals("3gp")||end.equals("mp4")){  
	        type = "video/*";  
	      }  
	      else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||  
	      end.equals("jpeg")||end.equals("bmp")){  
	        type = "image/*";  
	      }  
	      else if(end.equals("apk")){   
	        type = "application/vnd.android.package-archive"; 
	      }
	      else{
//	              /*����޷�ֱ�Ӵ򿪣�����������б���û�ѡ�� */  
	        type="*/*";
	      }
	      return type;
	    }
}
