package com.awrdev.cameracomposetest.presentation.history.components

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.awrdev.cameracomposetest.R

data class Chat(
    val name: String = "Test chat",
    val image_id: Int = R.drawable.baseline_chat_icon_24,
    val lastMessage: String = "Blah blah",
    val lastMessageTime: String = "12:04"
)