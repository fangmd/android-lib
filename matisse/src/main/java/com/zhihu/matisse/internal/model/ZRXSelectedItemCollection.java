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
package com.zhihu.matisse.internal.model;

import android.content.Context;

import com.zhihu.matisse.R;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

@SuppressWarnings("unused")
public class ZRXSelectedItemCollection extends SelectedItemCollection {

    public ZRXSelectedItemCollection(Context context) {
        super(context);
    }

    @Override
    public IncapableCause isAcceptable(Item item) {
        if (maxSelectableReached()) {
            int maxSelectable = currentMaxSelectable();
            String cause = mContext.getString(
                    R.string.error_over_count_zrx,
                    maxSelectable
            );
            return new IncapableCause(IncapableCause.ZRX_TOAST, cause);
        } else if (typeConflict(item)) {
            return new IncapableCause(mContext.getString(R.string.error_type_conflict));
        }

        return PhotoMetadataUtils.isAcceptable(mContext, item);
    }
}
