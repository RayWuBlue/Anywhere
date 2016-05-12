package com.ray.anywhere.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.utils.PatternUtils;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.MyProgressBar;

import java.util.ArrayList;
import java.util.List;

public class Register extends BaseActivity {

	private MyProgressBar mpb;

	private EditText email, pwd, pwd2, nickname, stuid, username,
			edt_register_verify;
	private ImageView img_register_verify;
	private Button btn1;


	List<CharSequence> sexSelect = new ArrayList<CharSequence>();

	private static final int REGISTER_INFO = 1;
	private static final int UPLOAD_HRAD_IMAGE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_register);
		setTitle("����ע��");

		this.nickname = (EditText) super.findViewById(R.id.register_nickname);

		this.email = (EditText) super.findViewById(R.id.register_email);
		this.pwd = (EditText) super.findViewById(R.id.register_pwd);
		this.pwd2 = (EditText) super.findViewById(R.id.register_pwd2);
		this.username = (EditText) super.findViewById(R.id.register_username);
		this.edt_register_verify = (EditText) super
				.findViewById(R.id.edt_register_verify);
		this.btn1 = (Button) super.findViewById(R.id.register_btn_reg1);

		this.btn1.setOnClickListener(new onBtnClickListener());

		this.img_register_verify = (ImageView) findViewById(R.id.img_register_verify);


		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, this.sexSelect);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}


	public class onBtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			final String emailVal = Editval(email);
			final String pwdVal = Editval(pwd);
			final String pwd2Val = Editval(pwd2);
			final String nicknameVal = Editval(nickname);
			final String usernameVal = Editval(username);
			final String verifyVal = Editval(edt_register_verify);
			if (v == btn1) {
				// ע���һ��
				if (emailVal.equals("")) {
					T.showShort(Register.this, "����������");
				} else if (!PatternUtils.CheckEmail(emailVal)) {
					T.showShort(Register.this, "�����ʽ����");
				} else if (emailVal.length() >= 50) {
					T.showShort(Register.this, "���䳤������Ϊ50�ַ���");
				} else if (pwdVal.equals("")) {
					T.showShort(Register.this, "���벻��Ϊ��");
				} else if (nicknameVal.equals("")) {
					T.showShort(Register.this, "��������Ϊ��");
				} else if (usernameVal.equals("")) {
					T.showShort(Register.this, "�û�������Ϊ��");
				} else if (verifyVal.equals("")) {
					T.showShort(Register.this, "��֤�벻��Ϊ��");
				} else if (pwdVal.length() < 8 || pwdVal.length() > 16) {
					T.showShort(Register.this, "��������Ϊ8-16�ַ�");
				} else if (!PatternUtils.CheckPwd(pwdVal)) {
					T.showShort(Register.this, "����ֻ�ܰ�����ĸ������");
				} else if (!pwdVal.equals(pwd2Val)) {
					T.showShort(Register.this, "2����������벻һ��");
				} else {
					mpb = new MyProgressBar(Register.this);
					mpb.setMessage("����ע��...");
					new Thread(new Runnable() {
						@Override
						public void run() {
							Message msg = new Message();
							msg.what = REGISTER_INFO;

						}
					}).start();
				}
			}
		}

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REGISTER_INFO:

				break;
			case UPLOAD_HRAD_IMAGE:

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent it = new Intent(Register.this, Main.class);
						it.putExtra("param", "2");
						startActivity(it);
						finish();
					}
				}, 2000);
			default:
				break;
			}
			mpb.dismiss();

		}

	};

	/**
	 * ��ȡ������ֵ
	 * 
	 * @param et
	 * @return
	 */
	public String Editval(EditText et) {
		return et.getText().toString();

	}

}
