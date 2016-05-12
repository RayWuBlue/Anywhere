package com.ray.anywhere.helper;

public class CollegeHelper {
	
	public final static String[] collegeName={
		"��ľ����ѧԺ","���繤��ѧԺ","��������ӹ���ѧԺ","��Ϣ����ѧԺ"
	      ,"���ѧԺ","����ѧԺ","������ѧѧԺ","�����ѧԺ","����ѧԺ","������ѧԺ"
	       ,"���ù���ѧԺ","��������ѧѧԺ","����ѧԺ","�����ͨѧԺ","��������ѧԺ"
	};
	
	public final static String[] collegeId={
		"01","03","02","06","21","05","08","09","11","61","04","07","12","13","00"
	};
	
	public static String getCollegeName(String Id){
		for(int i=0;i<collegeId.length;i++){
			if(collegeId[i].equals(Id)){
				return collegeName[i];
			}
		}
		return collegeName[0];
	}

}
