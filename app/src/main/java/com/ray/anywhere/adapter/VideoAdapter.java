package com.ray.anywhere.adapter;

import android.content.Context;
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

public class VideoAdapter extends BaseAdapter {
	private Context context;
	private List<IndexItemBase> list;
	private LayoutInflater inflater;
	
	public VideoAdapter(Context context,List<IndexItemBase> list){
		this.list=list;
		this.context = context;
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
			convertView = inflater.inflate(R.layout.item_video_news, null, false);
			holder.title=(TextView) convertView.findViewById(R.id.item_title);
			holder.time=(TextView) convertView.findViewById(R.id.item_publish_time);
			holder.video_image=(ImageView) convertView.findViewById(R.id.video_image);			
			holder.view=(TextView) convertView.findViewById(R.id.item_view);			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(item.getTitle());
		holder.time.setText(TimeUtil.friendlyFormat(item.getTime()));//��ʱ���ת��Ϊ�Ѻõĸ�ʽ���缸Сʱǰ
		holder.view.setText("���Ź�"+item.getView()+"��");
		String cover = item.getCover();
		if(cover!=null&&!"".equals(cover)){
				holder.video_image.setVisibility(View.VISIBLE);

		}else{
			holder.video_image.setImageDrawable(context.getResources().getDrawable(R.drawable.nothing));
		}
		return convertView;
	}
	
	static class ViewHolder {
		TextView title;
		TextView time;
		TextView view;
		ImageView video_image;
	}

}
