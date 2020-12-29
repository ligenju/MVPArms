package com.example.mvparms.manager;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;


/**
 * 登录和退出登录相关
 */

public class TokenManager {
    public static final String ACCESS_LOCAL_TOKEN = "token";
    private static String accessLocalToken;


    private TokenManager() {
    }

    public static TokenManager getInstance() {
        return DataManagerHolder.INSTANCE;
    }

    public String getAccessLocalToken() {
        if (TextUtils.isEmpty(accessLocalToken)) {
            return SPUtils.getInstance().getString(ACCESS_LOCAL_TOKEN, null);
        } else {
            return accessLocalToken;
        }
    }

    public void setLocalToken(String accessLocalToken) {
        TokenManager.accessLocalToken = accessLocalToken;
        SPUtils.getInstance().put(ACCESS_LOCAL_TOKEN, accessLocalToken);
    }

    public void cleanToken() {
        TokenManager.accessLocalToken = null;
        SPUtils.getInstance().remove(ACCESS_LOCAL_TOKEN);
    }

    public void cleanLocalToken() {
        TokenManager.accessLocalToken = null;
        SPUtils.getInstance().remove(ACCESS_LOCAL_TOKEN);
    }

    private static class DataManagerHolder {
        private static final TokenManager INSTANCE = new TokenManager();
    }
}
