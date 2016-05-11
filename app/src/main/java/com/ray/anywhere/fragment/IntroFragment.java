package com.ray.anywhere.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.bussiness.JwcNewsListParser;
import com.ray.anywhere.entity.News;
import com.ray.anywhere.util.N;
import com.ray.anywhere.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ray on 16-5-7.
 */
public class IntroFragment extends Fragment {
    @Bind(R.id.auto_scrol_viewpager)
    AutoScrollViewPager autoScrolViewpager;

    @Bind(R.id.listview_news)
    ListView listView;

    private List<News> newslist = new ArrayList<News>();

    private ArrayList<ImageView> imageViewList;

    private NewsAdapter newsAdapter;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_intro, null);
        ButterKnife.bind(this, rootView);

        prepareData();
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        autoScrolViewpager.setAdapter(adapter);

        int n = Integer.MAX_VALUE / 2 % imageViewList.size();
        int itemPosition = Integer.MAX_VALUE / 2 - n;

        autoScrolViewpager.setCurrentItem(itemPosition);
        autoScrolViewpager.setInterval(3 * 1000);
        autoScrolViewpager.setBorderAnimation(true);
        autoScrolViewpager.setStopScrollWhenTouch(true);

        loadNews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return rootView;
    }

    private void loadNews() {
        newsAdapter = new NewsAdapter(getActivity(),newslist);
        listView.setAdapter(newsAdapter);
        new LoadNewsAsyncTask().execute(N.JWTZ,"炫酷资讯");
    }

    private void prepareData() {
        imageViewList = new ArrayList<ImageView>();
        int[] imageResIDs = getImageResIDs();
        ImageView iv;
        for (int i = 0; i < imageResIDs.length; i++) {
            iv = new ImageView(getActivity());
            iv.setBackgroundResource(imageResIDs[i]);
            imageViewList.add(iv);
        }
    }

    private int[] getImageResIDs() {
        return new int[]{R.mipmap.yangtzeu_pic1, R.mipmap.yangtzeu_pic2, R.mipmap.yangtzeu_pic3, R.mipmap.yangtzeu_pic4, R.mipmap.yangtzeu_pic5, R.mipmap.yangtzeu_pic6};
    }


    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 判断出去的view是否等于进来的view 如果为true直接复用
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewList.get(position % imageViewList.size()));
        }

        /**
         * 创建一个view
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position % imageViewList.size()));
            return imageViewList.get(position % imageViewList.size());
        }
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
                convertView = inflater.inflate(R.layout.item_home_news, null);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
