package com.awrdev.cameracomposetest

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.awrdev.cameracomposetest.common.Utils.convertPixelsToDp
import com.awrdev.cameracomposetest.common.Utils.hasPermissions
import com.awrdev.cameracomposetest.presentation.decoder.MainViewModel
import com.awrdev.cameracomposetest.presentation.decoder.components.CameraPreview

@Composable
fun DecoderScreen(viewModel: MainViewModel){
    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            if (isGranted)
                viewModel.updatePermission(true)
        }
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(){
            if (hasPermissions(context)){
                viewModel.updatePermission(true)
                if (viewModel.state.value.permission){
                    CameraPreview(
                        modifier = Modifier
                            .padding(top = convertPixelsToDp(context, 25f).dp)
                            .clip(RoundedCornerShape(10.dp))
                            .pointerInput(this) {
                                detectTapGestures(onTap = {
                                    viewModel.updateInputSources_Channel(0, it)
                                    viewModel.resetLuminosityThreshold()
                                })
                            },
                        viewModel = viewModel)
                }
            }
            else {
                Toast.makeText(context, "Camera permission hasn't been granted", Toast.LENGTH_SHORT).show()
                SideEffect{
                    cameraPermissionResultLauncher.launch(
                        android.Manifest.permission.CAMERA
                    )
                }
            }
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
                .fillMaxWidth(),
                onClick = {
                    if (!viewModel.addNewChannel()){
                        Toast.makeText(context, "Max amount of channels reached!", Toast.LENGTH_LONG).show()
                    }}) {
                Text(text = "+")
            }
//            Button(modifier = Modifier
//                .padding(5.dp)
//                .fillMaxWidth(), onClick = {viewModel.isLogging = !viewModel.isLogging}) {
//                Text(text = "Logging")
//            }
        }
    }
}