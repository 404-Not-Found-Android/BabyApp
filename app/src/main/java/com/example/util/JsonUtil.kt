package com.example.util

import android.util.Log
import com.google.gson.Gson

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/21/21
 */
class JsonUtil {
    companion object {
        fun <T> parse(json: String?, clazz: Class<T>): T? {
            return try {
                Gson().fromJson(json, clazz)
            } catch (e: Exception) {
                Log.e("TAG", "json异常=$json")
                null
            }
        }

        fun toJson(bean: Any): String {
            return Gson().toJson(bean)
        }
    }
}