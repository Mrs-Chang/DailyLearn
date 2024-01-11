package com.chang.dailylearn.dm.dialog

import android.content.Context
import android.util.Log

/**
 * 模拟 隐私政策弹窗
 * */
class PolicyDialog(context: Context) : AbsDialog(context) {
    override fun getPriority(): Int = 0

    override fun needShownDialog(): Boolean {
        // 这里可以根据业务逻辑判断是否需要显示弹窗，如接口控制等等
        // 这里通过Sp存储来模拟
        return needShow(POLICY_DIALOG_KEY)
    }

    override fun show() {
        Log.d(LOG_TAG, "显示隐私政策弹窗")
        setShown(POLICY_DIALOG_KEY, true)
    }
}