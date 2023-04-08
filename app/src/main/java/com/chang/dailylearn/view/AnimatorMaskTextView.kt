package com.chang.dailylearn.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

/***
 *         mBinding.tvHello.setMaskDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.bg_view_wave
            )!!)
            mBinding.tvHello.setOnClickListener {
                mBinding.tvHello.startAnim()
            }
 *
 *
 */
class AnimatorMaskTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var shader: BitmapShader? = null
    private var shaderMatrix: Matrix = Matrix()
    private var offsetY = 0f

    var maskX = 0f
        set(value) {
            field = value
            invalidate()
        }

    var maskY = 0f
        set(value) {
            field = value
            invalidate()
        }

    fun setMaskDrawable(source: Drawable) {
        val maskW: Int = source.intrinsicWidth
        val maskH: Int = source.intrinsicHeight

        val bitmap = Bitmap.createBitmap(maskW, maskH, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        canvas.drawColor(currentTextColor)
        source.setBounds(0, 0, maskW, maskH)
        source.draw(canvas)

        shader = BitmapShader(
            bitmap, android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.CLAMP
        )
        paint.shader = shader
        offsetY = (height - maskH) / 2f
    }

    fun startAnim() {
        val animatorSet = AnimatorSet()
        Log.d("GaoChang", "startAnim: width:$width, height: $height")
        val animator1 = ObjectAnimator.ofFloat(this, "maskX", 0f, width.toFloat())
        val animator2 = ObjectAnimator.ofFloat(this, "maskY", height.toFloat(), 0f)
        animatorSet.playTogether(animator1, animator2)
        animatorSet.duration = 2000
        animatorSet.start()
    }

    override fun onDraw(canvas: android.graphics.Canvas?) {
        shaderMatrix.setTranslate(maskX, maskY + offsetY)
        shader?.setLocalMatrix(shaderMatrix)
        paint.shader = shader
        super.onDraw(canvas)
    }

}