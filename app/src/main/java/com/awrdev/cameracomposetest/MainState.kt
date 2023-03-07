package com.awrdev.cameracomposetest

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class MainState(
    val selectedAreaImg: List<Int> = emptyList(),
    val currentOffset: Offset = Offset(0f, 0f),
    val currentLuminosity: Float = 0f,
    val currentSignal: String = "",
    val lastSignal: String = "",
    val lastSignalTime: Long = 0,
    val message: List<String> = emptyList(),
    val words: List<String> = emptyList(),

    //val bitmapWindow: Bitmap = Bitmap.createBitmap(0,0,Bitmap.Config.ARGB_8888),
    val log_text: String = "",
    val maxLumen: Double = 0.0,
    val minLumen: Double = 250.0,

    val inputSources: List<Offset> = listOf(Offset(0f,0f)),
    val inputImages: List<Bitmap> = listOf(Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)),
    val channels: List<Channel> = listOf(Channel())
)
