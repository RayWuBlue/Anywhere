package com.ray.anywhere.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SmartBarUtils {

	public static final int SMART_BAR_HEIGH = 96;

	/**
	 * ���� ActionBar.setTabsShowAtBottom(boolean) ������ ���
	 * android:uiOptions="splitActionBarWhenNarrow"���������ActionBar Tabs��ʾ�ڵ�����
	 * 
	 * ʾ���� public class MyActivity extends Activity implements
	 * ActionBar.TabListener { protected void onCreate(Bundle
	 * savedInstanceState) { super.onCreate(savedInstanceState); ...
	 * 
	 * final ActionBar bar = getActionBar();
	 * bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	 * SmartBarUtils.setActionBarTabsShowAtBottom(bar, true);
	 * 
	 * bar.addTab(bar.newTab().setText(&quot;tab1&quot;).setTabListener(this));
	 * ... } }
	 * 
	 */
	public static void setActionBarTabsShowAtBottom(ActionBar actionbar,
			boolean showAtBottom) {
		try {
			Method method = Class.forName("android.app.ActionBar").getMethod(
					"setTabsShowAtBottom", new Class[] { boolean.class });
			try {
				method.invoke(actionbar, showAtBottom);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� ActionBar.setActionBarViewCollapsable(boolean) ������
	 * ����ActionBar��������ʾ����ʱ�Ƿ����ء�
	 * 
	 * ʾ����
	 * 
	 * public class MyActivity extends Activity {
	 * 
	 * protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); ...
	 * 
	 * final ActionBar bar = getActionBar();
	 * 
	 * // ����setActionBarViewCollapsable��������ActionBarû����ʾ���ݣ���ActionBar��������ʾ
	 * SmartBarUtils.setActionBarViewCollapsable(bar, true);
	 * bar.setDisplayOptions(0); } }
	 */
	public static void setActionBarViewCollapsable(ActionBar actionbar,
			boolean collapsable) {
		try {
			Method method = Class.forName("android.app.ActionBar").getMethod(
					"setActionBarViewCollapsable",
					new Class[] { boolean.class });
			try {
				method.invoke(actionbar, collapsable);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� ActionBar.setActionModeHeaderHidden(boolean) ������ ����ActionMode�����Ƿ����ء�
	 * 
	 * public class MyActivity extends Activity {
	 * 
	 * protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); ...
	 * 
	 * final ActionBar bar = getActionBar();
	 * 
	 * // ActionBarתΪActionModeʱ������ʾActionMode����
	 * SmartBarUtils.setActionModeHeaderHidden(bar, true); } }
	 */
	public static void setActionModeHeaderHidden(ActionBar actionbar,
			boolean hidden) {
		try {
			Method method = Class.forName("android.app.ActionBar").getMethod(
					"setActionModeHeaderHidden", new Class[] { boolean.class });
			try {
				method.invoke(actionbar, hidden);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ԭ����SmartBar�ķ���
	 * �˷����Ѵ�Flyme2.4.1��ʼʧЧ ʾ����
	 * 
	 * final ActionBar actionBar = getActionBar(); SmartBarUtils.hide(this);
	 * 
	 */
	@Deprecated
	public static final void hide(FragmentActivity activity) {
		ActionBar actionBar = activity.getActionBar();
		if (actionBar == null) {
			return;
		}
		Class<? extends ActionBar> ActionBarClass = actionBar.getClass();
		Method setTabsShowAtBottom;
		try {
			setTabsShowAtBottom = ActionBarClass.getMethod(
					"setTabsShowAtBottom", Boolean.TYPE);
			setTabsShowAtBottom.invoke(activity.getActionBar(), true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������������ԭ����Ϊc����(http://weibo.com/u/1698085875),
	 * ��Shawn(http://weibo.com/linshen2011)��������ϸĽ���һ���ж�SmartBar�Ƿ���ڵķ���,
	 * ע��÷�������Ľӿ�ֻ������2013��6��֮�������flyme�̼���
	 */

	/**
	 * ����һ:uc����ʹ�õķ���(�¾ɰ�flyme����Ч)��
	 * �˷�����Ҫ���requestWindowFeature(Window.FEATURE_NO_TITLE
	 * )ʹ��,ȱ���ǳ����޷�ʹ��ϵͳactionbar
	 * 
	 * @param decorView
	 *            window.getDecorView
	 */
	public static void hide(View decorView) {
		if (!hasSmartBar())
			return;

		try {
			@SuppressWarnings("rawtypes")
			Class[] arrayOfClass = new Class[1];
			arrayOfClass[0] = Integer.TYPE;
			Method localMethod = View.class.getMethod("setSystemUiVisibility",
					arrayOfClass);
			Field localField = View.class
					.getField("SYSTEM_UI_FLAG_HIDE_NAVIGATION");
			Object[] arrayOfObject = new Object[1];
			try {
				arrayOfObject[0] = localField.get(null);
			} catch (Exception e) {

			}
			localMethod.invoke(decorView, arrayOfObject);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������˷�����Ҫ���requestWindowFeature(Window.FEATURE_NO_TITLE)ʹ��
	 * ��ȱ���ǳ����޷�ʹ��ϵͳactionbar
	 * 
	 * @param context
	 * @param window
	 */
	public static void hide(Context context, Window window) {
		hide(context, window, 0);
	}

	private static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * ����������Ҫʹ�ö���actionbar��Ӧ����ʹ�ô˷���
	 * 
	 * @param context
	 * @param window
	 * @param smartBarHeight
	 *            set SmartBarUtils.SMART_BAR_HEIGHT_PIXEL
	 */
	public static void hide(Context context, Window window, int smartBarHeight) {
		if (!hasSmartBar()) {
			return;
		}
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return;
		}

		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		int statusBarHeight = getStatusBarHeight(context);

		window.getDecorView()
				.setPadding(0, statusBarHeight, 0, -smartBarHeight);
	}

	/**
	 * ���ͺſ��÷������Build.hasSmartBar()���ж�����SmartBar
	 * @return
	 */
	public static boolean hasSmartBar() {
		try {
			Method method = Class.forName("android.os.Build").getMethod(
					"hasSmartBar");
			return ((Boolean) method.invoke(null)).booleanValue();
		} catch (Exception e) {
		}

		if (Build.DEVICE.equals("mx2")) {
			return true;
		} else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
			return false;
		}
		return false;
	}

}
