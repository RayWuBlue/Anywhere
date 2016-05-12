package com.ray.anywhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.YUNewsDetail;
import com.ray.anywhere.adapter.YUNewsAdapter;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.bean.NewsChannelItem;
import com.ray.anywhere.bean.YUNewsItem;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.DataCache;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.widgets.MyPopMenu;
import com.ray.anywhere.widgets.MyPopMenu.MyPopMenuImp;
import com.ray.anywhere.widgets.XListView;
import com.ray.anywhere.widgets.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

public class YangtzeNews extends BaseActivity {
	private static final int LOAD_NEWS = 0;
	private YUNewsAdapter adapter;
	private int page = 0;
	private XListView lv;
	private List<YUNewsItem> list;
	private List<YUNewsItem> net_list;
	private TextView tv_loading_failed;
	private ProgressBar mpb;
	private IXListViewListenerImp xlvl;
	private MyPopMenu popmenu;
	private ArrayList<NewsChannelItem>  YUNewsChannelList = (ArrayList<NewsChannelItem>)(Api.News.getYUNewsChannels());
	private int type = YUNewsChannelList.get(0).getId();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_yu_news);
		list = new ArrayList<YUNewsItem>();
		net_list = new ArrayList<YUNewsItem>();
		adapter = new YUNewsAdapter(YangtzeNews.this, list);
		xlvl = new IXListViewListenerImp();
		initView();
	}
	private void initView() {
		setTitle(YUNewsChannelList.get(0).getName());
		addButton("���ŷ���", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popmenu=new MyPopMenu(YangtzeNews.this);
				int c =YUNewsChannelList.size();
				String[] menuArray = new String[c];
				for(int i=0;i<YUNewsChannelList.size();i++)
					menuArray[i] = YUNewsChannelList.get(i).getName();
				
				popmenu.addItems(menuArray);
				popmenu.showAsDropDown(v);
				popmenu.setOnItemClickListener(new MyPopMenuImp() {
					@Override
					public void onItemClick(int index) {
						setTitle(YUNewsChannelList.get(index).getName());
						type = YUNewsChannelList.get(index).getId();
						page = 0;
						list.clear();
						initCacheData();
						xlvl.onRefresh();
					}});
				
			}
		});
		mpb = (ProgressBar)findViewById(R.id.detail_loading);

		lv.setAdapter(adapter);
		lv.setPullLoadEnable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(YangtzeNews.this,YUNewsDetail.class);
				intent.putExtra("com.pw.schoolknow.ser",list.get(position-1));
				startActivity(intent);
			}
		});
		lv.setXListViewListener(xlvl);
		initCacheData();
		xlvl.onRefresh();
	}

	private void initCacheData() {
		DataCache dc = new DataCache(YangtzeNews.this);
		String url=Api.News.getYUNews(page, type);
		String cache = dc.load(url);
		if(!"".equals(cache))
		{
			adapter.notifyDataSetChanged();
		}
	}

	class IXListViewListenerImp implements IXListViewListener{
		
		@Override
		public void onRefresh() {
			if(!NetHelper.isNetConnected(YangtzeNews.this)){
				T.showShort(YangtzeNews.this,R.string.net_error_tip);
				lv.stopRefresh();
				lv.stopLoadMore();
				return;
			}
			lv.setRefreshTime(""+TimeUtil.getUpdateTime(System.currentTimeMillis()));
			page = 0;
		}
		
		@Override
		public void onLoadMore() {
			if(!NetHelper.isNetConnected(YangtzeNews.this)){
				T.showShort(YangtzeNews.this,R.string.net_error_tip);
				lv.stopRefresh();
				lv.stopLoadMore();
				return;
			}
			if(list.size()!=0){
				page++;
			}
		}	
	}

}