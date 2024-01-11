package com.chang.dailylearn.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.Log
import kotlin.math.cos
import kotlin.math.sin

class RabbitDrawable : Drawable() {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()

        //椭圆: 中心点，长轴，短轴
        val ellipseCenterX = width / 2f
        val ellipseCenterY = height / 2f
        val majorAxis = width / 3f  // 长轴
        val minorAxis = height / 3.5f   // 短轴

        // 画兔子的头部为椭圆
        paint.color = Color.WHITE
        val ovalRect = RectF(
            ellipseCenterX - majorAxis,
            ellipseCenterY - minorAxis,
            ellipseCenterX + majorAxis,
            ellipseCenterY + minorAxis
        )
        canvas.drawOval(ovalRect, paint)

        // 左耳朵，计算椭圆上的点
        val leftAngleStart = Math.toRadians(-110.0) // 这里用-110度作为开始点的参数，可以调整
        val leftAngleEnd = Math.toRadians(-140.0)   // 这里用-140度作为结束点的参数，可以调整

        val leftStartX = ellipseCenterX + majorAxis * cos(leftAngleStart).toFloat()
        val leftStartY = ellipseCenterY + minorAxis * sin(leftAngleStart).toFloat()

        val leftEndX = ellipseCenterX + majorAxis * cos(leftAngleEnd).toFloat()
        val leftEndY = ellipseCenterY + minorAxis * sin(leftAngleEnd).toFloat()

        // 画兔子的左耳使用三阶贝塞尔曲线
        val leftEarPath = Path().apply {
            moveTo(leftStartX, leftStartY)
            cubicTo(
                240f, 0f,
                0f, 0f,
                leftEndX, leftEndY
            )
        }
        canvas.drawPath(leftEarPath, paint)


        Log.d(
            "RabbitDrawable",
            "draw: leftStartX=$leftStartX, leftStartY=$leftStartY, leftEndX=$leftEndX, leftEndY=$leftEndY"
        )

        // 右耳朵，计算椭圆上的点
        val rightAngleStart = Math.toRadians(-40.0) // 这里用-50度作为开始点的参数，可以调整
        val rightAngleEnd = Math.toRadians(-70.0)   // 这里用-70度作为结束点的参数，可以调整

        val rightStartX = ellipseCenterX + majorAxis * cos(rightAngleStart).toFloat()
        val rightStartY = ellipseCenterY + minorAxis * sin(rightAngleStart).toFloat()

        val rightEndX = ellipseCenterX + majorAxis * cos(rightAngleEnd).toFloat()
        val rightEndY = ellipseCenterY + minorAxis * sin(rightAngleEnd).toFloat()

        // 画兔子的右耳朵使用三阶贝塞尔曲线
        val rightEarPath = Path().apply {
            moveTo(rightStartX, rightStartY)
            cubicTo(
                760f,0f,
                518f,0f,
                rightEndX, rightEndY
            )
        }
        canvas.drawPath(rightEarPath, paint)
        Log.d(
            "RabbitDrawable",
            "draw: rightStartX=$rightStartX, rightStartY=$rightStartY, rightEndX=$rightEndX, rightEndY=$rightEndY"
        )


//        // 画兔子的眼睛
//        paint.color = Color.BLACK
//        canvas.drawCircle(width * 0.4f, height * 0.45f, width * 0.05f, paint)
//        canvas.drawCircle(width * 0.6f, height * 0.45f, width * 0.05f, paint)
//
//        // 画兔子的鼻子
//        paint.color = Color.RED
//        canvas.drawCircle(width / 2f, height * 0.5f, width * 0.025f, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicHeight(): Int {
        return 800
    }

    override fun getIntrinsicWidth(): Int {
        return 800
    }
}
