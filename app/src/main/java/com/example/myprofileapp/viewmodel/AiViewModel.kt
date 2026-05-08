package com.example.myprofileapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.remote.AiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
    val role: String, // "user" or "model"
    val text: String
)

sealed interface AiUiState {
    object Idle : AiUiState
    object Loading : AiUiState
    data class Success(val response: String) : AiUiState
    data class Error(val message: String) : AiUiState
}

class AiViewModel(private val aiService: AiService) : ViewModel() {
    
    private val _summaryState = MutableStateFlow<AiUiState>(AiUiState.Idle)
    val summaryState: StateFlow<AiUiState> = _summaryState.asStateFlow()

    private val _chatMessages = mutableStateListOf<ChatMessage>()
    val chatMessages: List<ChatMessage> = _chatMessages

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    fun summarizeNote(title: String, content: String) {
        viewModelScope.launch {
            _summaryState.value = AiUiState.Loading
            val result = aiService.summarizeNote(title, content)
            if (result.startsWith("Error:")) {
                _summaryState.value = AiUiState.Error(result)
            } else {
                _summaryState.value = AiUiState.Success(result)
            }
        }
    }

    fun sendMessage(message: String) {
        if (message.isBlank()) return
        
        viewModelScope.launch {
            _chatMessages.add(ChatMessage("user", message))
            _isChatLoading.value = true
            
            val history = _chatMessages.dropLast(1).map { it.role to it.text }
            var aiResponse = ""
            
            _chatMessages.add(ChatMessage("model", ""))
            val lastIndex = _chatMessages.lastIndex
            
            aiService.chatWithAi(history, message).collect { chunk ->
                aiResponse += chunk
                _chatMessages[lastIndex] = ChatMessage("model", aiResponse)
            }
            
            _isChatLoading.value = false
        }
    }

    fun clearSummary() {
        _summaryState.value = AiUiState.Idle
    }
}
