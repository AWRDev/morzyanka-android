package com.awrdev.cameracomposetest

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import com.awrdev.cameracomposetest.common.dataStore
import com.awrdev.cameracomposetest.presentation.decoder.MainViewModel
import com.awrdev.cameracomposetest.ui.theme.CameraComposeTestTheme

typealias LumaListener = (channelIndex: Int, luma: Double) -> Unit

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appSettings = dataStore.data.collectAsState(initial = AppSettings()).value
            CameraComposeTestTheme(darkTheme = !appSettings.appThemeIsLight) {
                val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

                //DecoderScreen(this,
                 //           viewModel = viewModel)
                MainScreen(viewModel = viewModel)

            }
        }
    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}

@Composable
fun viewButton(modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier.size(150.dp),
        factory = { context ->
            Button(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = "Click"
            }
        })
}


