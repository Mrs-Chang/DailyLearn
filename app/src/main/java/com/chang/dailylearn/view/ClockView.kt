package com.chang.dailylearn.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.min


class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mRadius = 0f
    private var mActualTop = 0f
    private var mActualLeft = 0f
    private var mActualRight = 0f
    private var mActualBottom = 0f
    private var mDefaultPadding = 0f


    private val mTextPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#80ffffff")
            textSize = 14.sp2px().toFloat()
        }
    }
    private val mCircleStrokeWidth = 2f
    private val mCirclePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.parseColor("#80ffffff")
            strokeWidth = mCircleStrokeWidth
        }
    }

    private val mTextRect = Rect()
    private val mCircleRectF = RectF()

    /* 刻度圆弧的外接矩形 */
    private val mScaleArcRectF = RectF()
    private var mScaleLength = 0f
    private val mScaleArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#80ffffff")
        strokeWidth = 2f
    }
    private val mScaleLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#237EAD")
        strokeWidth = 2f
    }

    private val mGradientMatrix = Matrix()

    /* 时针角度 */
    private var mHourDegree = 0f

    /* 分针角度 */
    private var mMinuteDegree = 0f

    /* 秒针角度 */
    private var mSecondDegree = 0f

    /* 梯度扫描渐变 */
    private lateinit var mSweepGradient: SweepGradient

    /* 秒针path */
    private val mSecondHandPath = Path()

    /* 时针paint */
    private val mSecondHandPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#ffffff")
    }

    /* 时针path */
    private val mHourHandPath = Path()

    /* 时针paint */
    private val mHourHandPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#80ffffff")
    }

    /* 分针path */
    private val mMinuteHandPath = Path()

    /* 分针paint */
    private val mMinuteHandPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#ffffff")
    }

    /**
     * 由于onSizeChanged方法在构造方法、onMeasure之后，又在onDraw之前
     * 此时已经完成全局变量初始化，也得到了控件的宽高，所以可以在这个方法中确定一些与宽高有关的数值
     * 比如这个View的半径啊、padding值等，方便绘制的时候计算大小和位置：
     * */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = min(w - (paddingLeft + paddingRight), h - (paddingBottom + paddingTop)) / 2f
        mDefaultPadding = 0.12f * mRadius
        mActualTop = paddingTop + h / 2 - mRadius + mDefaultPadding
        mActualLeft = paddingLeft + w / 2 - mRadius + mDefaultPadding
        mActualRight = paddingRight + w / 2 + mRadius - mDefaultPadding
        mActualBottom = paddingBottom + h / 2 + mRadius - mDefaultPadding
        mScaleLength = 0.12f * mRadius
        mScaleArcPaint.strokeWidth = mScaleLength
        mScaleLinePaint.strokeWidth = 0.012f * mRadius
        mSweepGradient = SweepGradient(
            w / 2f,
            h / 2f,
            intArrayOf(Color.parseColor("#80ffffff"), Color.parseColor("#ffffff")),
            floatArrayOf(0.75f, 1f)
        )
    }


    override fun onDraw(canvas: Canvas) {
        getTimeDegree()
        drawTimeText(canvas)
        drawScaleLine(canvas)
        drawSecondHand(canvas)
        drawHourHand(canvas)
        drawMinuteHand(canvas)
        drawCoverCircle(canvas)
        invalidate() // 重绘会再次调用onDraw方法
    }
    /**
     * 获取当前 时分秒 所对应的角度
     * 为了不让秒针走得像老式挂钟一样僵硬，需要精确到毫秒
     */
    private fun getTimeDegree() {
        val calendar: Calendar = Calendar.getInstance()
        val milliSecond: Float = calendar.get(Calendar.MILLISECOND).toFloat()
        val second: Float = calendar.get(Calendar.SECOND) + milliSecond / 1000
        val minute: Float = calendar.get(Calendar.MINUTE) + second / 60
        val hour: Float = calendar.get(Calendar.HOUR) + minute / 60
        mSecondDegree = second / 60 * 360
        mMinuteDegree = minute / 60 * 360
        mHourDegree = hour / 12 * 360
    }

    // 绘制12 3 6 9
    private fun drawTimeText(canvas: Canvas) {
        var timeText = "12"
        // 获取文本的边界放入mTextRect
        mTextPaint.getTextBounds(timeText, 0, timeText.length, mTextRect)
        val largeTextWidth = mTextRect.width() //两位数的宽度
        canvas.drawText(
            timeText,
            (width / 2 - largeTextWidth / 2).toFloat(),
            mActualTop + mTextRect.height(),
            mTextPaint
        )
        // 绘制3 放在右边
        timeText = "3"
        mTextPaint.getTextBounds(timeText, 0, timeText.length, mTextRect)
        val smallTextWidth = mTextRect.width() //一位数的宽度
        canvas.drawText(
            timeText,
            mActualRight - smallTextWidth,
            (height / 2 + mTextRect.height() / 2).toFloat(),
            mTextPaint
        )
        // 绘制6 放在下边
        timeText = "6"
        mTextPaint.getTextBounds(timeText, 0, timeText.length, mTextRect)
        canvas.drawText(
            timeText,
            (width / 2 - smallTextWidth / 2).toFloat(),
            mActualBottom,
            mTextPaint
        )
        // 绘制9 放在左边
        timeText = "9"
        mTextPaint.getTextBounds(timeText, 0, timeText.length, mTextRect)
        canvas.drawText(
            timeText,
            mActualLeft + mTextRect.height() / 2 - smallTextWidth / 2,
            (height / 2 + mTextRect.height() / 2).toFloat(),
            mTextPaint
        )
        // 绘制连接四个数字的弧线
        mCircleRectF.set(
            mActualLeft + mTextRect.height() / 2 + mCircleStrokeWidth / 2,
            mActualTop + mTextRect.height() / 2 + mCircleStrokeWidth / 2,
            mActualRight - mTextRect.height() / 2 + mCircleStrokeWidth / 2,
            mActualBottom - mTextRect.height() / 2 + mCircleStrokeWidth / 2
        )
        for (i in 0..4) {
            canvas.drawArc(mCircleRectF, 5 + 90f * i, 80f, false, mCirclePaint)
        }
    }

    private fun Int.sp2px(): Int {
        return (this * resources.displayMetrics.scaledDensity + 0.5f).toInt()
    }

    // 绘制刻度线
    private fun drawScaleLine(canvas: Canvas) {
        mScaleArcRectF.set(
            mActualLeft + 1.5f * mScaleLength + mTextRect.height() / 2,
            mActualTop + 1.5f * mScaleLength + mTextRect.height() / 2,
            mActualRight - 1.5f * mScaleLength - mTextRect.height() / 2,
            mActualBottom - 1.5f * mScaleLength - mTextRect.height() / 2
        )
        // 绘制刻度圈
        mGradientMatrix.setRotate(mSecondDegree - 90f, width / 2f, height / 2f)
        mSweepGradient.setLocalMatrix(mGradientMatrix)
        mScaleArcPaint.shader = mSweepGradient
        canvas.drawArc(mScaleArcRectF, 0f, 360f, false, mScaleArcPaint)
        // 绘制刻度线
        canvas.save() //保存画布==>保存当前画布的状态
        for (i in 0..199) {
            canvas.drawLine(
                width / 2f,
                mActualTop + mTextRect.height() / 2 + mScaleLength,
                width / 2f,
                mActualTop + mTextRect.height() / 2 + 2 * mScaleLength,
                mScaleLinePaint
            )
            canvas.rotate(1.8f, width / 2f, height / 2f)
        }
        canvas.restore() //恢复画布==>恢复到上一次保存的状态
    }

    // 绘制秒针
    private fun drawSecondHand(canvas: Canvas) {
        canvas.save()
        canvas.rotate(mSecondDegree, width / 2f, height / 2f)
        mSecondHandPath.reset()
        val offset = mActualTop + mTextRect.height() / 2
        mSecondHandPath.moveTo(width / 2f, offset + 0.27f * mRadius)
        mSecondHandPath.lineTo(width / 2f - 0.05f * mRadius, offset + 0.35f * mRadius)
        mSecondHandPath.lineTo(width / 2f + 0.05f * mRadius, offset + 0.35f * mRadius)
        mSecondHandPath.close()
        mSecondHandPaint.color = Color.parseColor("#ffffff")
        canvas.drawPath(mSecondHandPath, mSecondHandPaint)
        canvas.restore()
    }


    private fun drawHourHand(canvas: Canvas) {
        canvas.save()
        canvas.rotate(mHourDegree, width / 2f, height / 2f)
        mHourHandPath.reset()
        val offset = mActualTop + mTextRect.height() / 2
        mHourHandPath.moveTo(width / 2f - 0.02f * mRadius, height / 2f)
        mHourHandPath.lineTo(width / 2f - 0.01f * mRadius, offset + 0.5f * mRadius)
        mHourHandPath.quadTo(
            width / 2f,
            offset + 0.48f * mRadius,
            width / 2f + 0.01f * mRadius,
            offset + 0.5f * mRadius
        )
        mHourHandPath.lineTo(width / 2f + 0.02f * mRadius, height / 2f)
        mHourHandPath.close()
        canvas.drawPath(mHourHandPath, mHourHandPaint)
        canvas.restore()
    }

    private fun drawMinuteHand(canvas: Canvas){
        canvas.save()
        canvas.rotate(mMinuteDegree, width / 2f, height / 2f)
        mMinuteHandPath.reset()
        val offset = mActualTop + mTextRect.height() / 2
        mMinuteHandPath.moveTo(width / 2f - 0.01f * mRadius, height / 2f)
        mMinuteHandPath.lineTo(width / 2f - 0.008f * mRadius, offset + 0.38f * mRadius)
        mMinuteHandPath.quadTo(
            width / 2f,
            offset + 0.36f * mRadius,
            width / 2f + 0.008f * mRadius,
            offset + 0.38f * mRadius
        )
        mMinuteHandPath.lineTo(width / 2f + 0.01f * mRadius, height / 2f)
        mMinuteHandPath.close()
        canvas.drawPath(mMinuteHandPath, mMinuteHandPaint)
        canvas.restore()
    }

    private fun drawCoverCircle(canvas: Canvas) {
        canvas.save()
        canvas.drawCircle(width / 2f, height / 2f, mRadius / 20f, mSecondHandPaint)
        mSecondHandPaint.color = Color.parseColor("#237EAD")
        canvas.drawCircle(width / 2f, height / 2f, mRadius / 40f, mSecondHandPaint)
        canvas.restore()
    }
}