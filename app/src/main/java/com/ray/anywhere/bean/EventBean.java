package com.ray.anywhere.bean;

import java.io.Serializable;

public class EventBean implements Comparable<EventBean>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2688280672772261774L;
	private int id;
	private String view;
	private String attention;
	private String comment_num;
	private String title;
	private String people;
	private String type;
	private String time;
	private String deadline;
	private String summary;
	private String cover;
	public EventBean() {}
	@Override
	public int compareTo(EventBean another) {
		return 0;
	}
	public String getAttention() {
		return attention;
	}
	public String getComment_num() {
		return comment_num;
	}
	public String getCover() {
		return cover;
	}
	public String getDeadline() {
		return deadline;
	}
	public int getId() {
		return id;
	}
	public String getPeople() {
		return people;
	}
	public String getSummary() {
		return summary;
	}

	public String getTime() {
		return time;
	}
	public String getTitle() {
		return title;
	}
	public String getType() {
		return type;
	}
	public String getView() {
		return view;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public void setComment_num(String comment_num) {
		this.comment_num = comment_num;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPeople(String people) {
		this.people = people;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setView(String view) {
		this.view = view;
	}
}
