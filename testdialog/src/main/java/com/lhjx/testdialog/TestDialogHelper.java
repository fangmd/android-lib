package com.lhjx.testdialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Author: Created by fangmingdong on 2018/7/31-下午2:50
 * Description:
 */
public class TestDialogHelper {

    public static void setUp(final AppCompatActivity activity, final ITestDialog listener) {
        FrameLayout fl = (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);

        Button btn = new Button(activity);
        btn.setText("Set");
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundResource(R.drawable.bg_test_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btn.setElevation(2000);
        }
        fl.addView(btn);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp2px(activity, 50), dp2px(activity, 50));
        btn.setGravity(Gravity.CENTER);
        btn.setX(activity.getResources().getDisplayMetrics().widthPixels - dp2px(activity, 100));
        btn.setY(dp2px(activity, 50));
        btn.setLayoutParams(params);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestDialogFragment testDialogFragment = TestDialogFragment.newInstance();
                testDialogFragment.setITestDialog(listener);
                testDialogFragment.show(activity.getSupportFragmentManager(), "TestDialogHelper");
            }
        });
    }

    private static int dp2px(Context context, int dp) {
        return ((int) (context.getResources().getDisplayMetrics().density * dp));
    }

}
