package com.ray.anywhere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.T;

public class ModifyValue extends Activity {
	
	private EditText et;
	private LoginHelper lh;
	private Button btn_confirm;
	private TextView title;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.act_modify_value);
		lh=new LoginHelper(this);
		et=(EditText) super.findViewById(R.id.update_username_edit);
		title=(TextView) super.findViewById(R.id.modify_title);
		title.setText(getIntent().getStringExtra("name"));
		btn_confirm = (Button)findViewById(R.id.modify_comfirm);
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String val=et.getText().toString().trim();
				if(null==val||val.length()==0){
					T.showShort(ModifyValue.this, "����Ϊ��");	
				}else{
					if(!NetHelper.isNetConnected(ModifyValue.this)){
						T.showShort(ModifyValue.this,R.string.net_error_tip);
					}else if(val.equals(lh.getNickname())){
						finish();
					}else{
						v.setEnabled(false);
						new AsyncUpdate().execute(v);
					}
				}
			}
		});
		et.setText(getIntent().getStringExtra("value"));
	}
	public class AsyncUpdate extends AsyncTask<View,Void,String>{

		private View v;
		protected String doInBackground(View... v) {
			return "";
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			System.out.println("�޸��û���Ϣ��"+result);
			//JSONObject jObj = JSONObject.parseObject(result);
			//if(jObj!=null&&jObj.getInteger("retCode")==1){
				T.showShort(ModifyValue.this, "�޸ĳɹ�");
				String name = getIntent().getStringExtra("name");
				Intent  it = new Intent(ModifyValue.this,UserInfo.class);
				Bundle bd = new Bundle();
				bd.putString("value",et.getText().toString().trim());
				if ("����".equals(name))
					bd.putString("name","����");
				else if ("QQ".equals(name))
					bd.putString("name","QQ");
				it.putExtras(bd);
				setResult(RESULT_OK, it);
				//startActivity(it);
				finish();
			//}else{
			//	v.setEnabled(true);
			//	T.showShort(ModifyValue.this, "�޸�ʧ��,�����³���");
			//}
		}
	}
}
