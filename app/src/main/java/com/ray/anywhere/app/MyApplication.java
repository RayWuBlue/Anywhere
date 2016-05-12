package com.ray.anywhere.app;



import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.ray.anywhere.config.PathConfig;
import com.ray.anywhere.config.ServerConfig;
import com.ray.anywhere.db.SQLHelper;
import com.ray.anywhere.utils.FileUtils;

public class MyApplication extends Application {
	
	
	private static MyApplication mApplication;

	private SQLHelper sqlHelper;
	private Gson mGson;
	
	public synchronized static MyApplication getInstance() {
		return mApplication;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;

		FileUtils.createPath(PathConfig.BASEPATH);

		if(!ServerConfig.DEVELOP_MODE){
			CrashHandler crashHandler = CrashHandler.getInstance();  
	        crashHandler.init(this); 
		}

		initImageLoader(getApplicationContext());
	}
	
	public synchronized Gson getGson() {
		if (mGson == null)
			mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return mGson;
	}
	
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

	public static MyApplication getApp() {
		return mApplication;
	}

	public SQLHelper getSQLHelper() {
		if (sqlHelper == null)
			sqlHelper = new SQLHelper(mApplication);
		return sqlHelper;
	}
}
