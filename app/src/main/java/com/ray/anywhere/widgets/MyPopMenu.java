package com.ray.anywhere.widgets;

import java.util.ArrayList;

import com.ray.anywhere.R;
import com.ray.anywhere.helper.SystemHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;



public class MyPopMenu implements OnItemClickListener {
	
	public interface MyPopMenuImp {
		public void onItemClick(int index);
	}
	
	private MyPopMenuImp listener;
	
	private ArrayList<String> itemList;
	private Context context;
	private PopupWindow popupWindow;
	private ListView listView;
	private LayoutInflater inflater;
	private LinearLayout layout;

	
	public MyPopMenu(Context context) {
		this.context = context;

		itemList = new ArrayList<String>(5);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.wg_pop_menu, null);

		listView = (ListView) view.findViewById(R.id.mypopmenu_listView);
		layout=(LinearLayout) view.findViewById(R.id.mypopmenu_layout);
		
		listView.setAdapter(new PopAdapter());
		listView.setOnItemClickListener(this);

		popupWindow = new PopupWindow(view, 
				SystemHelper.getScreenWidth(context)/2,  //��������Ҫ�Լ�ָ����ʹ�� WRAP_CONTENT ��ܴ�
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı�����������ģ�
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (listener != null) {
			listener.onItemClick(position);
		}
		dismiss();
	}

	// ���ò˵�����������
	public void setOnItemClickListener(MyPopMenuImp listener) {
		 this.listener = listener;
	}

	// ������Ӳ˵���
	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	// ������Ӳ˵���
	public void addItem(String item) {
		itemList.add(item);
	}

	// ����ʽ ���� pop�˵� parent ���½�
	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(parent, 10,
		// ��֤�ߴ��Ǹ�����Ļ�����ܶ�����
				context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));		
		// ʹ��ۼ�
		popupWindow.setFocusable(true);
		// ����������������ʧ
		popupWindow.setOutsideTouchable(true);
		// ˢ��״̬
		popupWindow.update();
	}
	
	/**
	 * ��ʾ�����м�
	 * @param v
	 */
	public void showCenter(View parent){
		int xoff = popupWindow.getWidth()/2-parent.getWidth()/2;
		popupWindow.showAsDropDown(parent, -xoff, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();

		
	}
	

	
	

	// ���ز˵�
	public void dismiss() {
		popupWindow.dismiss();
	}

	// ������
	private final class PopAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_popmenu,parent, false);
				holder = new ViewHolder();
				convertView.setTag(holder);
				holder.groupItem = (TextView) convertView.findViewById(R.id.mypopmenu_textView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.groupItem.setText(itemList.get(position));

			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
}