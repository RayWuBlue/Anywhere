package com.ray.anywhere.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.database.SQLException;
import android.util.Log;

import com.ray.anywhere.JobActivity;
import com.ray.anywhere.JwcActivity;
import com.ray.anywhere.JwcLoginActivity;
import com.ray.anywhere.KbDetail;
import com.ray.anywhere.LibBookQuery;
import com.ray.anywhere.NewsPaperActivity;
import com.ray.anywhere.PhoneNumberActivity;
import com.ray.anywhere.R;
import com.ray.anywhere.RadioActivity;
import com.ray.anywhere.VideoNewsActivity;
import com.ray.anywhere.activity.Cet;
import com.ray.anywhere.activity.YangtzeNews;
import com.ray.anywhere.db.ChannelDao;
import com.ray.anywhere.db.SQLHelper;

public class ChannelManage {
	public static ChannelManage channelManage;
	/**
	 * Ĭ�ϵ��û�ѡ��Ƶ���б�
	 * */
	public static List<ChannelItem> defaultUserChannels;
	/**
	 * Ĭ�ϵ�����Ƶ���б�
	 * */
	public static List<ChannelItem> smileNewsChannels;
	private ChannelDao channelDao;
	/** �ж����ݿ����Ƿ�����û����� */
	private boolean userExist = false;
	static {
		defaultUserChannels = new ArrayList<ChannelItem>();
		smileNewsChannels = new ArrayList<ChannelItem>();

		defaultUserChannels.add(new ChannelItem(0,R.drawable.yu_news, "��������",1, 1,YangtzeNews.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.job, "��ҵ��Ƹ",1, 2,JobActivity.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.newspper, "����У��", 1,3,NewsPaperActivity.class));
		defaultUserChannels.add(new ChannelItem(0, R.drawable.jwc,"����֪ͨ",1, 4,JwcActivity.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.video, "��Ƶ����", 1,5,VideoNewsActivity.class));
		
		defaultUserChannels.add(new ChannelItem(0,R.drawable.timelda_table, "�α��ѯ",1, 1,KbDetail.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.libary, "����ͼ���",1, 2,LibBookQuery.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.phone, "�����ѯ", 1,3,PhoneNumberActivity.class));
		defaultUserChannels.add(new ChannelItem(0, R.drawable.cet,"������",1, 4,Cet.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.score, "������ѯ", 1,5,JwcLoginActivity.class));
		defaultUserChannels.add(new ChannelItem(0,R.drawable.radio, "�㲥���", 1,6,RadioActivity.class));
	}

	public static String getTitleById(int id){
		for(int i = 0;i<defaultUserChannels.size();i++){
			if(defaultUserChannels.get(i).getId()==id)
				return defaultUserChannels.get(i).getName();
		}
		return "";
	}
	
	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		return;
	}

	/**
	 * ��ʼ��Ƶ��������
	 * @param paramDBHelper
	 * @throws SQLException
	 */
	public static ChannelManage getManage(SQLHelper dbHelper)throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * ������е�Ƶ��
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}
	/**
	 * ��ȡ������Ƶ��
	 * @return ���ݿ�����û����� ? ���ݿ��ڵ��û�ѡ��Ƶ�� : Ĭ���û�ѡ��Ƶ�� ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}
	
	/**
	 * ��ȡ������Ƶ��
	 * @return ���ݿ�����û����� ? ���ݿ��ڵ�����Ƶ�� : Ĭ������Ƶ�� ;
	 */
	public List<ChannelItem> getOtherChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()){
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate= new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if(userExist){
			return list;
		}
		cacheList = smileNewsChannels;
		return (List<ChannelItem>) cacheList;
	}
	
	/**
	 * �����û�Ƶ�������ݿ�
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			channelDao.addCache(channelItem);
		}
	}
	
	/**
	 * ��������Ƶ�������ݿ�
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			channelDao.addCache(channelItem);
		}
	}
	
	/**
	 * ��ʼ�����ݿ��ڵ�Ƶ������
	 */
	private void initDefaultChannel(){
		Log.d("deleteAll", "deleteAll");
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		saveOtherChannel(smileNewsChannels);
	}
}
