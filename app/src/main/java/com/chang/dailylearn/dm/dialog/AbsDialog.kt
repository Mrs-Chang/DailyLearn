package com.chang.dailylearn.dm.dialog

import android.content.Context
import android.content.SharedPreferences

abstract class AbsDialog(private val context: Context) {
    private var nextDialog: AbsDialog? = null

    abstract fun getPriority(): Int

    abstract fun needShownDialog(): Boolean

    fun setNextDialog(dialog: AbsDialog?) {
        nextDialog = dialog
    }

    open fun showDialog() {
        if (needShownDialog()) {
            show()
        } else {
            nextDialog?.showDialog()
        }
    }

    protected abstract fun show()

    // Sp存储
    open fun needShow(key: String): Boolean {
        val sp: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, true)
    }

    open fun setShown(key: String, show: Boolean) {
        val sp: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, !show).apply()
    }

    companion object {
        const val LOG_TAG = "Dialog"
        const val SP_NAME = "dialog"
        const val POLICY_DIALOG_KEY = "policy_dialog"
        const val AD_DIALOG_KEY = "ad_dialog"
        const val PRAISE_DIALOG_KEY = "praise_dialog"
    }
}