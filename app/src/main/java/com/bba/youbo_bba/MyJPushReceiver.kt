package com.bba.youbo_bba

import android.content.Context
import android.util.Log
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver

class MyJPushReceiver : JPushMessageReceiver() {
    private val TAG = "JPushReceiver"

    // 接收自定义消息（透传消息）
    override fun onMessage(context: Context?, customMessage: CustomMessage?) {
        super.onMessage(context, customMessage)
        val msg: String? = customMessage?.message
        Log.d(TAG, "收到极光推送消息: $msg")
    }

    // 接收标签操作结果
    override fun onTagOperatorResult(context: Context?, jPushMessage: JPushMessage) {
        super.onTagOperatorResult(context, jPushMessage)
        Log.d(TAG, "标签操作结果: $jPushMessage")
    }

    // 接收别名操作结果
    override fun onAliasOperatorResult(context: Context?, jPushMessage: JPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage)
        Log.d(TAG, "别名操作结果: $jPushMessage")
    }

}
