package com.awrdev.cameracomposetest

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Channel(
    val inputSource: Offset = Offset(0f, 0f),
    val inputImage: Bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888),
    val borderColor: Color = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow).random(),

    val lastSignal: String = "",
    val lastSignalTime: Long = 0,

    val message: List<String> = emptyList(),
    val words: List<String> = emptyList()
    )
