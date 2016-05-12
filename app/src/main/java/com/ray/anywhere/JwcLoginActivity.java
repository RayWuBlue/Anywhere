package com.ray.anywhere;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.T;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JwcLoginActivity extends BaseActivity {
	public static final int TYPE_SCORE = 1;
	public static final int TYPE_COURSE = 2;
	public static  final int TYPE_KB = 3;
    private EditText u_id=null;
    private EditText u_word=null;
    private EditText verify_code=null;
    private ImageView verify_image = null;
    private Map<String,String> item_type=new HashMap<String,String>();
    private Button login=null;
    private MyHandler myHandler;
    private String __VIEWSTATE;
    private String __EVENTVALIDATION;
    private URL url;
    private Document doc;
    private String u_stuid;
    private String u_name;
    private String u_class;
    private Map<String, String> u_cookie;
    private SharedPreferences sharedPreferences;
	private int type;
	//BitmapUtils bm;
	public LoginHelper lh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_jwc_login);
		initview();
		initUser();
		type = getIntent().getExtras().getInt("type");
		//bm = MyApplication.getApp().getBitmapUtils();
		initVerifyCode();
		lh = new LoginHelper(JwcLoginActivity.this);
		if(lh.hasJwcLogin())
			redirect();
	}
	
	private void redirect() {
		Intent it = null;
		switch (type) {
			case TYPE_COURSE:
				it = new Intent(JwcLoginActivity.this,OptionalCourseActivity.class);
				break;
			case TYPE_KB:
				it = new Intent(JwcLoginActivity.this,KbQuery.class);
				break;
			default:
				it = new Intent(JwcLoginActivity.this,ScoreDetailActivity.class);
				break;
		}
		startActivity(it);
		JwcLoginActivity.this.finish();
	}

	private void initVerifyCode() {
		///bm.closeCache();
		//bm.display(verify_image, Api.Jwc.getJwcLoginVerify());
	}

	private void initview(){
		setTitle("��¼����");
		try {
			url = new URL(Api.Jwc.getJwcLogin());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		//u_id=(EditText)super.findViewById(R.id.jwc_login_id);
		//u_word=(EditText)super.findViewById(R.id.jwc_login_word);
		verify_image = (ImageView)findViewById(R.id.img_verify);
		verify_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				initVerifyCode();
				
			}
		});
		verify_code = (EditText)findViewById(R.id.edt_verify);
		//u_type=(Spinner)super.findViewById(R.id.jwc_login_type);
		//login=(Button)super.findViewById(R.id.jwc_login);
		item_type.put("ѧ��","1");
		item_type.put("��ʦ","2");
		sharedPreferences=this.getSharedPreferences("sharedPreferences",MODE_PRIVATE);
		//u_type.setAdapter(new ArrayAdapter<String>(JwcLoginActivity.this, android.R.layout.simple_spinner_item, new ArrayList<String>(item_type.keySet())));
		myHandler=new MyHandler();
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					Editor editor=sharedPreferences.edit();
					editor.putString("jwc_user_id",u_id.getText().toString().trim());
					editor.putString("jwc_user_pwd",u_word.getText().toString().trim());
					editor.commit();
				if(u_id.getText().toString()==null||u_word.getText().toString()==null){
					Toast.makeText(JwcLoginActivity.this,"����д�û��������룡",Toast.LENGTH_SHORT).show();
				}else
				new MyThread(0).start();
				login.setText("���ڵ�½��...");
				login.setClickable(false);
			}
		});
	}
	public class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				new MyThread(1).start();
				break;
			case 1:
				if(lh.JwcLogin(u_stuid, u_name, u_class, u_cookie.get("ASP.NET_SessionId"), __VIEWSTATE, __EVENTVALIDATION))
					{
						redirect();
					}
				else
					T.showShort(JwcLoginActivity.this, "��¼ʧ��");
				break;
			case 2:
				login.setText("��½");
				login.setClickable(true);
				AlertDialog.Builder builder_login=new Builder(JwcLoginActivity.this);
				builder_login.setTitle("�ܱ�Ǹ�����񴦵�¼ʧ�ܣ�");
				builder_login.setMessage("��������û����������Ƿ���ȷ�������Ƿ���á�");
				builder_login.setCancelable(false);
				builder_login.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder_login.setPositiveButton("����",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new MyThread(0).start();
					}
					
				});
				Dialog dialog_login=builder_login.create();
				dialog_login.show();
				break;
			}
			
		}
		
	}
	public class MyThread extends Thread{
		private int statu;
		public MyThread(int statu){
			this.statu=statu;
		}
		@Override
		public void run() {
			switch(statu){
			case 0:
				try {
					doc = Jsoup.parse(url,10*1000);
					__VIEWSTATE=doc.select("#__VIEWSTATE").val();
					__EVENTVALIDATION=doc.select("#__EVENTVALIDATION").val();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				myHandler.sendEmptyMessage(0);
				break;
			case 1:
				Map<String,String> params=new HashMap<String,String>();
				params.put("txtUid",u_id.getText().toString().trim());
				params.put("txtPwd",u_word.getText().toString().trim());
				params.put("txtCheckCode",verify_code.getText().toString().trim());
				//params.put("selKind",String.valueOf(u_type.getSelectedItemId()+1));
				params.put("__VIEWSTATE",__VIEWSTATE);
				params.put("__EVENTVALIDATION",__EVENTVALIDATION);
				params.put("btLogin","");
					try {
	                     Response response=Jsoup.connect(url.toString()).data(params).timeout(10*1000).execute();
	                     doc= Jsoup.parse(response.body());
	                     Element div_user=doc.select("#lbPrompt").get(0);
	                     String[] u_str=div_user.text().split(" ");
	                     u_stuid=u_str[0];
	                     u_name=u_str[1];
	                     u_class=u_str[2];
	                     __VIEWSTATE=doc.select("#__VIEWSTATE").val();
	 					 __EVENTVALIDATION=doc.select("#__EVENTVALIDATION").val();
	 					 u_cookie=response.cookies();
					} catch (Exception e) {
						e.printStackTrace();
						myHandler.sendEmptyMessage(2);
						break;
					}
				myHandler.sendEmptyMessage(1);
				break;
			}
		}
		
	}
	private void initUser(){
		String username=sharedPreferences.getString("jwc_user_id",null);
		if(username!=null&&!username.equals("")){
			u_id.setText(sharedPreferences.getString("jwc_user_id",""));
			u_word.setText(sharedPreferences.getString("jwc_user_pwd",""));
		}
	}

}
