package com.awrdev.cameracomposetest

import android.graphics.Bitmap
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awrdev.cameracomposetest.ui.theme.Gray800

@Composable
fun DecodedOutputView(
    modifier: Modifier = Modifier,
    morseMessage: List<String>,
    decodedMessage: List<String>,
    inputImage: Bitmap,
    borderColor: Color
) {
    Card(modifier = modifier
        .clip(RoundedCornerShape(15.dp))
        .fillMaxWidth()
        .border(3.dp, Color.Blue, RoundedCornerShape(15.dp)),
        backgroundColor = Gray800,
        elevation = 5.dp
    ) {

        Column() {
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(modifier = Modifier.padding(5.dp).fillMaxWidth().weight(1f), text = morseMessage.joinToString())
                Image(
                    bitmap = inputImage.asImageBitmap(),
                    contentDescription = "Image",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .border(1.dp, borderColor)
                        .rotate(90f)
                    )
            }
            Divider()
            Text(modifier = Modifier.padding(5.dp), text = decodedMessage.joinToString(""),
            style = TextStyle(color = Color.White)
            )
        }
    }
}
