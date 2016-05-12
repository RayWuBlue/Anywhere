package com.ray.anywhere.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import android.os.Environment;
import android.util.Log;

public class FileUtils {
        private String SDPATH;       
        private int FILESIZE = 4 * 1024; 
        
        public String getSDPATH(){
                return SDPATH;
        }        
        public FileUtils(){
            //�õ���ǰ�ⲿ�洢�豸��Ŀ¼( /SDCARD )
            SDPATH = Environment.getExternalStorageDirectory() + "/";
        }
        
        /**
         * ��SD���ϴ����ļ�
         * @param fileName
         * @return
         * @throws IOException
         */
        public File createSDFile(String fileName) throws IOException{
                File file = new File(SDPATH + fileName);
                file.createNewFile();
                return file;
        }
        
        /**
         * ��SD���ϴ���Ŀ¼
         * @param dirName
         * @return
         */
        public File createSDDir(String dirName){
                File dir = new File(SDPATH + dirName);
                dir.mkdir();
                return dir;
        }
        
        /**
         * ����·��
         * @param dirPath
         * @return
         */
        public static File createPath(String dirPath){
        	File dir = new File(dirPath);
        	if(!dir.exists()){
        		dir.mkdir();
        	}
            return dir;
        }
        
        /**
         * �ж�SD���ϵ��ļ����Ƿ����
         * @param fileName
         * @return
         */
        public boolean isFileExist(String fileName){
                File file = new File(SDPATH + fileName);
                return file.exists();
        }
        
        /**
         * ��һ��InputStream���������д�뵽SD����
         * @param path
         * @param fileName
         * @param input
         * @return
         */
        public File write2SDFromInput(String path,String fileName,InputStream input){
                File file = null;
                OutputStream output = null;
                try {
                        createSDDir(path);
                        file = createSDFile(path + fileName);
                        output = new FileOutputStream(file);
                        byte[] buffer = new byte[FILESIZE];
                        while((input.read(buffer)) != -1){
                                output.write(buffer);
                        }
                        output.flush();
                } 
                catch (Exception e) {
                        e.printStackTrace();
                }
                finally{
                        try {
                                output.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                return file;
        }
        
        public static String readInStream(FileInputStream inStream){
        	
            try {

                   ByteArrayOutputStream outStream = new ByteArrayOutputStream();

                   byte[] buffer = new byte[1024];

                   int length = -1;

                   while((length = inStream.read(buffer)) != -1 ){

                          outStream.write(buffer, 0, length); //ÿ�ζ�ȡ�����������ڴ�д��

                   }

                   outStream.close();

                   inStream.close();

                   return outStream.toString();

            } catch (IOException e) {
            	
            	e.printStackTrace();
            	
            }

            return null;

     }
        //�������ͼƬ�ļ���
        public static String createImgFileName(){
        	Random rd=new Random();
        	int n=rd.nextInt(1000);
        	String temp= System.currentTimeMillis()+n+"";
        	return new Sha1Util().getDigestOfString(temp.getBytes())+".jpg";
        }

}
