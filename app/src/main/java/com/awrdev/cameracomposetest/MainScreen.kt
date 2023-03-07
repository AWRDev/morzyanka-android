package com.awrdev.cameracomposetest

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.awrdev.cameracomposetest.common.Utils.convertPixelsToDp
import com.awrdev.cameracomposetest.ui.theme.Gray600

@Composable
fun MainScreen(context: Context,
               state: MutableState<Bitmap>,
               offset: MutableState<Offset>,
               lumen: MutableState<Float>,
               viewModel: MainViewModel){
    Column(
        modifier = Modifier.fillMaxSize().background(Gray600),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(){
            CameraPreview(
                modifier = Modifier
                    .padding(top = convertPixelsToDp(context, 25f).dp)
                    .pointerInput(this) {
                        detectTapGestures(onTap = {
                            //offset.value = it
                            viewModel.updateInputSources_Channel(0, it)
                            //println(viewModel.state.value.inputSources)
                            viewModel.resetLuminosityThreshold()
                        })
                    },
                state = state,
                offset = viewModel.state.value.inputSources ,
                lumen = lumen,
                viewModel = viewModel)
            for (channel in viewModel.state.value.channels){
                Canvas(modifier = Modifier, onDraw = {
                            drawRect(channel.borderColor, Offset(channel.inputSource.x, channel.inputSource.y+25f), Size(50f,50f), 1f, Stroke(5f))
                        })
            }
        }
        Column(modifier = Modifier
            //.background(Color.Green)
            .padding(bottom = 25.dp)) {
//            Text(text = viewModel.state.value.log_text, modifier = Modifier.height(200.dp).verticalScroll(
//                        rememberScrollState(0 )))
            for (channel in viewModel.state.value.channels){
                DecodedOutputView(modifier = Modifier
                    .padding(5.dp),
                    channel.message,
                    channel.words,
                    channel.inputImage,
                    channel.borderColor)
             //   println(channel.toString())
            }
            Button(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(), onClick = {viewModel.updateColor_Channel(0)}) {
                Text(text = "+")
            }
            Button(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(), onClick = {viewModel.isLogging = !viewModel.isLogging}) {
                Text(text = "Logging")
            }
        }
    }
}