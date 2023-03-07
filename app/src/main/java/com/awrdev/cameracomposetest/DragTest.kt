package com.awrdev.cameracomposetest

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun DragTest(context: Context){
    val  screendensity = context.resources.displayMetrics.density

    val firstPoint = remember {
        mutableStateOf(listOf(0.0f, 0.0f))
    }
    val state = remember {
        mutableStateOf(listOf(0.0f, 0.0f))
    }
    val isBox = remember {
        mutableStateOf(false)
    }
    val boxParams = remember {
        mutableStateOf(listOf(0.0f, 0.0f))
    }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier
            .fillMaxSize()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)){
                Text(text = "Drag started at: ${firstPoint.value[0]}")
                Text(text = "Cursor position: ${state.value[0]}")
                Text(text = "Offset: ${state.value[1]}")
            }
            Box(modifier = Modifier
                .background(Color.Green)
                .fillMaxSize()
                .weight(1f)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            var pointX = it.x / screendensity
                            var pointY = it.y / screendensity
                            firstPoint.value = listOf(pointX, pointY)
                        },
                        onDragEnd = {
                            var sizeX =
                                abs((state.value[0]/screendensity - firstPoint.value[0]))
                            var sizeY =
                                abs((state.value[1]/screendensity - firstPoint.value[1]))
                            isBox.value = true
                            boxParams.value = listOf(sizeY, sizeX)
                        }
                    ) { change, dragAmount ->
                        state.value =
                            listOf(change.position.x, change.position.y)
                        var sizeX =
                            abs((state.value[0]/screendensity - firstPoint.value[0]))
                        var sizeY =
                            abs((state.value[1]/screendensity - firstPoint.value[1]))
                        isBox.value = true
                        boxParams.value = listOf(sizeY, sizeX)
                        println("$change  $dragAmount")
                    }
                }
            ){
                if (isBox.value) {
                    Log.d("BOX", "${boxParams.value[0].dp} ${boxParams.value[1].dp}")
                    Box(
                        modifier = Modifier
                            .padding(start = firstPoint.value[0].dp, top = firstPoint.value[1].dp)
                            .background(Color.Red)
                            .height(boxParams.value[0].dp)
                            .width(boxParams.value[1].dp)
                        //.height(150.dp)
                        //.width(150.dp)
                    )
                }
            }
        }
    }
}