package com.chang.dailylearn.view

import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import com.google.android.material.transition.platform.MaterialContainerTransform

class NineSquareGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr){

    private var clickView:View? = null
    private var onItemClickListener: ((View, Int) -> Unit) = { view, pos ->
        clickView = view
        startTransform(view)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            getChildAt(i).setOnClickListener {
                onItemClickListener.invoke(it, i)
            }
        }
    }

    /**
     * 处理Transform
     * */
    private lateinit var targetView: View
    private var throttledTime: Long = SystemClock.elapsedRealtime()
    private var duration: Long = 500L

    fun bindTargetView(targetView: View) {
        this.targetView = targetView.apply { visibility = View.INVISIBLE }
        this.targetView.setOnClickListener {
            finishTransform(this)
        }
    }

    private fun startTransform(startView: View) {
        startTransform(startView, this)
    }

    private fun startTransform(startView: View, container: ViewGroup) {
        container.post {
            require(::targetView.isInitialized) {
                "must set a targetView using bindTargetView() or transformation_targetView attribute."
            }
            // 处理多次点击的间隔
            val now = SystemClock.elapsedRealtime()
            if (now - throttledTime >= duration) {
                throttledTime = now
                beginDelayingAndTransform(container, startView, targetView)
            }
        }
    }

    private fun finishTransform(container: ViewGroup) {
        if (clickView == null) {
            return
        }
        container.post {
            require(::targetView.isInitialized) {
                "must set a targetView using bindTargetView() or transformation_targetView attribute."
            }
            beginDelayingAndTransform(container, targetView, clickView!!)
        }
    }

    private fun beginDelayingAndTransform(
        container: ViewGroup,
        startView: View,
        endView: View
    ) {
        startView.visibility = View.INVISIBLE
        endView.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(container, getTransform(startView, endView))
    }

    private fun getTransform(startItemView: View, targetView: View): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            startView = startItemView
            endView = targetView
            addTarget(targetView)
            duration = this@NineSquareGridLayout.duration
            containerColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
            scrimColor = Color.TRANSPARENT
        }
    }
}