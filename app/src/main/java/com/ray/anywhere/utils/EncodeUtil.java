package com.ray.anywhere.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodeUtil {
	
	
	/**
	 * ת��Ϊutf-8
	 * @param str
	 * @return
	 */
	public static String ToUtf8(String str){
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;		
	}

}
