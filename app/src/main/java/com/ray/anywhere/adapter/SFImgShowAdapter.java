package com.ray.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ray.anywhere.R;
import com.ray.anywhere.utils.ViewHolder;

import java.util.List;

public class SFImgShowAdapter extends BaseAdapter {
	
	private Context context;
	private  List<String> list;
	public GridView gv;
	private int ResId;
	public SFImgShowAdapter(Context context, List<String> list,GridView gv,int ResId) {
		this.context = context;
		this.list = list;
		this.gv=gv;
		this.ResId=ResId;

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
	          .inflate(ResId, parent, false);
	    }
		ImageView img = ViewHolder.get(convertView, R.id.gv_item_img);
		String url=list.get(position);
		return convertView;
	}
	
	

}
