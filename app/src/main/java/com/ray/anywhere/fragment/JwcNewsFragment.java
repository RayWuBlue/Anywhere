package com.ray.anywhere.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ray.anywhere.NewsActivity;
import com.ray.anywhere.R;
import com.ray.anywhere.bussiness.JwcNewsListParser;
import com.ray.anywhere.entity.News;
import com.ray.anywhere.util.N;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ray on 16-5-7.
 */
public class JwcNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.listview_news)
    ListView listView;

    @Bind(R.id.yu_news_refresh)
    SwipeRefreshLayout yuNewsRefresh;

    private List<News> newslist = new ArrayList<News>();

    private NewsAdapter newsAdapter;

    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_jwc_news, null);
        ButterKnife.bind(this, rootView);
        yuNewsRefresh.setColorSchemeResources(R.color.colorPrimary);
        yuNewsRefresh.setOnRefreshListener(this);
        yuNewsRefresh.setRefreshing(true);


        newsAdapter = new NewsAdapter(getActivity(),newslist);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newslist.get(position);
                if (!news.getPath().startsWith("http://jwc")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(N.YUNEWS_DOMAIN + news.getPath());
                    intent.setData(content_url);
                    startActivity(intent);
                    return;
                }
                Bundle bd = new Bundle();
                bd.putSerializable(News.SERIALIZE_KEY, news);
                Intent it = new Intent(getActivity(), NewsActivity.class);
                it.putExtras(bd);
                startActivity(it);
            }
        });

        loadNews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return rootView;
    }

    private void loadNews() {
        new LoadNewsAsyncTask().execute(N.JWTZ, "教务通知");
        new LoadNewsAsyncTask().execute(N.JXDT2,"教学动态");
        new LoadNewsAsyncTask().execute(N.JXJB,"教学简报");
        new LoadNewsAsyncTask().execute(N.GJXX,"高教信息");
        new LoadNewsAsyncTask().execute(N.SJJX,"实践教学");
        new LoadNewsAsyncTask().execute(N.XZZX,"下载中心");
    }


    class LoadNewsAsyncTask extends AsyncTask<String, Void, List<News>> {
        @Override
        protected List<News> doInBackground(String... params) {
            String url = params[0];
            String title = params[1];
            JwcNewsListParser listParser = new JwcNewsListParser(url, title);
            listParser.startParse(false);
            return listParser.newslist;
        }

        @Override
        protected void onPostExecute(List<News> list) {
            if (list!=null) {
                newslist.addAll(list);
                listView.setFocusable(false);
                newsAdapter.notifyDataSetChanged();
            }
        }
    }

    public class NewsAdapter extends BaseAdapter {
        private List<News> data;
        private LayoutInflater inflater;

        public NewsAdapter(Context context, List<News> data) {
            super();
            this.data = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public News getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            }else {
                convertView = inflater.inflate(R.layout.item_home_jwc_news, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            News news = getItem(position);
            holder.newsTitle.setText(news.getTitle());
            holder.newsCate.setText(news.getCataloge());
            holder.newsTime.setText(news.getTime());
            return convertView;
        }

        public class ViewHolder {
            @Bind(R.id.news_title)
            TextView newsTitle;
            @Bind(R.id.news_time)
            TextView newsTime;
            @Bind(R.id.news_cate)
            TextView newsCate;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onRefresh() {
        loadNews();
    }
}
