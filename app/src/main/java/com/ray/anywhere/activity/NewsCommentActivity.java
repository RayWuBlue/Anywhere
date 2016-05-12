package com.ray.anywhere.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ray.anywhere.R;
import com.ray.anywhere.adapter.CommentAdapter;
import com.ray.anywhere.base.BaseActivity;
import com.ray.anywhere.base.IndexItemBase;
import com.ray.anywhere.entity.NewsComment;
import com.ray.anywhere.entity.Status;
import com.ray.anywhere.helper.LoginHelper;
import com.ray.anywhere.utils.Api;
import com.ray.anywhere.utils.T;
import com.ray.anywhere.widgets.EmotionBox;
import com.ray.anywhere.widgets.EmotionBox.EmotionBoxClickListener;
import com.ray.anywhere.widgets.MyAlertDialog;
import com.ray.anywhere.widgets.MyProgressBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class NewsCommentActivity extends BaseActivity {

	private ListView lv;

	private ArrayList<NewsComment> list;

	private EmotionBox emotionBox;

	private IndexItemBase item;

	private MyProgressBar mpb;

	private CommentAdapter adapter;

	private int page = 1;

	private MyAlertDialog mad;

	private LoginHelper loginHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_comment);
		Init();
		loadComments(page);
	}
	public void Init() {

		loginHelper = new LoginHelper(this);
		item = (IndexItemBase) getIntent().getSerializableExtra(NewsActivity.SER_KEY);
		lv = (ListView) super.findViewById(R.id.comment_lv);
		emotionBox = new EmotionBox(NewsCommentActivity.this);
		emotionBox.setEditHint("请文明发言");

        emotionBox.setOnClick(new EmotionBoxClickListener() {
			@Override
			public void onClick(final String EditTextVal, View view) {

				if (loginHelper.hasLogin()) {

					if (EditTextVal.length() > 128) {
						T.showShort(NewsCommentActivity.this, "内容不超过128个字符，即64个汉字");
						return;
					}else if(EditTextVal.length()==0){
						T.showShort(NewsCommentActivity.this, "内容不为空");
						return;
					}

                    OkHttpUtils.get()
                            .url(Api.News.postNewsComment("Blog", "Article",item.getId() + "",EditTextVal.trim(),loginHelper.getUid()))
                            .build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {

                                }
                                @Override
                                public void onResponse(String s) {

                                    Status status = new Gson().fromJson(s,Status.class);

                                    if(status!=null&&status.isSuccuss()){
                                        T.showShort(NewsCommentActivity.this, "评论成功");
                                        emotionBox.SetValue("");

                                    }
                                }
                            });
				} else {
                    loginHelper.ToLogin();
				}
			}
		});

		lv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				emotionBox.HideEmotionBox();
				return false;
			}
		});
		list = new ArrayList<NewsComment>();
		adapter = new CommentAdapter(this, list);
		lv.setAdapter(adapter);
	}


	public void loadComments(int page){
		OkHttpUtils.get().url(Api.News.getNewsComment(item.getId() + "", loginHelper.getUid(), page)).build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e) {

			}

			@Override
			public void onResponse(String s) {
				List<NewsCommentActivity> comments = new Gson().fromJson(s,new TypeToken<List<NewsCommentActivity>>(){}.getType());
				if(comments!=null){
					//do thing
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if(emotionBox.isHasShow())
			emotionBox.HideEmotionBox();
		else
			finish();
	}
}
