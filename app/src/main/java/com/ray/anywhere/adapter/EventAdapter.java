package com.ray.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.bean.EventBean;

import java.util.List;

public class EventAdapter extends BaseAdapter {
	
	private List<EventBean> list;
	private LayoutInflater inflater;
	public EventAdapter(Context context,List<EventBean> list){
		this.list=list;
		this.inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		EventBean item=list.get(position);
		if (convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_event_news, null, false);
			holder.title=(TextView) convertView.findViewById(R.id.item_title);
			holder.cover=(ImageView) convertView.findViewById(R.id.item_image);
			holder.btnJoin=(Button) convertView.findViewById(R.id.item_btn_join);

			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		System.out.println("�����:"+item.getTitle());
		holder.title.setText(item.getTitle());
		String cover = item.getCover();
		if(cover!=null&&!"".equals(cover)){
				holder.cover.setVisibility(View.VISIBLE);
		}else{
			holder.cover.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	static class ViewHolder {
		 TextView title;
		 ImageView cover;
		 Button btnJoin;
	}

}
