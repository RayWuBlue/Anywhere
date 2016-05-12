package com.ray.anywhere.widgets;

import java.util.List;
import java.util.Map;

import com.ray.anywhere.R;
import com.ray.anywhere.config.ServerConfig;
import com.ray.anywhere.helper.JsonHelper;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TextUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


/**
 * ����������ѯѧ�Ų����б���ʾ
 * @author wei8888go
 *
 */

public class StuidSelect extends AsyncTask<String, Void, String> {
	
	
	//�¼����ݽӿ�,����ѧ��
	public interface StuidSelectInterface{  
        public void onSelect(String stuid);  
    } 
	
	public StuidSelectInterface callback=null;
	public Context mcontext;
	public MyAlertDialog mad;
	
	public StuidSelect(Context context){
		this.mcontext=context;
	}
	
	public void getSelected(StuidSelectInterface callback){
		this.callback=callback;
	}

	protected String doInBackground(String... params) {
		String result=GetUtil.getRes(ServerConfig.HOST+"/schoolknow/api/getNameByStuid.php?name="+params[0]);
		return result;
	}
	protected void onPostExecute(String result) {
		if(TextUtils.isSpace(result)){
			T.showShort(mcontext, "��ѯ����������");
		}else if(result.trim().equals("[]")){
			T.showShort(mcontext, "�����ֵ�ͬѧ������");
		}else{
			try {
				final List<Map<String,Object>> UserList=new JsonHelper(result).parseJson(
						new String[]{"name","stuid","className","collegeName"});
				if(UserList.size()==1){
					callback.onSelect(UserList.get(0).get("stuid").toString());
				}else{
					mad=new MyAlertDialog(mcontext);
					mad.setTitle("��ѡ���ѯ�Ķ���");
					ListView UserLv=new ListView(mcontext);
					UserLv.setAdapter(new SimpleAdapter(mcontext,UserList,R.layout.item_getuserinfo_lv,
							new String[]{"name","stuid","className","collegeName"},
							new int[]{R.id.item_getuserinfo_name,R.id.item_getuserinfo_stuid,
							R.id.item_getuserinfo_classname,R.id.item_getuserinfo_collegename}));
					UserLv.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							String stuid=UserList.get(position).get("stuid").toString();
							callback.onSelect(stuid);
							mad.dismiss();
						}
					});
					mad.setContentView(UserLv);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onPostExecute(result);
	}

}
