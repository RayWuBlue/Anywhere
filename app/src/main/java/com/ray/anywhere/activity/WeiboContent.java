package com.ray.anywhere.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.ray.anywhere.R;
import com.ray.anywhere.adapter.Comment2Adapter;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.base.SchoolFellowBase;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.RegUtils;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.utils.ViewHolder;
import com.ray.anywhere.widgets.EmotionBox;
import com.ray.anywhere.widgets.EmotionBox.EmotionBoxClickListener;
import com.ray.anywhere.widgets.MyAlertMenu;
import com.ray.anywhere.widgets.MyAlertMenu.MyDialogMenuInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint("HandlerLeak")
public class WeiboContent extends BaseActivity {

	private List<Map<String, Object>> list;
	
	private Comment2Adapter adapter;

	private ListView lv;
	
	public static EmotionBox emotionBox;

	public SchoolFellowBase item;
	public int position;

	public LoginHelper lh;

	public Context mcontext;

	public  static String  targetUser ="";

	public MyAlertMenu mam;

	public String userid = "";
	
	public String userUid = "";

	private String input;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_schoolfellow_content);

		Intent getIntent = getIntent();
		item = (SchoolFellowBase) getIntent
				.getSerializableExtra(NewsActivity.SER_KEY);
		position = getIntent.getIntExtra("position", 0);

		setTitle(item.getNickname());

		lh = new LoginHelper(this);
		mcontext = WeiboContent.this;
		userUid = item.getUid();

		lv = (ListView) super
				.findViewById(R.id.schoolfellow_content_comment_lv);

		list = new ArrayList<Map<String, Object>>();

		// View
		// head=SchoolFellowSquare.adapter.getView(position,null,null,false);
		View head = getView(mcontext, item);
		lv.addHeaderView(head);
		adapter = new Comment2Adapter(this, list);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(final AdapterView<?> parent, View arg1,
					int position, long arg3) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) parent
						.getItemAtPosition(position);
				final String copyVal = map.get("content").toString();
				mam = new MyAlertMenu(mcontext, new String[] { "�ظ�", "����" });
				mam.setOnItemClickListener(new MyDialogMenuInt() {
					@SuppressLint("NewApi")
					public void onItemClick(int position) {
						switch (position) {
						case 1:
							// �õ������������
							ClipboardManager cmb = (ClipboardManager) mcontext
									.getSystemService(Context.CLIPBOARD_SERVICE);
							String contentVal = copyVal.trim();
							int start = contentVal.indexOf("</at>");
							if (start != -1) {
								start += 5;
								contentVal = contentVal.substring(start);
							}
							cmb.setText(contentVal);
							T.showShort(mcontext, "�Ѿ����Ƶ�ճ����");
							break;
						}
					}
				});

			}
		});

		emotionBox = new EmotionBox(this);
		emotionBox.setEditHint("@"+item.getNickname());
		emotionBox.setOnClick(new EmotionBoxClickListener() {
			public void onClick(String EditTextVal, View view) {
				 input = EditTextVal;
				if (!lh.hasLogin()) {
					T.showShort(mcontext, "���¼���ٷ�������");
				} else if (EditTextVal.trim().length() == 0) {
					T.showShort(mcontext, "���������ݲ���Ϊ��");
				} else if (EditTextVal.length() > 255) {
					T.showShort(mcontext, "���������ݲ��ܳ���256���ַ�");
				} else if (!NetHelper.isNetConnected(mcontext)) {
					T.showShort(mcontext, R.string.net_error_tip);
					return;
				} else {
					if(input!=null&&!"".equals(input.trim())){
						new Thread(new Runnable() {
							public void run() {

							}
						}).start();
					}

				}
			}
		});
		// �����̻߳�ȡ����
		new AsyncLoadComment().execute();
		
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

		}

	};

	// �첽��������
	class AsyncLoadComment extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... params) {
			String data = GetUtil.getRes(Api.Weibo.getWeiboComment(item.getId()
					+ ""));
			return data;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("WEIBO__;" + result);

		}
	}

	public  View getView(final Context context, final SchoolFellowBase item) {
		final View convertView = LayoutInflater.from(context).inflate(
				R.layout.item_school_fellow_lv_2, null, false);
		ImageView head = ViewHolder.get(convertView,
				R.id.schoolfellow_item2_user_img);
		TextView nick = ViewHolder.get(convertView,
				R.id.schoolfellow_item2_user_nick);
		TextView time = ViewHolder.get(convertView,
				R.id.schoolfellow_item2_time);
		TextView content = ViewHolder.get(convertView,
				R.id.schoolfellow_item2_content);
		
		LinearLayout ll_single = ViewHolder.get(convertView,
				R.id.image_layout_single);
		//commentTip.setText("����( " + item.getCommentNum() + " )");
		setTitle("����( " + item.getCommentNum() + " )");
		nick.setText(item.getNickname());
		time.setText(TimeUtil.getCommentTime(Long.parseLong(item.getTime())));


		final List<String> imgList = item.getImgList();
		ImageView image_single = ViewHolder.get(convertView,
				R.id.item_img_single);
		if (imgList != null) {
			if (imgList.size() == 1) {
				ll_single.setVisibility(View.VISIBLE);
			} 
		} else {
			ll_single.setVisibility(View.GONE);
		}
		String str = item.getContent();

		content.setText(EmotionBox.convertNormalStringToSpannableString(
				context, RegUtils.replaceImage(str)),
				BufferType.SPANNABLE);
		return convertView;
	}

}
