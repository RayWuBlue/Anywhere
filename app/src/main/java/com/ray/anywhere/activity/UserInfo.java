package com.ray.anywhere.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.config.DecodeConfig;
import com.ray.anywhere.config.PathConfig;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.BitmapUtil;
import com.ray.anywhere.utils.FileUtils;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.ImageUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.widgets.MyAlertDialog;
import com.ray.anywhere.widgets.MyAlertMenu;
import com.ray.anywhere.widgets.MyAlertMenu.MyDialogMenuInt;
import com.ray.anywhere.widgets.RoundedImageView;

import java.io.File;

public class UserInfo extends BaseActivity {

	private MyAlertMenu mam;

	private RoundedImageView head;

	private TextView nickname;
	private TextView sex;
	private LoginHelper lh;

	private MyAlertDialog sexMad;

	private MyAlertDialog modify;

	public String picName;

	private Button exit;

	private TextView tv_birthday;

	private TextView tv_qq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_user_info);
		setTitle("������Ϣ");
		lh = new LoginHelper(this);
		head = (RoundedImageView) findViewById(R.id.user_info_head);


		nickname = (TextView) super.findViewById(R.id.user_info_nickname);
		nickname.setText(lh.getNickname());

		sex = (TextView) super.findViewById(R.id.user_info_sex);
		tv_birthday = (TextView)findViewById(R.id.user_info_birthday);
		tv_birthday.setText(lh.getBirthday());
		
		tv_qq = (TextView)findViewById(R.id.user_info_qq);
		tv_qq.setText(lh.getQQ());
		
		sex.setText(lh.getSex());
		exit = (Button) super.findViewById(R.id.setting_exit_login_btn);
		exit.setOnClickListener(new ExitLogin());

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// �����ǳ�
		String temp_name = lh.getNickname().trim();
		if (temp_name.length() == 0) {
			nickname.setText("�����û�");
		} else {
			nickname.setText(lh.getNickname());
		}
	}

	public void onClick(View v) {
		Bundle bd ;
		Intent it;
		switch (v.getId()) {
		case R.id.user_info_head_layout:
			mam = new MyAlertMenu(UserInfo.this, new String[] { "�����ѡ��",
					"�������ѡ��", "ȡ��" });
			mam.setOnItemClickListener(new MyDialogMenuInt() {
				@Override
				public void onItemClick(int position) {
					switch (position) {
					case 0:
						// ѡ��ͼ��
						Intent it = new Intent(Intent.ACTION_PICK, null);
						it.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(it, 1);
						break;
					case 1:
						// ѡ�����
						picName = String.valueOf(TimeUtil.getCurrentTime())
								+ ".jpg";
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						FileUtils.createPath(PathConfig.SavePATH);
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(new File(PathConfig.SavePATH,
										picName)));
						startActivityForResult(intent, 2);
						break;
					case 2:
						break;
					default:
						break;
					}
				}
			});
			break;
		case R.id.user_info_nick_layout:
			bd = new Bundle();
			bd.putString("name", "����");
			bd.putString("value", lh.getNickname());
			it = new Intent(UserInfo.this, ModifyValue.class);
			it.putExtras(bd);
			startActivityForResult(it, 4);
			break;
		case R.id.user_info_qq_layout:
			bd = new Bundle();
			bd.putString("name", "QQ");
			bd.putString("value", lh.getQQ());
			it = new Intent(UserInfo.this, ModifyValue.class);
			it.putExtras(bd);
			startActivityForResult(it, 4);
			break;
		case R.id.user_info_birthday_layout:
			
			DatePickerDialog dpd = new DatePickerDialog(UserInfo.this,new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					monthOfYear++;
					String str = year+"-"+(monthOfYear<10?("0"+monthOfYear):(monthOfYear))+"-"+(dayOfMonth<10?("0"+dayOfMonth):(dayOfMonth));
					new AsyncUpdateInfo(tv_birthday).execute("birthday", str);
					lh.setBirthday(str);
				}
			},1990,0,1);
			dpd.show();
			
			break;
		case R.id.user_info_sex_layout:
			sexMad = new MyAlertDialog(UserInfo.this);
			sexMad.setTitle("��ѡ���Ա�");
			final ListView sexLv = new ListView(UserInfo.this);
			sexLv.setDivider(getResources().getDrawable(R.color.dedede));
			sexLv.setDividerHeight(2);
			String[] sexArr = { "��", "Ů", "����" };
			ArrayAdapter<CharSequence> colleageAda = new ArrayAdapter<CharSequence>(
					UserInfo.this, R.layout.item_textview, sexArr);
			sexLv.setAdapter(colleageAda);
			sexMad.setContentView(sexLv);
			sexLv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					sexMad.dismiss();
					String str = "";
					if (position == 0) {
						str = "��";
					} else if (position == 1) {
						str = "Ů";
					} else {
						str = "����";
					}
					if (!NetHelper.isNetConnected(UserInfo.this)) {
						T.showShort(UserInfo.this, R.string.net_error_tip);
						return;
					}
					new AsyncUpdateInfo(sex).execute("sex", str);
				}
			});
			break;
		case R.id.user_info_change_password_layout:

			break;
		default:
			break;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// ������ȡ
		case 1:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// �������
		case 2:
			File temp = new File(PathConfig.SavePATH, picName);
			if (temp.exists()) {
				startPhotoZoom(Uri.fromFile(temp));
			}
			break;
		// ȡ�òü����ͼƬ
		case 3:
			if (data != null) {
				setPicToView(data);
			}
		case 4:
			if (data != null) {
				String name = data.getStringExtra("name");
				if ("����".equals(name))
					nickname.setText(data.getStringExtra("value"));
				else if ("QQ".equals(name))
					tv_qq.setText(data.getStringExtra("value"));
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			head.setImageBitmap(ImageUtil.toRoundCorner(photo, 10));
			final LoginHelper lh = new LoginHelper(UserInfo.this);
			final String path = PathConfig.HEADPATH;
			final String name = DecodeConfig.decodeHeadImg(lh.getUid())
					+ ".jpg";
			try {
				BitmapUtil.saveImg(photo, path, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			File f = new File(path + name);
			/*RequestParams params = new RequestParams();
			params.addBodyParameter("pic", f);
			HttpUtils http = new HttpUtils();
			http.send(
					HttpMethod.POST,
					Api.UserConfig.uploadTempAvatar(lh.getUid(), lh.getToken()),
					params, new RequestCallBack<String>() {
						public void onFailure(HttpException arg0, String arg1) {
							T.showLong(UserInfo.this, "ͷ���ϴ�ʧ�ܣ������³���");
						}

						public void onSuccess(ResponseInfo<String> arg0) {
							System.out.println("ͼƬ�ϴ�:" + arg0.result);
							JSONObject retObject = JSONObject
									.parseObject(arg0.result);
							lh.setHeadImg(Api.DOMAIN
									+ retObject.getString("data"));
							T.showLong(UserInfo.this, "ͷ���ϴ��ɹ�");
						}
					});*/

			// ByteArrayOutputStream stream = new ByteArrayOutputStream();
			// photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			// byte[] b = stream.toByteArray();
			// ��ͼƬ�����ַ�����ʽ�洢����
			// tp = new String(Base64Coder.encodeLines(b));

		}
	}

	public class AsyncUpdateInfo extends AsyncTask<String, Void, String> {

		public TextView tv;
		public String param;

		public AsyncUpdateInfo(TextView tv) {
			this.tv = tv;
		}

		protected String doInBackground(String... str) {
			param = str[1];

			if ("sex".equals(str[0]))
				return GetUtil.sendGet(Api.UserConfig.modifysex(lh.getUid(),
						lh.getToken(), str[1]), null);
			else if ("birthday".equals(str[0]))
				return GetUtil.sendGet(
						Api.UserConfig.modifybirthday(lh.getUid(),
								lh.getToken(), str[1]), null);
			else
				return "";
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			/*System.out.println("�޸���Ϣ:" + result);
			if (result != null && !"".equals(result)) {
				JSONObject jsonResult;
				try {
					jsonResult = JSONObject.parseObject(result);
					if (jsonResult.getInteger("retCode") == 1) {
						tv.setText(param);
						if (param.equals("��"))
							lh.setSex("1");
						else if (param.equals("Ů"))
							lh.setSex("2");
						else
							lh.setSex("0");
						T.showShort(UserInfo.this, "�޸ĳɹ�");
					} else {
						T.showShort(UserInfo.this, "�޸�ʧ��,�����³���");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}*/
		}

	}

	public class ExitLogin implements OnClickListener {
		public void onClick(View arg0) {
			new LoginHelper(UserInfo.this).logout();
			T.showShort(UserInfo.this, "�ɹ��˳���ǰ�˺�");
			finish();
		}
	}

	
}
