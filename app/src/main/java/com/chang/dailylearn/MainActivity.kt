package com.chang.dailylearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.chang.dailylearn.databinding.ActivityMainBinding
import com.chang.dailylearn.dm.dialog.AbsDialog
import com.chang.dailylearn.dm.dialog.AdDialog
import com.chang.dailylearn.dm.dialog.PolicyDialog
import com.chang.dailylearn.dm.dialog.PraiseDialog
import com.chang.dailylearn.view.immerse
import com.chang.snow.SnowView

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        immerse(statusBarDarkMode = true)
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

        val snowView = SnowView.Builder(this)
            .setSnowflakeImageResId(R.drawable.icon_small_snowflake)
            .setSnowflakeCount(100)
            .setSpeedRange(10, 20)
            .setSizeRangeInPx(40, 60)
            .setAlphaRange(150, 255)
            .setAngleMax(10)
            .build()

        mBinding.clRoot.addView(
            snowView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }
}