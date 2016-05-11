package com.ray.anywhere.fragment;


import android.content.Context;
import android.content.Intent;
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

import com.ray.anywhere.PostDetaiActivity;
import com.ray.anywhere.R;
import com.ray.anywhere.bussiness.YUNewsListParser;
import com.ray.anywhere.entity.News;
import com.ray.anywhere.util.N;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ray on 16-5-7.
 */
public class CommunityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
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
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_community, null);
        ButterKnife.bind(this, rootView);
        yuNewsRefresh.setColorSchemeResources(R.color.colorPrimary);
        yuNewsRefresh.setOnRefreshListener(this);
        yuNewsRefresh.setRefreshing(true);

        newsAdapter = new NewsAdapter(getActivity(), newslist);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newslist.get(position);
                Bundle bd = new Bundle();
                bd.putSerializable(News.SERIALIZE_KEY, news);
                Intent it = new Intent(getActivity(), PostDetaiActivity.class);
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
        new LoadNewsAsyncTask().execute(N.CDXW, "长大要闻");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        loadNews();
    }

    class LoadNewsAsyncTask extends AsyncTask<String, Void, List<News>> {
        @Override
        protected List<News> doInBackground(String... params) {
            String url = params[0];
            String title = params[1];
            YUNewsListParser listParser = new YUNewsListParser(url, title);
            listParser.startParse(false);
            return listParser.newslist;
        }

        @Override
        protected void onPostExecute(List<News> list) {
            if (list!=null) {
                newslist.addAll(list);
                listView.setFocusable(false);
                yuNewsRefresh.setRefreshing(false);
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
            } else {
                convertView = inflater.inflate(R.layout.item_home_community, null);
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
}