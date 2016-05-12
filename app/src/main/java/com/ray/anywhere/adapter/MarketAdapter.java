package com.ray.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.utils.ViewHolder;

import java.util.List;
import java.util.Map;

public class MarketAdapter extends BaseAdapter {
	
	public Context context;
	private List<Map<String,Object>> list;
	
	public MarketAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
	        convertView = LayoutInflater.from(context)
	          .inflate(R.layout.item_market_tz_lv, parent, false);
	    }
		ImageView img = ViewHolder.get(convertView, R.id.market_tz_lv_item_img);
		TextView title=ViewHolder.get(convertView, R.id.market_tz_lv_item_text);
		TextView time=ViewHolder.get(convertView, R.id.market_tz_lv_item_time);
		TextView num=ViewHolder.get(convertView, R.id.market_tz_lv_item_reply);
		
		Map<String,Object> map=list.get(position);

		
		title.setText(map.get("title").toString());
		time.setText("����ʱ��:"+TimeUtil.getMarketTime(Long.parseLong(map.get("time").toString())));
		num.setText("");
		
		return convertView;
	}

}
