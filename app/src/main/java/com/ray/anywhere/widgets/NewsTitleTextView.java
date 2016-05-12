package com.ray.anywhere.widgets;

import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;

public class NewsTitleTextView extends TextView {
	private boolean isVerticalLine = false;
	private boolean isHorizontaline = false;
	private int verticalLineColor = Color.LTGRAY;
	private int horizontalineColor = Color.RED;
	private float screen_density;
	
	public NewsTitleTextView(Context context) {
		super(context);
		init(context);
	}

	public NewsTitleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NewsTitleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(Context context) {
		this.setGravity(Gravity.CENTER);
		this.setTextColor(Color.BLACK);
		this.setBackgroundColor(Color.WHITE);
	
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		this.screen_density = metrics.density;
	}

	/**
	 * ���ÿؼ��ײ��Ƿ���Ҫ����
	 * @param is
	 */
	public void setIsHorizontaline(boolean is) {
		this.isHorizontaline = is;
		invalidate();
	}

	/**
	 * ���ÿؼ�����Ƿ���Ҫ����
	 * @param is
	 */
	public void setIsVerticalLine(boolean is) {
		this.isVerticalLine = is;
	}

	/**
	 * ���ú�����ɫ
	 * @param color ��ɫ��Դ
	 */
	public void setHorizontalineColor(int color) {
		this.horizontalineColor = color;
	}

	/**
	 * ����������ɫ
	 * @param color ��ɫ��Դ
	 */
	public void setVerticalLineColor(int color) {
		this.verticalLineColor = color;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		Paint paint;
		if (isVerticalLine) {
			paint = new Paint();
			paint.setColor(verticalLineColor);
			paint.setStyle(Style.FILL);
			canvas.drawRect(0, 14, 1 * screen_density,
					this.getHeight() -14 , paint);
		}
		if (isHorizontaline) {
			paint = new Paint();
			paint.setColor(horizontalineColor);
			paint.setStyle(Style.FILL);
			 canvas.drawRect(5, getHeight()-6, getWidth() * screen_density, getHeight()
			 * screen_density, paint);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
