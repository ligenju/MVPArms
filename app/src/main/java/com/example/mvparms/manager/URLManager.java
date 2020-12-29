package com.example.mvparms.manager;



import com.example.mvparms.BuildConfig;

import lombok.Getter;

public class URLManager {

    public final static String LOCAL_FILE_URI = "file://";
    public final String NET_MODEL_URL = BuildConfig.NET_MODEL_URL;
    public final String NET_LOGIN_URL = BuildConfig.NET_LOGIN_URL;
    public final String TEST_NET_MODEL_URL = BuildConfig.TEST_NET_MODEL_URL;
    public final String TEST_NET_LOGIN_URL = BuildConfig.TEST_NET_LOGIN_URL;
    public final String LOCAL_MODEL_TEST_URL = "http://192.168.0.169:8000/";
    public final String LOCAL_API_TEST_URL = "http://192.168.0.169:8000/";
    @Getter
    private String modelUrl;
    @Getter
    private String apiUrl;

    //https相关常量域名
    private URLManager() {
        if (!BuildConfig.URL_DEBUG) {
            if (BuildConfig.URL_PROD) {
                if (BuildConfig.URL_PROD_TEST) {
                    modelUrl = TEST_NET_MODEL_URL;
                } else {
                    modelUrl = NET_MODEL_URL;
                }
                apiUrl = NET_LOGIN_URL;
            } else {
                modelUrl = TEST_NET_MODEL_URL;
                apiUrl = TEST_NET_LOGIN_URL;
            }

        } else {
            modelUrl = LOCAL_MODEL_TEST_URL;
            apiUrl = LOCAL_API_TEST_URL;
        }
    }

    public static URLManager getInstance() {
        return URLManagerHolder.INSTANCE;
    }

    private static class URLManagerHolder {
        private static final URLManager INSTANCE = new URLManager();
    }
}
