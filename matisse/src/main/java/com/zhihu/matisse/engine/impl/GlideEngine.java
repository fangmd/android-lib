/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhihu.matisse.engine.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zhihu.matisse.R;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * {@link ImageEngine} implementation using Glide.
 */
public class GlideEngine implements ImageEngine {

    public void loadThumbnail(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.loading)
                .dontAnimate()
                .into(imageView);
    }

    public void loadThumbnailVideo(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(R.drawable.video)
                .asBitmap()
                .error(R.drawable.video)
                .fitCenter()
                .into(imageView);
    }


    public void loadThumbnail(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .placeholder(R.drawable.loading)
                .fitCenter()
                .dontAnimate()
                .into(imageView);
    }

    public void loadThumbnailVideo(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(R.drawable.video)
                .asBitmap()
                .error(R.drawable.video)
                .fitCenter()
                .into(imageView);
    }



    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .placeholder(placeholder)
                .override(resize, resize)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .placeholder(R.drawable.loading)
                .override(resize, resize)
                .centerCrop()
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.loading)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .priority(Priority.HIGH)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asGif()
                .placeholder(R.drawable.loading)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

}
