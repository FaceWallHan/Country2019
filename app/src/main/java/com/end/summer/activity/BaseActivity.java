package com.end.summer.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.end.summer.R;

public class BaseActivity extends AppCompatActivity {
    protected void setText(String text){
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView content=findViewById(R.id.content);
        content.setText(text);
    }
}
