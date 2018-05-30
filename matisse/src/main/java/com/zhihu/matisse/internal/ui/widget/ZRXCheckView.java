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

import android.content.Context;
import android.util.AttributeSet;

import com.zhihu.matisse.R;

public class ZRXCheckView extends android.support.v7.widget.AppCompatImageView {

    public static final int UNCHECKED = Integer.MIN_VALUE;
    private boolean mCountable;
    private boolean mChecked;
    private int mCheckedNum;
    private boolean mEnabled = true;

    public ZRXCheckView(Context context) {
        super(context);
    }

    public ZRXCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZRXCheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setChecked(boolean checked) {
        if (mCountable) {
            throw new IllegalStateException("CheckView is countable, call setCheckedNum() instead.");
        }
        mChecked = checked;
        updateResource();
    }

    private void updateResource() {
        if (mChecked) {
            setImageResource(R.drawable.zrx_selected_ic);
        } else {
            setImageResource(R.drawable.zrx_unselected_ic);
        }
    }

    public void setCountable(boolean countable) {
        mCountable = countable;
    }

    public void setCheckedNum(int checkedNum) {
        if (!mCountable) {
            throw new IllegalStateException("CheckView is not countable, call setChecked() instead.");
        }
        if (checkedNum != UNCHECKED && checkedNum <= 0) {
            throw new IllegalArgumentException("checked num can't be negative.");
        }
        mCheckedNum = checkedNum;
        updateResource();
    }

    public void setEnabled(boolean enabled) {
        if (mEnabled != enabled) {
            mEnabled = enabled;
        }
    }

}
