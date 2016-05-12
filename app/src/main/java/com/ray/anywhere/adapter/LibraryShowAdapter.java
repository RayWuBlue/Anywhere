package com.ray.anywhere.adapter;

import java.util.List;
import java.util.Map;

import com.ray.anywhere.R;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.widgets.MyAlertDialog;
import com.ray.anywhere.widgets.MyAlertDialog.MyDialogInt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LibraryShowAdapter extends BaseAdapter {
	
	private List<Map<String,Object>> list;
	private LayoutInflater inflater;
	private Context context;
	private MyAlertDialog mad;
	private String params;
	
	public LibraryShowAdapter(Context context,List<Map<String,Object>> list,String params){
		this.list=list;
		this.inflater = LayoutInflater.from(context);
		this.context=context;
		this.params=params;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;
		if (convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_library_data, null, false);
			holder.name=(TextView) convertView.findViewById(R.id.library_book);
			holder.borrow=(TextView) convertView.findViewById(R.id.library_borrow);
			holder.ret=(TextView) convertView.findViewById(R.id.library_return);
			holder.num=(TextView) convertView.findViewById(R.id.library_num);
			holder.btn=(Button) convertView.findViewById(R.id.library_renew);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Map<String,Object> map=list.get(position);
		holder.name.setText(map.get("book").toString());
		holder.borrow.setText("���ʱ��:"+map.get("borrow").toString());
		holder.ret.setText("Ӧ��ʱ��:"+map.get("return").toString());
		holder.num.setText("�������:"+map.get("num").toString());
		
		String[] ret_time=map.get("return").toString().split("\\-");
		Long retTimeSamp=TimeUtil.createtimesamp(intParse(ret_time[0]),intParse(ret_time[1]),
				intParse(ret_time[2]), 0, 0);
		
		
		//���ڲ���������
		if(TimeUtil.betweenDays(retTimeSamp)<=0){
			holder.ret.setTextColor(Color.RED);
			holder.btn.setEnabled(false);
		}else if(TimeUtil.betweenDays(retTimeSamp)<=7){
			holder.ret.setTextColor(Color.rgb(176,211,67));
			holder.btn.setEnabled(true);
		}else{
			holder.ret.setTextColor(Color.rgb(129,129,129));
			holder.btn.setEnabled(true);
			
			//ֻ������һ��
			if(Integer.parseInt(map.get("num").toString())>0){
				holder.btn.setEnabled(false);
			}else{
				holder.btn.setEnabled(true);
			}
		}
		
		
		
		
		
		holder.btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mad=new MyAlertDialog(context);
				mad.setTitle("��Ϣ��ʾ");
				mad.setMessage("��ȷ��Ҫ���衶"+map.get("book").toString()+"����?");
				mad.setLeftButton("ȷ��",new MyDialogInt() {
					@Override
					public void onClick(View view) {
						final String param=params+"&action=renew&id="+map.get("id")+"&code="+map.get("code");
						new Thread(new Runnable() {
							@Override
							public void run() {
								Message msg=new Message();
								msg.what=1212;
								msg.obj=GetUtil.getRes(param);
								handler.sendMessage(msg);
							}
						}).start();
					}
				});
				mad.setRightButton("ȡ��",new MyDialogInt() {
					@Override
					public void onClick(View view) {
						mad.dismiss();
					}
				});
				
			}
		});
		
		return convertView;
	}
	
	@SuppressLint("HandlerLeak")
	Handler  handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1212){
				T.showShort(context,msg.obj.toString());
				mad.dismiss();
			}
		}
		
	};
	
	public int intParse(String num){
		return Integer.parseInt(num);
	}
	
	static class ViewHolder {
		TextView name;
		TextView borrow;
		TextView ret;
		TextView num;
		Button btn;
	}

}
