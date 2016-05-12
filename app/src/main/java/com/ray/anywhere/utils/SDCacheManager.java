package com.ray.anywhere.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class SDCacheManager {

	public LinkedHashMap<String, File> cache;
	// ����
	private int maxSize;
	private int curSize;

	public SDCacheManager(int maxSize) {
		cache = new LinkedHashMap<String, File>();
		this.maxSize = maxSize;
	}

	/**
	 * ����KEY��Ӧ��Value
	 * 
	 * @param k
	 *            Key
	 * @return value
	 */
	public File get(String k) {
		cache.put(k, cache.remove(k));
		return cache.get(k);
	}

	/**
	 * 
	 * @param k
	 *            key
	 * @return �Ƿ������KEY
	 */
	public boolean contains(String k) {
		return cache.containsKey(k);
	}

	public void add(String k, File v) {
		cache.put(k, v);
		curSize += v.length();
		// ��ǰ�ļ���С�����趨���ֵ ɾ�����δ�����ʵ�
		while (curSize > maxSize) {
			String fileName = cache.keySet().iterator().next();
			File f = cache.get(fileName);
			curSize -= f.length();
			cache.remove(fileName);
			Log.v("Test", "Deleteing..." + f.getName());
			f.delete();
		}
	}

	/**
	 * ������SD����
	 * 
	 * @param k
	 *            �ļ�����
	 * @param dw
	 *            ͼ��
	 */
	public void saveToSdCard(String filePath, String fileName, Drawable dw) {
		File fpath = new File(filePath);
		if (!fpath.exists()) {
			fpath.mkdirs();
		}
		File f = new File(fpath.getAbsolutePath() + "/" + fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(f);
				Bitmap bitmap = drawableToBitmap(dw);
				bitmap.compress(CompressFormat.JPEG, 100, fos);
				add(fileName, f);
				fos.close();
				bitmap.recycle();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public InputStream getInputStream(String filePath, String fileName) {
		File f = new File(filePath + "/" + fileName);
		if (f.exists()) {
			InputStream is = null;
			try {
				is = new FileInputStream(f);
				return is;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * ���ͼƬ����
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public Drawable getDrawable(String filePath, String fileName) {
		File f = new File(filePath + "/" + fileName);
		if (f.exists()) {
			return Drawable.createFromPath(f.getAbsolutePath());
		}
		return null;
	}

	public Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
