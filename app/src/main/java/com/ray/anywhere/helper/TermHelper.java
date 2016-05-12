package com.ray.anywhere.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TermHelper {
	
	/**
	 * ��ȡ��ǰѧ��
	 * @return
	 */
	public static String getNowTerm(){
		String temp=null;
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+ 1;
		if(month>8){
			temp=year+"-"+(year+1)+"-1";
		}else if(month<2){
			temp=(year-1)+"-"+year+"-1";
		}else{
			temp=year+"-"+(year+1)+"-2";
		}
		return temp;
	}
	
	
	/**
	 * ��ȡ��ǰ5��ѧ��
	 * @return
	 */
	public static List<CharSequence> getAllTerm(){
		List<CharSequence> list=new ArrayList<CharSequence>();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+ 1;
		year=month>8?year:year-1;
		
		for(int i=year;i>year-4;i--){
			if(i==year){
				if(month>8){
					list.add(i+"-"+(i+1)+"��ѧ��");
				}else{
					list.add(i+"-"+(i+1)+"��ѧ��");
					list.add(i+"-"+(i+1)+"��ѧ��");
				}
			}else{
				list.add(i+"-"+(i+1)+"��ѧ��");
				list.add(i+"-"+(i+1)+"��ѧ��");
			}
		}
		return list;
		
	}
	
	/**
	 * ��2012-2013��ѧ��ת��Ϊ2012.1
	 * @return
	 */
	public static String getNumTerm(String Term){
		String year=Term.substring(0,4);
		String term=Term.indexOf("��ѧ��")!=-1?"1":"2";
		return year+"."+term;		
	}
	
	/**
	 * ��2012.1ת��Ϊ2012-2013��ѧ��
	 * @param Term
	 * @return
	 */
	public static String getStringTerm(String Term){
		if(Term==null||Term.equals(""))return "";
		int year=Integer.parseInt(Term.substring(0,4));
		String term=Term.substring(5,6).equals("1")?"��ѧ��":"��ѧ��";
		return year+"-"+(year+1)+term;
	}
	
	/**
	 * ��2012.1ת��Ϊ2012-2013-1
	 * @param Term
	 * @return
	 */
	public static String getStringTerm2(String Term){
		int year=Integer.parseInt(Term.substring(0,4));
		String term=Term.substring(5,6).equals("1")?"1":"2";
		return year+"-"+(year+1)+"-"+term;
	}
	
	/**
	 * ��2012-2013-1ת��Ϊ2012-2013��ѧ��
	 * @param Term
	 * @return
	 */
	public static String getStringTerm3(String Term){
		int year=Integer.parseInt(Term.substring(0,4));
		String[] data=Term.split("\\-");
		String term=data[2].equals("1")?"��ѧ��":"��ѧ��";
		return year+"-"+(year+1)+term;
	}
	
	/**
	 * ��2012.1����ת��Ϊ2012-2013��ѧ������
	 * @param term
	 * @return
	 */
	public static String[] getTermArr(String[] term){
		for(int i=0;i<term.length;i++){
			term[i]=getStringTerm(term[i]);
		}
		return term;
	}
	
	
	/**
	 * ��õ�ǰ���꼶
	 * @return
	 */
	public static List<CharSequence> getGrade(){
		List<CharSequence> list=new ArrayList<CharSequence>();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+ 1;
		int startYear=month>=8?year:year-1;
		for(int i=startYear; i>startYear-5; i--){
			list.add(String.valueOf(i));
		}
		return list;
		
	}
	
	
}

