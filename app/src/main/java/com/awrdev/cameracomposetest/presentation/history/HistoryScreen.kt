package com.awrdev.cameracomposetest.presentation.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.awrdev.cameracomposetest.R
import com.awrdev.cameracomposetest.presentation.history.components.Chat
import com.awrdev.cameracomposetest.presentation.history.components.ChatItem

@Composable
fun HistoryScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier.fillMaxSize()){
        ChatItem(chat = Chat())
        ChatItem(chat = Chat())
        ChatItem(chat = Chat())
        ChatItem(chat = Chat())

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
                    painter = painterResource(R.drawable.under_construction_2),
                    contentDescription = "Under construction",
                    modifier = Modifier.size(128.dp)
                )
                Text("Under construction")
            }
        }

    }
}