package com.ray.anywhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.ray.anywhere.R;
import com.ray.anywhere.entity.NewsComment;
import com.ray.anywhere.utils.RegUtils;
import com.ray.anywhere.widgets.EmotionBox;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
	
	private List<NewsComment> list;
	private LayoutInflater inflater;
	private Context context;
	public ListView lv;
	public CommentAdapter(Context context,List<NewsComment> list){
		this.list=list;
		this.context=context;
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
		NewsComment comment=list.get(position);
		if (convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_comment_lv, null, false);
			holder.name=(TextView) convertView.findViewById(R.id.comment_lv_user);
			holder.time=(TextView) convertView.findViewById(R.id.comment_lv_time);
			holder.content=(TextView) convertView.findViewById(R.id.comment_lv_content);
			holder.head=(ImageView) convertView.findViewById(R.id.comment_lv_user_head);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(comment.getNickname());
		holder.time.setText(comment.getTime());
		holder.content.setText(EmotionBox.convertNormalStringToSpannableString(context,RegUtils.replaceImage(comment.getContent())),BufferType.SPANNABLE);
		holder.name.setOnClickListener(new Onclick(context,comment.getUid()+""));
		holder.head.setOnClickListener(new Onclick(context,comment.getUid()+""));
		return convertView;
	}

	class Onclick implements OnClickListener{
		public String uid;
		public Context context;
		public Onclick(Context context,String uid){
			this.uid=uid;
			this.context=context;
		}
		public void onClick(View v) {
		}
	}
	
	static class ViewHolder {
		ImageView head;
		TextView name;
		TextView time;
		TextView content;
	}

}
