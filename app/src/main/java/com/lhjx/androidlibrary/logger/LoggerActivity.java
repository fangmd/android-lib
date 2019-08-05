package com.lhjx.androidlibrary.logger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lhjx.androidlibrary.R;
import com.passon.loglib.LoggerUtils;

public class LoggerActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoggerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logger_activity);

        findViewById(R.id.btn_logger_d).setOnClickListener(this);
        findViewById(R.id.btn_logger_w).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logger_d:
                LoggerUtils.d(" d 日志输出");
                break;
            case R.id.btn_logger_w:
                LoggerUtils.w(" w 日志输出");
                break;
        }
    }
}
