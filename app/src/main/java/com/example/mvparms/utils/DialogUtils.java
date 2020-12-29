package com.example.mvparms.utils;

import android.content.Context;

import com.blankj.utilcode.util.ObjectUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lombok.Getter;

public class DialogUtils {
    private Context context;
    private String title;
    private String content;
    private String okText;
    private String cancelText;
    private DialogType dialogType;
    private boolean enableCancelButton;
    private boolean enableOkButton = true;
    private OnClickListener okListener;
    private OnClickListener cancelListener;

    private SweetAlertDialog dialog;

    public static interface OnClickListener {
        void click(SweetAlertDialog dialog);
    }

    public enum DialogType {
        NORMAL_TYPE(0),
        ERROR_TYPE(1),
        SUCCESS_TYPE(2),
        WARNING_TYPE(3),
        CUSTOM_IMAGE_TYPE(4),
        PROGRESS_TYPE(5);
        @Getter
        private int type;

        DialogType(int type) {
            this.type = type;
        }
    }

    private SweetAlertDialog show() {
        String title = "提示";
        if (ObjectUtils.isEmpty(this.title)) {
            if (this.dialogType == DialogType.PROGRESS_TYPE) {
                title = "";
            } else {
                title = "提示";
            }
        } else {
            title = this.title;
        }
        this.enableCancelButton = this.enableCancelButton && this.dialogType != DialogType.PROGRESS_TYPE;
        if (ObjectUtils.isEmpty(dialog)) {
            dialog = new SweetAlertDialog(this.context, this.dialogType.type);
        }
        dialog
                .setTitleText(title)
                .setContentText(this.content)
                .setCancelText(enableCancelButton ? (ObjectUtils.isNotEmpty(this.cancelText) ? this.cancelText : "取消") : null)
                .setConfirmText(ObjectUtils.isNotEmpty(this.okText) ? this.okText : "确定")
                .showCancelButton(this.enableCancelButton)
                .setCancelClickListener(sDialog -> {
                    if (ObjectUtils.isNotEmpty(this.cancelListener)) {
                        this.cancelListener.click(sDialog);
                    }
                    sDialog.dismiss();
                })
                .setConfirmClickListener(sDialog -> {
                    if (ObjectUtils.isNotEmpty(this.okListener))
                        this.okListener.click(sDialog);
                    sDialog.dismiss();
                }).changeAlertType(this.dialogType.type);
        if (!dialog.isShowing())
            dialog.show();
        return dialog;
    }

    public static class Builder {
        private Context context;
        private String title;
        private String content;
        private String okText;
        private String cancelText;
        private DialogType dialogType = DialogType.NORMAL_TYPE;
        private boolean enableCancelButton;
        private boolean enableOkButton = true;
        private OnClickListener okListener;
        private OnClickListener cancelListener;

        public Builder setEnableCancelButton(boolean enableCancelButton) {
            this.enableCancelButton = enableCancelButton;
            this.cancelText = ObjectUtils.isEmpty(this.cancelText) ? "取消" : this.cancelText;
            return this;
        }

        public Builder setEnableOkButton(boolean enableOkButton) {
            this.enableOkButton = enableOkButton;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setOkText(String okText) {
            this.okText = okText;
            return this;
        }

        public Builder setDialogType(DialogType dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public Builder setCancelListener(OnClickListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setOkListener(OnClickListener okListener) {
            this.okListener = okListener;
            return this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        private DialogUtils build() {
            return new DialogUtils(this);
        }

        public SweetAlertDialog show() {
            return build().show();
        }
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public DialogUtils(Builder builder) {
        this.context = builder.context;
        this.title = builder.title;
        this.content = builder.content;
        this.okListener = builder.okListener;
        this.cancelListener = builder.cancelListener;
        this.okText = builder.okText;
        this.dialogType = builder.dialogType;
        this.cancelText = builder.cancelText;
        this.enableOkButton = builder.enableOkButton;
        this.enableCancelButton = builder.enableCancelButton;
    }
}
