package com.ray.anywhere;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.ray.anywhere.adapter.YelpagAdapter;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.bean.Yelpag;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberActivity extends BaseActivity {
    private List<Yelpag> list = null;
    private MyHandler myhandler;
    private ListView lv;
    private YelpagAdapter adapter;
    public EditText key;
    public String key_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO �Զ����ɵķ������
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        initview();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setdata();
                    break;
            }
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            try {
                list = new YelPagService().getTel(key_str);
            } catch (Exception e) {
                // TODO �Զ����ɵ� catch ��
                e.printStackTrace();
            }
            myhandler.sendEmptyMessage(0);
        }

    }

    public void doBack(View v) {
        finish();
    }

    public void initview() {
        setTitle("У�ں����ѯ");
        myhandler = new MyHandler();
        //loading=Method.createLoadingDialog(this,"���ڲ�ѯ...");
/*		bt=(Button)super.findViewById(R.id.yp_bt);
        bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				

				else
					Toast.makeText(PhoneNumberActivity.this, "������ؼ���", Toast.LENGTH_SHORT).show();
			}
		});*/
        lv = (ListView) super.findViewById(R.id.yp_res);
        key = (EditText) super.findViewById(R.id.yp_ser);

        key.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                key_str = key.getText().toString();
                if (!key_str.trim().equals("")) {
                    new MyThread().start();
                }
                return false;
            }
        });

    }

    public void setdata() {
        adapter = new YelpagAdapter(this, list);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    class YelPagService {
        private String url = "http://online.yangtzeu.edu.cn/app/app_data.php?act=yel_pag&m=tel";
        private static final int TIMEOUT = 50000;
        private List<Yelpag> list = null;

        private String getInputStream(String path) {
			/*try {
				HttpGet httpget=new HttpGet(path);
				HttpClient client=new DefaultHttpClient();
				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
				HttpResponse response=new DefaultHttpClient().execute(httpget);
				if(200==response.getStatusLine().getStatusCode())	
					return EntityUtils.toString(response.getEntity());
		    	}catch (Exception e) {
				e.printStackTrace();
				return "";
		 	}*/
            return "";
        }

        private List<Yelpag> parseJSON(String str) {
            List<Yelpag> list = new ArrayList<Yelpag>();
            try {
                System.out.println("json" + str);
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(str);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Yelpag one = new Yelpag(jsonObject.getInt("id"),
                            jsonObject.getString("position"),
                            jsonObject.getString("department"),
                            jsonObject.getString("tel")
                    );
                    list.add(one);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        /**
         * ��ȡ����
         *
         * @return
         * @throws Exception
         */
        public List<Yelpag> getTel(String key) throws Exception {
            String key_str = key.replaceAll(" +", " ");
            System.out.println(key_str);
            String arr[] = key_str.split(" ");
            String key_1, key_2;
            if (arr.length > 1) {
                key_1 = arr[0];
                key_2 = arr[1];
            } else {
                key_1 = arr[0];
                key_2 = "";
            }
            String str = "";
            if ((str = getInputStream(url + "&key_1=" + key_1 + "&key_2=" + key_2)) != null) {
                list = parseJSON(str);
                System.out.println("��ҳ��" + list.size());
                return list;
            }

            return null;
        }

    }


}
