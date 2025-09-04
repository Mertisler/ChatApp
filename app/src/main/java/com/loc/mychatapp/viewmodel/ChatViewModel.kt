package com.loc.mychatapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.mychatapp.data.model.Message
import com.loc.mychatapp.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val repo: ChatRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    // Mesaj dinlemeyi başlat
    fun startConversation(userA: String, userB: String) {
        viewModelScope.launch {
            // callback'i Flow'a dönüştür
            repo.listenMessages(userA, userB) { list ->
                _messages.value = list
            }
        }
    }

    // Mesaj gönderme
    fun sendMessage(fromId: String, toId: String, text: String) {
        viewModelScope.launch {
            repo.sendMessage(fromId, toId, text)
        }
    }
}
