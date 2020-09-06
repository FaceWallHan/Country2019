package com.end.summer.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.end.summer.R;
import com.end.summer.bean.sql.SqlCheck;
import com.github.mikephil.charting.data.Entry;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WebCheckActivity extends BaseActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private int index=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        setText("积分签到");
        inView();
        startProgressBarThread();
        setWebView();
    }
    @SuppressLint("JavascriptInterface")
    private void setWebView(){
        webView.loadUrl("file:///android_asset/user_self/user.html");
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(WebCheckActivity.this,"change");
    }
    @JavascriptInterface/**sdk17版本以上加上注解,否则js不能调用android*/
    public void javaScriptCallAndroid(){
        /**JS调用的Android代码 is used*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String text="";
                List<SqlCheck>list= LitePal.findAll(SqlCheck.class);
                if (list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        SqlCheck check=list.get(i);
                        if (check.getTime().equals(getTodayTime())){
                            text="您已领取";
                            break;
                        }else {
                            SqlCheck sql =new SqlCheck();
                            sql.setCheck(true);
                            sql.setId(list.size()+1);
                            sql.setTime(getTodayTime());
                            sql.save();
                            text="签到有彩蛋，积分+100";
                        }
                    }
                }else {
                    SqlCheck sqlCheck =new SqlCheck();
                    sqlCheck.setCheck(true);
                    sqlCheck.setTime(getTodayTime());
                    sqlCheck.setId(list.size()+1);
                    sqlCheck.save();
                    text="签到有彩蛋，积分+100";
                }

                Toast.makeText(WebCheckActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startProgressBarThread(){
        new Thread(){
            @Override
            public void run() {
                while (index<progressBar.getMax()){
                    index++;
                    progressBar.setProgress(index);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private String getTodayTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        Date date=new Date(System.currentTimeMillis());
        return format.format(date);
    }
    private void inView(){
        webView=findViewById(R.id.webView);
        progressBar=findViewById(R.id.progressBar);
    }
}
