package com.chang.dailylearn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chang.dailylearn.R

class FlipClockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        immerse()
        setContentView(R.layout.activity_flip_clock)
    }
}