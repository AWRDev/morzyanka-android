package com.awrdev.cameracomposetest.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SettingsRow(title: String, description: String = "", value: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Navigate to settings",
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 5.dp)
        )
        Column(modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.onSurface
            )
            if (description.isNotEmpty()){
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = value.toString(),
                fontSize = 14.sp,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}
