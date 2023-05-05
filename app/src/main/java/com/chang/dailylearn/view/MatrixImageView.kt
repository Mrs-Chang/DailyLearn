package com.chang.dailylearn.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.animation.MatrixEvaluator

class MatrixImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    var transY = 0f
        set(value) {
            field = value
            invalidate()
        }

    fun doSomeThingAnimation() {
        this.let { iv ->
            val imgWidth = iv.width
            val imgHeight = iv.height
            iv.layoutParams.width = imgWidth / 2
            iv.layoutParams.height = imgHeight / 2
            val tX = imgWidth.toFloat() / 2
            val ty = imgHeight.toFloat() / 2
            val initMatrix = iv.imageMatrix

            // 获取6个Matrix
            val oneMatrix = Matrix(initMatrix)
            val twoMatrix = Matrix(initMatrix).apply {
                postTranslate(-tX, 0f)
            }
            val threeMatrix = Matrix(initMatrix).apply {
                postTranslate(-tX, -ty)
            }
            val fourthMatrix = Matrix(initMatrix).apply {
                postTranslate(0f, -ty)
            }
            val fiveMatrix = Matrix(initMatrix).apply {
                postTranslate(-tX / 2, -ty / 2)
            }
            val sixMatrix = Matrix(initMatrix)

            iv.scaleType = ScaleType.MATRIX
            iv.imageMatrix = oneMatrix

            // 构建6段动画
            val evaluator = MatrixEvaluator()
            val firstAnimator = ValueAnimator.ofFloat(0f, 1f)
            firstAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, oneMatrix, twoMatrix)
                iv.translationX = it.animatedFraction * imgWidth / 2
                iv.imageMatrix = matrix
            }

            val secondAnimator = ValueAnimator.ofFloat(0f, 1f)
            secondAnimator.addUpdateListener {
                val fraction = it.animatedFraction
                iv.translationY = fraction * imgHeight / 2
                val matrix = evaluator.evaluate(fraction, twoMatrix, threeMatrix)
                iv.imageMatrix = matrix
            }

            val threeAnimator = ValueAnimator.ofFloat(0f, 1f)
            threeAnimator.addUpdateListener {
                val fraction = it.animatedFraction
                iv.translationX = ((1 - fraction) * imgWidth / 2)
                val matrix = evaluator.evaluate((fraction), threeMatrix, fourthMatrix)
                iv.imageMatrix = matrix
            }
            val fourAnimator = ValueAnimator.ofFloat(0f, 1f)
            fourAnimator.addUpdateListener {
                val fraction = it.animatedFraction
                iv.translationY = ((1 - fraction) * imgHeight / 2)
                val matrix = evaluator.evaluate((fraction), fourthMatrix, oneMatrix)
                iv.imageMatrix = matrix
            }
            val fiveAnimator = ValueAnimator.ofFloat(0f, 1f)
            fiveAnimator.addUpdateListener {
                val fraction = it.animatedFraction
                iv.translationY = (fraction * imgHeight / 4)
                iv.translationX = (fraction * imgWidth / 4)
                val matrix = evaluator.evaluate((fraction), oneMatrix, fiveMatrix)
                iv.imageMatrix = matrix
            }
            val sixAnimator = ValueAnimator.ofFloat(0f, 1f)
            sixAnimator.addUpdateListener {
                val fraction = it.animatedFraction
                iv.layoutParams.width = (imgWidth / 2 + imgWidth / 2 * fraction).toInt()
                iv.layoutParams.height = (imgHeight / 2 + imgHeight / 2 * fraction).toInt()
                iv.translationY = ((1 - fraction) * imgHeight / 4)
                iv.translationX = ((1 - fraction) * imgWidth / 4)
                val matrix = evaluator.evaluate((fraction), fiveMatrix, sixMatrix)
                iv.imageMatrix = matrix
                iv.requestLayout() // 宽高发生变化，需要重新测量
            }
            val set = AnimatorSet()
            set.playSequentially(
                firstAnimator,
                secondAnimator,
                threeAnimator,
                fourAnimator,
                fiveAnimator,
                sixAnimator
            )
            set.duration = 1000
            set.start()
        }
    }

    fun doSomeThingAnimation2() {
        this.let { iv ->
            val imgWidth = iv.width
            val imgHeight = iv.height
            iv.layoutParams.width = imgWidth / 2
            iv.layoutParams.height = imgHeight / 2
            val tX = imgWidth.toFloat() / 2
            val ty = imgHeight.toFloat() / 2
            // 左上->右下->左下->右上->左上->中心->放大恢复原样
            val initMatrix = iv.imageMatrix
            val oneMatrix = Matrix(initMatrix)
            val twoMatrix = Matrix(initMatrix).apply {
                postTranslate(-tX, -ty)
            }
            val threeMatrix = Matrix(initMatrix).apply {
                postTranslate(0f, -ty)
            }
            val fourMatrix = Matrix(initMatrix).apply {
                postTranslate(-tX, 0f)
            }
            val fiveMatrix = Matrix(initMatrix)
            val sixMatrix = Matrix(initMatrix).apply {
                postTranslate(-tX / 2, -ty / 2)
            }
            val sevenMatrix = Matrix(initMatrix)
            iv.scaleType = ScaleType.MATRIX
            iv.imageMatrix = oneMatrix

            val evaluator = MatrixEvaluator()
            val animSet = AnimatorSet()
            val firstAnimator = ValueAnimator.ofFloat(0f, 1f)
            firstAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, oneMatrix, twoMatrix)
                iv.translationX = it.animatedFraction * tX
                iv.translationY = it.animatedFraction * ty
                iv.imageMatrix = matrix
            }
            val secondAnimator = ValueAnimator.ofFloat(0f, 1f)
            secondAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, twoMatrix, threeMatrix)
                iv.translationX = (1 - it.animatedFraction) * tX
                iv.imageMatrix = matrix
            }
            val threeAnimator = ValueAnimator.ofFloat(0f, 1f)
            threeAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, threeMatrix, fourMatrix)
                iv.translationX = it.animatedFraction * tX
                iv.translationY = (1 - it.animatedFraction) * ty
                iv.imageMatrix = matrix
            }
            val fourAnimator = ValueAnimator.ofFloat(0f, 1f)
            fourAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, fourMatrix, fiveMatrix)
                iv.translationX = (1 - it.animatedFraction) * tX
                iv.imageMatrix = matrix
            }
            val fiveAnimator = ValueAnimator.ofFloat(0f, 1f)
            fiveAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, fiveMatrix, sixMatrix)
                iv.translationX = (it.animatedFraction) * tX / 2
                iv.translationY = (it.animatedFraction) * ty / 2
                iv.imageMatrix = matrix
            }
            val sixAnimator = ValueAnimator.ofFloat(0f, 1f)
            sixAnimator.addUpdateListener {
                val matrix = evaluator.evaluate(it.animatedFraction, sixMatrix, sevenMatrix)
                iv.translationX = (1 - it.animatedFraction) * tX / 2
                iv.translationY = (1 - it.animatedFraction) * ty / 2
                iv.imageMatrix = matrix
                iv.layoutParams.width = (imgWidth / 2 + imgWidth / 2 * it.animatedFraction).toInt()
                iv.layoutParams.height =
                    (imgHeight / 2 + imgHeight / 2 * it.animatedFraction).toInt()
                iv.requestLayout() // 宽高发生变化，需要重新测量
            }
            animSet.playSequentially(
                firstAnimator,
                secondAnimator,
                threeAnimator,
                fourAnimator,
                fiveAnimator,
                sixAnimator
            )
            animSet.duration = 1000
            animSet.start()
        }
    }


    fun doSomeThingAnimation3() {
        /**
         * setTranslate:设置矩阵
         * preTranslate：左乘
         * postTranslate：右乘
         * */

        val initMatrix = this.imageMatrix
        val twoMatrix = Matrix(initMatrix).apply {
            this.setTranslate(100f, 0f)
            this.preTranslate(100f, 0f)
            this.postTranslate(100f, 0f)
            this.preTranslate(-50f, 0f)
            this.preTranslate(10f, 0f)
            this.postTranslate(20f, 0f)
        }

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}