package com.ray.anywhere.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.VersionHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.DataCache;
import com.ray.anywhere.utils.GetUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * ����Ƿ����
 * @author wei8888go
 *
 */
public class Init  extends Service {
	
	public LoginHelper lh;
	public VersionHelper vh;
	@Override
	public void onCreate() {
		super.onCreate();
		
		lh=new LoginHelper(this);
		vh=new VersionHelper(this);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg=new Message();
				msg.what=102;
				msg.obj=GetUtil.getRes(Api.UPDATE);
				System.out.println("msg.obj:"+msg.obj);
				handler.sendMessage(msg);
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg=new Message();
				msg.what=101;
				String helloUrl = Api.News.getHelloNews();
				handler.sendMessage(msg);
			}
		}).start();
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==102){
				String temp=msg.obj.toString();
				if(!temp.equals("")&&temp.length()!=0){
					try {
						System.out.println("temp:"+temp);
						int version=VersionHelper.getVerCode(Init.this);
						JSONObject JsonData = new JSONObject(temp);
						
						String serverCode=JsonData.getString("name").trim();
						
						int serverVer=Integer.parseInt(serverCode);
						System.out.println("serverVer:"+serverVer);
						System.out.println("localVer:"+version);

						vh.update(temp,true);
							
						
					}catch (JSONException e) {
					}catch (Exception e) {
					}
				}
			}
			else if(msg.what==101){
				System.out.println("��ó���:"+msg.obj.toString());
				DataCache dc  = new DataCache(getApplicationContext());
				dc.save(Api.News.getHelloNews(), msg.obj.toString());
			}
			stopSelf();
		}
		
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
