package com.ray.anywhere.base;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.ray.anywhere.R;
import com.ray.anywhere.utils.SmartBarUtils;

/**
 * ����activity
 * @author wei8888go
 *
 */

public abstract class BaseActivity extends Activity{
	
	protected BaseLayout baseLy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(SmartBarUtils.hasSmartBar()){
			View decorView = getWindow().getDecorView();
			SmartBarUtils.hide(decorView);
		}
	}
	
	/**
	 * ���ò����ļ�
	 */
	@Override
	public void setContentView(int layoutId) {
		baseLy = new BaseLayout(this, layoutId);
		setContentView(baseLy);
		baseLy.setTitleBarColor(getResources().getColor(R.color.blue));
		baseLy.setBackPressedEvent(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	/**
	 * ���ñ���
	 * @param title
	 */
	public void setTitle(String title){
		if(baseLy!=null){
			baseLy.TitleText.setText(title);
		}
	}
	public void setTitleBarColor(int color){
		baseLy.setTitleBarColor(color);
	}
	public View addButton(String text,OnClickListener event){
		return this.addButton(text,0,event);
	}
	public View addButton(int imgRes,OnClickListener event){
		return this.addButton(null,imgRes,event);
	}
	public View addButton(String text,int imgRes,OnClickListener event){
		return baseLy.addButton(text,imgRes,event);
	}
	public void setBackPressedEvent(OnClickListener onclick){
		baseLy.setOnClickListener(onclick);
	}
}
