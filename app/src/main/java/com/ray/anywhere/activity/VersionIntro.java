package com.ray.anywhere.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ray.anywhere.R;
import com.ray.anywhere.helper.InitHelper;
import com.ray.anywhere.utils.SmartBarUtils;

import java.util.ArrayList;

public class VersionIntro extends Activity implements OnPageChangeListener,OnClickListener {
	
	public int[] images={R.drawable.versionintro_01, R.drawable.versionintro_02,
			R.drawable.versionintro_03,R.drawable.versionintro_04};
	public Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private Button startButton;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.act_versionintro);
		context = this;
		
		if(SmartBarUtils.hasSmartBar()){
			View decorView = getWindow().getDecorView();
			SmartBarUtils.hide(decorView);
		}
		
		//���������ݷ�ʽ
		//new CreateShut(this);
		
		//��ʼ������
	    new InitHelper(this).install();
		
		initView();
	}
	
	//��ʼ����ͼ
	private void initView() {
		// ʵ������ͼ�ؼ�
		viewPager = (ViewPager) findViewById(R.id.versionintro_viewpage);
		startButton = (Button) findViewById(R.id.versionintro_Button);
		startButton.setOnClickListener(this);
		indicatorLayout = (LinearLayout) findViewById(R.id.versionintro_indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length]; // ����ָʾ�������С
		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		param.setMargins(10, 0, 10, 0);
		for (int i = 0; i < images.length; i++) {
			// ѭ������ͼƬ
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			// ѭ������ָʾ��
			indicators[i] = new ImageView(context);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			indicatorLayout.addView(indicators[i],param);
		}
		//pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter); // ����������
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==startButton){
			Intent it = new Intent(context, Main.class);
			startActivity(it);
			finish();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// ��ʾ���һ��ͼƬʱ��ʾ��ť
		if (position == indicators.length - 1) {
			startButton.setVisibility(View.VISIBLE);
		} else {
			startButton.setVisibility(View.INVISIBLE);
		}
		// ����ָʾ��ͼƬ
		for (int i = 0; i < indicators.length; i++) {
			indicators[position].setBackgroundResource(R.drawable.indicators_now);
			if (position != i) {
				indicators[i]
						.setBackgroundResource(R.drawable.indicators_default);
			}
		}
	}
	
}
