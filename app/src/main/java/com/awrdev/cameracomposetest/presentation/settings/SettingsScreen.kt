package com.awrdev.cameracomposetest

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.awrdev.cameracomposetest.common.UNIT_LENGTH
import com.awrdev.cameracomposetest.presentation.settings.components.NumericValueDialog
import com.awrdev.cameracomposetest.presentation.settings.components.SettingsRow
import com.awrdev.cameracomposetest.presentation.settings.components.ThemeDialog

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    var value by remember { mutableStateOf(42) }
    val scrollState = rememberScrollState()

    Column(modifier = modifier.padding(bottom = 56.dp).verticalScroll(scrollState)) {
        SettingsRow(
            title = "Auto-detecting unit length",
            icon = Icons.Default.ArrowForward,
            description = "Decoder will analyze signals and calculate correct unit length",
            value = false.toString()
        ) {

        }
        SettingsRow(
            title = "Unit length",
            icon = ImageVector.vectorResource(id = R.drawable.baseline_unit_length_24),
            description = "Defines the length of one unit (DOT length)",
            value = value.toString()
        ) {
            showDialog = true
        }
        SettingsRow(
            title = "Current locale",
            icon = ImageVector.vectorResource(id = R.drawable.baseline_locale_24),
            value = "LOCALE_EN"
        ) {
        }
        Divider()
        SettingsRow(title = "App theme",icon = ImageVector.vectorResource(id = R.drawable.baseline_dark_mode_24), value = "THEME_DARK") {
            showThemeDialog = true
        }
        // Add more rows for other settings here
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.under_construction),
                    contentDescription = "Under construction",
                    modifier = Modifier.size(128.dp)
                )
                Text("Under construction")
            }
        }
    }
    if (showDialog) {
        NumericValueDialog(
            title = "Change value",
            currentValue = value,
            onValueChange = {
                value = it;
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
    if (showThemeDialog) {
        ThemeDialog {
            showThemeDialog = false
        }
    }
}