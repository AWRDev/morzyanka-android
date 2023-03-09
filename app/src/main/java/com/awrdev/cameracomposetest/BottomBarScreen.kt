package com.awrdev.cameracomposetest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Decoder: BottomBarScreen(
        route = "decoder",
        title = "Decoder",
        icon = Icons.Default.Search
    )
    object Chat: BottomBarScreen(
        route = "chat",
        title = "Chat",
        icon = Icons.Default.MailOutline
    )
    object Settings: BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}
