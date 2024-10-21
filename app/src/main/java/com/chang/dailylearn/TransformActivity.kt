package com.chang.dailylearn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chang.dailylearn.databinding.ActivityTransformBinding
import com.chang.dailylearn.view.immerse

class TransformActivity : AppCompatActivity(){

    private lateinit var bingding: ActivityTransformBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        immerse(statusBarDarkMode = true)
        bingding = ActivityTransformBinding.inflate(layoutInflater)
        setContentView(bingding.root)
        bingding.nineSquareGridLayout.bindTargetView(bingding.targetView)
    }
}