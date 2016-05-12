package com.ray.anywhere.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.utils.T;

public class CetData extends BaseActivity {
	
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_cet_data);
		setTitle("��������ѯ");
		
		String cetDa=getIntent().getStringExtra("cet");
		T.showShort(this, cetDa);
		System.out.println("CETDATA:"+cetDa);
		String[] data=cetDa.split(",");
		if(data==null||data.length==0){
			T.showShort(this, "����������");
			finish();
		}
		//	String result = name+","+school+","+type+","+id+","+time+","
		//+elementtwo.getElementsByTag("td").text().replace("������", ",").replace("�Ķ���", ",").replace("д���뷭�룺", ",").replace("����", ",");
		String[] cetInfo = new String[data.length];
		String[] param;
		if(getIntent().getIntExtra("entry",0)==1)
			param=new String[]{"��������","����","�Ķ�","����","�ܷ�"};
		else
			param=new String[]{"����","ѧУ","��������","ѧ��","����ʱ��","�ܷ�","����","�Ķ�","д���뷭��"};
		
		//��Ӳ���
		for(int i=0;i<cetInfo.length;i++){
			if(getIntent().getIntExtra("",0)==1){
				if("6".equals(data[i]))
					data[i] = "Ӣ���ļ�";
			}
			cetInfo[i]=param[i]+":  "+data[i];
		}
		
		lv=(ListView) super.findViewById(R.id.cet_data_lv);
		
		lv.setAdapter(new CetDataAdapter(this,cetInfo));
	}


	
	
	/**
	 * cet��ʾЧ��������
	 * @author wei8888go
	 *
	 */
	public class CetDataAdapter extends BaseAdapter{
		
		public String[] list;
		public Context context;
		private LayoutInflater mInflater;
		
		public CetDataAdapter(Context context,String[] list){
			this.context=context;
			this.list=list;
			mInflater = LayoutInflater.from(this.context);
		}

		@Override
		public int getCount() {
			return list.length;
		}

		@Override
		public Object getItem(int position) {
			return list[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null||convertView.getTag(R.drawable.ic_launcher+position) == null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_cet_data, null);
				holder.text=(TextView) convertView.findViewById(R.id.cet_data_item_text);
				convertView.setTag(R.drawable.ic_launcher + position);
			}else{
				holder = (ViewHolder) convertView.getTag(R.drawable.ic_launcher
						+ position);
			}
			holder.text.setText(list[position]);
			convertView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_list_item));
			return convertView;
		}
		
	}
	
	public class ViewHolder {
		TextView text;
	}
	

}
