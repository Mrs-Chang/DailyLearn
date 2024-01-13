package com.chang.snow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

class SnowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val snowflakeCount:Int = 50,
    private val params: SnowflakeParams? = null
) : View(context, attrs, defStyleAttr) {

    companion object {
        val snowflakeBitmapCache = mutableMapOf<Int, Bitmap>()
    }

    private lateinit var mSnowItemList: List<Snowflake>

    private val mSnowflakeImage =
        (ContextCompat.getDrawable(context, R.drawable.icon_snowflake) as BitmapDrawable).bitmap

    private lateinit var mHandler: Handler
    private lateinit var mHandlerThread: HandlerThread

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mHandlerThread = HandlerThread("SnowView").apply {
            start()
            mHandler = Handler(looper)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mSnowItemList = createSnowItemList()
    }

    private fun createSnowItemList(): List<Snowflake> {
        return List(snowflakeCount) {
            if (params == null) {
                Snowflake(
                    SnowflakeParams(
                        canvasWidth = width,
                        canvasHeight = height,
                        snowflakeImage = mSnowflakeImage
                    )
                )
            } else {
                params.canvasWidth = width
                params.canvasHeight = height
                Snowflake(params)
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        for (snowItem in mSnowItemList) {
            snowItem.draw(canvas)
        }
        mHandler.post {
            for (snowItem in mSnowItemList) {
                snowItem.update()
            }
            postInvalidateOnAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        mHandlerThread.quitSafely()
        super.onDetachedFromWindow()
    }

    class Builder(private val context: Context) {
        private var snowflakeCount: Int = 0
        private var canvasWidth: Int = 0
        private var canvasHeight: Int = 0
        private var sizeMinInPx: Int = 40
        private var sizeMaxInPx: Int = 60
        private var speedMin: Int = 10
        private var speedMax: Int = 20
        private var alphaMin: Int = 150
        private var alphaMax: Int = 255
        private var angleMax: Int = 10
        private var snowflakeImage: Bitmap? = null

        fun setSnowflakeCount(snowflakeCount: Int) = apply {
            this.snowflakeCount = snowflakeCount
        }

        fun setCanvasSize(canvasWidth: Int, canvasHeight: Int) = apply {
            this.canvasWidth = canvasWidth
            this.canvasHeight = canvasHeight
        }

        fun setSizeRangeInPx(sizeMin: Int, sizeMax: Int) = apply {
            this.sizeMinInPx = sizeMin
            this.sizeMaxInPx = sizeMax
        }

        fun setSpeedRange(speedMin: Int, speedMax: Int) = apply {
            this.speedMin = speedMin
            this.speedMax = speedMax
        }

        fun setAlphaRange(alphaMin: Int, alphaMax: Int) = apply {
            this.alphaMin = alphaMin
            this.alphaMax = alphaMax
        }

        fun setAngleMax(angleMax: Int) = apply {
            this.angleMax = angleMax
        }

        fun setSnowflakeImage(snowflakeImage: Bitmap) = apply {
            this.snowflakeImage = snowflakeImage
        }

        fun setSnowflakeImageResId(@DrawableRes snowflakeImageResId: Int) = apply {
            this.snowflakeImage = ContextCompat.getDrawable(context, snowflakeImageResId)?.let {
                (it as BitmapDrawable).bitmap
            }
        }

        fun build(): SnowView {
            return SnowView(
                context, snowflakeCount = snowflakeCount, params = SnowflakeParams(
                    canvasWidth = canvasWidth,
                    canvasHeight = canvasHeight,
                    sizeMinInPx = sizeMinInPx,
                    sizeMaxInPx = sizeMaxInPx,
                    speedMin = speedMin,
                    speedMax = speedMax,
                    alphaMin = alphaMin,
                    alphaMax = alphaMax,
                    angleMax = angleMax,
                    snowflakeImage = snowflakeImage
                )
            )
        }
    }
}