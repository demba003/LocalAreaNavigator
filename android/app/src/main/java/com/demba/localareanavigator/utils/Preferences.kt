package com.demba.localareanavigator.utils

import android.content.Context
import android.preference.PreferenceManager

object Preferences {
    private const val LAST_PLACE = "LAST_PLACE"

    fun write(context: Context, key: String, value: String) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putString(key, value)
                .apply()
    }

    fun read(context: Context, key: String): String {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(key, "")
    }
}
