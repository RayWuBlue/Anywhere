package com.ray.anywhere.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ray.anywhere.ActFlashPlay;
import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.base.IndexItemBase;
import com.ray.anywhere.db.DBUtil;
import com.ray.anywhere.db.SQLHelper;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.AndroidShare;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.DownloadManageUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.MyProgressBar;

public class EventNewsDetail extends BaseActivity {

	private WebSettings webSettings = null;
	private WebView web = null;
	private MyProgressBar mpb;
	private IndexItemBase item;

	private LoginHelper loginHelper;

	private RelativeLayout clickReLoad;

	private IndexItemBase temmItem;

	public Context mcontext;

	private Cursor result = null;
	private Boolean IsCollected = false;
	private ImageView btnCollect;

	private ImageView imageCover;
	private TextView imageText;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_news_content);

		
		mcontext = this;

		loginHelper = new LoginHelper(this);

		temmItem = (IndexItemBase) getIntent().getSerializableExtra(
				NewsActivity.SER_KEY);

		if (temmItem != null && item == null) {
			item = temmItem;
		} 

		web = (WebView) super.findViewById(R.id.news_content_webview);

		// ���ͼƬ���¼���
		clickReLoad.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (!NetHelper.isNetConnected(mcontext)) {
					T.showShort(mcontext, R.string.net_error_tip);
				} else {
					onCreate(null);
				}

			}
		});

		// ������ϲ�����
		if (!NetHelper.isNetConnected(this)) {
			web.setVisibility(View.GONE);
			clickReLoad.setVisibility(View.VISIBLE);
			return;
		} else {
			web.setVisibility(View.VISIBLE);
			clickReLoad.setVisibility(View.GONE);
		}

		webSettings = web.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBlockNetworkImage(false);
		webSettings.setUseWideViewPort(false);
		webSettings.setLoadWithOverviewMode(true);
		// web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// web.setHorizontalScrollBarEnabled(false);
		web.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

		// web.loadUrl(ServerConfig.HOST+"/schoolknow/temmItem.php?id="+item.getId());
		// http://localhost/index.php?s=/blog/article/detail/id/140.html
		web.loadUrl(Api.News.getNewsDetail(item.getId()));
	//	web.addJavascriptInterface(new JavascriptInterface(this),
			//	"imagelistner");
		web.setDownloadListener(new MyWebViewDownLoadListener());
		web.setWebViewClient(new MyWebViewClient());

		web.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
			}
		});
		
		initToolbar();

		String img = item.getCover();

		if (null!=img&&!"".equals(img)){
			View cover_ll = findViewById(R.id.news_cover_ll);
			cover_ll.setVisibility(View.VISIBLE);
			imageCover = (ImageView) cover_ll.findViewById(R.id.news_cover);
			imageText = (TextView) cover_ll.findViewById(R.id.news_text);
			//BitmapUtils bm = MyApplication.getInstance().getBitmapUtils();
		//	bm.display(imageCover, img);
			imageText.setText(item.getTitle());
		}
		initcollect();

	}

	private void initToolbar() {
		addButton(item.getComment_num()+"",R.drawable.comment,new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(EventNewsDetail.this, ActFlashPlay.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable(NewsActivity.SER_KEY, item);
				it.putExtras(mBundle);
				startActivity(it);
			}
		});
		
		View view = addButton(R.drawable.colllect, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Oncollect();
			}
		});
		
		btnCollect = (ImageView) view.findViewById(R.id.btn_image);
		
		addButton(R.drawable.intro_to_gay, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AndroidShare as = new AndroidShare(EventNewsDetail.this, "#"
						+ temmItem.getTitle() + "#"
						+ Api.News.getNewsDetail(temmItem.getId()), "");
				as.show();
			}
		});
	}

	private void Oncollect() {
		DBUtil dbu = DBUtil.getInstance(EventNewsDetail.this);
		if (!IsCollected) {
			try {
				ContentValues values = new ContentValues();
				values.put("news_id", EventNewsDetail.this.temmItem.getId());
				values.put("news_title", EventNewsDetail.this.temmItem.getTitle());
				values.put("news_time", EventNewsDetail.this.temmItem.getTime());
				values.put("news_column", "У԰��Ѷ");
				//List<String> list = temmItem.getImgList();
				values.put("news_image", temmItem.getCover());
				long insert_result = dbu.insertData(SQLHelper.TABLE_COLLECT,
						values);
				if (insert_result != -1) {
					IsCollected = true;
					T.show(EventNewsDetail.this, "�ղسɹ�", Toast.LENGTH_SHORT);
					btnCollect.setImageResource(R.drawable.colllected);
				} else
					T.show(EventNewsDetail.this, "�ղ�ʧ��", Toast.LENGTH_SHORT);
				System.out.println("��������" + insert_result);
			} catch (SQLException e) {
				T.show(EventNewsDetail.this, "�ղ�ʧ�ܣ�����Ի�����Ϣ", Toast.LENGTH_SHORT);
				e.printStackTrace();
			}
		} else {
			T.show(EventNewsDetail.this, "ȡ���ղ�", Toast.LENGTH_SHORT);
			dbu.execSQL("delete from collect_tb where news_id="
					+ temmItem.getId());
			IsCollected = false;
			btnCollect.setImageResource(R.drawable.colllect);
		}

	}

	private void initcollect() {
		/*
		 * db = openOrCreateDatabase("news_db.db", Context.MODE_PRIVATE, null);
		 * db.execSQL("");
		 */
		DBUtil dbu = DBUtil.getInstance(EventNewsDetail.this);
		int id = EventNewsDetail.this.temmItem.getId();
		result = dbu.rawQuery("SELECT news_id FROM collect_tb where news_id="
				+ id);
		result.moveToFirst();
		if (result != null && result.getCount() >= 1) {
			IsCollected = true;
			btnCollect.setImageResource(R.drawable.colllected);
		} else {
			IsCollected = false;
			btnCollect.setImageResource(R.drawable.colllect);
		}
		result.close();
	}

	// ע��js��������
	private void addImageClickListner() {
		// ���js�����Ĺ��ܾ��ǣ��������е�img���㣬�����onclick�����������Ĺ�������ͼƬ�����ʱ����ñ���java�ӿڲ�����url��ȥ
		web.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(this.src);  "
				+ "    }  " + "}" + "})()");
	}

	// jsͨ�Žӿ�
	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

		public void openImage(String img) {
			Intent intent = new Intent();
			intent.putExtra("imgsrc", img);
			intent.setClass(context, ImgPreview.class);
			context.startActivity(intent);
		}
	}

	// ����
	@SuppressLint("SetJavaScriptEnabled")
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			web.setVisibility(View.VISIBLE);
			mpb.dismiss();
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			// html�������֮����Ӽ���ͼƬ�ĵ��js����
			addImageClickListner();

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			web.setVisibility(View.GONE);
			mpb = new MyProgressBar(EventNewsDetail.this);
			mpb.setMessage("���ڼ�����...");
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {

			super.onReceivedError(view, errorCode, description, failingUrl);

		}
	}

	/**
	 * ����ϵͳ�������������
	 * 
	 */
	private class MyWebViewDownLoadListener implements DownloadListener {
		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			// �����������������
			/*
			 * Uri uri = Uri.parse(url); Intent intent = new
			 * Intent(Intent.ACTION_VIEW, uri); startActivity(intent);
			 */

			// ����ϵͳ����
			DownloadManageUtil.DownloadFile(mcontext, url, "/yuol/DownLoad");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// DownloadUtil.unregisterReceiver(this);
	}
	@Override
	public void onPause() {// �̳���Activity
		super.onPause();
		web.onPause();
	}

	@Override
	public void onResume() {// �̳���Activity
		super.onResume();
		web.onResume();
	}



}
