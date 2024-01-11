package com.chang.dailylearn.dm.dialog

import android.content.Context
import android.util.Log

/**
 * 模拟 广告弹窗
 * */
class AdDialog(private val context: Context) : AbsDialog(context) {
    private val ad = DialogData(1, "XX广告弹窗") // 模拟广告数据

    override fun getPriority(): Int = 1

    override fun needShownDialog(): Boolean {
        // 广告数据通过接口获取，广告id应该是唯一的，所以根据id保持sp
        return needShow(AD_DIALOG_KEY + ad.id)
    }

    override fun show() {
        Log.d(LOG_TAG, "显示广告弹窗:${ad.name}")
        setShown(AD_DIALOG_KEY + ad.id, true)
    }
}