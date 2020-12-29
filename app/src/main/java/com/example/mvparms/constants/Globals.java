package com.example.mvparms.constants;


import com.example.mvparms.BuildConfig;
import com.example.mvparms.mvp.model.entity.bean.UsersBean;

/**
 * Author by luolu, Date on 2018/8/16.
 * COMPANY：InnovationAI
 */

public class Globals {

    public static String CLIENTTYPE = "1309035088465387522";
    /**
     * 来源标识
     * GSC("GSC", "国寿财"),
     * PICC("PICC", "中国人保"),
     * CIC("CIC", "中华联合"),
     * INNOVATIONAI("INNOVATIONAI", "翔创科技");
     */
    public static ApplicationChannel APPLICATION_CHANNEL = BuildConfig.APPLICATION_CHANNEL;
    public static UsersBean LOGIN_USER;

    static {
        if (BuildConfig.URL_PROD) {
            Globals.CLIENTTYPE = BuildConfig.CLIENT_ID;
        } else {
            Globals.CLIENTTYPE = BuildConfig.TEST_CLIENT_ID;
        }
    }

}
