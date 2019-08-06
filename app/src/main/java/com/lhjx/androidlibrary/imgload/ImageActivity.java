package com.lhjx.androidlibrary.imgload;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lhjx.androidlibrary.MockData;
import com.lhjx.androidlibrary.R;
import com.passon.imagelib.CropType;
import com.passon.imagelib.ImageManager;

public class ImageActivity extends AppCompatActivity {

    private ImageView mIv;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;
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
        mIv2 = (ImageView) findViewById(R.id.iv_img2);
        mIv3 = (ImageView) findViewById(R.id.iv_img3);
        mIv4 = (ImageView) findViewById(R.id.iv_img4);

        mIvRound = (ImageView) findViewById(R.id.iv_img_round);


        ImageManager.load(this, MockData.getImg(), mIv, CropType.FIT_CROP);
        ImageManager.load(this, MockData.getImg(), mIv2, CropType.CENTER_CROP);
        ImageManager.load(this, MockData.getImg(), mIv3, CropType.CENTER_INSIDE);
        ImageManager.load(this, MockData.getImg(), mIv4, CropType.NONE);

        ImageManager.loadRoundedCornersImg(this, MockData.getImgRound(), mIvRound, 40);
    }
}
