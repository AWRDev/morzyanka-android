package com.awrdev.cameracomposetest.common

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

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

    fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

}