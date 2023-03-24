package com.awrdev.cameracomposetest.presentation.settings.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NumericValueDialog(
    title: String,
    currentValue: Int,
    onValueChange: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    var newValue by remember { mutableStateOf(currentValue) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = {
            OutlinedTextField(
                value = newValue.toString(),
                onValueChange = { newValue = it.toIntOrNull() ?: 0 },
                label = { Text("New value") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        },
        confirmButton = {
            TextButton(onClick = { onValueChange(newValue) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}