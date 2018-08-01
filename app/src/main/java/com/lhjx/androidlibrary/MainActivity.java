package com.lhjx.androidlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lhjx.testdialog.ITestDialog;
import com.lhjx.testdialog.TestDialogHelper;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testPush();

        TestDialogHelper.setUp(this, "", new ITestDialog() {
            @Override
            public void onResult(String result) {
                Log.d(TAG, "onResult: " + result);
            }
        });

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
