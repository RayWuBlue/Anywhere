package com.ray.anywhere.config;

import android.os.Environment;

public class PathConfig {
	
	//sd��·��
	public static final String BASEPATH=Environment.getExternalStorageDirectory()
			.getPath() + "/yuol";
	
	//����ͷ��
	public static final String HEADPATH=BASEPATH+"/data/head/";
	
	//�������ص�ͼƬ
   public static final String SavePATH=PathConfig.BASEPATH + "/" + "photo" + "/";
   
   //ͼƬ�����ַ
   public static final String CacheImgPATH=PathConfig.BASEPATH + "/data/imgCache";
   
   //���ݻ����ַ
   public static final String CacheDataPATH=PathConfig.BASEPATH + "/data/dataCache";
   
   //��ʱ�ļ���
   public static final String TempPATH=PathConfig.BASEPATH + "/data/temp";
   
   //�����ļ���
   public static final String DownPATH=PathConfig.BASEPATH + "/DownLoad";
   
   // ���sd�Ƿ����
   public static boolean checkSD(){
	   String sdStatus = Environment.getExternalStorageState();
       if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { 
             return true;
       }
	   return false;
   }
   
   
   
   
}
