package com.ray.anywhere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ray.anywhere.Notice;
import com.ray.anywhere.R;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.VersionHelper;
import com.ray.anywhere.utils.AndroidShare;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.RoundedImageView;

public class User extends Activity {
	
	private RoundedImageView userImg;
	private TextView userName;
	public LoginHelper lh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_user);		
		userImg=(RoundedImageView)findViewById(R.id.myzone_user_img);
		
		lh=new LoginHelper(User.this);
	    userName=(TextView) findViewById(R.id.myzone_user_name);
	    if(lh.hasLogin())
	    	userName.setText(lh.getNickname());
	    else
	    	userName.setText("���ȵ�¼");
	    userImg.setOnClickListener(new userHeadClick());

	}
	@Override
	protected void onResume() {
		userName.setText(lh.getNickname());
		super.onResume();
	}

	public class userHeadClick implements OnClickListener{
		public void onClick(View v) {
			Intent it = null;
			if(lh.hasLogin())
				it = new Intent(User.this,UserInfo.class);
			else
				it = new Intent(User.this,Login.class);
			startActivity(it);
		}
	}

	public void onClick(View v){
		Intent it = null;
		switch (v.getId()) {
		case R.id.btn_about:
			it = new Intent(User.this,About.class);
			break;
		case R.id.btn_feedback:
			it = new Intent(User.this,Suggest.class);
			break;
		case R.id.btn_check_update:
			VersionHelper vh=new VersionHelper(User.this);
			if(vh.checkUpdate()){
				vh.updateTip();
			}else{
				T.showShort(User.this, "�Ѿ������°汾");
			}
			break;
		case R.id.btn_info_me:
			if(lh.hasLogin())
				it = new Intent(User.this,UserInfo.class);
			else
				it = new Intent(User.this,Login.class);
			break;
		case R.id.btn_collect:
			it = new Intent(User.this,MyCollection.class);
			break;
		case R.id.btn_notice:
			it = new Intent(User.this,Notice.class);
			break;
		case R.id.btn_intro:
			AndroidShare as = new AndroidShare(User.this,"С����ǣ�����ʹ�ó���У԰ͨ�ɣ�������","");
			as.show();
			break;
		default:
			break;
		}
		if(it!=null)
		startActivity(it);
	}

}
