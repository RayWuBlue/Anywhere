package com.ray.anywhere.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class N {

    public static String APP_DOMAIN = "http://169.254.58.1";
    public static String JWC_DOMAIN = "http://jwc.yangtzeu.edu.cn:8080";
    public static String YUNEWS_DOMAIN = "http://news.yangtzeu.edu.cn";

    public static final String JWC_HOME = "http://jwc.yangtzeu.edu.cn:8080";

    public static final String JWC_LOGIN = "http://jwc2.yangtzeu.edu.cn:8080/login.aspx";

    public static final String YANGTZEU_JWC_INNER_PATH = "http://10.10.11.242:8080";

    public static String APP_HOST = APP_DOMAIN + "/sns/index.php?s=/app/";

    public static final String JWTZ = JWC_DOMAIN + "/jwnews/jwxw/jwtz/";// 教务通知
    public static final String JXDT2 = JWC_DOMAIN + "/jwnews/jwxw/jxdt/";// 教学动态
    public static final String JXJB = JWC_DOMAIN + "/jwnews/jwxw/jxjb/";// 教学简报
    public static final String GJXX = JWC_DOMAIN + "/jwnews/jwxw/gjxx/";// 高教信息
    public static final String SJJX = JWC_DOMAIN + "/jwnews/sjcx/sjjx/";// 实践教学
    public static final String XZZX = JWC_DOMAIN + "/jwnews/xzzx/";// 下载中心

    public static final String CDXW = YUNEWS_DOMAIN + "/news/changdayaowen/";// 长大要闻


    public static boolean isNetWorkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null) && info.isAvailable();
    }

}
