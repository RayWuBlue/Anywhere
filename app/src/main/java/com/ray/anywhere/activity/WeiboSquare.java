package com.ray.anywhere.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.ray.anywhere.R;
import com.ray.anywhere.adapter.SchoolFellowAdapter;
import com.ray.anywhere.base.SchoolFellowBase;
import com.ray.anywhere.config.PathConfig;
import com.ray.anywhere.helper.NetHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.FileUtils;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.utils.TimeUtil;
import com.ray.anywhere.widgets.XListView;
import com.ray.anywhere.widgets.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

public class WeiboSquare extends Activity {

	private XListView lv;
	private List<SchoolFellowBase> list;
	public static SchoolFellowAdapter adapter;

	//public MyProgressBar mpb;

	public String num = "0";
	public Context mcontext;
	/*
	 * 
	 * */
	public String loadMoreUrl = null;
	public int loadCount = 1;
	public int page = 1;
	public int lastId;
	public int total_count;


	private IXListViewListenerImp dataListener;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_schoolfellow);


		mcontext = this;

		list = new ArrayList<SchoolFellowBase>();
		lv = (XListView) super.findViewById(R.id.school_fellow_listview);
		lv.setPullLoadEnable(true);

		FileUtils.createPath(PathConfig.BASEPATH + "/data");

		//mpb = new MyProgressBar(this);
		//mpb.setMessage("���ڼ�����...");
		adapter = new SchoolFellowAdapter(WeiboSquare.this, list);
		lv.setAdapter(adapter);
		dataListener = new IXListViewListenerImp();
		lv.setXListViewListener(dataListener);
		dataListener.onRefresh();
/*		if (!NetHelper.isNetConnected(this)) {
			try {
				List<SchoolFellowBase> dbItemList = SchoolfellowDB.getInstance(
						mcontext).getList();
				list.addAll(dbItemList);
				adapter.notifyDataSetInvalidated();
				T.showLong(mcontext, R.string.net_error_tip);
				mpb.dismiss();
				return;
			} catch (Exception e) {

			}
		} else {
			new AsyncloadSF().execute("getNews", num);
		}*/
	}

	class IXListViewListenerImp implements IXListViewListener {
		public void onRefresh() {
			//mpb.show();
			//mpb.setMessage("���ڼ�����...");
			if (!NetHelper.isNetConnected(mcontext)) {
				T.showShort(mcontext, R.string.net_error_tip);
				lv.stopRefresh();
				lv.stopLoadMore();
				//mpb.dismiss();
				return;
			}
			list.clear();
			loadCount = 1;
			page = 1;
			lastId = 0;
			new AsyncloadSF().execute("getNews");
			lv.setRefreshTime(""+ TimeUtil.getUpdateTime(TimeUtil.getCurrentTime()));
		}

		public void onLoadMore() {
			if (!NetHelper.isNetConnected(mcontext)) {
				T.showShort(mcontext, R.string.net_error_tip);
				lv.stopRefresh();
				lv.stopLoadMore();
				return;
			}
			new AsyncloadSF().execute("readMore");
		}

	}

/*	@Override
	protected void HandleTitleBarEvent(int buttonId, View v) {
		switch (buttonId) {
		case 2:
			Intent it = new Intent(SchoolFellowSquare.this, NewAsk.class);
			it.putExtra("param", "schoolfellow");
			startActivity(it);
			break;
		default:
			break;
		}
	}*/
	
	public class AsyncloadSF extends AsyncTask<String, Void, String> {
		private String loadMoreInPage() {
			String url;
			if (page > 1)
				url = Api.HOST+"index.php?s=/weibo/index/appindex/page/"+ page + ".html";
			else
				url = Api.HOST+"index.php?s=/weibo/index/apploadweibo/page/"+ page+ "/loadCount/"+ loadCount+ "/lastId/"+ lastId;
			return GetUtil.getRes(url);
		}

		protected String doInBackground(String... params) {
			String result = "";
			if ("getNews".equals(params[0])) {
				result = GetUtil.getRes(Api.Weibo.getWeiboIndex());
				System.out.println("Get��������" + result);
			} else {
				if (page == 1 && loadCount < 3) {
					loadCount++;
					result = loadMoreInPage();
				} else {
					page++;
					result = loadMoreInPage();
				}
			}
			return result;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			/*
			 * if(!result.trim().equals("[]")){ try { List<Map<String,Object>>
			 * dataList=new JsonHelper(result).parseJson( new
			 * String[]{"id","uid","nn","ct","tm","num"});
			 * for(Map<String,Object> map:dataList){ SchoolFellowBase base=new
			 * SchoolFellowBase(Integer.parseInt(map.get("id").toString()),
			 * map.get
			 * ("uid").toString(),map.get("nn").toString(),map.get("tm").toString
			 * (), map.get("ct").toString(),map.get("num").toString());
			 * list.add(base);
			 * 
			 * //���������ݿ� SchoolfellowDB.getInstance(mcontext).save(base); } }
			 * catch (Exception e) { e.printStackTrace(); }
			 * adapter.notifyDataSetChanged(); }
			 */

			/*
			 * private int id; private int type; private String title; private
			 * String intro; private String address; private long timesamp;
			 */

			//mpb.dismiss();
			lv.stopRefresh();
			lv.stopLoadMore();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		dataListener.onRefresh();
		super.onActivityResult(requestCode, resultCode, data);
	}
}
