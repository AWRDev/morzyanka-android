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

class MorseUnit(private val unitLength: Int = 250){
    val errorRate = unitLength / 2
    val dotLength = IntRange(unitLength - errorRate, unitLength + errorRate)
    val dashLength = IntRange(3*unitLength - errorRate, 3*unitLength + errorRate)
    val letterBrakeLength = IntRange((1.5*unitLength).toInt(), 4*unitLength)
    val spaceLength = IntRange(7*unitLength - errorRate, 8*unitLength + errorRate)
}