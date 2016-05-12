package com.ray.anywhere.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.ray.anywhere.config.PathConfig;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureUtil {

	/**
	 * ����ͼƬ������ֵ
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	
	/**
	 * ����·�����ͼƬ��ѹ������bitmap
	 * 
	 * @param filePath	
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, 720,1280);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}
	
	//ѹ��Ϊ���浽������
	public static String saveSmallPic(String filePath){
		String newPath=PathConfig.TempPATH;
		File filePathTemp=new File(newPath);
		if(!filePathTemp.exists()){
			filePathTemp.mkdirs();
		}
		String newFilePath=newPath+"/"+TimeUtil.getCurrentTime()+".jpg";
		Bitmap bm = PictureUtil.getSmallBitmap(filePath);	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(newFilePath));
			int options = 100;  
			// �������80kb���ٴ�ѹ��,���ѹ������
			while (baos.toByteArray().length / 1024 > 80 && options != 10) { 
				// ���baos
				baos.reset();  
				// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
				bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
	            options -= 30;
			}
			fos.write(baos.toByteArray());
			fos.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return newFilePath;
	}

	
}
