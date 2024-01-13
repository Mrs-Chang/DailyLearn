package com.chang.snow

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import com.chang.snow.SnowView.Companion.snowflakeBitmapCache
import java.util.Random
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class Snowflake(private val params: SnowflakeParams) {
    private val mRandom = Random()

    private var mSize: Double = 0.0
    private var mAlpha: Int = 255
    private var mSpeedX: Double = 0.0
    private var mSpeedY: Double = 0.0
    private var mPositionX: Double = 0.0
    private var mPositionY: Double = 0.0
    private var mSnowflakeImage: Bitmap? = null

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Style.FILL
    }

    init {
        reset()
    }

    private fun reset(){
        val deltaSize = params.sizeMaxInPx - params.sizeMinInPx
        mSize = abs(getRandomGaussian()) * deltaSize + params.sizeMinInPx
        params.snowflakeImage?.let {
            mSnowflakeImage = getSnowflakeBitmapFromCache(mSize.toInt())
        }
        //做一个线性插值，根据雪花的大小，来确定雪花的速度
        val lerp = (mSize - params.sizeMinInPx) / (params.sizeMaxInPx - params.sizeMinInPx)
        val speed = lerp * (params.speedMax - params.speedMin) + params.speedMin

        val angle = mRandom.nextDouble() * params.angleMax
        val leftOrRight = mRandom.nextBoolean() //true: left, false: right
        val radians = if (leftOrRight) {
            Math.toRadians(-angle)
        } else {
            Math.toRadians(angle)
        }
        mSpeedX = speed * sin(radians)
        mSpeedY = speed * cos(radians)

        mAlpha = mRandom.nextInt(params.alphaMax - params.alphaMin) + params.alphaMin
        mPaint.alpha = mAlpha

        mPositionX = mRandom.nextDouble() * params.canvasWidth
        mPositionY = -mSize
    }

    fun update() {
        mPositionX += mSpeedX
        mPositionY += mSpeedY
        if (mPositionY > params.canvasHeight) {
            reset()
        }
        //根据雪花的位置，来确定雪花的透明度
        val alphaPercentage = (params.canvasHeight - mPositionY).toFloat() / params.canvasHeight
        mPaint.alpha = (alphaPercentage * mAlpha).toInt()
    }

    fun draw(canvas: Canvas) {
        if (mSnowflakeImage != null) {
            canvas.drawBitmap(mSnowflakeImage!!, mPositionX.toFloat(), mPositionY.toFloat(), mPaint)
        } else {
            canvas.drawCircle(mPositionX.toFloat(), mPositionY.toFloat(), mSize.toFloat(), mPaint)
        }
    }

    private fun getRandomGaussian(): Double {
        val gaussian = mRandom.nextGaussian() / 2
        return if (gaussian > -1 && gaussian < 1) {
            gaussian
        } else {
            getRandomGaussian() // 确保在(-1, 1)之间
        }
    }

    private fun getSnowflakeBitmapFromCache(size: Int): Bitmap {
        return snowflakeBitmapCache.getOrPut(size) {
            // 创建新的 Bitmap 并放入缓存
            Bitmap.createScaledBitmap(params.snowflakeImage!!, size, size, false)
        }
    }
}