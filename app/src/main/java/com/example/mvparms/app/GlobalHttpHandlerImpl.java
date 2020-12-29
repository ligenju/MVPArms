
package com.example.mvparms.app;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.example.mvparms.BuildConfig;
import com.example.mvparms.constants.Globals;
import com.example.mvparms.manager.LoginManager;
import com.example.mvparms.manager.TokenManager;
import com.example.mvparms.manager.URLManager;
import com.example.mvparms.mvp.model.entity.base.BaseResponse;
import com.example.mvparms.mvp.model.entity.bean.UsersBean;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.utils.ArmsUtils;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     *      * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    @NonNull
    @Override
    public Response onHttpResultResponse(@Nullable String httpResult, @NonNull Interceptor.Chain chain, @NonNull Response response) {
        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
//            try {
//                List<User> list = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<List<User>>() {
//                }.getType());
//                User user = list.get(0);
//                Timber.w("Result ------> " + user.getLogin() + "    ||   Avatar_url------> " + user.getAvatarUrl());
//            } catch (Exception e) {
//                e.printStackTrace();
//                return response;
//            }
        }

        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        Request request = chain.request();
        try {
//                41601 token过期
//                401 token无效,未登录
            if (response.code() == 401) {
                BaseResponse<UsersBean> baseResponse = LoginManager.getInstance().refreshLogin();
                if (ObjectUtils.isNotEmpty(baseResponse) && baseResponse.getCode() == 200) {
                    UsersBean usersBean = baseResponse.getData();
                    TokenManager.getInstance().setLocalToken(usersBean.getToken());
                    Request newRequest = request.newBuilder().header("Authorization", "" + TokenManager.getInstance().getAccessLocalToken()).build();//重新拼装请求头
                    Response execute = ArmsUtils.obtainAppComponentFromContext(context).okHttpClient().newCall(newRequest).execute();
                    if (execute.code() == HTTP_OK) {
                        ResponseBody body = execute.body();
                        String bodyString = body.string();
                        okhttp3.MediaType contentType = body.contentType();
                        return response.newBuilder().body(ResponseBody.create(bodyString, contentType)).build();
                    }
                    return execute;
                }
                return response.newBuilder().code(200).build();
            }
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    @Override
    public Request onHttpRequestBefore(@NonNull Interceptor.Chain chain, @NonNull Request request) {
        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
//        return chain.proceed(request);
        Request.Builder builder = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Client-Source", Globals.APPLICATION_CHANNEL.code)
                .addHeader("Client-Id", Globals.CLIENTTYPE)
                .addHeader("Authorization", "" + TokenManager.getInstance().getAccessLocalToken());
        if (BuildConfig.URL_PROD_TEST && BuildConfig.URL_PROD) {
            String url = request.url().url().toString();
            if (url.contains(URLManager.getInstance().getApiUrl())){
                String[] strings = url.split(URLManager.getInstance().getApiUrl())[1].split("/");
                HttpUrl httpUrl = HttpUrl.parse(URLManager.getInstance().getApiUrl() + strings[0] + "-dev" + url.split(strings[0])[1]);
                return builder
                        .url(httpUrl)
                        .build();
            }
        }
        return builder.build();
    }
}
