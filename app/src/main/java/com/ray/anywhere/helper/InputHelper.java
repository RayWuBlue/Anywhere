package com.ray.anywhere.helper;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;


//���뷨������
public class InputHelper {
	
	//�������뷨
	public static void Hide(Activity act){
		((InputMethodManager)act.getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
