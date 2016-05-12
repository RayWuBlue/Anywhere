package com.ray.anywhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ray.anywhere.R;
import com.ray.anywhere.adapter.EventAdapter;
import com.ray.anywhere.bean.EventBean;

import java.util.ArrayList;
import java.util.List;

public class EventNewsFragment extends Fragment {
	private Context activity;
	private ListView lv;
	private List<EventBean> list;
	private EventAdapter adapter;
	private int page = 1;
	private ProgressBar pb;
	//private int type;
	public final static String SER_KEY = "com.pw.schoolknow.ser";
	private boolean loaded = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = new ArrayList<EventBean>();
		adapter = new EventAdapter(activity, list);

	}

	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_smile_news, null);
		lv = (ListView) root.findViewById(R.id.index_lv);
		pb = (ProgressBar) root.findViewById(R.id.detail_loading);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
									long id) {
				/*EventBean item=list.get(position-1);
				Intent it=new Intent(activity,NewsContent.class);
				Bundle mBundle = new Bundle();  
			    mBundle.putSerializable(SER_KEY,item);  
			    it.putExtras(mBundle);  
				startActivity(it);*/
			}
		});
		return root;
	}
}
		/*
	private boolean parseResult(String result) {

		*//*
		 * 		 
		 TextView view;
		 TextView attention;
		 TextView title;
		 TextView type;
		 TextView people;
		 TextView time;
		 TextView deadline;
		 TextView summary;
		 ImageView cover;
		 Button btnJoin;
		 
		 *//*
		JSONObject jsonResult = JSON.parseObject(result);
		JSONArray jsonNewsList = jsonResult.getJSONArray("contents");
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

			EventBean item=new EventBean();
			item.setId( Integer.parseInt(jsonNewsItem.getString("id")));
			item.setTitle(jsonNewsItem.getString("title"));
			item.setTime(jsonNewsItem.getString("update_time"));
			item.setView(jsonNewsItem.getString("view_count"));
			item.setAttention(jsonNewsItem.getString("attentionCount"));
			item.setType(jsonNewsItem.getJSONObject("type").getString("title"));
			item.setPeople(jsonNewsItem.getJSONObject("user").getString("nickname"));
			item.setDeadline(jsonNewsItem.getString("deadline"));
			item.setCover(jsonNewsItem.getString("cover_id"));
			list.add(item);
		}
		adapter.notifyDataSetChanged();
		return true;
	}*/


