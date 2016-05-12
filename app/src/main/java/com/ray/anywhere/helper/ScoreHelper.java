package com.ray.anywhere.helper;

public class ScoreHelper {
	
	/**
	 * �������
	 * @param str
	 * @return
	 */
	public static float getScore(String str){
		String score=str.trim();
		float myScore=0;
		try{
			myScore=Float.parseFloat(score);
		}catch(Exception e){
			if(score.equals("������")||score.equals("���ϸ�")){
				myScore=45;
			}else if(score.equals("����")||score.equals("�ϸ�")){
				myScore=60;
			}else if(score.equals("�е�")){
				myScore=70;
			}else if(score.equals("����")){
				myScore=80;
			}else if(score.equals("����")){
				myScore=90;
			}else if(score.equals("����")){
				myScore=85;
			}
		}
		return myScore;
	}

}
