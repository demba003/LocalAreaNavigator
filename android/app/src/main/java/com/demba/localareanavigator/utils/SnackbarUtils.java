package com.demba.localareanavigator.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.demba.localareanavigator.R;

public class SnackbarUtils {
    public static void showError(Context context, View view, String text, int length) {
        Snackbar snackbar = Snackbar.make(view, text, length);
        snackbar.getView().setBackgroundColor(context.getColor(R.color.colorError));
        snackbar.show();
    }

    public static void showSuccess(Context context, View view, String text, int length) {
        Snackbar snackbar = Snackbar.make(view, text, length);
        snackbar.getView().setBackgroundColor(context.getColor(R.color.colorSuccess));
        snackbar.show();
    }
}
