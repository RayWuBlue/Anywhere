package com.ray.anywhere.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.adapter.NewsFragmentPagerAdapter;
import com.ray.anywhere.bean.NewsChannelItem;
import com.ray.anywhere.fragment.EventNewsFragment;
import com.ray.anywhere.fragment.SmileNewsFragment;
import com.ray.anywhere.fragment.VideoNewsFragment;
import com.ray.anywhere.utils.Api;

public class SmileNews extends FragmentActivity {
	private View indicator_line;
	private int screenWidth;
	private int pageIndex = 0;
	private ViewPager mViewPager;
	private List<View> indicators;
	/** �û�ѡ������ŷ����б� */
	private ArrayList<NewsChannelItem> SmileNewsChannelList = new ArrayList<NewsChannelItem>();

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private LinearLayout indicator_ll;
	private final int COL_NUM = 3;
	
	private String[] columns = new String[]{"�Ķ�","����","ԭ��"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smile_news);
		initView();
	}

	private void initIndicator() {
		screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		indicator_line = findViewById(R.id.indicator_line);
		indicator_ll = (LinearLayout)findViewById(R.id.indicator_ll);
		LayoutParams indicator_line_lp = (LayoutParams) indicator_line.getLayoutParams();
		indicator_line_lp.width = screenWidth/COL_NUM;
		indicator_line.setLayoutParams(indicator_line_lp);
		
		indicators = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		for(int i=0;i<COL_NUM;i++){
			final int index = i;
			View indicator = inflater.inflate(R.layout.item_indicator, null);
			LayoutParams lp = new LayoutParams(indicator_line_lp.width,LayoutParams.MATCH_PARENT);
			indicator.setLayoutParams(lp);
			indicator.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(pageIndex!=index)
					mViewPager.setCurrentItem(index);
				}
			});
			TextView col_text = (TextView) indicator.findViewById(R.id.indicator_text);
			col_text.setText(columns[index]);
			indicator_ll.addView(indicator);
			indicators.add(indicator);
		}
		
		resetIndicatorTextViewColor();
		
		TextView col_text = (TextView)(indicators.get(0).findViewById(R.id.indicator_text));
		col_text.setTextColor(getResources().getColor(R.color.blue));
	}

	private void resetIndicatorTextViewColor()
	{
		for(int i=0;i<COL_NUM;i++){
			TextView col_text = (TextView)(indicators.get(i).findViewById(R.id.indicator_text));
			col_text.setTextColor(getResources().getColor(R.color.text_gray));
		}
	}
	
	/** ��ʼ��layout�ؼ� */
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		initFragment();
		initIndicator();
	}
	/**
	 * ��ʼ��Fragment
	 * */
	private void initFragment() {
		SmileNewsChannelList = (ArrayList<NewsChannelItem>)(Api.News.getSmileNewsChannels());
		fragments.clear();// ���
		int count = SmileNewsChannelList.size();
		//for (int i = 0; i < count-1; i++) {
		{
			Bundle data1 = new Bundle();
			data1.putString("text", SmileNewsChannelList.get(0).getName());
			data1.putInt("id", SmileNewsChannelList.get(0).getId());
			SmileNewsFragment newfragment1 = new SmileNewsFragment();
			newfragment1.setArguments(data1);
			fragments.add(newfragment1);
		}

		{
			//Bundle data3 = new Bundle();
			//data3.putString("text", SmileNewsChannelList.get(2).getName());
			//data3.putInt("id", 47);
			
			EventNewsFragment newfragment3 = new EventNewsFragment();
			//newfragment3.setArguments(data3);
			fragments.add(newfragment3);
		}
		{
			Bundle data2 = new Bundle();
			data2.putString("text", SmileNewsChannelList.get(2).getName());
			data2.putInt("id", SmileNewsChannelList.get(2).getId());
			VideoNewsFragment newfragment = new VideoNewsFragment();
			newfragment.setArguments(data2);
			fragments.add(newfragment);
		}
		NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);

		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
	}
	/**
	 * ViewPager�л���������
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int position, float screenOffset, int offsetPx) {
			LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) indicator_line.getLayoutParams();
			//if(pageIndex == 0 && position == 0)
				lp.leftMargin = (int) ((screenOffset+(pageIndex>position?-1:0)+pageIndex)*(screenWidth/3));
			//else if(pageIndex == 1 && position == 0)
				//lp.leftMargin = (int) ((screenOffset-1+pageIndex)*(screenWidth/3));
			
			indicator_line.setLayoutParams(lp);
		}

		@Override
		public void onPageSelected(int position) {
			resetIndicatorTextViewColor();
			TextView col_text = (TextView)(indicators.get(position).findViewById(R.id.indicator_text));
			col_text.setTextColor(getResources().getColor(R.color.blue));
			mViewPager.setCurrentItem(position);
			pageIndex = position;
		}
	};
}
