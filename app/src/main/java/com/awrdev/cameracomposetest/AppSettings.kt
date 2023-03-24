package com.awrdev.cameracomposetest

@kotlinx.serialization.Serializable
data class AppSettings(
    val unitLengthMillis: Int = 250,
    val autoDetectUnitLength: Boolean = false
)

