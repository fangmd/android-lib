package com.lhjx.androidlibrary.versionUpdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lhjx.androidlibrary.R;
import com.lhjx.androidlibrary.TagConstants;
import com.passon.loglib.LoggerUtils;
import com.passon.versionupdate.DownloadBuilder;
import com.passon.versionupdate.DownloadListener;
import com.passon.versionupdate.UpdateManager;

public class VersionUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvVersion;

    public static void start(Context context) {
        Intent starter = new Intent(context, VersionUpdateActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_update_activity);

        mTvVersion = findViewById(R.id.tv_vu);
        findViewById(R.id.btn_vu).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        UpdateManager instance = UpdateManager.getInstance();
        DownloadBuilder builder = instance.createBuilder();

        builder.setContext(this)
                .setVersionCode(123)
                .setDownloadUrl("https://tango.ezcleasing.com/v1.14.apk")
                .setDownloadFileName("biyouche")
                .setAppName("必优车")
                .setAppLogoResource(R.mipmap.ic_launcher)
                .setDownloadListener(new DownloadListener() {
                    @Override
                    public void onDownloadStart() {
                        LoggerUtils.d(TagConstants.UPDATE, "onDownloadStart");
                    }

                    @Override
                    public void onDownloadFail(String s) {
                        LoggerUtils.d(TagConstants.UPDATE, "onDownloadFail");
                    }

                    @Override
                    public void onProgressChange(int i) {
                        LoggerUtils.d(TagConstants.UPDATE, "onProgressChange" + i);
                    }

                    @Override
                    public void onDownloadCancel() {
                        LoggerUtils.d(TagConstants.UPDATE, "onDownloadCancel");
                    }

                    @Override
                    public void onDownloadFinish() {
                        LoggerUtils.d(TagConstants.UPDATE, "onDownloadFinish");
                    }
                }).build();
    }
}
