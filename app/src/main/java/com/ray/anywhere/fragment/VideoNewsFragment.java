package com.ray.anywhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.ray.anywhere.R;
import com.ray.anywhere.activity.NewsContent;
import com.ray.anywhere.adapter.VideoAdapter;
import com.ray.anywhere.base.IndexItemBase;

import java.util.ArrayList;
import java.util.List;

public class VideoNewsFragment extends Fragment {
	private Context activity;
	private GridView lv;
	private List<IndexItemBase> list;
	private VideoAdapter adapter;
	private int page = 1;
	private ProgressBar pb;
	private int type;
	public  final static String SER_KEY = "com.pw.schoolknow.ser";
	private boolean loaded = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getInt("id");
		list = new ArrayList<IndexItemBase>();
		adapter=new VideoAdapter(activity,list);
	}
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_news, null);

		pb = (ProgressBar)root.findViewById(R.id.detail_loading);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				IndexItemBase item=list.get(position);
				Intent it=new Intent(activity,NewsContent.class);
				Bundle mBundle = new Bundle();  
			    mBundle.putSerializable(SER_KEY,item);  
			    it.putExtras(mBundle);  
				startActivity(it);
			}
		});
		return root;
	}/*
	//异步载入新闻
	private boolean parseResult(String result) {

		JSONObject jsonResult = JSON.parseObject(result);
		JSONArray jsonNewsList = jsonResult.getJSONArray("list");
		if(jsonNewsList==null){
			lv.onRefreshComplete();
			T.showShort(activity, "没有新闻");
			return false;
		}
		if(page==1){
			list.clear();
			adapter.notifyDataSetChanged();
			lv.onRefreshComplete();
		}
		for(int i=0;i<jsonNewsList.size();i++){

			JSONObject jsonNewsItem = jsonNewsList.getJSONObject(i);

			IndexItemBase item=new IndexItemBase();
			item.setId( Integer.parseInt(jsonNewsItem.getString("id")));
			item.setIntro( jsonNewsItem.getString("description"));
			item.setSource(jsonNewsItem.getString("source"));
			item.setComment_num( Integer.parseInt(jsonNewsItem.getString("comment")));
			item.setTitle(jsonNewsItem.getString("title"));
			item.setTime(jsonNewsItem.getString("update_time"));
			item.setView(jsonNewsItem.getString("view"));
			
			System.out.println("TIME:"+jsonNewsItem.getString("update_time"));
			
			*//*List<String> imgList = new ArrayList<String>();
			JSONArray jsonImgList = jsonNewsItem.getJSONArray("imgList");
			if(jsonImgList!=null&&jsonImgList.size()>0)
			for(int j = 0;j<jsonImgList.size();j++){
				String str = jsonImgList.getString(j);
				if(!str.contains("http://"))str = Api.DOMAIN+str;
				imgList.add(str);//此处用于调试是使用
				//imgList.add(jsonImgList.getString(j));
				System.out.println(str);
			}
			item.setImgList(imgList);*//*
			
			item.setCover(jsonNewsItem.getString("cover_url"));
			
			list.add(item);

		}
		adapter.notifyDataSetChanged();
		return true;
	}
	*/
}
