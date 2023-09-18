package com.chang.dailylearn.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart


class FlipClockViewV2(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#2b292c")
    }

    private val bgPaint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1e1e1e")
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#ffffff")
        textSize = 300f
    }

    private val textBounds = Rect()

    private var number4 = "4"
    private var number5 = "5"
    private var currentNumber = number4

    private val space = 10f //上下半间隔
    private val bgBorderR = 10f //背景圆角

    private var degree = 0f
    private val camera = Camera()
    private var flipping = false


    init {
        setOnClickListener {
            startFlip()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //上半部分
        val upperHalfBottom = height.toFloat() / 2 - space / 2
        //下半部分
        val lowerHalfTop = height.toFloat() / 2 + space / 2

        // 居中绘制数字4
        textPaint.getTextBounds(number4, 0, number4.length, textBounds)
        val x = (width - textBounds.width()) / 2f - textBounds.left
        val y = (height + textBounds.height()) / 2f - textBounds.bottom


        if (!flipping) {
            //上半部分裁剪
            canvas.save()
            canvas.clipRect(
                0f,
                0f,
                width.toFloat(),
                upperHalfBottom
            )
            canvas.drawRoundRect(
                0f,
                0f,
                width.toFloat(),
                upperHalfBottom,
                bgBorderR,
                bgBorderR,
                bgPaint
            )
            canvas.drawText(currentNumber, x, y, textPaint)
            canvas.restore()
            // 下半部分裁剪
            canvas.save()
            canvas.clipRect(
                0f,
                lowerHalfTop,
                width.toFloat(),
                height.toFloat()
            )
            canvas.drawRoundRect(
                0f,
                lowerHalfTop,
                width.toFloat(),
                height.toFloat(),
                bgBorderR,
                bgBorderR,
                bgPaint
            )
            canvas.drawText(currentNumber, x, y, textPaint)
            canvas.restore()
        } else {
            canvas.save()
            canvas.clipRect(
                0f,
                0f,
                width.toFloat(),
                upperHalfBottom
            )
            canvas.drawRoundRect(
                0f,
                0f,
                width.toFloat(),
                upperHalfBottom,
                bgBorderR,
                bgBorderR,
                bgPaint
            )
            canvas.drawText(number5, x, y, textPaint)
            canvas.restore()
            // 下半部分裁剪
            canvas.save()
            canvas.clipRect(
                0f,
                lowerHalfTop,
                width.toFloat(),
                height.toFloat()
            )
            canvas.drawRoundRect(
                0f,
                lowerHalfTop,
                width.toFloat(),
                height.toFloat(),
                bgBorderR,
                bgBorderR,
                bgPaint
            )
            canvas.drawText(number4, x, y, textPaint)
            canvas.restore()
            if (degree < 90) {
                //上半部分裁剪
                canvas.save()
                canvas.clipRect(
                    0f,
                    0f,
                    width.toFloat(),
                    upperHalfBottom
                )
                camera.save()
                canvas.translate(width / 2f, height / 2f)
                camera.rotateX(-degree)
                camera.applyToCanvas(canvas)
                canvas.translate(-width / 2f, -height / 2f)
                camera.restore()
                canvas.drawRoundRect(
                    0f,
                    0f,
                    width.toFloat(),
                    upperHalfBottom,
                    bgBorderR,
                    bgBorderR,
                    bgPaint2
                )
                canvas.drawText(number4, x, y, textPaint)
                canvas.restore()
            } else {
                canvas.save()
                canvas.clipRect(
                    0f,
                    lowerHalfTop,
                    width.toFloat(),
                    height.toFloat()
                )
                camera.save()
                canvas.translate(width / 2f, height / 2f)
                val bottomDegree = 180 - degree
                camera.rotateX(bottomDegree)
                camera.applyToCanvas(canvas)
                canvas.translate(-width / 2f, -height / 2f)
                camera.restore()
                canvas.drawRoundRect(
                    0f,
                    lowerHalfTop,
                    width.toFloat(),
                    height.toFloat(),
                    bgBorderR,
                    bgBorderR,
                    bgPaint2
                )
                canvas.drawText(number5, x, y, textPaint)
                canvas.restore()
            }
        }
    }

    private fun setDegree(degree: Float) {
        this.degree = degree
        invalidate()
    }

    private fun startFlip() {
        val animator = ValueAnimator.ofFloat(0f, 180f)
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            setDegree(animatedValue)
        }
        animator.doOnStart {
            flipping = true
            currentNumber = number5
        }
        animator.doOnEnd {
            flipping = false
        }
        animator.duration = 1000
        animator.interpolator = LinearInterpolator()
        animator.start()
    }


}
