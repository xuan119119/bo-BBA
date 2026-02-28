package com.bba.youbo_bba

import android.app.Application
import android.content.BroadcastReceiver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import cn.asus.push.BuildConfig
import cn.jpush.android.api.JPushInterface


class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
        // 3. 注册广播监听 RegistrationID 生成（解决立即获取为空的问题）
        val registrationReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent ?: return
                if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
                    // 此时获取的 RegistrationID 是有效值
                    val regId = intent.getStringExtra(JPushInterface.EXTRA_REGISTRATION_ID)
                    Log.d("JPush", "有效 RegistrationID：$regId")
                }
            }
        }

        // 注册广播过滤器，监听 RegistrationID 生成事件
        val filter = IntentFilter(JPushInterface.ACTION_REGISTRATION_ID)
        registerReceiver(registrationReceiver, filter, RECEIVER_NOT_EXPORTED)
    }

}