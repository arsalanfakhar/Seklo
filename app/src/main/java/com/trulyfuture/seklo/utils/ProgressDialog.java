package com.trulyfuture.seklo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.trulyfuture.seklo.R;

public class ProgressDialog {

    public static Dialog loadDialog = null;

    public static void showLoader(Activity context) {

        if (loadDialog != null) {
            if (loadDialog.isShowing())
                loadDialog.dismiss();
        }
        loadDialog = new Dialog(context, R.style.ProgressDialog);
        loadDialog.setContentView(R.layout.progress_dialog);
        loadDialog.setCanceledOnTouchOutside(false);

//        loadDialog.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);

        if (!loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    public static boolean isShowing() {
        if (loadDialog != null) {
            if (loadDialog.isShowing()) {
                return true;
            }
        }
        return false;
    }

    public static void hideLoader() {
        if (loadDialog != null && loadDialog.isShowing())
            loadDialog.dismiss();
    }
}
