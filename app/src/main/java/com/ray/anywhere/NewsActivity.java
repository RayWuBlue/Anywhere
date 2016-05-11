package com.ray.anywhere;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.ray.anywhere.bussiness.BaseNewsParser;
import com.ray.anywhere.bussiness.NewsParserFactory;
import com.ray.anywhere.entity.News;
import com.ray.anywhere.util.N;

public class NewsActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();
    }


    public void initView() {
        webView = (WebView) findViewById(R.id.webview);
        webView.setBackgroundColor(Color.rgb(240, 240, 240));
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebViewClient(new MyWebViewClient());
        new LoadNewsAsyncTask().execute();
    }

    class LoadNewsAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            News news = (News) getIntent().getExtras().getSerializable(News.SERIALIZE_KEY);
            BaseNewsParser newsParser = NewsParserFactory.getInstance().getNewsParser(news);
            newsParser.startParse(false);
            return newsParser.toWap();
        }

        @Override
        protected void onPostExecute(String wap) {
            System.out.println(wap);
            webView.loadDataWithBaseURL(null, wap, "text/html", "utf-8", null);
        }
    }
    /*
     * 自定义点击链接后的打开方式
     */
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = null;
            // 没有http头,添加上教务处的网址
            if (!url.startsWith("http://") && !url.startsWith("mailto")) {
                url = N.JWC_HOME + "/" + url;
            } else if (url.startsWith(N.YANGTZEU_JWC_INNER_PATH)) {
                // 如果是内网,尝试转化为外网
                L.d("检测到内网地址!");
                url = url.replace(N.YANGTZEU_JWC_INNER_PATH, N.JWC_HOME);
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // L.d("进入网址:"+url);
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "无法解析该网址", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
