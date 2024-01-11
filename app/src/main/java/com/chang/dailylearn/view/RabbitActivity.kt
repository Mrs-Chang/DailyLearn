package com.chang.dailylearn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.chang.dailylearn.R

class RabbitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rabbit)
        supportActionBar?.hide()
        immerse()
        val rabbitView = findViewById<ImageView>(R.id.iv_fish)
        rabbitView.setImageDrawable(RabbitDrawable())
    }
}