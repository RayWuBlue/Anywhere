package com.ray.anywhere.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.ray.anywhere.R;
import com.ray.anywhere.activity.NewsActivity;
import com.ray.anywhere.activity.WeiboContent;
import com.ray.anywhere.base.SchoolFellowBase;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.RegUtils;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.utils.ViewHolder;
import com.ray.anywhere.widgets.EmotionBox;

import java.util.List;

public class SchoolFellowAdapter extends BaseAdapter {
	
	private List<SchoolFellowBase> list;
	private Context context;
	public ListView lv;
	public SchoolFellowAdapter(Context context,List<SchoolFellowBase> list){
		this.list=list;
		this.context=context;
	}

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
	
	public void update(){
		notifyDataSetChanged();
	}
	


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
	        convertView = LayoutInflater.from(context)
	          .inflate(R.layout.item_school_fellow_lv, parent, false);
	    }
		 ImageView head = ViewHolder.get(convertView, R.id.schoolfellow_item_user_img);
		 TextView nick=ViewHolder.get(convertView, R.id.schoolfellow_item_user_nick);
		 TextView time=ViewHolder.get(convertView, R.id.schoolfellow_item_time);
		 TextView content=ViewHolder.get(convertView, R.id.schoolfellow_item_content);
		 
		 LinearLayout ll_single=ViewHolder.get(convertView, R.id.image_layout_single);

		 TextView commentBtn=ViewHolder.get(convertView, R.id.schoolfellow_item_bar_comment);
		 TextView praiseBtn=ViewHolder.get(convertView, R.id.schoolfellow_item_bar_praise);
		 
		 SchoolFellowBase item=list.get(position);
		 

		 praiseBtn.setText(item.getLikeNum());
		 
		 content.setOnClickListener(new onBarClickListener(position));
		 commentBtn.setOnClickListener(new onBarClickListener(position));
		 praiseBtn.setOnClickListener(new onBarClickListener(position));
		 
		 Drawable drawable=null;
		 if(item.isHasLike()){
			 drawable = context.getResources().getDrawable(R.drawable.timeline_icon_like);
		 }else{
			 drawable = context.getResources().getDrawable(R.drawable.timeline_icon_like_disable);
		 }
		 drawable.setBounds(0, 0, 45, 45);
	     praiseBtn.setCompoundDrawables(drawable, null, null, null);
		
	     drawable = context.getResources().getDrawable(R.drawable.timeline_icon_comment);
	     drawable.setBounds(0, 0, 45, 45);
	     commentBtn.setCompoundDrawables(drawable, null, null, null);
	     
		 nick.setText(item.getNickname());
		 time.setText(TimeUtil.getSquareTime(Long.parseLong(item.getTime())));
		 commentBtn.setText(item.getCommentNum());

		content.setText(EmotionBox.convertNormalStringToSpannableString(context,
				RegUtils.replaceImage(item.getContent())),BufferType.SPANNABLE);
		return convertView;
	}




	//���鹤������ť�¼�
	class onBarClickListener implements OnClickListener{
		public int position;
		public onBarClickListener(int position){
			this.position=position;
		}
		public void onClick(final View v) {
			final SchoolFellowBase item = (SchoolFellowBase)getItem(position);
			switch(v.getId()){
			case R.id.schoolfellow_item_content:
			case R.id.schoolfellow_item_bar_comment:
				Intent it=new Intent(context,WeiboContent.class);
				Bundle mBundle = new Bundle();  
			    mBundle.putSerializable(NewsActivity.SER_KEY,item);
			    mBundle.putInt("position", position);
			    it.putExtras(mBundle); 
				context.startActivity(it);
				break;
			case R.id.schoolfellow_item_bar_praise:
				new AsyncTask<Integer, Void, String>() {
					@Override
					protected String doInBackground(Integer... params) {
						return GetUtil.getRes(Api.Weibo.postLike(new LoginHelper(context).getUid(), params[0]+""));
					}
					@Override
					protected void onPostExecute(String result) {
					}
				}.execute(item.getId());
				break;
			default:
				break;
			}
		}
		
	}
}
