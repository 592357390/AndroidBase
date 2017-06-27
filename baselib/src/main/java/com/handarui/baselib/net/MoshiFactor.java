package com.handarui.baselib.net;

import com.handarui.baselib.common.converter.DateJsonAdapter;
import com.handarui.baselib.common.converter.VoidJsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by konie on 16-8-22.
 */
public class MoshiFactor {

    public static Moshi create() {
        Moshi moshi = new Moshi.Builder().add(new DateJsonAdapter())
                .add(new VoidJsonAdapter())
                .build();
        return moshi;
    }
}
