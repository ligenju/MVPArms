package com.example.mvparms.mvp.model.entity.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UsersBean implements Serializable {

    private static final long serialVersionUID = 6123670080176960662L;
    /**
     * accountId : 1184285445204418561
     * companyId : 1266300677928259585
     * name : 超级赛亚人
     * menus : [{"code":"applySearch","name":"申报查询","icon":""},
     * {"code":"app_apply","name":"补贴申报","icon":""},
     * {"code":"app_collect","name":"牛脸采集","icon":""},
     * {"code":"app_inspect","name":"巡检","icon":""},
     * {"code":"app_approval","name":"审批","icon":""},
     * {"code":"app_upload","name":"数据上传","icon":""}]
     * userId : 1
     * token : eyJhbGciOiJIUzUxMiJ9.eyJhY2NvdW50SWQiOjExODQyODU0NDUyMDQ0MTg1NjEsInN1YiI6ImFkbWluIiwiZXhwIjoxNTkxOTMxMzcyLCJpYXQiOjE1OTE5Mjc3NzJ9.2EzwAsNOEZQJO7k-d4WfuTIYkcsybZ-96zYiytvMPFL5Onhxi8Xd-0_g2LDphQvW0rwe3PVo4dfdPj1tGX7WCg
     */

    private String accountId;
    /**
     * 企业id
     */
    private String companyId;
    private String name;
    private String userId;
    private String token;
    private String account;
    private String password;
    private List<MenusBean> menus;
    private double currentLat;
    private double currentLon;
    private String deptName;
    /**
     * 机构id
     */
    private String groupId;
    private String avatarUrl;
    private String companyName;
    private String companyShortName;
    /**
     * 0监管方 1养殖场 2屠宰场 3保险公司 4无害化
     */
    private int userType;
    private String dutyName;
    @Data
    public static class MenusBean implements Serializable {
        /**
         * code : applySearch
         * name : 申报查询
         * icon :
         */

        private String code;
        private String name;
        private String icon;
        private int sort;

        @Override
        public String toString() {
            return "MenusBean{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", sort='" + sort + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UsersBean{" +
                "accountId='" + accountId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", menus=" + menus +
                '}';
    }
}
