package com.ray.anywhere.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.MyProgressBar;

@SuppressLint("HandlerLeak")
public class Login extends BaseActivity implements OnClickListener {

	private EditText uid = null;
	private TextView pwd = null;
	private Button login = null;
	private Button reg = null;
	private MyProgressBar mpb;
	ImageView img_verify;
	EditText edt_verify;
	private static final int Verify_State = 0;
	private static final int Login_State = 1;
	private LoginHelper lh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.act_login);

		setTitle("��¼");
		
		uid = (EditText) super.findViewById(R.id.login_edit_uid);
		pwd = (TextView) super.findViewById(R.id.login_edit_pwd);
		login = (Button) super.findViewById(R.id.login_btn_login);
		reg = (Button) super.findViewById(R.id.login_btn_reg);
		edt_verify = (EditText) super.findViewById(R.id.edt_verify);
		login.setOnClickListener(this);
		reg.setOnClickListener(this);

		img_verify = (ImageView) super.findViewById(R.id.img_verify);

	}


	@Override
	public void onClick(View view) {
		if (view == login) {
			final String stuid = uid.getText().toString();
			final String password = pwd.getText().toString();
			final String verifyCode = edt_verify.getText().toString();
			if (!TextUtils.isEmpty(stuid) && !TextUtils.isEmpty(password)
					&& !TextUtils.isEmpty(verifyCode)) {

				mpb = new MyProgressBar(Login.this);
				mpb.setMessage("���ڵ�¼��...");
				new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						msg.what = Login_State;


						handler.sendMessage(msg);
					}
				}).start();
			} else {
				T.showShort(this, "����д����");
				return;
			}
		} else if (view == reg) {
			// ��ת��ע��ҳ��
			Intent it = new Intent(Login.this, Register.class);
			startActivity(it);
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Login_State:

				break;
			case Verify_State:

				break;
			default:
				break;
			}
			mpb.dismiss();
		}

	};


}
