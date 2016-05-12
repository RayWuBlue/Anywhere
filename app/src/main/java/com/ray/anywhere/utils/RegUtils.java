package com.ray.anywhere.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ray.anywhere.config.ServerConfig;

public class RegUtils {
	
	/**
	 * ���ַ����л�ȡͼƬ��ַ
	 * @param s
	 * @return
	 */
	/*public static List<String> getImgFromString(String s)   
	{   
	   String regex;   
	   List<String> list = new ArrayList<String>();   
	   regex = "src=[\"|\'](.*?)[\"|\']";  
	   //regex = "<[img|IMG].*?src=[\'|\"](.*?)[\'|\"].*?/>";
	   Pattern pa = Pattern.compile(regex, Pattern.DOTALL);   
	   Matcher ma = pa.matcher(s);   
	   while (ma.find())   
	   {
		   String temp=ma.group().trim();
		   temp=temp.substring(5, temp.length()-1);
		   list.add(temp);   
	   }   
	   return list;   
	}*/
	
	public static List<String> getImgFromString(String s)  
	{   
	   String regex;   
	   List<String> list = new ArrayList<String>();   
	   //regex = "href=[\"|\'](.*?)[\"|\']";  
	   //regex = "<[img|IMG].*?src=[\'|\"](.*?)[\'|\"].*?/>";
	   regex = "<a(?: [^>]*)+href=([^ >]*)(?: [^>]*)*>";
	   Pattern pa = Pattern.compile(regex, Pattern.DOTALL); 
	   Matcher ma;
	   if(pa!=null)	   
		   ma = pa.matcher(s);
	   else
		   return null;
	   while (ma.find())   
	   {
		   String temp=ma.group().trim();
		   temp=temp.substring(10, temp.indexOf("title")-3);
		   String url="http://169.254.170.4/"+temp;
		   list.add(url);   
	   }   
	   return list;   
	}
	
	/**
	 * ���ַ�����ͼƬ�滻Ϊ��
	 * @param s
	 * @return
	 */
	public static String replaceImage(String s){
		return s.replaceAll("<[img|IMG].*?src=[\'|\"](.*?)[\'|\"].*?/>", "");
	}
	
	
	public static List<String> getMarketImgFromString(String s,String uid)   
	{   
	   String regex;   
	   List<String> list = new ArrayList<String>();   
	   regex = "src=[\"|\'](.*?)[\"|\']";  
	   //regex = "<[img|IMG].*?src=[\'|\"](.*?)[\'|\"].*?/>";
	   Pattern pa = Pattern.compile(regex, Pattern.DOTALL);   
	   Matcher ma = pa.matcher(s);   
	   while (ma.find())   
	   {
		   String temp=ma.group().trim();
		   temp=temp.substring(5, temp.length()-1);
		   String url=ServerConfig.HOST+"/schoolknow/plugin/market/img/"+uid+"/"+temp;
		   list.add(url);   
	   }   
	   return list;   
	}

}
