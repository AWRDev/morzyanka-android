package com.awrdev.cameracomposetest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DecodedOutputView(modifier: Modifier = Modifier, text: List<String>){
    Row(modifier = modifier
        .fillMaxWidth()
        .border(3.dp, Color.Blue, RoundedCornerShape(3.dp))) {
            Text(modifier = Modifier.padding(5.dp), text = text.joinToString())
    }
}
