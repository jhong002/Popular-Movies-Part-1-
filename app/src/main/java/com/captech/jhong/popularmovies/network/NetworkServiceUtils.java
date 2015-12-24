package com.captech.jhong.popularmovies.network;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by jhong on 12/21/15.
 */
public class NetworkServiceUtils {

    private static CookieManager sCookieManager;

    public static synchronized CookieManager getCookieManagerInstance() {
        if (sCookieManager == null) {
            sCookieManager = new CookieManager();
            sCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        }
        return sCookieManager;
    }
}