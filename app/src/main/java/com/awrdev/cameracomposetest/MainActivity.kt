package com.awrdev.cameracomposetest

import android.Manifest
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import com.awrdev.cameracomposetest.ui.theme.CameraComposeTestTheme

typealias LumaListener = (channelIndex: Int, luma: Double) -> Unit

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraComposeTestTheme {
                var viewModel = ViewModelProvider(this)[MainViewModel::class.java]
                val state = remember {
                    mutableStateOf(Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888))
                }
                val luminosity = remember {
                    mutableStateOf(0f)
                }
                val offset = remember { mutableStateOf(Offset(0f, 0f))}
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Top,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = offset.value.toString())
//                    Text(text = luminosity.value.toString())
//                    Text(text = viewModel.state.value.message.joinToString(separator = " "))
//                    Box()
//                    {
//                        CameraPreview(
//                            modifier = Modifier
//                                .padding(top = Utils.convertPixelsToDp(this@MainActivity, 25f).dp)
//                                .pointerInput(this) {
//                                    detectTapGestures(onTap = {
//                                        offset.value = it
//                                        viewModel.resetLuminosityThreshold()
//                                    })
//                                },
//                            state = state,
//                            offset = offset,
//                            lumen = luminosity,
//                            viewModel = viewModel
//                        )
//                        //CameraMod( )
//                        Canvas(modifier = Modifier, onDraw = {
//                            drawRect(Color.Green, Offset(offset.value.x, offset.value.y+25f), Size(50f,50f), 1f, Stroke(5f))
//                        })
//                    }
//                    Row() {
//                        Image(
//                            bitmap = state.value.asImageBitmap(),
//                            contentDescription = "Image",
//                            modifier = Modifier
//                                .border(1.dp, Color.Red)
//                                .rotate(90f),
//
//                        )
//                    }
//                    //Offset control
//                    Row(horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically){
//                        Button(onClick = {offset.value = Offset(offset.value.x - 5, offset.value.y)}){
//                            Text(text = "Left")
//                        }
//                        Column() {
//                            Button(onClick = {offset.value = Offset(offset.value.x, offset.value.y-5)}){
//                                Text(text = "Up")
//                            }
//                            Button(onClick = {offset.value = Offset(offset.value.x, offset.value.y+5)}){
//                                Text(text = "Dowm")
//                            }
//                        }
//                        Button(onClick = {offset.value = Offset(offset.value.x + 5, offset.value.y)}){
//                            Text(text = "Right")
//                        }
//                    }
//                    //Clear button
//                    Button(onClick = {viewModel.clearMessage()}){
//                        Text(text = "Clear")
//                    }
//                    //"Useful" info
//                    Text(text = viewModel.state.value.currentSignal)
//                    Text(text = viewModel.state.value.words.toString())
//                    Text(text = ((viewModel.state.value.maxLumen + viewModel.state.value.minLumen)/2).toString())
//                    Text(text = viewModel.state.value.log_text, modifier = Modifier.verticalScroll(
//                        rememberScrollState(0 )))
//                    //
//                }
                DecoderScreen(this, state = state,
                            offset = offset,
                            lumen = luminosity,
                            viewModel = viewModel)
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


