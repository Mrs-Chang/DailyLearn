package com.chang.dailylearn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chang.dailylearn.databinding.ActivityClockBinding

class ClockActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityClockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隐藏toolbar
        supportActionBar?.hide()
        immerse()
        mBinding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}