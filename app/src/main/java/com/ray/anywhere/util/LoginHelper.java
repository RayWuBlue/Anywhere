package com.ray.anywhere.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public class LoginHelper {

    static SharedPreferences share;
    static SharedPreferences.Editor editor = null;
    private static String FILENAME = "jwc_login";
    private static String u_stuid;
    private static String u_name;
    private static String u_class;
    private static String u_cookie;
    private static String __VIEWSTATE;
    private static String __EVENTVALIDATION;

    private static LoginHelper _instance = null;

    private LoginHelper() {
    }

    public static LoginHelper getInstance() {
        if (_instance == null) {
            _instance = new LoginHelper();
        }
        return _instance;
    }

    public static void init(Context context) {
        share = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = share.edit();
    }

    public boolean JwcLogin(String u_stuid, String u_name, String u_class, String u_cookie, String p1, String p2) {
        if (!TextUtils.isEmpty(u_stuid) && !TextUtils.isEmpty(u_name) && !TextUtils.isEmpty(u_class) && !TextUtils.isEmpty(u_cookie) && !TextUtils.isEmpty(p1) && !TextUtils.isEmpty(p2)) {
            LoginHelper.u_stuid = u_stuid;
            LoginHelper.u_name = u_name;
            LoginHelper.u_class = u_class;
            LoginHelper.u_cookie = u_cookie;
            LoginHelper.__VIEWSTATE = p1;
            LoginHelper.__EVENTVALIDATION = p2;
            return true;
        } else
            return false;
    }

    public boolean hasJwcLogin() {
        return u_stuid != null && !u_stuid.equals("");
    }

    public String getJwcId() {
        return u_stuid;
    }

    public String getJwcName() {
        return u_name;
    }

    public String getJwcClass() {
        return u_class;
    }

    public String getJwcCookie() {
        return u_cookie;
    }

    public String getJwcP1() {
        return __VIEWSTATE;
    }

    public String getJwcP2() {
        return __EVENTVALIDATION;
    }

    public void clear() {
        editor.clear().commit();
    }

    public void saveLoginHistory(String stuid, String password) {

        if (!share.contains(stuid)) {
            String history = share.getString("login_history", "");
            history += stuid + ":";
            editor.putString("login_history", history);
            editor.putString(stuid, password);
            editor.commit();
        } else {
            System.out.print("学号已存在");
        }
    }

    public Map<String, String> getLoginHistory() {
        Map<String, String> historyMap = new LinkedHashMap<String, String>();
        String history = share.getString("login_history", "");
        String[] ids = history.split(":");
        for (int i = ids.length - 1; i >= 0; i--) {
            String stuid = ids[i];
            System.out.println(stuid + ":" + share.getString(stuid, ""));
            historyMap.put(stuid, share.getString(stuid, ""));
        }
        return historyMap;
    }

    public String getStuId() {
        return share.getString("stuid", "");
    }

    public void setStuId(String stuid) {
        editor.putString("stuid", stuid);
        editor.commit();
    }
}
