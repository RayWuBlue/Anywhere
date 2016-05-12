package com.ray.anywhere.base;


/**
 * ��Ϣ��
 * @author wei8888go
 *
 */

public class MessageItem {
	public static final int MESSAGE_TYPE_TEXT = 1;
	public static final int MESSAGE_TYPE_IMG = 2;
	public static final int MESSAGE_TYPE_FILE = 3;
	
	private int id;
	private int msgType;
	private long time;  //��Ϣ����
	private String message;// ��Ϣ����
	private String uid;
	private boolean isComMeg = true;// �Ƿ�Ϊ�յ�����Ϣ
	
	
	public MessageItem(int msgType,long date, String message,
			String uid, boolean isComMeg) {
		this.msgType = msgType;
		this.time = date;
		this.message = message;
		this.uid = uid;
		this.isComMeg = isComMeg;
		id=0;
	}
	
	public MessageItem(int id,int msgType,long date, String message,
			String uid, boolean isComMeg) {
		this.msgType = msgType;
		this.time = date;
		this.message = message;
		this.uid = uid;
		this.isComMeg = isComMeg;
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMsgType() {
		return msgType;
	}


	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}


	


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public String getUid() {
		return uid;
	}


	public void setHeadImg(String uid) {
		this.uid = uid;
	}


	public boolean isComMeg() {
		return isComMeg;
	}


	public void setComMeg(boolean isComMeg) {
		this.isComMeg = isComMeg;
	}
		
}
