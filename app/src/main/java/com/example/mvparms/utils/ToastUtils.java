package com.example.mvparms.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.blankj.utilcode.util.ObjectUtils;
import com.example.mvparms.app.AppLifecyclesImpl;
import com.jess.arms.integration.AppManager;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

/**
 * @ProjectName: LivestockInsurance
 * @Desc:
 * @Author: whosmyqueen
 * @Date: 2020/10/9
 */
public class ToastUtils {
    public static void error(String message) {
        Activity activity = AppManager.getAppManager().getTopActivity();
        error(activity, message);
    }

    public static void error(Activity activity, String message) {
        if (ObjectUtils.isNotEmpty(activity))
            DialogUtils.with(activity)
                    .setEnableCancelButton(false)
                    .setContent(message)
                    .setOkListener(SweetAlertDialog::dismissWithAnimation)
                    .show();
    }

    public static void success(Context context,String message) {
        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void info(Context context,String message) {
        Toasty.info(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void warning(Context context,String message) {
        Toasty.warning(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void normal(Context context,String message) {
        Toasty.normal(context, message, Toast.LENGTH_SHORT).show();
    }

}
