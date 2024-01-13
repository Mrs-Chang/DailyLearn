package com.chang.snow

import android.graphics.Bitmap

data class SnowflakeParams(
    var canvasWidth: Int = 0, // 画布的宽度
    var canvasHeight: Int = 0, // 画布的高度
    val sizeMinInPx: Int = 40, // 雪花的最小大小
    val sizeMaxInPx: Int = 60, // 雪花的最大大小
    val speedMin: Int = 10,  // 雪花的最小速度
    val speedMax: Int = 20, // 雪花的最大速度
    val alphaMin: Int = 150, // 雪花的最小透明度
    val alphaMax: Int = 255, // 雪花的最大透明度
    val angleMax: Int = 10, // 雪花的最大角度
    val snowflakeImage: Bitmap? = null, // 雪花的图片
)
