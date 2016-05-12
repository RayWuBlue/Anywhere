package com.ray.anywhere.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ray.anywhere.R;
import com.ray.anywhere.config.PathConfig;
import com.ray.anywhere.utils.BitmapUtil;
import com.ray.anywhere.utils.GetUtil;
import com.ray.anywhere.utils.Sha1Util;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.ZoomImageView;

public class ImgPreview extends Activity {
	private ZoomImageView img;
	private ImageButton save;
	private String imgsrc;
	private RelativeLayout layout;
	
	private Bitmap CurrentBm;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_img_preview);
		
		imgsrc=getIntent().getStringExtra("imgsrc");
		
		img=(ZoomImageView) super.findViewById(R.id.img_preview);
		
		
		layout=(RelativeLayout) super.findViewById(R.id.img_load_layout);
		
		//����ͼƬ������
		save=(ImageButton) super.findViewById(R.id.preview_btn_save);
		save.setOnClickListener(new saveImageToLocal());
		
		//��ʾͼƬ
		new Thread(new Runnable(){
			@Override
			public void run() {
				Bitmap bm=GetUtil.getBitMap(imgsrc);
				Message msg=new Message();
				msg.what=102;
				msg.obj=bm;
				handler.sendMessage(msg);				
		}}).start();
				
	}
	
	/**
	 * ͼƬ����
	 * @author wei8888go
	 *
	 */
	public class saveImageToLocal implements OnClickListener{
		@Override
		@SuppressLint("SdCardPath")
		public void onClick(View v) {
			String imageName = new Sha1Util().getDigestOfString(imgsrc.getBytes());
			//��ȡ�ļ���׺��
			String[] ss = imgsrc.split("\\.");
			String ext = ss[ss.length - 1];
			
			// ����ͼƬ���ֵĵ�ַ
			String savePath = PathConfig.SavePATH + imageName + "." + ext;
			
			File file = new File(savePath);
			
			if (file.exists()) {
				// ����ļ��Ѿ�����
				T.showShort(ImgPreview.this,"��ͼƬ�Ѿ�������SD��schoolknow�ļ�����");
				return;
			}		
			try {
				if(CurrentBm!=null){
					BitmapUtil.saveImg(CurrentBm,PathConfig.SavePATH,imageName + "." + ext);
					T.showShort(ImgPreview.this,"ͼƬ�ѱ��浽SD����schoolknow�ļ�����!");
				}
			} catch (Exception e) {
				T.showShort(ImgPreview.this,"����ʧ��");
			}
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
            if(msg.what == 102){
            	CurrentBm=(Bitmap) msg.obj;
            	layout.setVisibility(View.GONE);
            	img.setVisibility(View.VISIBLE);
    			img.setImageBitmap(CurrentBm);
            }
        };
    };
	

	protected void HandleTitleBarEvent(int buttonId, View v) {
		switch(buttonId){
		case 0:
			finish();
			break;
		case 1:
			break;
		default:
			break;
		}

	}


}
