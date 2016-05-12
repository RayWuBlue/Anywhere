package com.ray.anywhere.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei8888go
 *
 */
public class TextUtils {
	
	
	/**
	 * �ַ����Ƿ�ȫ��������
	 * @param str
	 * @return
	 */
	public static boolean isAllNum(String str){
		Pattern pattern=Pattern.compile("[0-9]*");
		Matcher isNum=pattern.matcher(str);
		return isNum.matches();
	}
	
	/**
	 * �ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isSpace(String str){
		String tempStr=str.trim();
		if(tempStr.length()==0&&tempStr.equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * str�Ƿ����contain
	 * @param str
	 * @param contain
	 * @return
	 */
	public static boolean contain(String str,String contain){
		return str.indexOf(contain)!=-1;
	}
	
	

}
