package com.chang.dailylearn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chang.dailylearn.databinding.ActivityMatrixBinding

class MatrixActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMatrixBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMatrixBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.ivMatrix.setOnClickListener {
            mBinding.ivMatrix.doSomeThingAnimation2()
        }
    }
}