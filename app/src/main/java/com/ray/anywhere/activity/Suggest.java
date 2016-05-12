package com.ray.anywhere.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.MyProgressBar;

public class Suggest extends BaseActivity {
	
	private EditText content;
	private LoginHelper lh;
	private MyProgressBar mpb;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_suggest);
		setTitle("������Ϣ");
		addButton("����", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String SuggestVal=content.getText().toString();
				if(SuggestVal.length()<=0){
					T.showShort(Suggest.this,"��������Ҫ����������");
				}else if(SuggestVal.length()>256){
					T.showShort(Suggest.this,"��������������256�ַ���");
				}else{
					if(NetHelper.isNetConnected(Suggest.this)){
						mpb=new MyProgressBar(Suggest.this);
						mpb.setMessage("�����ϴ�������Ϣ...");
						new AsyncUpload().execute(SuggestVal);
					}else{
						T.showShort(Suggest.this,R.string.net_error_tip);
					}
				}
				
			}
		});
		content=(EditText) super.findViewById(R.id.suggest_content);
		lh=new LoginHelper(this);
	}

	
	public class AsyncUpload extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... str) {

			return "";
		}

		protected void onPostExecute(String result) {
			mpb.dismiss();

			super.onPostExecute(result);
		}
		
		
		
	}

}
