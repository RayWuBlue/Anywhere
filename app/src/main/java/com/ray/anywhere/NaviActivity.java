package com.ray.anywhere;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout.LayoutParams;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ray.anywhere.fragment.CommunityFragment;
import com.ray.anywhere.fragment.IntroFragment;
import com.ray.anywhere.fragment.JwcNewsFragment;
import com.ray.anywhere.fragment.YUNewsFragment;

import java.util.ArrayList;
import java.util.List;


public class NaviActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private View indicator_line;
    private int screenWidth;
    private int pageIndex = 0;
    private ViewPager mViewPager;
    private List<View> indicators;
    private ArrayList<String> indicator_titles = new ArrayList<String>();
    /**
     * ViewPager
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int position, float screenOffset, int offsetPx) {
            LayoutParams lp = (LayoutParams) indicator_line.getLayoutParams();
            lp.leftMargin = (int) ((screenOffset + (pageIndex > position ? -1 : 0) + pageIndex) * (screenWidth / indicator_titles.size()));
            indicator_line.setLayoutParams(lp);
        }

        @Override
        public void onPageSelected(int position) {
            resetIndicatorTextViewColor();
            TextView col_text = (TextView) (indicators.get(position).findViewById(R.id.indicator_text));
            col_text.setTextColor(getResources().getColor(R.color.white));
            mViewPager.setCurrentItem(position);
            pageIndex = position;
        }
    };

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private LinearLayout indicator_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
    }


    private void initIndicator() {
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        screenWidth = dm2.widthPixels;

        indicator_line = findViewById(R.id.indicator_line);
        indicator_ll = (LinearLayout) findViewById(R.id.indicator_ll);

        LayoutParams indicator_line_lp = (LayoutParams) indicator_line.getLayoutParams();
        indicator_line_lp.width = screenWidth / indicator_titles.size();
        indicator_line.setLayoutParams(indicator_line_lp);
        indicators = new ArrayList<View>();

        for (int i = 0; i < indicator_titles.size(); i++) {
            final int index = i;
            View indicator = View.inflate(this, R.layout.item_fragment_indicator, null);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(indicator_line_lp.width, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            indicator.setLayoutParams(lp);
            indicator.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (pageIndex != index)
                        mViewPager.setCurrentItem(index);
                }
            });
            TextView col_text = (TextView) indicator.findViewById(R.id.indicator_text);
            col_text.setText(indicator_titles.get(index));
            indicator_ll.addView(indicator);
            indicators.add(indicator);
        }
        resetIndicatorTextViewColor();
        TextView col_text = (TextView) (indicators.get(0).findViewById(R.id.indicator_text));
        col_text.setTextColor(getResources().getColor(R.color.white));
    }

    private void resetIndicatorTextViewColor() {
        for (int i = 0; i < indicator_titles.size(); i++) {
            TextView col_text = (TextView) (indicators.get(i).findViewById(R.id.indicator_text));
            col_text.setTextColor(getResources().getColor(R.color.whiteTrans50));
        }
    }

    private void initView() {
        buildFragment();//
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.setAdapter(new NewsFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        mViewPager.addOnPageChangeListener(pageListener);
        initIndicator();
    }

    /**
     * Fragment
     */
    public void addFragment(String text, Fragment fragment) {
        indicator_titles.add(text);
        fragments.add(fragment);
    }

    private void buildFragment() {
        addFragment("推荐", new IntroFragment());
        addFragment("社区", new CommunityFragment());
        addFragment("教务新闻", new JwcNewsFragment());
        addFragment("新闻网讯", new YUNewsFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_yangtzeu) {

        } else if (id == R.id.nav_jwc) {

        } else if (id == R.id.nav_user) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private FragmentManager fm;

        public NewsFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        public NewsFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void setFragments(ArrayList<Fragment> fragments) {
            if (this.fragments != null) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : this.fragments) {
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fm.executePendingTransactions();
            }
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            return obj;
        }
    }

}