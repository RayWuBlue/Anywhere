package com.ray.anywhere.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

public class ToastUtil extends Toast {

    final public static int TYPE_ERROR = 0;
    final public static int TYPE_SUCCESS = 1;
    final public static int TYPE_INFO = 2;

    public ToastUtil(Context context) {
        super(context);
    }

    public static void show(Context context, String text, int type) {
        Toast toast = makeText(context, text, LENGTH_SHORT);
        View v = toast.getView();
        Resources res = context.getResources();
        toast.show();
    }

    public static void show(Context context, String text) {
        makeText(context, text, LENGTH_SHORT).show();
    }

    public static void show(Context context, int res) {
        makeText(context, context.getResources().getString(res), LENGTH_SHORT).show();
    }

    public static void show(Context context, int res, int type) {
        ToastUtil.show(context, context.getResources().getString(res), type);
    }
}
