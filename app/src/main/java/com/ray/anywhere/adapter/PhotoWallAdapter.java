package com.ray.anywhere.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.ray.anywhere.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * GridView���������������첽������������ͼƬչʾ����Ƭǽ�ϡ�
 * 
 * @author guolin
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class PhotoWallAdapter extends ArrayAdapter<String> implements OnScrollListener {

	/**
	 * ��¼�����������ػ�ȴ����ص�����
	 */
	private Set<BitmapWorkerTask> taskCollection;

	/**
	 * ͼƬ���漼���ĺ����࣬���ڻ����������غõ�ͼƬ���ڳ����ڴ�ﵽ�趨ֵʱ�Ὣ�������ʹ�õ�ͼƬ�Ƴ�����
	 */
	private LruCache<String, Bitmap> mMemoryCache;

	/**
	 * GridView��ʵ��
	 */
	private GridView mPhotoWall;

	/**
	 * ��һ�ſɼ�ͼƬ���±�
	 */
	private int mFirstVisibleItem;

	/**
	 * һ���ж�����ͼƬ�ɼ�
	 */
	private int mVisibleItemCount;
	
	private String[] imgArr;

	/**
	 * ��¼�Ƿ�մ򿪳������ڽ��������򲻹�����Ļ����������ͼƬ�����⡣
	 */
	private boolean isFirstEnter = true;

	public PhotoWallAdapter(Context context, int textViewResourceId, String[] objects,
			GridView photoWall) {
		super(context, textViewResourceId, objects);
		imgArr=objects;
		mPhotoWall = photoWall;
		taskCollection = new HashSet<BitmapWorkerTask>();
		// ��ȡӦ�ó����������ڴ�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// ����ͼƬ�����СΪ�����������ڴ��1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
		mPhotoWall.setOnScrollListener(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String url = getItem(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.item_img_gv, null);
		} else {
			view = convertView;
		}
		final ImageView photo = (ImageView) view.findViewById(R.id.gv_item_img);
		// ��ImageView����һ��Tag����֤�첽����ͼƬʱ��������
		photo.setTag(url);
		setImageView(url, photo);
		return view;
	}

	/**
	 * ��ImageView����ͼƬ�����ȴ�LruCache��ȡ��ͼƬ�Ļ��棬���õ�ImageView�ϡ����LruCache��û�и�ͼƬ�Ļ��棬
	 * �͸�ImageView����һ��Ĭ��ͼƬ��
	 * 
	 * @param imageUrl
	 *            ͼƬ��URL��ַ��������ΪLruCache�ļ���
	 * @param imageView
	 *            ������ʾͼƬ�Ŀؼ���
	 */
	private void setImageView(String imageUrl, ImageView imageView) {
		Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.empty_photo);
		}
	}

	/**
	 * ��һ��ͼƬ�洢��LruCache�С�
	 * 
	 * @param key
	 *            LruCache�ļ������ﴫ��ͼƬ��URL��ַ��
	 * @param bitmap
	 *            LruCache�ļ������ﴫ������������ص�Bitmap����
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * ��LruCache�л�ȡһ��ͼƬ����������ھͷ���null��
	 * 
	 * @param key
	 * LruCache�ļ������ﴫ��ͼƬ��URL��ַ��
	 * @return
	 * ��Ӧ�������Bitmap����,����null
	 */
	@SuppressLint("NewApi")
	public Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// ����GridView��ֹʱ��ȥ����ͼƬ��GridView����ʱȡ�������������ص�����
		if (scrollState == SCROLL_STATE_IDLE) {
			loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
		} else {
			cancelAllTasks();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		mFirstVisibleItem = firstVisibleItem;
		mVisibleItemCount = visibleItemCount;
		// ���ص�����Ӧ����onScrollStateChanged����ã����״ν������ʱonScrollStateChanged��������ã�
		// ���������Ϊ�״ν����������������
		if (isFirstEnter && visibleItemCount > 0) {
			loadBitmaps(firstVisibleItem, visibleItemCount);
			isFirstEnter = false;
		}
	}

	/**
	 * ����Bitmap���󡣴˷�������LruCache�м��������Ļ�пɼ���ImageView��Bitmap����
	 * ��������κ�һ��ImageView��Bitmap�����ڻ����У��ͻῪ���첽�߳�ȥ����ͼƬ��
	 * 
	 * @param firstVisibleItem
	 *            ��һ���ɼ���ImageView���±�
	 * @param visibleItemCount
	 *            ��Ļ���ܹ��ɼ���Ԫ����
	 */
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
		try {
			for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
				String imageUrl = imgArr[i];
				Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
				if (bitmap == null) {
					BitmapWorkerTask task = new BitmapWorkerTask();
					taskCollection.add(task);
					task.execute(imageUrl);
				} else {
					ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
					if (imageView != null && bitmap != null) {
						imageView.setImageBitmap(bitmap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ȡ�������������ػ�ȴ����ص�����
	 */
	public void cancelAllTasks() {
		if (taskCollection != null) {
			for (BitmapWorkerTask task : taskCollection) {
				task.cancel(false);
			}
		}
	}

	/**
	 * �첽����ͼƬ������
	 * 
	 * @author guolin
	 */
	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

		/**
		 * ͼƬ��URL��ַ
		 */
		private String imageUrl;

		@Override
		protected Bitmap doInBackground(String... params) {
			imageUrl = params[0];
			// �ں�̨��ʼ����ͼƬ
			Bitmap bitmap = downloadBitmap(params[0]);
			if (bitmap != null) {
				// ͼƬ������ɺ󻺴浽LrcCache��
				addBitmapToMemoryCache(params[0], bitmap);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			// ����Tag�ҵ���Ӧ��ImageView�ؼ��������غõ�ͼƬ��ʾ������
			ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
			if (imageView != null && bitmap != null) {
				imageView.setImageBitmap(bitmap);
			}
			taskCollection.remove(this);
		}

		/**
		 * ����HTTP���󣬲���ȡBitmap����
		 * 
		 * @param imageUrl
		 *            ͼƬ��URL��ַ
		 * @return �������Bitmap����
		 */
		private Bitmap downloadBitmap(String imageUrl) {
			Bitmap bitmap = null;
			HttpURLConnection con = null;
			try {
				URL url = new URL(imageUrl);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5 * 1000);
				con.setReadTimeout(10 * 1000);
				con.setDoInput(true);
				con.setDoOutput(true);
				bitmap = BitmapFactory.decodeStream(con.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					con.disconnect();
				}
			}
			return bitmap;
		}

	}

}
