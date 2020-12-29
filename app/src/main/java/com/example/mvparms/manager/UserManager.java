package com.example.mvparms.manager;

import com.blankj.utilcode.util.SPUtils;
import com.example.mvparms.mvp.model.entity.bean.UsersBean;

public class UserManager {
    public static final String LOGIN_COMPANY_ID = "LOGIN_COMPANY_ID";
    public static final String BIZ_COMPANY_ID = "BIZ_COMPANY_ID";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String COMPANY_SHORT_NAME = "COMPANY_SHORT_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_COVER = "USER_COVER";
    public static final String LOGIN_DEPT_ID = "LOGIN_DEPT_ID";
    public static final String DEPT_ID = "DEPT_ID";
    public static final String DEPT_NAME = "DEPT_NAME";
    public static final String ANIMAL_TYPES = "ANIMAL_TYPES";
    public static final String ANIMAL_TYPE = "ANIMAL_TYPE";
    public static final String PIG_SCAN_FACE_TIMES = "PIG_SCAN_FACE_TIMES";
    public static final String CAMERA_COUNT_TYPE = "CAMERA_COUNT_TYPE";
    public static final String INSURE_VALIDATE = "INSURE_VALIDATE";
    private static final String CHECK_NUM_FIX = "CHECK_NUM_FIX";

    public static void logout() {
        TokenManager.getInstance().cleanLocalToken();
        TokenManager.getInstance().cleanToken();
        SPUtils.getInstance().clear();
    }

    public static String getAnimalTypes() {
        return SPUtils.getInstance().getString(ANIMAL_TYPES);
    }

    public static void setAnimalTypes(String animalTypes) {
        SPUtils.getInstance().put(ANIMAL_TYPES, animalTypes);
    }

    public static int getAnimalType() {
        return SPUtils.getInstance().getInt(ANIMAL_TYPE);
    }

    public static void setAnimalType(int animalType) {
        SPUtils.getInstance().put(ANIMAL_TYPE, animalType);
    }

    public static void setUserInfo(UsersBean user) {
        SPUtils.getInstance().put(LOGIN_COMPANY_ID, user.getCompanyId());
        SPUtils.getInstance().put(ACCOUNT_ID, user.getAccountId());
        SPUtils.getInstance().put(DEPT_NAME, user.getDeptName());
        SPUtils.getInstance().put(DEPT_ID, user.getGroupId());
        SPUtils.getInstance().put(LOGIN_DEPT_ID, user.getGroupId());
        SPUtils.getInstance().put(USER_ID, user.getUserId());
        SPUtils.getInstance().put(USER_TYPE, user.getUserType());
        SPUtils.getInstance().put(USER_NAME, user.getName());
        SPUtils.getInstance().put(USER_COVER, user.getAvatarUrl());
        SPUtils.getInstance().put(COMPANY_NAME, user.getCompanyName());
        SPUtils.getInstance().put(COMPANY_SHORT_NAME, user.getCompanyShortName());
    }

    public static String getUserId() {
        return SPUtils.getInstance().getString(USER_ID);
    }

    public static String getUserName() {
        return SPUtils.getInstance().getString(USER_NAME);
    }


    public static String getCompanyShortName() {
        return SPUtils.getInstance().getString(COMPANY_SHORT_NAME);
    }

    public static void getCompanyShortName(String name) {
        SPUtils.getInstance().put(COMPANY_SHORT_NAME, name);
    }

    public static String getCompanyName() {
        return SPUtils.getInstance().getString(COMPANY_NAME);
    }

    public static void setCompanyName(String companyName) {
        SPUtils.getInstance().put(COMPANY_NAME, companyName);
    }

    public static String getCompanyId() {
        if (getUserType() == 3)
            return SPUtils.getInstance().getString(BIZ_COMPANY_ID);
        else if (getUserType() == 1)
            return SPUtils.getInstance().getString(LOGIN_COMPANY_ID);
        return "";
    }

    public static String getUserCover() {
        return SPUtils.getInstance().getString(USER_COVER);
    }

    public static int getUserType() {
        return SPUtils.getInstance().getInt(USER_TYPE);
    }

    public static String getDeptId() {
        return SPUtils.getInstance().getString(DEPT_ID);
    }

    public static void setDeptId(String deptId) {
        SPUtils.getInstance().put(DEPT_ID, deptId);
    }

    public static String getLoginDeptId() {
        return SPUtils.getInstance().getString(LOGIN_DEPT_ID);
    }

    public static void setLoginDeptId(String deptId) {
        SPUtils.getInstance().put(LOGIN_DEPT_ID, deptId);
    }

    public static void setBizCompanyId(String enterpriseId) {
        SPUtils.getInstance().put(BIZ_COMPANY_ID, enterpriseId);
    }

    public static int getPigScanFaceTimes() {
        return SPUtils.getInstance().getInt(PIG_SCAN_FACE_TIMES, 1);
    }

    public static void setPigScanFaceTimes(int faceTimes) {
        SPUtils.getInstance().put(PIG_SCAN_FACE_TIMES, faceTimes);
    }

    public static int getCameraCountType() {
        return SPUtils.getInstance().getInt(CAMERA_COUNT_TYPE, 1);
    }

    public static void setCameraCountType(int cameraCountType) {
        SPUtils.getInstance().put(CAMERA_COUNT_TYPE, cameraCountType);
    }

    public static int getInsureValidate() {
        return SPUtils.getInstance().getInt(INSURE_VALIDATE, 0);
    }

    /**
     * @param insureValidate 0 关闭 1 开启
     */
    public static void setInsureValidate(int insureValidate) {
        SPUtils.getInstance().put(INSURE_VALIDATE, insureValidate);
    }

    public static void setCheckNumFix(int num) {
        SPUtils.getInstance().put(CHECK_NUM_FIX, num);
    }

    public static int getCheckNumFix() {
        return SPUtils.getInstance().getInt(CHECK_NUM_FIX);
    }
}
