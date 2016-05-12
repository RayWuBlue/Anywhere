package com.ray.anywhere.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.bean.LibBook;
public class BookListAdapter extends BaseAdapter {
    private Context mContext;
    private List<LibBook>list;
    public BookListAdapter(Context mContext,List<LibBook>list){
    	this.mContext=mContext;
    	this.list=list;
    }
	@Override
	public int getCount() {
		if(list!=null)
		    return list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存�?
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存�?
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null)
			convertView=LayoutInflater.from(mContext).inflate(R.layout.lib_book_item,null);
        TextView title=(TextView)convertView.findViewById(R.id.book_title);
        TextView author=(TextView)convertView.findViewById(R.id.book_author);
        TextView publishing=(TextView)convertView.findViewById(R.id.book_publishing);
        TextView price=(TextView)convertView.findViewById(R.id.book_price);
        TextView callNum=(TextView)convertView.findViewById(R.id.book_callNum);
        title.setText(list.get(position).getTitle());
        author.setText(list.get(position).getAuthor());
        publishing.setText(list.get(position).getPublishing());
        price.setText(list.get(position).getPrice());
        callNum.setText(list.get(position).getCallNum());
        if(position%2==0)
        	convertView.setBackgroundColor(mContext.getResources().getColor(R.color.act_bg));
        else 
        	convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        return convertView;
	}
	public void setData(List<LibBook> list){
		this.list=list;
	}

}
