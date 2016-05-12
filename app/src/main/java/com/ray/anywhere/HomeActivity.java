package com.ray.anywhere;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ray.anywhere.activity.Login;
import com.ray.anywhere.activity.UserInfo;
import com.ray.anywhere.adapter.HomeChannelAdapter;
import com.ray.anywhere.bean.ChannelItem;
import com.ray.anywhere.bean.ChannelManage;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.DataCache;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.widgets.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
	private AutoScrollView asView;
	private GridView gv;
	private List<ChannelItem> channels;
	private HomeChannelAdapter adapter;
	private Button btnRetry;
	private TextView tv_tp;
	private TextView tv_weather;
	private ImageView img_weather;
	private RoundedImageView btn_head_image;
	private LoginHelper lh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		lh = new LoginHelper(HomeActivity.this);
		initView();
		initCacheData();
		new AsyncLoadScrollNews().execute();
		new AsyncLoadWeather().execute();
	}

	private void initView() {
		asView = new AutoScrollView(HomeActivity.this);
		btnRetry = (Button) findViewById(R.id.home_btn_retry);
		btnRetry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new AsyncLoadScrollNews().execute();

			}
		});
		((RelativeLayout) findViewById(R.id.act_rl_scrollview)).addView(asView
				.getView());
		findViewById(R.id.count_down).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Main.mSlidingLayer.openLayer(true);
			}
		});

		
		btn_head_image = (RoundedImageView) findViewById(R.id.myzone_user_img);
		btn_head_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = null;
				if(lh.hasLogin())
					it = new Intent(HomeActivity.this,UserInfo.class);
				else
					it = new Intent(HomeActivity.this,Login.class);
				startActivity(it);
			}
		});

		channels = ((ArrayList<ChannelItem>) ChannelManage.defaultUserChannels);
		adapter = new HomeChannelAdapter(this, channels);

		gv = (GridView) findViewById(R.id.home_channel_grid);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (channels.get(position).getClazz() != null) {
					Bundle bd = new Bundle();
					Intent it = new Intent(HomeActivity.this, channels.get(
							position).getClazz());
					bd.putInt("type", channels.get(position).getId());
					it.putExtras(bd);
					startActivity(it);
				}
			}
		});

		tv_tp = (TextView) findViewById(R.id.tv_tp);
		tv_weather = (TextView) findViewById(R.id.tv_weather);
		img_weather = (ImageView) findViewById(R.id.img_weather);

		findViewById(R.id.home_weather_ll).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new AsyncLoadWeather().execute();
					}
				});
	}

	
	private void initCacheData() {
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==101){
					//parseResult(msg.obj.toString());
				}
			}
			
		};
		DataCache dc = new DataCache(HomeActivity.this);
		String url = Api.News.getScrollNews();
		String cache = dc.load(url);
//		if (!"".equals(cache))
//			parseResult(cache);
//		else
//		{
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					Message msg=new Message();
//					msg.what=101;
//					String scroll_url = Api.News.getScrollNews();
//					msg.obj = HttpUtil.getRequest(scroll_url, null);
//					handler.sendMessage(msg);
//				}
//			}).start();
//
//		}
	}


	public class AsyncLoadScrollNews extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String json = GetUtil.getRes(Api.News.getScrollNews());
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (null!=result&&!"".equals(result))
				new DataCache(HomeActivity.this).save(Api.News.getScrollNews(), result);
		}
	}
	public class AsyncLoadWeather extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			/*// http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			list.add(new BasicNameValuePair("city", "����"));
			System.out.println("����Ϊ:" + URLEncodedUtils.format(list, "UTF-8"));
			// String weatherUrl = Api.getWeatherUrl(101200801+"");
			String weatherUrl = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=0&dfc=1&charset=utf-8";
			System.out.println("weatherUrl:" + weatherUrl);*/
			String result = null;
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("����:" + result);
			// JSONObject object = JSONObject.parseObject(result);
			System.out.println("����:" + result);
			try {
				String s1 = result.substring(result.indexOf("s1") + 4,
						result.indexOf("s2") - 2);
				String s2 = result.substring(result.indexOf("s2") + 4,
						result.indexOf("f1") - 2);
				String t1 = result.substring(result.indexOf("t1") + 4,
						result.indexOf("t2") - 2);
				String t2 = result.substring(result.indexOf("t2") + 4,
						result.indexOf("p1") - 2);
				String weather = null;
				if (s1.equals(s2))
					weather = s1;
				else
					weather = s1 + "ת" + s2;
				String tem = t1 + "C/" + t2 + "C";
				tv_tp.setText(tem);
				tv_weather.setText(weather);
				img_weather.setImageResource(Api.getWeatherIcon(weather));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
