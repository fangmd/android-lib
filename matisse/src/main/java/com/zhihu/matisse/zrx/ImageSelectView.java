package com.zhihu.matisse.zrx;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhihu.matisse.R;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.zrx.uploadprogress.IProgressView;
import com.zhihu.matisse.zrx.uploadprogress.UploadProgressView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: Created by fangmingdong on 2018/4/13-下午4:34
 * Description:
 */
public class ImageSelectView extends FrameLayout implements View.OnClickListener, IProgressView {

    private static final String TAG = ImageSelectView.class.getSimpleName();
    /**
     * 当前状态
     */
    private int mState = STATE_NONE;

    /**
     * 当前模式：MODE_IMG， MODE_VIDEO
     */
    protected int mMode = MODE_IMG;

    private Context mContext;
    private ImageView mIvSelected;
    private ImageView mIvState;
    private UploadProgressView mUploadProgressView;

    protected Uri mSelectedFile;
    protected String mSelectedFilePath;
    protected String mSelectedFileUrl;

    private int mProgress;
    private SelectViewListener mSelectViewListener;
    private FrameLayout mFlRoot;
    private ImageView mIvShow;
    private LinearLayout mLLAddRoot;


    public ImageSelectView(@NonNull Context context) {
        this(context, null);
    }

    public ImageSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.image_select_layout_zrx, this, true);

        mLLAddRoot = ((LinearLayout) findViewById(R.id.ll_image_select));
        mIvShow = ((ImageView) findViewById(R.id.iv_image_select_show));
        mFlRoot = ((FrameLayout) findViewById(R.id.fl_image_select_root));
        mIvSelected = ((ImageView) findViewById(R.id.iv_image_select));
        mUploadProgressView = ((UploadProgressView) findViewById(R.id.upload_progress_view));
        mIvState = ((ImageView) findViewById(R.id.iv_image_select_delete));
        mFlRoot.setOnClickListener(this);
        mIvState.setOnClickListener(this);
        mIvSelected.setOnClickListener(this);
    }


    private void updateUI() {
        switch (mState) {
            case STATE_NONE:
                cleanSelectedFile();
                showNoFile();
                break;
            case STATE_READY:
                showFileReady();
                break;
            case STATE_UPLOADING:
                showUploading();
                break;
            case STATE_PAUSE:

                break;
            case STATE_FAIL:
                showUploadFail();
                break;
            case STATE_SUCCESS:
                showUploadSuccess();
                mUploadProgressView.setType(UploadProgressView.STATE_NONE);
                break;
        }
    }

    private void cleanSelectedFile() {
        mSelectedFile = null;
        mSelectedFilePath = null;
        mSelectedFileUrl = null;
    }

    private void showNoFile() {
        mIvState.setVisibility(GONE);
        mUploadProgressView.setVisibility(GONE);
        mIvShow.setImageResource(android.R.color.transparent);
        mLLAddRoot.setVisibility(VISIBLE);
        switch (mMode) {
            case MODE_IMG:
                mIvSelected.setImageResource(R.drawable.zrx_add_img_ic);
                break;
            case MODE_VIDEO:
                mIvSelected.setImageResource(R.drawable.zrx_add_video_ic);
                break;
        }
    }

    private void showFileReady() {
        mUploadProgressView.setVisibility(VISIBLE);
        mIvState.setVisibility(VISIBLE);
        mLLAddRoot.setVisibility(GONE);
        if (TextUtils.isEmpty(mSelectedFileUrl)) {
            if (mMode == MODE_IMG) {
                new GlideEngine().loadThumbnail(mContext, mIvShow, mSelectedFile);
            } else {
                new GlideEngine().loadThumbnailVideo(mContext, mIvShow, mSelectedFile);
            }


        } else {
            if (mMode == MODE_IMG) {
                new GlideEngine().loadThumbnail(mContext, mIvShow, mSelectedFileUrl);
            } else {
                new GlideEngine().loadThumbnailVideo(mContext, mIvShow, mSelectedFileUrl);
            }

            // 加载网络图片的时候直接设置控件状态 STATE_SUCCESS
            setState(STATE_SUCCESS);
        }
    }

    private void showUploading() {
        mUploadProgressView.setType(UploadProgressView.STATE_UPLOADING);
        mUploadProgressView.setVisibility(VISIBLE);
        mUploadProgressView.setUploadingText("上传中");
        mIvState.setVisibility(VISIBLE);
        mLLAddRoot.setVisibility(GONE);
        mIvState.setImageResource(R.drawable.zrx_cancel_upload_ic);
    }

    private void showUploadFail() {
        mUploadProgressView.setType(UploadProgressView.STATE_FAIL);
        mUploadProgressView.setVisibility(VISIBLE);
        mUploadProgressView.setUploadingText("重新上传");
        mIvState.setVisibility(VISIBLE);
        mLLAddRoot.setVisibility(GONE);
        mIvState.setImageResource(R.drawable.zrx_reupload_ic);
    }

    private void showUploadSuccess() {
        mUploadProgressView.setType(UploadProgressView.STATE_NONE);
        mUploadProgressView.setVisibility(GONE);
        mIvState.setVisibility(VISIBLE);
        mLLAddRoot.setVisibility(GONE);
        mIvState.setImageResource(R.drawable.zrx_delete_ic);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_image_select_delete) {
            dealStateImgClick();
        } else if (i == R.id.iv_image_select || i == R.id.fl_image_select_root) {
            dealImgClick();
        }
    }

    private void dealStateImgClick() {
        switch (mState) {
            case STATE_NONE:
                if (mSelectViewListener != null) {
                    mSelectViewListener.selectFile();
                }
                break;
            case STATE_UPLOADING:
                if (mSelectViewListener != null) {
                    mSelectViewListener.cancelUpload(mSelectedFilePath);
                }
                setState(STATE_NONE);
                break;
            case STATE_SUCCESS:
                if (mSelectViewListener != null) {
                    mSelectViewListener.cancelSelected(mSelectedFilePath);
                }
                // 等接口返回删除成功，再 setState(STATE_NONE);
//                setState(STATE_NONE);
                break;
            case STATE_FAIL:
                if (mSelectViewListener != null) {
                    mSelectViewListener.reUpload(mSelectedFilePath);
                }
                break;
            default:
                Log.d(TAG, "dealStateImgClick not handler: state=" + mState);
                break;
        }
    }

    private void dealImgClick() {
        switch (mState) {
            case STATE_NONE:
                if (mSelectViewListener != null) {
                    mSelectViewListener.selectFile();
                }
                break;
            case STATE_SUCCESS:
                if (mSelectViewListener != null) {
                    mSelectViewListener.selectedFileClick(mSelectedFilePath, mSelectedFile, mSelectedFileUrl);
                }
                break;
            default:
                Log.d(TAG, "dealImgClick not handler: state=" + mState);
                break;
        }
    }

    public int getState() {
        return mState;
    }

    public void setState(@ImageSelectViewType int type) {
        mState = type;
        updateUI();
    }

    public int getMode() {
        return mMode;
    }

    public void setMode(@SelectViewMode int mode) {
        mMode = mode;
        if (mState == STATE_NONE) {
            showNoFile();
        }
    }

    public Uri getSelectedFile() {
        return mSelectedFile;
    }

    public void setSelectedFile(String fileUrl) {
        if (TextUtils.isEmpty(fileUrl)) {
            return;
        }
        mSelectedFileUrl = fileUrl;
        setState(STATE_READY);
    }

    public void setSelectedFile(Uri selectedFile, String filePath) {
        if (selectedFile == null && TextUtils.isEmpty(filePath)) {
            return;
        }
        mSelectedFile = selectedFile;
        mSelectedFilePath = filePath;
        setState(STATE_READY);
    }

    public SelectViewListener getSelectViewListener() {
        return mSelectViewListener;
    }

    public void setSelectViewListener(SelectViewListener selectViewListener) {
        mSelectViewListener = selectViewListener;
    }

    /**
     * 上传状态：NONE, 默认状态, 未选择图片
     */
    public static final int STATE_NONE = 0x00000004;

    /**
     * 上传状态：STATE_READY, 文件已经选择，准备开始上传
     */
    public static final int STATE_READY = 0x00000003;

    /**
     * 上传状态：正在上传
     */
    public static final int STATE_UPLOADING = 0x00000000;

    /**
     * 上传状态：停止
     */
    public static final int STATE_PAUSE = 0x00000008;

    /**
     * 上传失败
     */
    public static final int STATE_FAIL = 0x00000009;

    /**
     * 上传成功
     */
    public static final int STATE_SUCCESS = 0x00000002;

    @Override
    public int getProgress() {
        return mProgress;
    }

    @Override
    public void setProgress(int progress) {
        mProgress = progress;
        mUploadProgressView.setProgress(mProgress);
    }


    @IntDef({STATE_UPLOADING, STATE_NONE, STATE_READY, STATE_PAUSE, STATE_FAIL, STATE_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageSelectViewType {

    }

    /**
     * 上传视频
     */

    public static final int MODE_VIDEO = 0x00000009;

    /**
     * 上传图片
     */
    public static final int MODE_IMG = 0x00000002;

    @IntDef({MODE_VIDEO, MODE_IMG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectViewMode {

    }
}
