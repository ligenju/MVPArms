package com.example.mvparms.mvp.model.entity.bean;


import java.io.Serializable;

import lombok.Data;

/**
 * 企业信息类
 */
@Data
public class EnterpriseSettingBean implements Serializable {
    private static final long serialVersionUID = -6590961727209831532L;
    private String breedEnterprisesId;
    private String breedEnterprisesName;

    private String animalTypes;

    private int faceTimes;

    /**
     * 1广角摄像头 2手机摄像头
     */
    private int cameraCountType;

    private String groupId;
    /**
     * 是否开启投保比对校验
     */
    private int insureValidate;

    /**
     * 点数修改正负值
     */
    private int fixNum;

    public int getCameraCountType() {
        return cameraCountType == 0 ? 1 : cameraCountType;
    }
}
