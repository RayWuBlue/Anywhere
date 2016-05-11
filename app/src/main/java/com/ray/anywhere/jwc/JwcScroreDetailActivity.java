package com.ray.anywhere.jwc;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.util.LoginHelper;
import com.ray.anywhere.util.ToastUtil;
import com.ray.anywhere.widget.NoScrollListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JwcScroreDetailActivity extends AppCompatActivity {
    @Bind(R.id.score_container)
    NestedScrollView scoreContainer;
    @Bind(R.id.course_list)
    NoScrollListView listView;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private List<Course> list;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwc_scrore_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<Course>();
        adapter = new CourseAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int year = getSystemDateYear();
        int t = 0, c = (year - 2012 + 1) * 2;
        for (int i = year; i >= 2012; i--)
            for (int j = 1; j <= 2; j++) {
                menu.add(0, t, t, j == 1 ? i + " " + "上学期" : i + " " + "下学期");
                t++;
            }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                collapsingToolbarLayout.setTitle("成绩单(" + item.getTitle() + ")");
                int year = getSystemDateYear();
                int index = item.getItemId();
                String currentYear = (year - index / 2) + "";
                String currentTermId = (index % 2 + 1) + "";
                new ScoreLoadAsyncTask().execute(currentYear, currentTermId, "btXqcj", 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getSystemDateYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    class Course {
        private String title;
        private String score;
        private String point;
        private String type;

        public Course(String title, String score, String point, String type) {
            this.title = title;
            this.score = score;
            this.point = point;
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    class CourseAdapter extends BaseAdapter {
        List<Course> list;
        Context context;


        public CourseAdapter(Context contex, List<Course> list) {
            this.list = list;
            this.context = contex;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_jwc_score_detail, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            Course course = list.get(position);

            holder.courseTitle.setText(course.getTitle());
            holder.courseScrore.setText(course.getScore());
            holder.coursePoint.setText(course.getPoint());
            holder.courseType.setText(course.getType());

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.course_title)
            TextView courseTitle;
            @Bind(R.id.course_scrore)
            TextView courseScrore;
            @Bind(R.id.course_title_ll)
            LinearLayout courseTitleLl;
            @Bind(R.id.course_point)
            TextView coursePoint;
            @Bind(R.id.course_type)
            TextView courseType;
            @Bind(R.id.course_detail_ll)
            LinearLayout courseDetailLl;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class ScoreLoadAsyncTask extends AsyncTask<Object, Void, Boolean> {

        private boolean query(String year, String term, String __EVENTTARGET, int type) {
            String url = "http://jwc2.yangtzeu.edu.cn:8080/cjcx.aspx";
            try {
                Map<String, String> map = new HashMap<String, String>();
                String cookie = LoginHelper.getInstance().getJwcCookie();
                map.put("ASP.NET_SessionId", cookie);

                Document doc = Jsoup.connect(url).cookies(map).timeout(10 * 1000).post();
                String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
                String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
                Map<String, String> params = new HashMap<String, String>();

                params.put("__VIEWSTATE", __VIEWSTATE);
                params.put("__EVENTVALIDATION", __EVENTVALIDATION);
                params.put("selYear", year);
                params.put("selTerm", term);
                params.put("__EVENTARGUMENT", "");
                if (type != 0) {
                    params.put("__EVENTTARGET", "");//其他查询
                    if (type == 1)
                        params.put("Button1", "学位课成绩列表");
                    else
                        params.put("Button2", "必修课成绩列表");
                } else
                    params.put("__EVENTTARGET", __EVENTTARGET);//学期查询

                //再次请求查询
                doc = Jsoup.connect(url).data(params).cookies(map).timeout(10 * 1000).post();
                Elements trs = doc.select("#dgCj").get(0).select("tr");


                list.clear();
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    String title = tds.get(0).select("font").first().text();
                    String score = tds.get(1).select("font").first().text();
                    String point = tds.get(2).select("font").first().text();
                    String course_type = tds.get(5).select("font").first().text();
                    list.add(new Course(title, score, point, course_type));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            return query((String) params[0], (String) params[1], (String) params[2], (int) params[3]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                adapter.notifyDataSetChanged();
                ToastUtil.show(JwcScroreDetailActivity.this, "查询成功");
            } else
                ToastUtil.show(JwcScroreDetailActivity.this, "查询失败,请重试");
        }
    }
}
