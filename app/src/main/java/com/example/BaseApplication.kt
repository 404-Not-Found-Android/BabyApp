package com.example

import android.app.Application
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/21/21
 */
@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initQMUI()
    }

    private fun initQMUI() {
//        QMUISwipeBackActivityManager.init(this)
    }
}