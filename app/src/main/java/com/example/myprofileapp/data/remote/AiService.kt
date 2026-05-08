package com.example.myprofileapp.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AiService {
    suspend fun summarizeNote(title: String, content: String): String
    suspend fun chatWithAi(history: List<Pair<String, String>>, message: String): Flow<String>
}

class GeminiAiService(apiKey: String) : AiService {
    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey,
        systemInstruction = content { 
            text("Anda adalah asisten cerdas untuk aplikasi catatan 'My Notes App'. " +
                 "Tugas Anda adalah membantu pengguna mengelola, meringkas, dan menjawab pertanyaan seputar catatan mereka. " +
                 "Berikan jawaban yang ringkas, membantu, dan dalam Bahasa Indonesia.") 
        }
    )

    override suspend fun summarizeNote(title: String, content: String): String {
        val prompt = "Ringkaslah catatan berikut ini yang berjudul '$title':\n\n$content"
        return try {
            val response = model.generateContent(prompt)
            response.text ?: "Gagal membuat ringkasan."
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }

    override suspend fun chatWithAi(history: List<Pair<String, String>>, message: String): Flow<String> = flow {
        val chat = model.startChat(
            history = history.map { (role, text) ->
                content(role) { text(text) }
            }
        )
        try {
            chat.sendMessageStream(message).collect { chunk ->
                chunk.text?.let { emit(it) }
            }
        } catch (e: Exception) {
            emit("Error: ${e.localizedMessage}")
        }
    }
}
