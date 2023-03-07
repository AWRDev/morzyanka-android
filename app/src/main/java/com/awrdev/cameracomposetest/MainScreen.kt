package com.awrdev.cameracomposetest

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

@Composable
fun MainScreen(context: Context,
               state: MutableState<Bitmap>,
               offset: MutableState<Offset>,
               lumen: MutableState<Float>,
               viewModel: MainViewModel){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
        Box(){
            CameraPreview(
                modifier = Modifier
                    .padding(top = convertPixelsToDp(context, 25f).dp)
                    .pointerInput(this) {
                        detectTapGestures(onTap = {
                            //offset.value = it
                            viewModel.updateInputSources(it)
                            //println(viewModel.state.value.inputSources)
                            viewModel.resetLuminosityThreshold()
                        })
                    },
                state = state,
                offset = viewModel.state.value.inputSources ,
                lumen = lumen,
                viewModel = viewModel)
            for (inputSource in viewModel.state.value.inputSources){
                Canvas(modifier = Modifier, onDraw = {
                            drawRect(Color.Green, Offset(inputSource.x, inputSource.y+25f), Size(50f,50f), 1f, Stroke(5f))
                        })
            }
        }
        Row() {
                        Image(
                            bitmap = state.value.asImageBitmap(),
                            contentDescription = "Image",
                            modifier = Modifier
                                .border(1.dp, Color.Red)
                                .rotate(90f),

                        )
                    }
//        Box(
//            modifier = Modifier
//                .padding(top = 50.dp)
//                .size(
//                    convertPixelsToDp(context, 480f).dp,
//                    convertPixelsToDp(context, 640f).dp
//                )
//                .background(
//                    Color.Red
//                )
//        )
        Column(modifier = Modifier
            .background(Color.Green)
            .padding(bottom = 25.dp)) {
            DecodedOutputView(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(), viewModel.state.value.words)
            Button(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(), onClick = {viewModel.updateColor()}) {
                Text(text = "+")
            }
        }
    }
}