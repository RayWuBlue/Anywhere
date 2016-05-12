package com.ray.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.VersionHelper;
import com.ray.anywhere.utils.T;

public class Setting extends BaseActivity {
	
	private RelativeLayout versionLayout,about,suggest;
	private TextView version;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_setting);
		setTitle("����");
		
		version=(TextView) super.findViewById(R.id.setting_check_version);
		version.setText(VersionHelper.getVerName(this));
		
		versionLayout=(RelativeLayout) super.findViewById(R.id.setting_check_version_layout);
		versionLayout.setOnClickListener(new LayoutOnclick());
		
		about=(RelativeLayout) super.findViewById(R.id.setting_check_about_layout);
		about.setOnClickListener(new LayoutOnclick());
		
		suggest=(RelativeLayout) super.findViewById(R.id.setting_check_suggest_layout);
		suggest.setOnClickListener(new LayoutOnclick());
	}

	
	public class LayoutOnclick implements OnClickListener{
		public void onClick(View v) {
			if(v==versionLayout){
				VersionHelper vh=new VersionHelper(Setting.this);
				if(vh.checkUpdate()){
					vh.updateTip();
				}else{
					T.showShort(Setting.this, "�Ѿ������°汾");
				}
			}else if(v==about){
				Intent it=new Intent(Setting.this,About.class);
				startActivity(it);
			}
		}
		
	}


}
