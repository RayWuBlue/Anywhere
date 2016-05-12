package com.ray.anywhere.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager������
 * 
 */
public class ViewPagerAdapter extends PagerAdapter {
	private List<View> list;

	public ViewPagerAdapter(Context context, List<View> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<View>();
		}
	}

	// ����positionλ�õĽ���
	@Override
	public void destroyItem(View view, int position, Object obj) {
		((ViewPager) view).removeView(list.get(position));
	}

	// ��ȡ��ǰ���������
	@Override
	public int getCount() {
		return list.size();
	}

	// ��ʼ��positionλ�õĽ���
	@Override
	public Object instantiateItem(View view, int position) {
		((ViewPager) view).addView(list.get(position));
		return list.get(position);
	}
	

	// �ж�View�Ͷ����Ƿ�Ϊͬһ��View
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

	@Override
	public void finishUpdate(View arg0) {

	}

}
