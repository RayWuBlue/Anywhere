package com.ray.anywhere.helper;

import com.ray.anywhere.base.CountdownItem;
import com.ray.anywhere.db.CountdownDB;
import com.ray.anywhere.utils.TimeUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class InitHelper {
	public SharedPreferences share;
	public SharedPreferences.Editor edit=null;
	private Context context;
	private static final String FILENAME="Init";
	
	@SuppressLint("CommitPrefEdits")
	public InitHelper(Context context){
		this.context=context;
		this.share=this.context.getSharedPreferences(FILENAME,
				Context.MODE_PRIVATE);
		this.edit=share.edit();
	}
	
	//��ʼ��
	public void install(){
		if(!hasInit()){
			this.InitCountDown();
			setInit(true);
		}
		
	}
	
	//��ʼ��������
	public void InitCountDown(){
		CountdownItem[] data=new CountdownItem[]{
				new CountdownItem("�ļ�",TimeUtil.createtimesamp(2014,6,21,9,0),"����15#����"),
				new CountdownItem("����",TimeUtil.createtimesamp(2014,6,21,9,0),"����15#����"),
				new CountdownItem("2013�����",TimeUtil.createtimesamp(2013,12,31,23,59),"����15#����"),
				new CountdownItem("����",TimeUtil.createtimesamp(2014,1,4,8,30),"����15#����"),
				new CountdownItem("2014����",TimeUtil.createtimesamp(2014,1,31,0,0),""),
				new CountdownItem("���˽�",TimeUtil.createtimesamp(2014,2,14,0,0),""),
				new CountdownItem("������ȼ�����",TimeUtil.createtimesamp(2014,3,29,10,0),""),
				new CountdownItem("��",TimeUtil.createtimesamp(2014,5,24,9,0),""),
				new CountdownItem("�߿�",TimeUtil.createtimesamp(2014,6,7,9,0),"")
		};
		for(int i=0;i<data.length;i++){
			new CountdownDB(context).insert(data[i]);
		}
	}
	
	public void setInit(boolean b){
		edit.putBoolean("init", b);
		edit.commit();
	}
	
	public boolean hasInit(){
		return share.getBoolean("init",false);
	}
	
	/**
	 * ����֮ǰ�İ汾��
	 * @return
	 */
	public int getLastVersion(){
		return share.getInt("version", 0);
	}
	
	/**
	 * ���ø��º�İ汾��
	 */
	public void setLastVersion(){
		edit.putInt("version", VersionHelper.getVerCode(context));
		edit.commit();
	}
	
	/**
	 * ���ǰ�װ��������ҳ��,�����ñ�������
	 * @return
	 */
	public boolean checkHasInit(){
		if(VersionHelper.getVerCode(context)!=getLastVersion()){
			install();
			setLastVersion();
			return false;
		}
		return true;
	}
	
	
}
