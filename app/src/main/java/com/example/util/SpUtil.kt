package com.example.util

import android.content.Context
import android.content.SharedPreferences
import com.example.globle.SpConst

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/25/21
 */
class SpUtil constructor(context: Context) {
    private val sp: SharedPreferences by lazy { context.getSharedPreferences(SpConst.SP_NAME, Context.MODE_PRIVATE) }
    private val editor: SharedPreferences.Editor by lazy {
        val edit = sp.edit()
        edit.apply()
        return@lazy edit
    }

    companion object {
        private var INSTANCE: SpUtil? = null

        fun getInstance(context: Context): SpUtil {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SpUtil(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    fun setString(key: String, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String): String {
        return sp.getString(key, "") ?: ""
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun setLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun getLong(key: String): Long {
        return sp.getLong(key, 0L)
    }

    fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }
}