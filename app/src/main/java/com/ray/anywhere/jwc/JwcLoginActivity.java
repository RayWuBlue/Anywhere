package com.ray.anywhere.jwc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ray.anywhere.R;
import com.ray.anywhere.util.LoginHelper;
import com.ray.anywhere.util.N;
import com.ray.anywhere.util.ToastUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JwcLoginActivity extends AppCompatActivity {

    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    @Bind(R.id.stuid)
    AutoCompleteTextView stuid;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.stuid_sign_in_button)
    Button stuidSignInButton;
    @Bind(R.id.stuid_login_form)
    LinearLayout stuidLoginForm;
    @Bind(R.id.login_form)
    ScrollView loginForm;

    private UserLoginTask mAuthTask = null;
    private LoginHelper loginHelper;
    private String __VIEWSTATE;
    private String __EVENTVALIDATION;
    private Document doc;
    private String u_stuid;
    private String u_name;
    private String u_class;
    private Map<String, String> u_cookie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwc_login);
        ButterKnife.bind(this);
        loginHelper = LoginHelper.getInstance();

        stuid = (AutoCompleteTextView) findViewById(R.id.stuid);
        addStuidsToAutoComplete();

        password = (EditText) findViewById(R.id.password);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mStuidSignInButton = (Button) findViewById(R.id.stuid_sign_in_button);
        mStuidSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        stuid.setError(null);
        password.setError(null);

        String stuidText = stuid.getText().toString();
        String passwordText = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(passwordText) && !isPasswordValid(passwordText)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(stuidText)) {
            stuid.setError(getString(R.string.error_field_required));
            focusView = stuid;
            cancel = true;
        } else if (!isStuidValid(stuidText)) {
            stuid.setError(getString(R.string.error_invalid_stuid));
            focusView = stuid;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(stuidText, passwordText);
            mAuthTask.execute(N.JWC_LOGIN);
        }
    }

    private boolean isStuidValid(String stuid) {
        return stuid.matches("\\d+");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            loginForm.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgress.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void addStuidsToAutoComplete() {
        final Map<String, String> map = loginHelper.getLoginHistory();
        final List<String> stuidCollection = new ArrayList<String>();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            stuidCollection.add(key);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(JwcLoginActivity.this, android.R.layout.simple_dropdown_item_1line, stuidCollection);
        stuid.setAdapter(adapter);
        stuid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                password.setText(map.get(stuidCollection.get(position)));
            }
        });
    }

    public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

        private final String mStuid;
        private final String mPassword;
        private URL url = null;

        UserLoginTask(String stuid, String password) {
            mStuid = stuid;
            mPassword = password;
        }

        private boolean getLoginParams() {
            try {
                doc = Jsoup.parse(url, 10 * 1000);
                __VIEWSTATE = doc.select("#__VIEWSTATE").val();
                __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;
            }
            return true;
        }

        private boolean login() {
            if (getLoginParams()) {
                Map<String, String> request_params = new HashMap<String, String>();
                request_params.put("txtUid", mStuid);
                request_params.put("txtPwd", mPassword);
                request_params.put("__VIEWSTATE", __VIEWSTATE);
                request_params.put("__EVENTVALIDATION", __EVENTVALIDATION);
                request_params.put("btLogin", "");
                try {
                    Connection.Response response = Jsoup.connect(url.toString()).data(request_params).timeout(10 * 1000).execute();
                    doc = Jsoup.parse(response.body());
                    Element div_user = doc.select("#lbPrompt").get(0);
                    String[] u_str = div_user.text().split(" ");
                    u_stuid = u_str[0];
                    u_name = u_str[1];
                    u_class = u_str[2];
                    __VIEWSTATE = doc.select("#__VIEWSTATE").val();
                    __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
                    u_cookie = response.cookies();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return loginHelper.JwcLogin(u_stuid, u_name, u_class, u_cookie.get("ASP.NET_SessionId"), __VIEWSTATE, __EVENTVALIDATION);
            }
            return false;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                this.url = new URL(params[0]);
                return login();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                loginHelper.saveLoginHistory(stuid.getText().toString(), password.getText().toString());
                JwcLoginActivity.this.finish();
            } else {
                ToastUtil.show(JwcLoginActivity.this, "登陆失败，请检查您的学号或密码是否输入正确");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

