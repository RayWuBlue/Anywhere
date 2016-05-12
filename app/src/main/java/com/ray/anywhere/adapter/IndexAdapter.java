package com.ray.anywhere.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.base.IndexItemBase;
import com.ray.anywhere.utils.TimeUtil;

import java.util.List;

public class IndexAdapter extends BaseAdapter {
	
	private List<IndexItemBase> list;
	private LayoutInflater inflater;
	
	public IndexAdapter(Context context,List<IndexItemBase> list){
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
		IndexItemBase item=list.get(position);
		if (convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_index_news, null, false);
			holder.title=(TextView) convertView.findViewById(R.id.item_title);
			holder.time=(TextView) convertView.findViewById(R.id.item_publish_time);
			holder.intro = (TextView)convertView.findViewById(R.id.item_abstract);
			holder.type = (TextView)convertView.findViewById(R.id.item_type);
			holder.source = (TextView)convertView.findViewById(R.id.item_source);
			holder.right_image=(ImageView) convertView.findViewById(R.id.right_image);			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(item.getTitle());
		holder.time.setText(TimeUtil.friendlyFormat(item.getTime()));//��ʱ���ת��Ϊ�Ѻõĸ�ʽ���缸Сʱǰ
		if(!TextUtils.isEmpty(item.getIntro()))
			holder.intro.setText(item.getIntro());
		else
			holder.intro.setVisibility(View.GONE);
		holder.type.setText(item.getView()+"�����");
		holder.source.setText(item.getSource());
		String cover = item.getCover();
		if(cover!=null&&!"".equals(cover)){
				holder.right_image.setVisibility(View.VISIBLE);
		}else{
			holder.right_image.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	static class ViewHolder {
		TextView title;
		TextView intro;
		TextView time;
		TextView type;
		TextView source;
		ImageView image1;
		ImageView image2;
		ImageView image3;
		ImageView right_image;
	}

}
