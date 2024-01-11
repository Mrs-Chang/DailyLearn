package com.chang.dailylearn.view

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

fun Activity.immerse(
    @WindowInsetsCompat.Type.InsetsType type: Int = WindowInsetsCompat.Type.systemBars(),
    statusBarDarkMode: Boolean = false,
    navigationBarDarkMode: Boolean = false,
    @ColorInt systemBarsColor: Int = Color.TRANSPARENT,
) {
    when(type) {
        WindowInsetsCompat.Type.systemBars() -> { //沉浸入导航栏和状态栏
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = systemBarsColor
            window.navigationBarColor = systemBarsColor
            WindowCompat.getInsetsController(window, window.decorView)?.apply {
                //false表示状态栏字体颜色是白色，true表示黑色
                isAppearanceLightStatusBars = !statusBarDarkMode
                isAppearanceLightNavigationBars = !navigationBarDarkMode
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = Color.TRANSPARENT
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            window.decorView.apply {
                post { setPadding(0, 0, 0, 0) }
            }
        }
        WindowInsetsCompat.Type.statusBars() -> {//只沉浸入状态栏
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = systemBarsColor
            WindowCompat.getInsetsController(window, window.decorView)?.apply {
                //false表示状态栏字体颜色是白色，true表示黑色
                isAppearanceLightStatusBars = !statusBarDarkMode
            }
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
                v.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom)
                insets
            }
        }
        WindowInsetsCompat.Type.navigationBars() -> { //只沉浸入导航栏
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.navigationBarColor = systemBarsColor
            WindowCompat.getInsetsController(window, window.decorView)?.apply {
                //false表示状态栏字体颜色是白色，true表示黑色
                isAppearanceLightNavigationBars = !navigationBarDarkMode
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = Color.TRANSPARENT
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
                v.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
                insets
            }
        }
        else -> {
            //do nothing
        }
    }
}

fun Activity.fullScreen() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    ViewCompat.getWindowInsetsController(window.decorView)?.let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Activity.exitFullScreen() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    ViewCompat.getWindowInsetsController(window.decorView)?.show(WindowInsetsCompat.Type.systemBars())
}