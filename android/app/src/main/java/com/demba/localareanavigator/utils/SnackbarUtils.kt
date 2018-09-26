package com.demba.localareanavigator.utils

import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View

import com.demba.localareanavigator.R

object SnackbarUtils {
    fun showError(context: Context, view: View, text: String, length: Int) {
        val snackbar = Snackbar.make(view, text, length)
        snackbar.view.setBackgroundColor(context.getColor(R.color.colorError))
        snackbar.show()
    }

    fun showSuccess(context: Context, view: View, text: String, length: Int) {
        val snackbar = Snackbar.make(view, text, length)
        snackbar.view.setBackgroundColor(context.getColor(R.color.colorSuccess))
        snackbar.show()
    }
}
