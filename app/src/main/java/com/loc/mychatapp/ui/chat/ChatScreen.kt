package com.loc.mychatapp.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.loc.mychatapp.data.model.User
import com.loc.mychatapp.viewmodel.ChatViewModel
import com.loc.mychatapp.viewmodel.UserViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    currentUserId: String,
    otherUserId: String
) {
    var text by remember { mutableStateOf("") }

    val messages by chatViewModel.messages.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { chatViewModel.startConversation(currentUserId, otherUserId) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                val isMe = message.fromId == currentUserId
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        message.text,
                        modifier = Modifier
                            .background(
                                if (isMe) Color(0xFF0D47A1) else Color.Gray,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        color = Color.White
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Mesaj yaz...") }
            )
            Button(onClick = {
                if (text.isNotBlank()) {
                    chatViewModel.sendMessage(currentUserId, otherUserId, text)
                    text = ""
                }
            }) { Text("Gönder") }
        }
    }
}
