package com.awrdev.cameracomposetest

import android.graphics.Bitmap
import android.util.Log
import android.widget.LinearLayout
import androidx.camera.core.*
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
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FIT_CENTER,
    viewModel: MainViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = LinearLayout.LayoutParams(
                    480, 640
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
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val cameraExecutor = Executors.newSingleThreadExecutor()
                val imageAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(
                            cameraExecutor, LuminosityAnalyzer(
                                viewModel = viewModel,
                                listener = { channelIndex, luma ->
                                    //Log.d("LuminosityTAG", "Average luminosity: $luma")
                                    viewModel.updateLuminosuty(luma)
                                    //println(offset.value.size)
//                                    println(offset[0].toString())
                                    val luma_threshold =
                                        ((viewModel.state.value.maxLumen - viewModel.state.value.minLumen) / 10)
                                    if (luma.toInt() in IntRange((viewModel.state.value.maxLumen - luma_threshold).toInt(),
                                            viewModel.state.value.maxLumen.toInt()
                                        )){
                                        viewModel.receiveSignal_Channel(channelIndex,"HIGH")
                                    }
                                    if (luma.toInt() in IntRange(viewModel.state.value.minLumen.toInt(),
                                            (viewModel.state.value.minLumen+luma_threshold).toInt()
                                        )){
                                        viewModel.receiveSignal_Channel(channelIndex, "LOW")
                                    }
                                })
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


            }, ContextCompat.getMainExecutor(context))

            previewView
        })
}

private class LuminosityAnalyzer(
    private val listener: LumaListener,
    val viewModel: MainViewModel,
) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()

        val pixels = data.map { it.toInt() and 0xFF }
        //Get part of the image
        val chW = 50
        val chH = 50
        for (index in viewModel.state.value.channels.indices){
            val offset = viewModel.state.value.channels[index].inputSource
            val chunk = getChunk(pixels, offset, chH, chW)
            val newBitmap = Bitmap.createBitmap(chW, chH, Bitmap.Config.ARGB_8888)
            newBitmap.setPixels(chunk.map { android.graphics.Color.rgb(it, it, it) }.toIntArray(), 0, chW, 0,0, chW, chH )
            viewModel.updateInputImage_Channel(index, Bitmap.createScaledBitmap(newBitmap, chW, chH, false))
            val luma = chunk.average()
            listener(index, luma)
        }

        image.close()
    }

    private fun getChunk(pixels: List<Int>, offset: Offset, height: Int, width: Int): List<Int> {
        //println(offset.toString())
        val imHeight = 640
        val imWidth = 480
        val result = mutableListOf<Int>()
        for (i in height downTo 0){
                val x = offset.x.toInt() + i
            for (j in 0 until width){
                val y = offset.y.toInt() + j
                try{
                    result.add(pixels[imHeight*(imWidth-x)+y])
                }
                catch (e: Exception){
                    result.add(0)
                }
            }
        }
        return result
    }
}