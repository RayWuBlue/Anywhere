package com.ray.anywhere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.adapter.IndexAdapter;
import com.ray.anywhere.base.IndexItemBase;
import com.ray.anywhere.bean.ChannelManage;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.widgets.XListView;
import com.ray.anywhere.widgets.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends Activity {
	
	private XListView lv;
	private List<IndexItemBase> list;

	private TextView title;
	private IndexAdapter adapter;
	private int page = 1;
	private Bundle bundle;
	private ProgressBar pb;
	public  final static String SER_KEY = "com.pw.schoolknow.ser";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_smile_news);
		this.init();
	}
	
	public void init(){
		pb = (ProgressBar)super.findViewById(R.id.detail_loading);
		title = (TextView)findViewById(R.id.title);
		
		lv.setPullLoadEnable(true);
		list=new ArrayList<IndexItemBase>();
		
		Collections.sort(list);
		bundle = getIntent().getExtras();
		
		if(bundle==null)
			title.setText("�Ƽ�");
		else
			title.setText(ChannelManage.getTitleById(bundle.getInt("type")));
		adapter=new IndexAdapter(NewsActivity.this,list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				IndexItemBase item=(IndexItemBase) lv.getItemAtPosition(position);
				Intent it=new Intent(NewsActivity.this,NewsContent.class);
				Bundle mBundle = new Bundle();  
			    mBundle.putSerializable(SER_KEY,item);  
			    it.putExtras(mBundle);  
				startActivity(it);
			}
		});
		IXListViewListenerImp xlvl = new IXListViewListenerImp();
		lv.setXListViewListener(xlvl);
		xlvl.onRefresh();
	}
	
	class IXListViewListenerImp implements IXListViewListener{
		@Override
		public void onRefresh() {
			if(!NetHelper.isNetConnected(NewsActivity.this)){
				T.showShort(NewsActivity.this,R.string.net_error_tip);
				lv.stopRefresh();
				lv.stopLoadMore();
				return;
			}
			if(list.size()!=0){
				Collections.sort(list);
			}
			lv.setRefreshTime(""+TimeUtil.getUpdateTime(System.currentTimeMillis()));
			page = 1;
			new AsyncLoadNews().execute(page);
		}
		@Override
		public void onLoadMore() {
			if(!NetHelper.isNetConnected(NewsActivity.this)){
				T.showShort(NewsActivity.this,R.string.net_error_tip);
				lv.stopRefresh();
				lv.stopLoadMore();
				return;
			}
			if(list.size()!=0){
				//Collections.sort(list);
				new AsyncLoadNews().execute(++page);
			}
			
		}
		
	}
	public class AsyncLoadNews extends AsyncTask<Integer, Void, String>{
		@Override
		protected String doInBackground(Integer... params) {
			String url;
			if(bundle==null)
				url=Api.News.getNewsIndex(params[0]);
			else
				url=Api.News.getNewsList(bundle.getInt("type"),params[0]);
			System.out.println("URL��"+url);
			String json=GetUtil.getRes(url);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

		}
		
	}

	@Override
	public void onBackPressed() {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null)
			finish();
	}
}
