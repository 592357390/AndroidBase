package com.handarui.baselib.common.converter;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.Date;

/**
 * Created by xubo on 15/10/29.
 */
public class DateJsonAdapter {

    @FromJson
    Date eventFromJson(long dateLong) {
        Date date = new Date(dateLong);
        return date;
    }

    @ToJson
    long eventToJson(Date date) {
        long dateLong = date.getTime();
        return dateLong;
    }

}
