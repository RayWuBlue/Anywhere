package com.ray.anywhere.widgets;



import com.ray.anywhere.R;
import com.ray.anywhere.activity.PhotoView;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Gallery;

/**
 * android.widget.Gallery���Ӻ�����
 */
public class QGallery extends Gallery {
	/**
	 * GestureDetector��
	 * ��onTouch()�����У����ǵ���GestureDetector��onTouchEvent()������
	 * ����׽����MotionEvent����GestureDetector  
	 * �������Ƿ��к��ʵ�callback�����������û�������  
	 */
	public Context context;
	private GestureDetector gestureScanner; 
	private QImageView imageView;
	private boolean isScroll = false;

	public QGallery(Context context) {
		super(context);
		this.context = context;

	}

	public QGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public QGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		gestureScanner = new GestureDetector(new MySimpleGesture());
		this.setOnTouchListener(new OnTouchListener() {

			float baseValue;
			float originalScale;

			//��дonTouch����ʵ������
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				View view = QGallery.this.getSelectedView();
				view=view.findViewById(R.id.item_photoview_img);
				if (view instanceof QImageView) {
					imageView = (QImageView) view;

					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						baseValue = 0;
						originalScale = imageView.getScale();
					}
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						//����˫ָ�϶�
						if (event.getPointerCount() == 2) {
							isScroll = false;
							float x = event.getX(0) - event.getX(1);
							float y = event.getY(0) - event.getY(1);
							float value = (float) Math.sqrt(x * x + y * y);// ��������ľ���
							if (baseValue == 0) {
								baseValue = value;
							} else {
								float scale = value / baseValue;// ��ǰ�����ľ��������ָ����ʱ�����ľ��������Ҫ���ŵı�����
								imageView.zoomTo(originalScale * scale);								
							}
						}
					}
					if(event.getAction() == MotionEvent.ACTION_UP){
						float ScaleRate = imageView.getScaleRate();
						if(imageView.getScale() < ScaleRate){ //���Żط�
							imageView.zoomTo(ScaleRate, event.getX(0), event.getY(0),500);								
							imageView.layoutToCenter();
						}
						isScroll = true;
					}
				}
				return false;
			}

		});
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		View view = QGallery.this.getSelectedView();
		view=view.findViewById(R.id.item_photoview_img);
		if (view instanceof QImageView) {
			imageView = (QImageView) view;

			float v[] = new float[9];
			Matrix m = imageView.getImageMatrix();
			m.getValues(v);
			// ͼƬʵʱ��������������
			float left, right;
			// ͼƬ��ʵʱ����
			float width, height;
			width = imageView.getScale() * imageView.getImageWidth();
			height = imageView.getScale() * imageView.getImageHeight();
			// �����߼�Ϊ�ƶ�ͼƬ�ͻ���gallery�������߼���
			if ((int) width <= PhotoView.screenWidth && (int) height <= PhotoView.screenHeight)// ���ͼƬ��ǰ��С<��Ļ��С��ֱ�Ӵ������¼�
			{
				if(isScroll)
					super.onScroll(e1, e2, distanceX, distanceY);
			} else {
				left = v[Matrix.MTRANS_X];
				right = left + width;	
				
				if (distanceX > 0)// �������󻬶�����һ��ͼ
				{
					if (right <= PhotoView.screenWidth) {
						super.onScroll(e1, e2, distanceX, distanceY);
					}else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				} else if (distanceX < 0)// �������һ�������һ��ͼ
				{
					if (left >= 0) {
						super.onScroll(e1, e2, distanceX, distanceY);
					} else {
						imageView.postTranslate(-distanceX, -distanceY);
					}
				}
			}

		} else {
			super.onScroll(e1, e2, distanceX, distanceY);
		}
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//		int position = MyGallery.this.getSelectedItemPosition();
//		int minV = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();	
//         
//		if(velocityX > minV){			
//			if(position > 0)
//				MyGallery.this.setSelection(position-1);
//		}else if(velocityX < -minV){
//			if(position < MyGallery.this.getCount()-1)
//				MyGallery.this.setSelection(position+1);
//		}
		if (e2.getX() > e1.getX()) {
			// ����߻���
			super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
		} else {
			// ���ұ߻���
			super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureScanner.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// �ж����±߽��Ƿ�Խ��
			View view = QGallery.this.getSelectedView();
			view=view.findViewById(R.id.item_photoview_img);
			if (view instanceof QImageView) {
				imageView = (QImageView) view;
				float width = imageView.getScale() * imageView.getImageWidth();
				float height = imageView.getScale() * imageView.getImageHeight();
				if ((int) width <= PhotoView.screenWidth && (int) height <= PhotoView.screenHeight)// ���ͼƬ��ǰ��С<��Ļ��С���жϱ߽�
				{
					break;
				}
				float v[] = new float[9];
				Matrix m = imageView.getImageMatrix();
				m.getValues(v);
				float top = v[Matrix.MTRANS_Y];
				float bottom = top + height;
				if (top > 0) {
					imageView.postTranslateDur(-top, 200f);
				}
				if (bottom < PhotoView.screenHeight) {
					imageView.postTranslateDur(PhotoView.screenHeight - bottom, 200f);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	private class MySimpleGesture extends SimpleOnGestureListener {
		// �����µĵڶ���Touch downʱ����
		public boolean onDoubleTap(MotionEvent e) {
			View view = QGallery.this.getSelectedView();
			view=view.findViewById(R.id.item_photoview_img);
			if (view instanceof QImageView) {
				imageView = (QImageView) view;
				if (imageView.getScale() > imageView.getScaleRate()) {
					imageView.zoomTo(imageView.getScaleRate(), PhotoView.screenWidth / 2, PhotoView.screenHeight / 2, 200f);
					// imageView.layoutToCenter();
				} else {
					imageView.zoomTo(1.0f, PhotoView.screenWidth / 2, PhotoView.screenHeight / 2, 200f);
				}
			} else {
			}
			return true;
		}
	}
}
