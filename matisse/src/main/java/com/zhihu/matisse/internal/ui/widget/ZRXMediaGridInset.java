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
package com.zhihu.matisse.internal.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ZRXMediaGridInset extends RecyclerView.ItemDecoration {

    private int mSpanCount;
    private int mSpacing;

    public ZRXMediaGridInset(int spanCount, int spacing) {
        this.mSpanCount = spanCount;
        this.mSpacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % mSpanCount; // item column

        if (column == 0) {
            outRect.set(mSpacing, mSpacing, mSpacing, 0);
        } else if (column == 1) {
            outRect.set(0, mSpacing, mSpacing, 0);
        } else if (column == 2) {
            outRect.set(0, mSpacing, mSpacing, 0);
        }

//        // column * ((1f / spanCount) * spacing)
//        outRect.left = column * mSpacing / mSpanCount;
//        // spacing - (column + 1) * ((1f / spanCount) * spacing)
//        outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount;
//        if (position >= mSpanCount) {
//            outRect.top = mSpacing; // item top
//        }
    }
}
