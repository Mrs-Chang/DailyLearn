package com.chang.dailylearn.dm.dialog

import android.content.Context
import android.util.Log

/**
 * 模拟 好评弹窗
 * */
class PraiseDialog(context: Context) : AbsDialog(context) {
    override fun getPriority(): Int = 2

    override fun needShownDialog(): Boolean {
        // 这里可以根据业务逻辑判断是否需要显示弹窗，如用户使用7天等
        // 这里通过Sp存储来模拟
        return needShow(PRAISE_DIALOG_KEY)
    }

    override fun show() {
        Log.d(LOG_TAG, "显示好评弹窗")
        setShown(PRAISE_DIALOG_KEY, true)
    }
}