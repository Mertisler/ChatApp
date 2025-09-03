package com.loc.mychatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loc.mychatapp.data.model.Message
import com.loc.mychatapp.data.repository.ChatRepository

class ChatViewModel(private val repo: ChatRepository) : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    fun startConversation(userA: String, userB: String) {
        repo.listenMessages(userA, userB) { list ->
            _messages.postValue(list)
        }
    }

    fun sendMessage(fromId: String, toId: String, text: String) {
        repo.sendMessage(fromId, toId, text)
    }
}
