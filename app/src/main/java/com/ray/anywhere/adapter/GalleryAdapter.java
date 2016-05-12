package com.ray.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.utils.ViewHolder;
import com.ray.anywhere.widgets.QImageView;
import com.ray.anywhere.widgets.RoundProgressBar;

import java.util.List;
/**
 * Gallery���������࣬��Ҫ���ڼ���ͼƬ
 * @author lyc
 *
 */
public class GalleryAdapter extends BaseAdapter {

	private Context context;
	private TextView tv;
	public List<String> list;

	public GalleryAdapter(Context context,TextView tv,List<String> list) {
		this.context = context;
		this.tv=tv;
		this.list=list;
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
		if (convertView == null) {
	        convertView = LayoutInflater.from(context)
	          .inflate(R.layout.item_photoview, parent, false);
	    }
		 tv.setText((position+1)+"/"+(list.size()));
		QImageView img = ViewHolder.get(convertView, R.id.item_photoview_img);
		final RoundProgressBar pb=ViewHolder.get(convertView, R.id.item_photoview_pb);
		pb.setMax(100);
		return convertView;
	}

}
