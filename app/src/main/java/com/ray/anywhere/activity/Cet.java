package com.ray.anywhere.activity;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ray.anywhere.R;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.utils.EncodeUtil;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.widgets.MyAlertDialog;
import com.ray.anywhere.widgets.MyPopMenu;
import com.ray.anywhere.widgets.MyProgressBar;
import com.ray.anywhere.widgets.MyAlertDialog.MyDialogInt;
import com.ray.anywhere.widgets.MyPopMenu.MyPopMenuImp;

public class Cet extends BaseActivity {
	
	private EditText name=null;
	private EditText zkz=null;
	private Button btn=null;
	private MyProgressBar mpb;
    private MyPopMenu popmenu;
    Map<String,String> ck;
    private int type = 0;
	private MyAlertDialog mad;
	private String phoneNumber = "1066335577";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_cet);
		setTitle("ѧ����(Ĭ��)");
		addButton("��ѯ��ʽ", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popmenu=new MyPopMenu(Cet.this);
				popmenu.addItems(new String[]{"ѧ����(Ĭ��)","99��������ѯ","����Ϣ��ѯ"});
				popmenu.showAsDropDown(v);
				popmenu.setOnItemClickListener(new MyPopMenuImp() {
					@Override
					public void onItemClick(int index) {
						switch (index) {
						case 0:
							type = 0;
							name.setVisibility(View.VISIBLE);
							setTitle("ѧ����(Ĭ��)");
							break;
						case 1:
							type = 1;
							name.setVisibility(View.VISIBLE);
							setTitle("99��������ѯ");
							break;
						case 2:
							type = 2;
							name.setVisibility(View.GONE);
							setTitle("����Ϣ��ѯ");
							break;
						default:
							break;
						}
					}});
				
			}
		});
		name=(EditText) super.findViewById(R.id.cet_edit_id);
		zkz=(EditText) super.findViewById(R.id.cet_edit_pwd);
		btn=(Button) super.findViewById(R.id.cet_login_btn);
		btn.setOnClickListener(new onclicklistener());
	}
	private void initAlert(){
		 mad=new MyAlertDialog(this);
		 mad.setTitle("��Ϣ��ʾ");
		 mad.setMessage("�й��ƶ�����ͨ�������ֻ��û�:����A��15λ׼��֤�ţ���A110000122100101���� 1066335577\n"
+"�ʷѣ�ȫ��1Ԫ/��������ͨ�ŷѡ��ͷ��绰: 010-68083018\n"
+"�ر���ʾ���ӱ��������й��ƶ��ֻ��û����� 8��15λ׼��֤�ţ���8110000122100101���� 10661660��"); 
		 mad.setLeftButton("ȷ��",new MyDialogInt() {
			@Override
			public void onClick(View view) {
				mad.dismiss();
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));          
	            intent.putExtra("sms_body", "A"+zkz.getText().toString());
	            startActivity(intent);
				return;
			}
		});
		mad.setRightButton("ȡ��",new MyDialogInt() {
			@Override
			public void onClick(View view) {
				mad.dismiss();
			}
		});
	}
	public class onclicklistener implements OnClickListener{
		@Override
		public void onClick(View v) {
			final String u=name.getText().toString();
			final String p=zkz.getText().toString();
			System.out.println("U:"+u+" P:"+p);
			if(type==0){
				mpb=new MyProgressBar(Cet.this);
				mpb.setMessage("���ڲ�ѯ��...");
				new	 Thread(new Runnable() {
					@Override
					public void run() {
						Message msg=new Message();
						msg.what=102;
						try {
							msg.obj = Jsoup.connect("http://www.chsi.com.cn/cet/query")
									.data("zkzh", p).data("xm", u) // �������
									.userAgent("I �� m jsoup") // ���� User-Agent
									.cookie("auth", "token")// ���� cookie
									.timeout(3000) // �������ӳ�ʱʱ��
									.header("Referer", "http://www.chsi.com.cn/cet/").get();
						} catch (IOException e) {
							e.printStackTrace();
						} // ʹ��POST��������URL
						handler.sendMessage(msg);
					}
				}).start();
				
			}else if(type == 1){
				
				mpb=new MyProgressBar(Cet.this);
				mpb.setMessage("���ڲ�ѯ��...");
				new	 Thread(new Runnable() {
					@Override
					public void run() {
						String url="http://online.yangtzeu.edu.cn/weixin/func//sljcx_api.php?name="+EncodeUtil.ToUtf8(u)+"&id="+p;
						Message msg=new Message();
						msg.what=102;
						//GetUtil.getRes(url);//��������ſ�ȡ����ȷ������
						msg.obj=GetUtil.getRes(url);
						handler.sendMessage(msg);
					}
				}).start();
			}else{
				initAlert();
			}
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==102){
				Intent it=new Intent(Cet.this,CetData.class);
				if(type==0){
					it.putExtra("entry",2);
					Document doc = (Document)msg.obj;
					it.putExtra("cet",parseScore(doc));
				}else{
					it.putExtra("entry",1);
					it.putExtra("cet",msg.obj.toString());
				}
				startActivity(it);
			}
			mpb.dismiss();
			super.handleMessage(msg);
		}
	};

	private String parseScore(Document doc) {
		System.out.println("CET DOC:"+doc.toString());
		String name = null,school = null,type = null,id = null,time = null;
		Element content = doc.getElementById("c");
		Element element = null;
		if(content!=null)
			element = content.getElementsByClass("error").first();

		if ((element != null)) {
				Toast.makeText(Cet.this,"��ѯ����",Toast.LENGTH_SHORT).show();
		} else {

			Element cetTable = doc.getElementsByClass("cetTable").first();
			Elements elements = cetTable.getElementsByTag("tr");
			for (Element elementtwo : elements) {

				if (elementtwo.getElementsByTag("th").text().equals(getResources().getString(R.string.name_3))) {

					name = elementtwo.getElementsByTag("td").text();
				} else if (elementtwo.getElementsByTag("th").text()
						.equals(getResources().getString(R.string.school))) {

					school = elementtwo.getElementsByTag("td").text();
				} else if (elementtwo.getElementsByTag("th").text()
						.equals(getResources().getString(R.string.type))) {

					type = elementtwo.getElementsByTag("td").text();
				} else if (elementtwo.getElementsByTag("th").text()
						.equals(getResources().getString(R.string.id_2))) {

					id = elementtwo.getElementsByTag("td").text();
				} else if (elementtwo.getElementsByTag("th").text().equals("����ʱ�䣺")) {

					time = elementtwo.getElementsByTag("td").text();
					System.out.println("����ʱ��:"+time);
					
				} else if (elementtwo.getElementsByTag("th").text()
						.equals(getResources().getString(R.string.sum_2))) {

					String result = name+","+school+","+type+","+id+","+time+","
							+elementtwo.getElementsByTag("td").text().replace("������", ",").replace("�Ķ���", ",").replace("д���뷭�룺", ",");
					return result;
				}
			}

		}
		return null;
	}

}
