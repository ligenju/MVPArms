package com.example.mvparms.constants;

import java.io.Serializable;

/**
 * ================================================
 * Created by whosmyqueen on 11:24
 * <a href="mailto:644049260@qq.com">Contact me</a>
 * <a href="https://github.com/whosmyqueen">Follow me</a>
 * ================================================
 */
public enum ApplicationChannel implements Serializable {
    GSC("GSC", "国寿财"),
    PICC("PICC", "中国人保"),
    CIC("CIC", "中华联合"),
    INNOVATIONAI("INNOVATIONAI", "翔创科技");
    public String code;
    public String name;

    ApplicationChannel(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
