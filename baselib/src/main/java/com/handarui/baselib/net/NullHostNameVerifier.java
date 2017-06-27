package com.handarui.baselib.net;

/**
 * Created by guofe on 2016/1/20.
 */

import com.orhanobut.logger.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NullHostNameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        Logger.w("NullHostNameVerifier", "Approving certificate for " + hostname);
        return true;
    }

}