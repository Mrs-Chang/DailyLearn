package com.chang.dailylearn

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.chang.dailylearn.databinding.ActivityMainBinding
import com.chang.dailylearn.dm.dialog.AbsDialog
import com.chang.dailylearn.dm.dialog.AdDialog
import com.chang.dailylearn.dm.dialog.PolicyDialog
import com.chang.dailylearn.dm.dialog.PraiseDialog
import com.chang.dailylearn.view.AnimatorMaskTextView

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.tvHello.setMaskDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.bg_view_wave
            )!!
        )
        mBinding.tvHello.setOnClickListener {
            mBinding.tvHello.startAnim()
        }

        val dialogs = mutableListOf<AbsDialog>()
        dialogs.add(PolicyDialog(this))
        dialogs.add(PraiseDialog(this))
        dialogs.add(AdDialog(this))
        dialogs.sortBy { it.getPriority() }
        //创建链条
        for (i in 0 until dialogs.size - 1) {
            dialogs[i].setNextDialog(dialogs[i + 1])
        }
        dialogs[0].showDialog()

    }
}