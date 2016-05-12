package com.ray.anywhere.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.AndroidShare;
import com.ray.anywhere.utils.DownloadManageUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.MyProgressBar;

public class WebContent extends BaseActivity {

	private WebSettings webSettings = null;
	private WebView web = null;
	private MyProgressBar mpb;
	private RelativeLayout clickReLoad;
	public Context mcontext;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_web_content);
		setTitle(getIntent().getStringExtra("title"));
		mcontext = this;
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
		webSettings.setBuiltInZoomControls(true);
		webSettings.setUseWideViewPort(false);
		webSettings.setLoadWithOverviewMode(true);
		web.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		web.loadUrl(getIntent().getStringExtra("url"));
		//web.addJavascriptInterface(new JavascriptInterface(this),"imagelistner");
		web.setDownloadListener(new MyWebViewDownLoadListener());
		web.setWebViewClient(new MyWebViewClient());

		web.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
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
			mpb = new MyProgressBar(WebContent.this);
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
			DownloadManageUtil.DownloadFile(mcontext, url,"/yuol/DownLoad");
		}
	}

	public void onClick(View v){
		switch (v.getId()) {
		case R.id.intro:
			AndroidShare as = new AndroidShare(WebContent.this,"#"+getIntent().getStringExtra("title")+"#"+getIntent().getStringExtra("url"),"");
			as.show();
			break;
		default:
			break;
		}
	}
	
}
