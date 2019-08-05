package com.lhjx.androidlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lhjx.androidlibrary.imgload.ImageActivity;
import com.lhjx.androidlibrary.logger.LoggerActivity;
import com.lhjx.androidlibrary.weblib.WebActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testPush();

//        TestDialogHelper.setUp(this, "", new ITestDialog() {
//            @Override
//            public void onResult(String result) {
//                Log.d(TAG, "onResult: " + result);
//            }
//        });


        findViewById(R.id.btn_img).setOnClickListener(this);
        findViewById(R.id.btn_web).setOnClickListener(this);
        findViewById(R.id.btn_logger).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_img:
                ImageActivity.start(this);
                break;
            case R.id.btn_web:
                WebActivity.start(this);
                break;
            case R.id.btn_logger:
                LoggerActivity.start(this);
                break;

        }
    }

//    private void testPush() {
//
//        PushManager pushManager = new PushManager.Builder(this)
//                .enableDebug(true)
//                .enableHWDebug(true)
//                .setPushCallback(new PushCallback() {
//                    @Override
//                    public void onSuccess(Object data, int flag) {
//
//                    }
//
//                    @Override
//                    public void onFail(Object data, int errCode, String msg) {
//
//                    }
//                })
//                .setAccount("account")
//                .enableMi("", "")
//                .enableHW("", "")
//                .enableMZ("", "")
//                .build();
//        pushManager.init();
//    }
}
