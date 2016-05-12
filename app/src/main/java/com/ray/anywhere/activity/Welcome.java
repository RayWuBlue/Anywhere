package com.ray.anywhere.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ray.anywhere.R;
import com.ray.anywhere.helper.InitHelper;
import com.ray.anywhere.service.Init;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.DataCache;

public class Welcome extends Activity{
	
	public RelativeLayout layout;
	private static ImageView img_image;
	private static DataCache dc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.act_welcome);
		layout=(RelativeLayout) super.findViewById(R.id.welcome_bg);
		img_image = (ImageView) findViewById(R.id.hello_img);
		initHello();
      
	}

	private void redirect(){
		Animation animation= AnimationUtils.loadAnimation(this,R.anim.scale);
		img_image.startAnimation(animation);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent updateIntent = new Intent(Welcome.this, Init.class); 
		        startService(updateIntent);
				Intent it=null;
				if(new InitHelper(Welcome.this).checkHasInit()){
					it = new Intent(Welcome.this, Main.class);
				}else{
					it = new Intent(Welcome.this, VersionIntro.class);
				}
				startActivity(it);
				finish();
			}
		},3000*1);
	}
	
	private void initHello() {
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==101){
				}
			}
			
		};
		dc  = new DataCache(Welcome.this);
		String url = Api.News.getHelloNews();
		String cache = dc.load(url);
		if(null!=cache&&!"".equals(cache)){
		}else
		{
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg=new Message();
					msg.what=101;
					String helloUrl = Api.News.getHelloNews();
					//msg.obj = HttpUtil.getRequest(helloUrl, null);
					handler.sendMessage(msg);
				}
			}).start();
		}

	}
}
