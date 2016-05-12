package com.ray.anywhere.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.bean.ChannelItem;
import com.ray.anywhere.widgets.RoundedImageView;

public class HomeChannelAdapter extends BaseAdapter {

	private List<ChannelItem> channels;
	LayoutInflater inflater = null;
	private Context activity;
	private int[] color = new int[]{R.drawable.shape_fill_circle_blue,R.drawable.shape_fill_circle_green,R.drawable.shape_fill_circle_yellow,R.drawable.shape_fill_circle_purple};
	public HomeChannelAdapter(Context activity, List<ChannelItem> channels) {
		this.activity = activity;
		this.channels = channels;
		inflater = LayoutInflater.from(activity);
	}

	@Override
	public int getCount() {
		return channels.size();
	}

	@Override
	public Object getItem(int position) {
		return channels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return channels.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_home_channel_gv, null);
			mHolder = new ViewHolder();
			mHolder.title = (TextView)view.findViewById(R.id.item_title);
			mHolder.image = (ImageView)view.findViewById(R.id.item_image);
			mHolder.bg = view.findViewById(R.id.item_image_background);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		//��ȡposition��Ӧ������
		ChannelItem item = (ChannelItem)getItem(position);
		mHolder.title.setText(item.getName());
		mHolder.image.setBackgroundResource(item.getCoverRes());
		mHolder.bg.setBackgroundResource(color[position%color.length]);
		return view;
	}

	static class ViewHolder {

		TextView title;

		ImageView image;
		
		View bg;
	}
}
