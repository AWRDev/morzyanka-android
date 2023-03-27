package com.awrdev.cameracomposetest.presentation.settings.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.awrdev.cameracomposetest.common.dataStore
import kotlinx.coroutines.launch


@Composable
fun ThemeDialog(onDismissRequest: () -> Unit) {
    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val (selectedOption, setSelectedOption) = remember { mutableStateOf(
        if (isSystemInDarkTheme) ThemeOption.SYSTEM else ThemeOption.LIGHT
    ) }
    val scope = rememberCoroutineScope()
    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colors.background)
            ) {
                Text("Select Theme")
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    RadioButton(
                        selected = selectedOption == ThemeOption.LIGHT,
                        onClick = { setSelectedOption(ThemeOption.LIGHT) }
                    )
                    Text("Light")
                }
                Row {
                    RadioButton(
                        selected = selectedOption == ThemeOption.DARK,
                        onClick = { setSelectedOption(ThemeOption.DARK) }
                    )
                    Text("Dark")
                }
                Row {
                    RadioButton(
                        selected = selectedOption == ThemeOption.SYSTEM,
                        onClick = { setSelectedOption(ThemeOption.SYSTEM) }
                    )
                    Text("System")
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = {
                    when (selectedOption) {
                        ThemeOption.LIGHT ->
                        scope.launch{
                            context.dataStore.updateData {
                                it.copy(appThemeIsLight = true)
                            }
                        }
                        ThemeOption.DARK ->scope.launch{
                            context.dataStore.updateData {
                                it.copy(appThemeIsLight = false)
                            }
                        }
                        ThemeOption.SYSTEM -> scope.launch{
                            context.dataStore.updateData {
                                it.copy(appThemeIsLight = !isSystemInDarkTheme)
                            }
                        }
                    }
                    onDismissRequest()
                }) {
                    Text("Apply")
                }
            }
        }
    )
}

enum class ThemeOption {
    LIGHT,
    DARK,
    SYSTEM
}
