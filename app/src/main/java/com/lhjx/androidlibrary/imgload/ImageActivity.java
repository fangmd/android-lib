package com.lhjx.androidlibrary.imgload;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lhjx.androidlibrary.MockData;
import com.lhjx.androidlibrary.R;
import com.lhjx.imagelib.ImageManager;

public class ImageActivity extends AppCompatActivity {

    private ImageView mIv;
    private ImageView mIvRound;

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mIv = (ImageView) findViewById(R.id.iv_img);
        mIvRound = (ImageView) findViewById(R.id.iv_img_round);


        ImageManager.load(this, MockData.getImg(), mIv);

        ImageManager.loadRoundedCornersImg(this, MockData.getImgRound(), mIvRound, 20);
    }
}
