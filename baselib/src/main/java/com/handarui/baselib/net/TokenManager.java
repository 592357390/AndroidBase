package com.handarui.baselib.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.handarui.baselib.AndroidBase;
import com.handarui.baselib.common.Constants;
import com.handarui.baselib.util.AESEncryptUtil;


/**
 * Created by konie on 16-8-22.
 */
public class TokenManager {

    /**
     * 缓存TOKEN的变量
     */
    private static String TOKEN;

    /**
     * 是否需要刷新
     */
    private static boolean NEED_REFRESH = true;

    /**
     * 设置TOKEN
     *
     * @param context
     * @param token
     */
    public static void setToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME_STORE_TOKEN, 0);
        SharedPreferences.Editor editor = sp.edit();
        String value = AESEncryptUtil.encrypt(token, AndroidBase.getAesKeyEncryptToken());
        editor.putString(Constants.SP_KEY_STORE_TOKEN, value);
        editor.commit();
        TOKEN = token;
        NEED_REFRESH = true;
    }

    /**
     * 获取TOKEN
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        if (NEED_REFRESH) {
            SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME_STORE_TOKEN, 0);
            String value = sp.getString(Constants.SP_KEY_STORE_TOKEN, null);
            if (value == null) {
                TOKEN = null;
            } else {
                TOKEN = AESEncryptUtil.decrypt(value, AndroidBase.getAesKeyEncryptToken());
            }
        }
        NEED_REFRESH = false;
        return TOKEN;
    }

    /**
     * 清除TOKEN
     *
     * @param context
     */
    public static void removeToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME_STORE_TOKEN, 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.remove(Constants.SP_KEY_STORE_TOKEN);
        editor.apply();
        TOKEN = null;
        NEED_REFRESH = false;
    }

}
