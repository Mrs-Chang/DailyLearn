package com.chang.dailylearn.anim

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.chang.dailylearn.R

class TodoItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.apply {
            // 设定动画
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_bottom)
            startAnimation(animation)
        }
        // 务必调用 dispatchAddFinished，否则会导致动画无法结束
        dispatchAddFinished(holder)
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchAddFinished(holder)
        return false
    }
}