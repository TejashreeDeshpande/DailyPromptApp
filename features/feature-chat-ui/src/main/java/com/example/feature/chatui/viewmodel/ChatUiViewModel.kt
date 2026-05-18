package com.example.feature.chatui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature.chatui.model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatUiViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Chat>>(emptyList())
    val messages: StateFlow<List<Chat>> = _messages

    fun send(message: String) {
        _messages.value += Chat(userName = "Me", message = "Me: $message")
        _messages.value += Chat(userName = "You", message = "You: $message")
    }
}
