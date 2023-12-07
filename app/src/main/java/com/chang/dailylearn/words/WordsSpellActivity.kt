package com.chang.dailylearn.words

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.chang.dailylearn.databinding.ActivityClockBinding
import com.chang.dailylearn.view.immerse

class WordsSpellActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityClockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immerse()
        supportActionBar?.hide()
        mBinding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.btnCheck.setOnClickListener {
            val actual = mBinding.etInput.text.toString().trim()
            mBinding.tvWordOrSense.text = "handsome"
            val editSteps = minEditDistance("handsome", actual)
            mBinding.etInput.text = renderMatchResult(editSteps, actual)
        }
        val result = minEditDistance("handsome", "hoodsomee")
        result?.forEach {
            Log.d("MinEditDistance", "Action: ${it.action}")
        }
    }

    private fun renderMatchResult(editSteps: List<EditStep>?, actual: String): Editable {
        val spannable = SpannableBuilder()
        var cursor = 0
        editSteps?.forEach {
            when (it.action) {
                EditAction.NOOP -> {
                    spannable.nextSpannable(actual[cursor++].toString()).setColor(Color.GREEN)
                }

                EditAction.REPLACE -> {
                    spannable.nextSpannable(actual[cursor++].toString()).setColor(Color.RED)
                }

                EditAction.INSERT -> {
                    spannable.nextSpannable("_").setColor(Color.RED)
                }

                EditAction.DELETE -> {
                    spannable.nextSpannable(actual[cursor++].toString()).setColor(Color.GRAY)
                }

                else -> {}
            }
        }
        return spannable.finish()
    }


}