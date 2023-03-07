package com.awrdev.cameracomposetest.common

import android.content.Context

object Utils {
     fun convertPixelsToDp(context: Context, pixels: Float): Float {
        val screenPixelDensity = context.resources.displayMetrics.density
        val dpValue = pixels / screenPixelDensity
        return dpValue
    }
    fun convertDpToPixels(context: Context, dpValue: Float): Float {
        val screenPixelDensity = context.resources.displayMetrics.density
        val pixels = dpValue * screenPixelDensity
        return pixels
    }
}