package com.handarui.baselib.common.converter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Created by guofe on 2016/1/15.
 */
public class VoidJsonAdapter {
    @FromJson
    Void eventFromJson(Object object) {
        return null;
    }

    @ToJson
    String eventToJson(Void data) {
        return null;
    }
}
