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
import com.ray.anywhere.bean.YUNewsItem;
import com.ray.anywhere.utils.TimeUtil;

import java.util.List;

public class YUNewsAdapter extends BaseAdapter {
	List<YUNewsItem> newsList;
	Context activity;
	LayoutInflater inflater = null;
	public YUNewsAdapter(Context activity, List<YUNewsItem> newsList) {
		this.activity = activity;
		this.newsList = newsList;
		inflater = LayoutInflater.from(activity);
	}
	@Override
	public int getCount() {
		return newsList == null ? 0 : newsList.size();
	}

	@Override
	public YUNewsItem getItem(int position) {
		if (newsList != null && newsList.size() != 0) {
			return newsList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_yu_news, null);
			mHolder = new ViewHolder();
			mHolder.title = (TextView)view.findViewById(R.id.item_title);
			mHolder.publishTime = (TextView)view.findViewById(R.id.item_publish_time);
			mHolder.right_image = (ImageView)view.findViewById(R.id.right_image);
			view.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		//��ȡposition��Ӧ������
		YUNewsItem news = getItem(position);
		mHolder.title.setText(news.getTitle());
		mHolder.publishTime.setText(TimeUtil.friendlyFormat(news.getPublishTime()));
		if(!TextUtils.isEmpty(news.getImageRight())){
			mHolder.right_image.setVisibility(View.VISIBLE);
		}else
			mHolder.right_image.setVisibility(View.GONE);
		return view;
	}


	static class ViewHolder {

		TextView title;

		TextView publishTime;

		ImageView right_image;

	}

}
