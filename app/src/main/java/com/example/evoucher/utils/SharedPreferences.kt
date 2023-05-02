package com.example.evoucher.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesImp {
    companion object {
        const val TOKEN = "token"
        const val USER_INFO = "user_info"
        const val IS_SAVE_ACCOUNT = "is_save_account"
    }

    var sharedPreference: SharedPreferences
    var editor: SharedPreferences.Editor

    @Inject constructor(@ApplicationContext context: Context) {
        sharedPreference =  context.getSharedPreferences("APP_PRE", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
    }

    fun removeKey(key: String) {
        editor.remove(key)
        editor.apply()
        editor.commit()
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
        editor.commit()
    }

    fun getString(key: String) : String {
        return sharedPreference.getString(key, "") ?: ""
    }

    fun getBoolean(key: String) : Boolean {
        return sharedPreference.getBoolean(key, false);
    }
}