package com.example.mvparms.manager;


import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.example.mvparms.callback.OnCallBack;
import com.example.mvparms.constants.Globals;
import com.example.mvparms.mvp.model.api.param.UserLoginParam;
import com.example.mvparms.mvp.model.api.service.CommonService;
import com.example.mvparms.mvp.model.entity.base.BaseResponse;
import com.example.mvparms.mvp.model.entity.bean.EnterpriseSettingBean;
import com.example.mvparms.mvp.model.entity.bean.UsersBean;
import com.example.mvparms.mvp.model.entity.response.EnterpriseHomeResponse;
import com.example.mvparms.utils.RSACodeHelper;
import com.example.mvparms.utils.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import retrofit2.Call;
import retrofit2.Response;


/**
 * 登录和退出登录相关
 */

public class LoginManager {
    private static final String LOGIN_USER_NAME = "login_user_name";
    private static final String LOGIN_USER_PASSWORD = "login_user_password";
    private final IRepositoryManager repositoryManager;
    public UsersBean mCurrentUser;
    private RxErrorHandler rxErrorHandler;


    private LoginManager() {
        repositoryManager = ArmsUtils.obtainAppComponentFromContext(Utils.getApp()).repositoryManager();
        rxErrorHandler = ArmsUtils.obtainAppComponentFromContext(Utils.getApp()).rxErrorHandler();
        mCurrentUser = new UsersBean();
    }

    public static LoginManager getInstance() {
        return DataManagerHolder.INSTANCE;
    }

    public void saveUserLocal(String account, String password) {
        SPUtils.getInstance().put(LOGIN_USER_NAME, account);
        SPUtils.getInstance().put(LOGIN_USER_PASSWORD, password);
    }

    public String getUserNameLocal() {
        return SPUtils.getInstance().getString(LOGIN_USER_NAME, null);
    }

    public String getUserPasswordLocal() {
        return SPUtils.getInstance().getString(LOGIN_USER_PASSWORD, null);
    }


    private void superLogin(BaseActivity activity, String account, String password, OnCallBack<Boolean> callback) {
        String passwords = RSACodeHelper.getInstance().cPubEncrypt(password).replace("\n",/**/"");
        mCurrentUser.setPassword(passwords);
        mCurrentUser.setAccount(account);
        UserLoginParam param = new UserLoginParam();
        param.setAccount(account);
        param.setPassword(passwords);
        Observable<BaseResponse<UsersBean>> observable = repositoryManager
                .obtainRetrofitService(CommonService.class)
                .login(param)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2));
        if (ObjectUtils.isNotEmpty(activity)) {
            IView iView = (IView) activity;
            observable = observable
                    .doOnSubscribe(disposable -> iView.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(iView::hideLoading)
                    .compose(RxLifecycleUtils.bindToLifecycle(activity));

        } else {
            observable = observable
                    .observeOn(AndroidSchedulers.mainThread());
        }
        observable.subscribe(new ErrorHandleSubscriber<BaseResponse<UsersBean>>(rxErrorHandler) {
            @Override
            public void onNext(BaseResponse<UsersBean> response) {
                if (!response.isSuccess()) {
                    ToastUtils.error(response.getMessage());
                    if (callback != null)
                        callback.onComplete(false);
                    return;
                }
                UsersBean bean = response.getData();
                int userType = bean.getUserType();
                TokenManager.getInstance().setLocalToken(bean.getToken());
                if (userType == 1) {
                    initSetting(activity, account, passwords, bean, callback);
                } else {
                    saveUserInfo(account, password, bean);
                    UserManager.setDeptId(bean.getCompanyId());
                    UserManager.setLoginDeptId(bean.getCompanyId());
                    if (callback != null)
                        callback.onComplete(true);
                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (callback != null)
                    callback.onComplete(false);
            }
        });
    }

    private void initSetting(BaseActivity activity, String account, String password, UsersBean usersBean, OnCallBack<Boolean> callback) {
        Observable<EnterpriseHomeResponse> observable = repositoryManager
                .obtainRetrofitService(CommonService.class)
                .getHomeInfo(usersBean.getCompanyId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2));
        if (ObjectUtils.isNotEmpty(activity)) {
            IView iView = (IView) activity;
            observable = observable
                    .doOnSubscribe(disposable -> iView.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(iView::hideLoading)
                    .compose(RxLifecycleUtils.bindToLifecycle(activity));

        } else {
            observable = observable
                    .observeOn(AndroidSchedulers.mainThread());

        }
        observable.subscribe(new ErrorHandleSubscriber<EnterpriseHomeResponse>(rxErrorHandler) {
            @Override
            public void onNext(EnterpriseHomeResponse response) {
                if (!response.isSuccess()) {
                    ToastUtils.error(response.getMessage());
                    if (callback != null)
                        callback.onComplete(false);
                    return;
                }
                EnterpriseSettingBean bean = response.getData();
                saveUserInfo(account, password, usersBean);
                UserManager.setAnimalTypes(bean.getAnimalTypes());
                int animal = Integer.parseInt(bean.getAnimalTypes().split(",")[0]);
                UserManager.setAnimalType(animal);
                UserManager.setPigScanFaceTimes(bean.getFaceTimes());
                UserManager.setDeptId(bean.getGroupId());
                UserManager.setLoginDeptId(bean.getGroupId());
                UserManager.setCameraCountType(bean.getCameraCountType());
                UserManager.setInsureValidate(bean.getInsureValidate());
                UserManager.setCompanyName(bean.getBreedEnterprisesName());
                UserManager.setCheckNumFix(bean.getFixNum());
                if (callback != null)
                    callback.onComplete(true);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (callback != null)
                    callback.onComplete(false);
            }
        });
    }

    private void saveUserInfo(String account, String passwords, UsersBean user) {
        TokenManager.getInstance().setLocalToken(user.getToken());
        saveUserLocal(account, passwords);
        mCurrentUser.setCompanyId(user.getCompanyId());
        mCurrentUser.setAccountId(user.getAccountId());
        mCurrentUser.setName(user.getName());
        mCurrentUser.setMenus(user.getMenus());
        mCurrentUser.setUserId(user.getUserId());
        mCurrentUser.setToken(user.getToken());
        mCurrentUser.setDeptName(user.getDeptName());
        mCurrentUser.setGroupId(user.getGroupId());
        mCurrentUser.setAvatarUrl(user.getAvatarUrl());
        mCurrentUser.setCompanyName(user.getCompanyName());
        mCurrentUser.setCompanyShortName(user.getCompanyShortName());
        mCurrentUser.setUserType(user.getUserType());
        mCurrentUser.setDutyName(user.getDutyName());
        UserManager.setUserInfo(mCurrentUser);
        Globals.LOGIN_USER = user;
    }
    //正常登陆
    public void login(BaseActivity activity, String account, String password, OnCallBack<Boolean> callback) {
        superLogin(activity, account, password, callback);
    }
    //获取登陆信息
    public void login(String account, String password, OnCallBack<Boolean> callback) {
        superLogin(null, account, password, callback);
    }

    public BaseResponse<UsersBean> refreshLogin() throws IOException {
        return superRefreshLogin();
    }

    private BaseResponse<UsersBean> superRefreshLogin() throws IOException {
        String userName = LoginManager.getInstance().getUserNameLocal();
        String pwd = LoginManager.getInstance().getUserPasswordLocal();

        UserLoginParam param = new UserLoginParam();
        param.setAccount(userName);
        param.setPassword(pwd);
        Call<BaseResponse> call = repositoryManager
                .obtainRetrofitService(CommonService.class)
                .refreshLocalToken(param);
        LogUtils.e("开始刷新token~~~~~~~~~~~");
        Response<BaseResponse> response = call.execute();
        BaseResponse baseResponse = null;
        try {
            LogUtils.e("开始刷新token~~~~~~~~~~~ : " + response);
            if (response.code() == 200) {
                baseResponse = response.body();
                baseResponse.setData(GsonUtils.fromJson(GsonUtils.toJson(baseResponse.getData()), UsersBean.class));
            } else if (response.code() == 500) {
                baseResponse = response.body();
            }
        } catch (Exception e) {
            baseResponse = new BaseResponse();
            baseResponse.setCode(500);
            baseResponse.setMessage(response.errorBody().string());
        }
        return baseResponse;
    }

    private static class DataManagerHolder {
        private static final LoginManager INSTANCE = new LoginManager();
    }
}
