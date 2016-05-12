package com.ray.anywhere.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class UploadUtil {
	
	private static final int TIME_OUT = 10*1000; //��ʱʱ�� 
	private static final String CHARSET = "utf-8"; //���ñ��� 

	/** 
	* android�ϴ��ļ��������� 
	* @param file ��Ҫ�ϴ����ļ� 
	* @param RequestURL �����rul 
	* ������Ӧ������ 
	*/

	public static String uploadFile(File file,String RequestURL) 

	{ 

	String result = null; 
	String BOUNDARY = UUID.randomUUID().toString(); //�߽��ʶ ������� 
	String PREFIX = "--" , LINE_END = "\r\n"; 
	String CONTENT_TYPE = "multipart/form-data"; //�������� 

	try { 
	URL url = new URL(RequestURL); 

	HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
	conn.setReadTimeout(TIME_OUT); 
	conn.setConnectTimeout(TIME_OUT); 
	conn.setDoInput(true); //���������� 
	conn.setDoOutput(true); //��������� 
	conn.setUseCaches(false); //������ʹ�û��� 
	conn.setRequestMethod("POST"); //����ʽ 
	conn.setRequestProperty("Charset", CHARSET); //���ñ���
	conn.setRequestProperty("connection", "keep-alive"); 
	conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 

	if(file!=null) 

	{ 
	/** 
	* ���ļ���Ϊ�գ����ļ���װ�����ϴ� 
	*/

	DataOutputStream dos = new DataOutputStream( conn.getOutputStream()); 

	StringBuffer sb = new StringBuffer(); 
	sb.append(PREFIX); 
	sb.append(BOUNDARY); 
	sb.append(LINE_END); 

	/** 
	* name�����ϴ��ļ��ı�ʾ��,���е�name
	* filename���ļ�������
	*/

	sb.append("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""+file.getName()+"\""+LINE_END); 

	sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END); 

	sb.append(LINE_END); 
	dos.write(sb.toString().getBytes()); 

	InputStream is = new FileInputStream(file); 

	byte[] bytes = new byte[1024]; 
	int len = 0; 

	while((len=is.read(bytes))!=-1) 

	{

	dos.write(bytes, 0, len); 

	} 
	is.close(); 
	dos.write(LINE_END.getBytes()); 

	byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes(); 

	dos.write(end_data); 
	dos.flush(); 
	/** 
	* ��ȡ��Ӧ�� 200=�ɹ� 
	* ����Ӧ�ɹ�����ȡ��Ӧ���� 
	*/
	//int res = conn.getResponseCode();

	// if(res==200) 

	// { 

	InputStream input = conn.getInputStream(); 
	StringBuffer sb1= new StringBuffer(); 

	int ss ; 
	while((ss=input.read())!=-1) 
	{ 
	sb1.append((char)ss); 

	} 

	result = sb1.toString(); 

	} 

	} catch (MalformedURLException e) { 
	e.printStackTrace(); 
	} catch (IOException e) { 
	e.printStackTrace(); 

	} 
	return result; 

	} 
	
	
	
}
