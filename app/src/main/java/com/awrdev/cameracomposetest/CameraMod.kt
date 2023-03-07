package com.awrdev.cameracomposetest

import android.graphics.Bitmap
import android.util.Log
import android.util.Size
import android.view.ScaleGestureDetector
import android.widget.LinearLayout
import androidx.camera.core.*
import androidx.camera.core.impl.PreviewConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.nio.ByteBuffer
import java.util.concurrent.Executors

@Composable
fun CameraMod(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = LinearLayout.LayoutParams(
                    960, 1280
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Preview is incorrectly scaled in Compose on some devices without this
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                // Preview
                val preview = Preview.Builder().setTargetResolution(Size(960, 1280))
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val cameraExecutor = Executors.newSingleThreadExecutor()

                val imageAnalyzer = ImageAnalysis.Builder().setTargetResolution(Size(960, 1280))
                    .build()
                    .also {
                        it.setAnalyzer(
                            cameraExecutor, Analyzer()
                        )
                    }


                lateinit var camera: Camera
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()

                    camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e("CameraView", "Use case binding failed", exc)
                }



                val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        val scale = camera.cameraInfo.zoomState.value?.zoomRatio?.times(detector.scaleFactor)
                        if (scale != null) {
                            camera.cameraControl.setZoomRatio(scale)
                        }
                        return true
                    }
                }
                val scaleGestureDetector = ScaleGestureDetector(context, listener)

                previewView.setOnTouchListener { _, event ->
                    scaleGestureDetector.onTouchEvent(event)
                    return@setOnTouchListener true
                }


            }, ContextCompat.getMainExecutor(context))

            previewView
        })
}

private class Analyzer : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer

        println(image.height)
        println(image.width)

        val data = buffer.toByteArray()

        val pixels = data.map { it.toInt() and 0xFF }

        image.close()
    }
}