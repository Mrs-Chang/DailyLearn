package com.chang.dailylearn.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.chang.dailylearn.R

class FlipClockView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var textColor: Int = Color.BLACK
    private var textSize: Float = 48f
    private var backgroundColor: Int = Color.WHITE

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var currentHour: Int = 0
    private var animationProgress: Float = 0f

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.FlipClockView, 0, 0).apply {
            try {
                textColor = getColor(R.styleable.FlipClockView_textColor, textColor)
                backgroundColor = getColor(R.styleable.FlipClockView_backgroundColor, backgroundColor)
                textSize = getDimension(R.styleable.FlipClockView_textSize, textSize)
            } finally {
                recycle()
            }
        }

        textPaint.color = textColor
        textPaint.textSize = textSize
        backgroundPaint.color = backgroundColor

        // Just a simple way to increment the hour for demonstration purposes
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentHour = (currentHour + 1) % 12
                startFlipAnimation()
                handler.postDelayed(this, 1000L) // 1 hour delay
            }
        }, 1000L)
    }

    private fun startFlipAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000
            addUpdateListener { animation ->
                animationProgress = animation.animatedValue as Float
                invalidate()
            }
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = height / 2f
        val nextHour = (currentHour + 1) % 12

        // Draw the top half of the current hour
        canvas.save()
        canvas.clipRect(0, 0, width, centerY.toInt())
        canvas.drawText(currentHour.toString(), width / 2f, centerY, textPaint)
        canvas.restore()

        // Draw the flipping effect for the next hour
        canvas.save()
        canvas.clipRect(0, centerY.toInt(), width, height)
        val translateY = centerY * animationProgress * 2
        canvas.translate(0f, translateY)
        canvas.drawText(nextHour.toString(), width / 2f, centerY, textPaint)
        canvas.restore()
    }
}
