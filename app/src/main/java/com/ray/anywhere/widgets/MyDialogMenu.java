package com.ray.anywhere.widgets;




import com.ray.anywhere.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ��QQ�����˵�
 * @author peng
 *
 */


public class MyDialogMenu {
	
	public Context mcontext;
	public Dialog dialog;
	public String[] param;
	public Button btn;
	public ListView lv;
	
	public OnItemClickCallBack callBack;
	
	public interface OnItemClickCallBack{
		public void OnItemClick(View view,int position);
	}
	
	public MyDialogMenu(Context context,String param[]){
		this.mcontext=context;
		this.param=param;
		
		//���ò˵�����
		View layout = LayoutInflater.from(context).inflate(R.layout.wg_dialog_menu, null);
		btn=(Button) layout.findViewById(R.id.wg_dialog_menu_btn);
		lv=(ListView) layout.findViewById(R.id.wg_dialog_menu_lv);
		if(param.length>=1){
			btn.setText(param[0]);
			lv.setAdapter(new MyDialogMenuAdapter(context, param));
		}
		
		/**
		 * �¼�����
		 */
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				callBack.OnItemClick(view,0);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				callBack.OnItemClick(view,position+1);
			}
		});
		
		
		//���õ���������
		dialog = new Dialog(context, R.style.transparentFrameWindowStyle);
		dialog.setContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// ������ʾ����
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wl.y = wm.getDefaultDisplay().getHeight();
		
		// ������������Ϊ�˱�֤��ť����ˮƽ����
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// ������ʾλ��
		dialog.onWindowAttributesChanged(wl);
		
		// ���õ����Χ��ɢ
		//dialog.setCanceledOnTouchOutside(true);
		
		dialog.show();
	}
	
	
	/**
	 * �¼���
	 * @param callBack
	 */
	public void setOnItemClick(OnItemClickCallBack callBack){
		this.callBack=callBack;
	}
	
	public void dismiss(){
		dialog.dismiss();
	}
	
	public void show(){
		dialog.show();
	}
	
	
	/**
	 * ����adapter
	 * @author peng
	 *
	 */
	public class MyDialogMenuAdapter extends BaseAdapter{
		
		public Context context;
		public String param[];
		private LayoutInflater inflater;

		public MyDialogMenuAdapter(Context context, String[] param) {
			this.context = context;
			this.param = param;
			this.inflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return param.length-1;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return param[position+1];
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
				convertView=inflater.inflate(R.layout.wg_dialog_menu_lv_item, null, false);
				holder.btn=(TextView) convertView.findViewById(R.id.wg_dialog_menu_item_btn);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.btn.setText(param[position+1]);
			if(getCount()==1){
				holder.btn.setBackgroundResource(R.drawable.photo_cancel_selector);
			}else if(getCount()==2){
				if(position==0){
					holder.btn.setBackgroundResource(R.drawable.photo_gallery_selector);
				}else{
					holder.btn.setBackgroundResource(R.drawable.photo_camera_selector);
				}
			}else if(getCount()>=3){
				if(position==0){
					holder.btn.setBackgroundResource(R.drawable.photo_gallery_selector);
				}else if(position==getCount()-1){
					holder.btn.setBackgroundResource(R.drawable.photo_camera_selector);
				}else{
					holder.btn.setBackgroundResource(R.drawable.photo_other_selector);
				}
			}
			return convertView;
		}
	}
	static class ViewHolder {
		TextView btn;
	}
}
