package com.lhjx.androidlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lhjx.pushlibrary.PushCallback;
import com.lhjx.pushlibrary.PushManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testPush();
    }

    private void testPush() {

        PushManager pushManager = new PushManager.Builder(this)
                .enableDebug(true)
                .enableHWDebug(true)
                .setPushCallback(new PushCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {

                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {

                    }
                })
                .setAccount("account")
                .enableMi("", "")
                .enableHW("", "")
                .enableMZ("", "")
                .build();
        pushManager.init();
    }
}
