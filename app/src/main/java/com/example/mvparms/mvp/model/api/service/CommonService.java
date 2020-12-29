package com.example.mvparms.mvp.model.api.service;



import com.example.mvparms.mvp.model.api.param.UserLoginParam;
import com.example.mvparms.mvp.model.entity.base.BaseResponse;
import com.example.mvparms.mvp.model.entity.bean.UsersBean;
import com.example.mvparms.mvp.model.entity.response.EnterpriseHomeResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * ================================================
 * Created by whosmyqueen on 10:18
 * <a href="mailto:644049260@qq.com">Contact me</a>
 * <a href="https://github.com/whosmyqueen">Follow me</a>
 * ================================================
 */
public interface CommonService {

    //登录
    @POST("/guns-cloud-auth/auth/appLogin")
    Observable<BaseResponse<UsersBean>> login(@Body UserLoginParam body);
    //刷新token
    @POST("/guns-cloud-auth/auth/appLogin")
    Call<BaseResponse> refreshLocalToken(@Body UserLoginParam body);
    //初始化
    @POST("/breed-server-insure/enterprises/init")
    Observable<EnterpriseHomeResponse> getHomeInfo(@Body String enterpriseId);
}
