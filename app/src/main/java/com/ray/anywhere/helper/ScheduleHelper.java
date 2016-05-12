package com.ray.anywhere.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * ����������Ϣ
 * @author wei8888go
 *
 */
public class ScheduleHelper {
	
	public SharedPreferences share;
	public SharedPreferences.Editor edit=null;
	private Context context;
	private static final String FILENAME="Schedule";
	@SuppressLint("CommitPrefEdits")
	public ScheduleHelper(Context context){
		this.context=context;
		this.share=this.context.getSharedPreferences(FILENAME,
				Context.MODE_PRIVATE);
		this.edit=share.edit();
	}
	public String getCurrentKBClassname(){
		return share.getString("classname","");
	}
	public String getCurrentKB(){
		return share.getString("kbstr","");
	}
	/**
	 * ���õ�ǰ�γ̱�ѧ��
	 */
	public void setCurrentKB(String classname,String kbstr){
		edit.putString("classname",classname);
		edit.putString("kbstr",kbstr);
		edit.commit();
	}


}
